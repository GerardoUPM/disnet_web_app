package edu.upm.midas.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/visualisation")
public class VisualisationController {
    @Value("${my.disnet.layers.datasource.u}")
    private String my_disnet_layers_datasource_u;

    @Value("${my.disnet.layers.datasource.p}")
    private String my_disnet_layers_datasource_p;

    @Value("${my.disnet.layers.datasource.bio}")
    private String my_disnet_layers_datasource_bio;

    private static Map<List<String>, List<String>> getIntersections(Map<String,List<String>> diseasesSymptomHM){
        Map<String, List<String>> diseasesSymptomTree = new TreeMap<String, List<String>>(diseasesSymptomHM);

        List<String> symptoms = new ArrayList<>();
        diseasesSymptomHM.values().forEach(symptoms::addAll);

        Set<String> symptomsSet = new HashSet<String>(symptoms);
        List<String> intersectingSymptoms = symptomsSet.stream()
                .filter(symptom -> Collections.frequency(symptoms, symptom)>1)
                .collect(Collectors.toList());

        System.out.println("dstb4"+diseasesSymptomHM);
        diseasesSymptomTree.forEach((key, value) -> value.retainAll(intersectingSymptoms));
        System.out.println("dstAf"+diseasesSymptomHM);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        HashMap<List<String>, List<String>> intersections = new HashMap<>();
        List<String> commonDis = new ArrayList<>();
        for (String symptom : intersectingSymptoms){

            diseasesSymptomTree.forEach((mapEntryDisease, mapEntrySymptoms) -> {
                if (mapEntrySymptoms.contains(symptom)) {
                    commonDis.add(mapEntryDisease);
                }
            });
            if (intersections.containsKey(commonDis)){
                intersections.get(commonDis).add(symptom);
            }
            else {
                intersections.put(new ArrayList<String>(commonDis), new ArrayList<String>(){{add(symptom);}});
            }
            commonDis.clear();
        }
        System.out.println(intersections);
        return intersections.entrySet().stream()
                .sorted(Comparator.comparingInt(e->-e.getKey().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
    };


    @GetMapping("/form")
    public String VisualisationForm(Model model) throws SQLException, JsonProcessingException {
        String url = this.my_disnet_layers_datasource_bio;
        Map<String, String> sources = new HashMap<>();
        Map<String, List<Date>> dates = new HashMap<>();
//        {source_id0: [date0, date1, ..., dateN], source_id1:[date0, date1, ..., dateN],...}

        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);

        Statement statement = conn.createStatement();

        ResultSet sourcesResultSet = statement.executeQuery("SELECT * FROM source");
        while (sourcesResultSet.next()){
            sources.put(sourcesResultSet.getString("source_id"),sourcesResultSet.getString("name"));
        }
        for (Map.Entry<String, String> source:sources.entrySet()) {
            System.out.println(source.getKey());
            ResultSet datesResultSet = statement.executeQuery("SELECT DISTINCT(date) FROM has_source WHERE source_id = '"+source.getKey()+"'"); //prepare statement
            List<Date> dateList = new ArrayList<>();
            while (datesResultSet.next()){
                dateList.add(datesResultSet.getDate("date"));
            }
            dates.put(source.getKey(), dateList);
        }
        String datesJson = new ObjectMapper().writeValueAsString(dates);
        model.addAttribute("sources", sources);
        model.addAttribute("dates", datesJson);
        System.out.println(dates);


      return "visualisation/form";
    };

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/common-nodes")
    public String Visualisation(Model model, @RequestParam String type,
                                             @RequestParam String sourceId,
                                             @RequestParam String date,
                                             @RequestParam String diseaseList) throws SQLException {
        System.out.println(sourceId);
        System.out.println(date);
        System.out.println(diseaseList);
        String diseaseListReplaced = diseaseList.replaceAll("(\\s*\\|\\s*)(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)","\\|");
        List<String> diseaseListToArray = Arrays.asList(diseaseListReplaced.split("\\|"));

        String url = null;
        String common = null;
        String commonId = null;
        switch (type) {
            case "symptoms":
                url = this.my_disnet_layers_datasource_bio;
                common = "symptom_name";
                commonId = "cui";
                break;
        /*    case "genes":
                url = url + "disnet_biolayer";
                common = "gene_name";
                commonId = "gene_id";
                break;
            case "drugs":
                url = url + "disnet_drugslayer";
                common = "drug_name";
                commonId = "drug_id";
                break; */ // TODO: uncomment for new update
        }


        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);
        StringBuilder builder = new StringBuilder();
        for( int i = 0 ; i < diseaseListToArray.size(); i++ ) {
            builder.append("?,");
        }
        int index = 1;

        PreparedStatement preparedStatement = null;
        switch (type) {
            case "symptoms":
                preparedStatement = conn.prepareStatement("SELECT DISTINCTROW d.disease_id, d.name disease_name, sym.name symptom_name, hsym.cui cui " +
                        "FROM disease d " +
                        // document
                        "       INNER JOIN has_disease hd ON hd.disorder_id = d.disease_id " +
                        "       INNER JOIN document doc ON doc.document_id = hd.document_id AND doc.date = hd.date " +
                        // source
                        "       INNER JOIN has_source hs ON hs.document_id = doc.document_id AND hs.date = doc.date " +
                        "       INNER JOIN source sce ON sce.source_id = hs.source_id " +
                        // texts
                        "       INNER JOIN has_section hsec ON hsec.document_id = doc.document_id AND hsec.date = doc.date " +
                        "       INNER JOIN has_text ht ON ht.document_id = hsec.document_id AND ht.date = hsec.date AND ht.section_id = hsec.section_id " +
                        // symptoms
                        "       INNER JOIN has_symptom hsym ON hsym.text_id = ht.text_id " +
                        "       INNER JOIN symptom sym ON sym.cui = hsym.cui " +
                        "WHERE sce.source_id =?" +
                        "  AND hs.date =? " +
                        "  AND d.relevant = true " +
                        "  AND hsym.validated = true " +
                        "  AND d.name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ") " +
                        "ORDER BY disease_name, symptom_name");
                System.out.println(preparedStatement);

                // setting date and source
                preparedStatement.setString(index++, sourceId);
                preparedStatement.setString(index++, date);

                break;
        /*    case "genes":
                preparedStatement = conn.prepareStatement("SELECT disease_name, disease.disease_id, gene_name, CONCAT('g',g.gene_id) gene_id FROM disease " +
                        "    JOIN disease_gene dg on disease.disease_id = dg.disease_id " +
                        "    JOIN gene g on dg.gene_id = g.gene_id " +
                        "WHERE disease_name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ") ;");
                System.out.println(preparedStatement);

                break;
            case "drugs":
                preparedStatement = conn.prepareStatement("SELECT disease_name, CONCAT('d',disease.disease_id) disease_id, drug_name, d.drug_id FROM disease " +
                        "    JOIN drug_disease dd on disease.disease_id = dd.disease_id " +
                        "    JOIN drug d on dd.drug_id = d.drug_id " +
                        "WHERE disease_name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ")");
                System.out.println(preparedStatement);
                break; */ //TODO: uncomment in new update
        }

        //setting the list of diseases
        for (String disease : diseaseListToArray){
            assert preparedStatement != null;
            preparedStatement.setObject(index++, disease);
        }
        System.out.println(preparedStatement);


        assert preparedStatement != null;
        ResultSet resultSet = preparedStatement.executeQuery();
        ObjectMapper mapper = new ObjectMapper();
        // D3 DATA
        ArrayNode linksArrayNode = mapper.createArrayNode();
        ArrayNode nodesArrayNode = mapper.createArrayNode();
        // TABLE DATA
        Map<String, List<String>> diseasesSymptomHM = new HashMap<>();
        Map<String, List<String>> diseasesSymptom = new HashMap<>(); //TODO: rewatch this
        List<String> symptoms = new ArrayList<>();
        String disease = "";
        String diseaseId = "";
        int count = 0;

        while (resultSet.next()){
            ObjectNode link = mapper.createObjectNode();
            ObjectNode symptomNode = mapper.createObjectNode();
            String symptomId =  resultSet.getString(commonId);
            String symptom =  resultSet.getString(common);
            System.out.println(symptom);
            if (!disease.equals(resultSet.getString("disease_name"))){
                if (count==0){
                    disease = resultSet.getString("disease_name");
                    diseaseId = resultSet.getString("disease_id");
                    count++;
                }else{
                    diseasesSymptomHM.put(disease, new ArrayList<>(symptoms));
                    diseasesSymptom.put(disease, new ArrayList<>(symptoms));
                    System.out.println(diseasesSymptomHM);
                    System.out.println("");
                    System.out.println("");
                    symptoms.clear();
                    disease = resultSet.getString("disease_name");
                    diseaseId = resultSet.getString("disease_id");
                }
                ObjectNode diseaseNode = mapper.createObjectNode();
                diseaseNode.put("id", diseaseId);
                diseaseNode.put("type", "disease");
                diseaseNode.put("name", disease);
                nodesArrayNode.add(diseaseNode);
            }
            symptoms.add(symptom);
            symptomNode.put("id", symptomId);
            symptomNode.put("type", "symptom");
            symptomNode.put("name", symptom);
            nodesArrayNode.add(symptomNode);
            link.put("source", diseaseId);
            link.put("target", symptomId);
            linksArrayNode.add(link);
        }
        diseasesSymptomHM.put(disease, new ArrayList<>(symptoms));
        diseasesSymptom.put(disease, new ArrayList<>(symptoms));
        model.addAttribute("diseasesSymptom", diseasesSymptom);

        List<String> diseasesWithSymptoms = new ArrayList<>(diseasesSymptom.keySet());
        List<String> diseasesWithoutSymptoms = new ArrayList<>(CollectionUtils.subtract(diseaseListToArray,diseasesWithSymptoms));
        boolean emptySVG = diseasesWithoutSymptoms.size() == diseaseListToArray.size();
        model.addAttribute("emptySVG", emptySVG);
        model.addAttribute("diseasesWithoutSymptoms",
                diseasesWithoutSymptoms.stream().map(d->"<b>"+d+"</b>").collect(Collectors.joining(", ")));

        System.out.println("DSHM   "+diseasesSymptomHM);

        Map<List<String>, List<String>> intersections = getIntersections(diseasesSymptomHM);

        AtomicInteger i= new AtomicInteger(0);
        Map<Integer, List<String>> intersectionsToJson = intersections.entrySet().stream()
                .collect(Collectors.toMap(x -> i.getAndIncrement(), Map.Entry::getValue));

        model.addAttribute("type", type);

        try {
            String preNodes = new ObjectMapper().writeValueAsString(mapper.createObjectNode().set("preNodes", nodesArrayNode));
            String preLinks = new ObjectMapper().writeValueAsString(mapper.createObjectNode().set("preLinks", linksArrayNode));
            String intersectionsJson = new ObjectMapper().writeValueAsString(intersectionsToJson);
            model.addAttribute("intersections", intersections);
            model.addAttribute("intersectionsJson", intersectionsJson);
            model.addAttribute("preNodes", preNodes);
            model.addAttribute("preLinks", preLinks);
            System.out.println(intersections);
            System.out.println(preNodes);
            System.out.println(preLinks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "visualisation/common-nodes";
    }

}

@RestController
@RequestMapping("/visualisation")
class VisualisationHelperController {
    @Value("${my.disnet.layers.datasource.u}")
    private String my_disnet_layers_datasource_u;

    @Value("${my.disnet.layers.datasource.p}")
    private String my_disnet_layers_datasource_p;

    @Value("${my.disnet.layers.datasource.bio}")
    private String my_disnet_layers_datasource_bio;

    @GetMapping("/autocompleteEndPoint")
    public Map<String, List<String>> AutocompleteEndPoint(@RequestParam String sourceId,
                                                          @RequestParam String date,
                                                          @RequestParam String query) throws SQLException, ParseException {
        String url = this.my_disnet_layers_datasource_bio;
        System.out.println("sourceId: "+sourceId);
        Date formattedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        System.out.println("date: "+date);
        System.out.println("QUERY: "+query);
        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT DISTINCT(name) " +
                "FROM disease JOIN has_disease hd on disease.disease_id = hd.disorder_id " +
                "WHERE document_id IN ( " +
                "    SELECT DISTINCT(document_id) " +
                "    FROM has_source " +
                "    WHERE source_id=? " +
                "    ) " +
                "AND hd.date=? " +
                "AND name LIKE ?;");
        preparedStatement.setString(1,sourceId);
        preparedStatement.setString(2, date);
        preparedStatement.setString(3,query+"%");

        ResultSet diseasesResultSet = preparedStatement.executeQuery();
        List<String> diseases = new ArrayList<>();

        while (diseasesResultSet.next()){
            diseases.add(diseasesResultSet.getString("name"));
        }
        Map<String, List<String>> json = new HashMap<>();
        json.put("options",diseases);

        return json;

    }

}
