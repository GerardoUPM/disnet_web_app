package edu.upm.midas.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/visualization")
public class VisualizationController {
    @Value("${my.disnet.layers.datasource.u}")
    private String my_disnet_layers_datasource_u;

    @Value("${my.disnet.layers.datasource.p}")
    private String my_disnet_layers_datasource_p;

    @Value("${my.disnet.layers.datasource.pheno}")
    private String my_disnet_layers_datasource_pheno;

    @Value("${my.disnet.layers.datasource.bio}")
    private String my_disnet_layers_datasource_bio;

//    @Value("${my.disnet.layers.datasource.drugs}")
//    private String my_disnet_layers_datasource_drugs;

                            /* HELPER FUNCTIONS */

    private static Map<List<String>, List<String>> getIntersections(Map<String,List<String>> diseasesSymptomHM){
        Map<String, List<String>> diseasesSymptomTree = new TreeMap<>(diseasesSymptomHM);
//        System.out.println(diseasesSymptomTree);

        List<String> symptoms = new ArrayList<>();
        diseasesSymptomHM.values().forEach(symptoms::addAll);

        Set<String> symptomsSet = new HashSet<>(symptoms);
        List<String> intersectingSymptoms = symptomsSet.stream()
                .filter(symptom -> Collections.frequency(symptoms, symptom)>1)
                .collect(Collectors.toList());

        diseasesSymptomTree.forEach((key, value) -> value.retainAll(intersectingSymptoms));

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
                intersections.put(new ArrayList<>(commonDis), new ArrayList<String>(){{add(symptom);}});
            }
            commonDis.clear();
        }

        return intersections.entrySet().stream()
                .sorted(Comparator.comparingInt(e->-e.getKey().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
    }

    private static Map<List<String>, List<String>> intersectionCorrectionForMultipleLinks(Map<List<String>, List<String>> intersections){
        return intersections.entrySet().stream().filter(x -> x.getKey().size()!=1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<String, List<String>> duplicateCorrectionForMultipleLinks(Map<String, List<String>> diseasesFeature){
        return diseasesFeature.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().distinct().collect(Collectors.toList())
                )
        );

    }

    @org.jetbrains.annotations.NotNull
    private static List<String> verticalBarStringToList(@NotNull String string){
        String stringReplaced = string.replaceAll("(\\s*\\|\\s*)(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)","\\|");
        return Arrays.asList(stringReplaced.split("\\|"));
    }


    @GetMapping("/form")
    public String VisualizationForm(Model model) throws SQLException, JsonProcessingException {
        String url = this.my_disnet_layers_datasource_pheno;
        Map<String, String> sources = new HashMap<>();
        Map<String, List<Date>> dates = new HashMap<>();
        List<String> mappingSources = new ArrayList<>();

        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);

        Statement statement = conn.createStatement();

        ResultSet sourcesResultSet = statement.executeQuery("SELECT * FROM source");
        while (sourcesResultSet.next()){
            sources.put(sourcesResultSet.getString("source_id"),sourcesResultSet.getString("name"));
        }
        for (Map.Entry<String, String> source:sources.entrySet()) {
            ResultSet datesResultSet = statement.executeQuery("SELECT DISTINCT(date) FROM has_source WHERE source_id = '"+source.getKey()+"'"); //prepare statement
            List<Date> dateList = new ArrayList<>();
            while (datesResultSet.next()){
                dateList.add(datesResultSet.getDate("date"));
            }
            dates.put(source.getKey(), dateList);
        }
        ResultSet mappingSourcesResultSet = statement.executeQuery("SELECT DISTINCT source FROM layersmappings;");
        while (mappingSourcesResultSet.next()){
            mappingSources.add(mappingSourcesResultSet.getString("source"));
        }
        String datesJson = new ObjectMapper().writeValueAsString(dates);
        model.addAttribute("sources", sources);
        model.addAttribute("dates", datesJson);
        model.addAttribute("mappingSources", mappingSources);

        conn.close();
        return "visualization/forms/form";
    }

    @GetMapping("/diseases-by-symptom")
    public String DiseasesBySymptom(Model model, @RequestParam String symptoms) throws SQLException {
        String url = this.my_disnet_layers_datasource_pheno;
        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);

        List<String> symptomList = verticalBarStringToList(symptoms);

        StringBuilder builder = new StringBuilder();
        for( int i = 0 ; i < symptomList.size(); i++ ) {
            builder.append("?,");
        }

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT sym.name symptom_name, sym.cui symptom_id, d.name disease_name, d.disease_id disease_id FROM disease d   " +
//                "        # DOCUMENT   " +
                "        INNER JOIN has_disease hd on d.disease_id = hd.disease_id   " +
                "        INNER JOIN document doc on hd.document_id = doc.document_id and hd.date = doc.date   " +
//                "        # TEXT   " +
                "        INNER JOIN has_section hsec on doc.document_id = hsec.document_id and doc.date = hsec.date   " +
                "        INNER JOIN has_text ht on hsec.document_id = ht.document_id and hsec.date = ht.date and hsec.section_id = ht.section_id   " +
//                "        # SYMPTOMS   " +
                "        INNER JOIN has_symptom hsym on ht.text_id = hsym.text_id   " +
                "        INNER JOIN symptom sym on hsym.cui = sym.cui   " +
                "    WHERE d.relevant = 1   " +
                "    AND hsym.validated = 1" +
                "  AND sym.name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ") " +
                "GROUP BY sym.name, d.name");


        int index = 1;
        for (String symptom : symptomList){
            preparedStatement.setObject(index++, symptom);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        ObjectMapper mapper = new ObjectMapper();
        // D3 DATA
        ArrayNode linksArrayNode = mapper.createArrayNode();
        ArrayNode nodesArrayNode = mapper.createArrayNode();
        // TABLE DATA
        Map<String, List<String>> diseasesFeatureHM = new HashMap<>();
        Map<String, List<String>> diseasesFeature = new HashMap<>(); //TODO: rewatch this
        Map<String, HashMap<String, Object>> symptomIdDiseases = new HashMap<>();
        List<String> features = new ArrayList<>();
        String disease = "";
        String diseaseId = "";
        int count = 0;

        while (resultSet.next()){
            ObjectNode link = mapper.createObjectNode();
            ObjectNode featureNode = mapper.createObjectNode();
            String featureId =  resultSet.getString("disease_id");
            String feature =  resultSet.getString("disease_name");

            if (!disease.equals(resultSet.getString("symptom_name"))){
                if (count==0){
                    disease = resultSet.getString("symptom_name");
                    diseaseId = resultSet.getString("symptom_id");
                    count++;
                }else{
                    diseasesFeatureHM.put(disease, new ArrayList<>(features));
                    diseasesFeature.put(disease, new ArrayList<>(features));
                    String finalDisease = disease;
                    symptomIdDiseases.put(
                            diseaseId, new HashMap<String, Object>(){{
                                put("name", finalDisease);
                                put("diseases", new ArrayList<>(features));
                            }}
                    );
//                    System.out.println("symptom id diseases");
//                    System.out.println(symptomIdDiseases);
                    features.clear();
                    disease = resultSet.getString("symptom_name");
                    diseaseId = resultSet.getString("symptom_id");
                }
                ObjectNode diseaseNode = mapper.createObjectNode();
                diseaseNode.put("id", diseaseId);
                diseaseNode.put("type", "disease");
                diseaseNode.put("name", disease);
                nodesArrayNode.add(diseaseNode);
            }
            features.add(feature);
            featureNode.put("id", featureId);
            featureNode.put("type", "feature");
            featureNode.put("name", feature);
            nodesArrayNode.add(featureNode);
            link.put("source", diseaseId);
            link.put("target", featureId);
            linksArrayNode.add(link);
        }
        diseasesFeatureHM.put(disease, new ArrayList<>(features));
        diseasesFeature.put(disease, new ArrayList<>(features));
        model.addAttribute("diseasesFeature", diseasesFeature);

        String finalDisease1 = disease;
        symptomIdDiseases.put(
                diseaseId, new HashMap<String, Object>(){{
                    put("name", finalDisease1);
                    put("diseases", new ArrayList<>(features));
                }}
        );
        model.addAttribute("symptomIdDiseases", symptomIdDiseases);

        List<String> diseasesWithFeatures = new ArrayList<>(diseasesFeature.keySet());
        List<String> diseasesWithoutFeatures = new ArrayList<>(CollectionUtils.subtract(symptomList,diseasesWithFeatures));
        boolean emptySVG = diseasesWithoutFeatures.size() == symptomList.size() || symptoms.isEmpty();
        model.addAttribute("emptySVG", emptySVG);
        model.addAttribute("diseasesWithoutFeatures",
                diseasesWithoutFeatures.stream().map(d->"<b>"+d+"</b>").collect(Collectors.joining(", ")));

        Map<List<String>, List<String>> intersections = getIntersections(diseasesFeatureHM);

        AtomicInteger i= new AtomicInteger(0);
        Map<Integer, List<String>> intersectionsToJson = intersections.entrySet().stream()
                .collect(Collectors.toMap(x -> i.getAndIncrement(), Map.Entry::getValue));

        model.addAttribute("type", "disease");

        try {
            String preNodes = new ObjectMapper().writeValueAsString(mapper.createObjectNode().set("preNodes", nodesArrayNode));
            String preLinks = new ObjectMapper().writeValueAsString(mapper.createObjectNode().set("preLinks", linksArrayNode));
            String intersectionsJson = new ObjectMapper().writeValueAsString(intersectionsToJson);
            model.addAttribute("intersections", intersections);
            model.addAttribute("intersectionsJson", intersectionsJson);
            model.addAttribute("preNodes", preNodes);
            model.addAttribute("preLinks", preLinks);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        conn.close();
        return "visualization/graphs/common-diseases";
    }

    @GetMapping("/common-nodes") //TODO NOW: GET/POST
    public String FeaturesByDisease(Model model, @RequestParam String type,
                                    @RequestParam String sourceId,
                                    @RequestParam String date,
                                    @RequestParam String diseases,
                                    @RequestParam(required = false) List<String> mapping
                                ) throws Exception {
//        System.out.println(mapping);
        if (mapping != null){
            if (mapping.isEmpty()){
                mapping = null;
            }
        }
        List<String> diseaseList = verticalBarStringToList(diseases);

        String url;
        String common;
        String commonId;
        switch (type) {
            case "symptom":
                url = this.my_disnet_layers_datasource_pheno;
                common = "symptom_name";
                commonId = "cui";
                break;
            case "gene":
            case "protein":
            case "pathway":
                url = this.my_disnet_layers_datasource_bio;
                common = "common_name"; // TODO: use same commonality names for "symptom" if possible
                commonId = "common_id";
                break;
            /*case "drugs":
                url = this.my_disnet_layers_datasource_drugs;
                common = "drug_name";
                commonId = "drug_id";
                break;*/
            default:
                return "error/404"; // TODO: revisar
        }


        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);
        StringBuilder builder = new StringBuilder();
        for( int i = 0 ; i < diseaseList.size(); i++ ) {
            builder.append("?,");
        }
        StringBuilder mappingBuilder = new StringBuilder();
        if (mapping != null){
            for ( int i = 0; i<mapping.size();i++){
                mappingBuilder.append("?,");
            }
        }
        int index = 1;

        PreparedStatement preparedStatement;
        switch (type) {
            case "symptom":
                preparedStatement = conn.prepareStatement("SELECT DISTINCTROW d.disease_id, d.name disease_name, sym.name symptom_name, hsym.cui cui " +
                        "FROM disease d " +
                        // document
                        "       INNER JOIN has_disease hd ON hd.disease_id = d.disease_id " +
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
                        "  AND d.relevant = 1 " +
                        "  AND hsym.validated = 1 " +
                        "  AND d.name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ") " +
                        "ORDER BY disease_name, symptom_name");

                // setting date and source
                preparedStatement.setString(index++, sourceId);
                preparedStatement.setString(index++, date);

                break;
            case "gene":
                preparedStatement = conn.prepareStatement("SELECT e.name disease_name,   " +
                        "       e.disnet_id              disease_id, " +
                        "       gene_name                common_name,   " +
                        "       CONCAT('id', dg.gene_id) common_id,   " +
                        "       dg.score score, " +
                        "       e.source, e.vocabulary, " +
                        "       d.disease_name mapped_disease " +
                        "FROM gene " +
                        "         JOIN disease_gene dg on gene.gene_id = dg.gene_id   " +
                        "         JOIN disease d on dg.disease_id = d.disease_id   " +
                        "         JOIN (   " +
                        "       SELECT edsssdb.layersmappings.cui, ed.name, edsssdb.layersmappings.disnet_id, edsssdb.layersmappings.source, edsssdb.layersmappings.vocabulary " +
                        "       FROM edsssdb.layersmappings   " +
                        "             JOIN edsssdb.disease ed on layersmappings.disnet_id = ed.disease_id   " +
                        (mapping==null ? "" : "WHERE edsssdb.layersmappings.source IN (" + mappingBuilder.deleteCharAt(mappingBuilder.length() - 1).toString() + ") ") +
                        "                         ) e on d.disease_id = e.cui   " +
                        "                         WHERE e.name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ") " +
                        " GROUP BY  e.disnet_id, gene_name, dg.score, e.source, e.vocabulary, d.disease_name");
//                System.out.println(preparedStatement);

                break;
            case "protein":
                preparedStatement = conn.prepareStatement("SELECT e.name                      disease_name, " +
                        "       e.disnet_id                 disease_id, " +
                        "       e2.protein_id               common_name, " +
                        "       CONCAT('id', e2.protein_id) common_id, " +
                        "       dg.score                    score, " +
                        "       e.source, " +
                        "       e.vocabulary, " +
                        "       d.disease_name              mapped_disease " +
                        "FROM protein " +
                        "         JOIN encodes e2 on protein.protein_id = e2.protein_id " +
                        "         JOIN gene g on e2.gene_id = g.gene_id " +
                        "         JOIN disease_gene dg on g.gene_id = dg.gene_id " +
                        "         JOIN disease d on dg.disease_id = d.disease_id " +
                        "         JOIN ( " +
                        "    SELECT edsssdb.layersmappings.cui, " +
                        "           ed.name, " +
                        "           edsssdb.layersmappings.disnet_id, " +
                        "           edsssdb.layersmappings.source, " +
                        "           edsssdb.layersmappings.vocabulary " +
                        "    FROM edsssdb.layersmappings " +
                        "             JOIN edsssdb.disease ed on layersmappings.disnet_id = ed.disease_id " +
                        (mapping==null ? "" : "WHERE edsssdb.layersmappings.source IN (" + mappingBuilder.deleteCharAt(mappingBuilder.length() - 1).toString() + ") ") +

                        ") e on d.disease_id = e.cui " +
                        "WHERE e.name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ") " +
                        "GROUP BY e.disnet_id, e2.protein_id, dg.score, e.source, e.vocabulary, d.disease_name");
                break;

            case "pathway":
                preparedStatement = conn.prepareStatement("SELECT e.name                           disease_name, " +
                        "       e.disnet_id                      disease_id, " +
                        "       pathway_name                     common_name, " +
                        "       CONCAT('id', pathway.pathway_id) common_id, " +
                        "       e.source, " +
                        "       e.vocabulary, " +
                        "       d.disease_name                   mapped_disease " +
                        "FROM pathway " +
                        "         JOIN gene_pathway gp on pathway.pathway_id = gp.pathway_id " +
                        "         JOIN gene g on gp.gene_id = g.gene_id " +
                        "         JOIN disease_gene dg on g.gene_id = dg.gene_id " +
                        "         JOIN disease d on dg.disease_id = d.disease_id " +
                        "         JOIN ( " +
                        "    SELECT edsssdb.layersmappings.cui, " +
                        "           ed.name, " +
                        "           edsssdb.layersmappings.disnet_id, " +
                        "           edsssdb.layersmappings.source, " +
                        "           edsssdb.layersmappings.vocabulary " +
                        "    FROM edsssdb.layersmappings " +
                        "             JOIN edsssdb.disease ed on layersmappings.disnet_id = ed.disease_id " +
                        (mapping==null ? "" : "WHERE edsssdb.layersmappings.source IN (" + mappingBuilder.deleteCharAt(mappingBuilder.length() - 1).toString() + ") ") +
                        ") e on d.disease_id = e.cui " +
                        "WHERE e.name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ") " +
                        "GROUP BY e.disnet_id, pathway_name, e.source, e.vocabulary, d.disease_name;");
                break;
        /*    case "drugs":
                preparedStatement = conn.prepareStatement("SELECT disease_name, CONCAT('d',disease.disease_id) disease_id, drug_name, d.drug_id FROM disease " +
                        "    JOIN drug_disease dd on disease.disease_id = dd.disease_id " +
                        "    JOIN drug d on dd.drug_id = d.drug_id " +
                        "WHERE disease_name IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ")");
                System.out.println(preparedStatement);
                break; */ //TODO: uncomment in new update
            default:
                return "error/404";
//
        }
//        System.out.println(preparedStatement);

        //setting the list of mappingSources
        if (mapping!=null){
            for (String mappingSource : mapping){
                assert preparedStatement != null;
                preparedStatement.setObject(index++,mappingSource);
            }
        }

        //setting the list of diseases
        for (String disease : diseaseList){
            assert preparedStatement != null;
            preparedStatement.setObject(index++, disease);
        }

        assert preparedStatement != null;

        ResultSet resultSet = preparedStatement.executeQuery();
        ObjectMapper mapper = new ObjectMapper();
        // D3 DATA
        ArrayNode linksArrayNode = mapper.createArrayNode();
        ArrayNode nodesArrayNode = mapper.createArrayNode();
        // TABLE DATA
        Map<String, List<String>> diseasesFeatureHM = new HashMap<>();
        Map<String, List<String>> diseasesFeature = new HashMap<>(); //TODO: rewatch this
        List<String> features = new ArrayList<>();
        String disease = "";
        String diseaseId = "";
        String score = "";
        String source = "";
        String vocabulary = "";
        String mappedDisease = "";
        int count = 0;

        while (resultSet.next()){
            ObjectNode link = mapper.createObjectNode();
            ObjectNode featureNode = mapper.createObjectNode();
            String featureId =  resultSet.getString(commonId);
            String feature =  resultSet.getString(common);
            if (!type.equals("symptom")){ /*|| type.equals("pathway")*/
                if (!type.equals("pathway")){
                    score = String.format("%.2f", resultSet.getDouble("score"));
                }
                source = resultSet.getString("source");
                vocabulary = resultSet.getString("vocabulary");
                mappedDisease = resultSet.getString("mapped_disease");
            }
            if (!disease.equals(resultSet.getString("disease_name"))){
                if (count==0){
                    disease = resultSet.getString("disease_name");
                    diseaseId = resultSet.getString("disease_id");
                    count++;
                }else{
                    diseasesFeatureHM.put(disease, new ArrayList<>(features));
                    diseasesFeature.put(disease, new ArrayList<>(features));

                    features.clear();
                    disease = resultSet.getString("disease_name");
                    diseaseId = resultSet.getString("disease_id");
                }
                ObjectNode diseaseNode = mapper.createObjectNode();
                diseaseNode.put("id", diseaseId);
                diseaseNode.put("type", "disease");
                diseaseNode.put("name", disease);
                nodesArrayNode.add(diseaseNode);
            }
            features.add(feature);
            featureNode.put("id", featureId);
            featureNode.put("type", "feature");
            featureNode.put("name", feature);
            nodesArrayNode.add(featureNode);
            link.put("source", diseaseId);
            link.put("target", featureId);
            if (!type.equals("symptom")){ /*|| type.equals("pathway")*/
                if (!type.equals("pathway")){
                    link.put("value", score);
                }
                link.put("mappingSource", source);
                link.put("mappingVocabulary", vocabulary);
                link.put("mappedDisease", mappedDisease);
            }
            linksArrayNode.add(link);
        }
        diseasesFeatureHM.put(disease, new ArrayList<>(features));
        diseasesFeature.put(disease, new ArrayList<>(features));
        if (!type.equals("symptom")){
            diseasesFeature = duplicateCorrectionForMultipleLinks(diseasesFeature);
        }
        model.addAttribute("diseasesFeature", diseasesFeature);

        List<String> diseasesWithFeatures = new ArrayList<>(diseasesFeature.keySet());
        List<String> diseasesWithoutFeatures = new ArrayList<>(CollectionUtils.subtract(diseaseList,diseasesWithFeatures));
        boolean emptySVG = diseasesWithoutFeatures.size() == diseaseList.size() || diseases.isEmpty();
        model.addAttribute("emptySVG", emptySVG);
        model.addAttribute("diseasesWithoutFeatures",
                diseasesWithoutFeatures.stream().map(d->"<b>"+d+"</b>").collect(Collectors.joining(", ")));

        Map<List<String>, List<String>> intersections = getIntersections(diseasesFeatureHM);
        if (!type.equals("symptom")){
            intersections =intersectionCorrectionForMultipleLinks(intersections);
        }
//        System.out.println(intersections);

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

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        conn.close();
        return "visualization/graphs/common-nodes";
    }


    // Find diseases having a specific symptom/gene/protein/pathway
    @GetMapping("/ajax-get-diseases")
    public String AjaxSearchBySymptom(Model model, @RequestParam String type, @RequestParam String id) throws SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        switch (type) {
            case "symptom":
                conn = DriverManager.getConnection(this.my_disnet_layers_datasource_pheno, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);

                preparedStatement = conn.prepareStatement("SELECT DISTINCT d.name disease_name " +
                        "                        FROM disease d " +
                        // document
                        "                               INNER JOIN has_disease hd ON hd.disease_id = d.disease_id " +
                        "                               INNER JOIN document doc ON doc.document_id = hd.document_id AND doc.date = hd.date " +
                        // source
                        "                               INNER JOIN has_source hs ON hs.document_id = doc.document_id AND hs.date = doc.date " +
                        "                               INNER JOIN source sce ON sce.source_id = hs.source_id " +
                        // texts
                        "                               INNER JOIN has_section hsec ON hsec.document_id = doc.document_id AND hsec.date = doc.date " +
                        "                               INNER JOIN has_text ht ON ht.document_id = hsec.document_id AND ht.date = hsec.date AND ht.section_id = hsec.section_id " +
                        // symptoms
                        "                               INNER JOIN has_symptom hsym ON hsym.text_id = ht.text_id " +
                        "                        WHERE " + // sce.source_id =?
                        // AND hs.date =?
                        // AND
                        "                              d.relevant = 1 " +
                        "                          AND hsym.validated = 1 " +
                        "                          AND hsym.cui =? " +
                        "                        ORDER BY disease_name;");

                //idStatement = conn.prepareStatement("SELECT symptom.cui FROM symptom WHERE symptom.name=?");

                break;
            case "gene":
                conn = DriverManager.getConnection(this.my_disnet_layers_datasource_bio, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);

                preparedStatement = conn.prepareStatement("SELECT DISTINCT e.name AS disease_name FROM disease d " +
                        "    JOIN disease_gene dg on d.disease_id = dg.disease_id " +
                        "    JOIN  ( " +
                        "        SELECT edsssdb.layersmappings.cui, ed.name, edsssdb.layersmappings.disnet_id FROM edsssdb.layersmappings " +
                        "        JOIN edsssdb.disease ed on layersmappings.disnet_id = ed.disease_id " +
                        "    ) e on d.disease_id = e.cui " +
                        "    WHERE dg.gene_id=?");
                break;
            case "protein":
                conn = DriverManager.getConnection(this.my_disnet_layers_datasource_bio, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);

                preparedStatement = conn.prepareStatement("SELECT DISTINCT e.name AS disease_name FROM disease d  " +
                        "      JOIN disease_gene dg on d.disease_id = dg.disease_id  " +
                        "      JOIN gene g on g.gene_id = dg.gene_id  " +
                        "      JOIN encodes e on g.gene_id = e.gene_id  " +
                        "      JOIN  (  " +
                        "                SELECT edsssdb.layersmappings.cui, ed.name, edsssdb.layersmappings.disnet_id FROM edsssdb.layersmappings  " +
                        "                    JOIN edsssdb.disease ed on layersmappings.disnet_id = ed.disease_id  " +
                        "            ) e on d.disease_id = e.cui  " +
                        "        WHERE e.protein_id=?");
                break;
            case "pathway":
                conn = DriverManager.getConnection(this.my_disnet_layers_datasource_bio, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);

                preparedStatement = conn.prepareStatement("SELECT DISTINCT e.name AS disease_name FROM disease d  " +
                        "         JOIN disease_gene dg on d.disease_id = dg.disease_id  " +
                        "         JOIN gene g on g.gene_id = dg.gene_id  " +
                        "         JOIN gene_pathway gp on g.gene_id = gp.gene_id  " +
                        "         JOIN  (  " +
                        "                    SELECT edsssdb.layersmappings.cui, ed.name, edsssdb.layersmappings.disnet_id FROM edsssdb.layersmappings  " +
                        "                          JOIN edsssdb.disease ed on layersmappings.disnet_id = ed.disease_id  " +
                        "                ) e on d.disease_id = e.cui  " +
                        "        WHERE gp.pathway_id=?");

                break;
            /*case "drugs":
                url = this.my_disnet_layers_datasource_drugs;
                common = "drug_name";
                commonId = "drug_id";
                break;*/
            default:
                break;
//                return "error/404"; // TODO: revisar
        }

        assert preparedStatement != null;
        preparedStatement.setString(1, id);

        ResultSet diseasesResultSet = preparedStatement.executeQuery();

        List<String> diseases = new ArrayList<>();

        while (diseasesResultSet.next()){
            diseases.add(diseasesResultSet.getString("disease_name"));
        }
        model.addAttribute("symptomId", id);
        model.addAttribute("diseases", diseases);

        conn.close();
        return "visualization/ajax/symptom-table";
    }
}

@RestController
@RequestMapping("/visualization")
class VisualizationRestController {
    @Value("${my.disnet.layers.datasource.u}")
    private String my_disnet_layers_datasource_u;

    @Value("${my.disnet.layers.datasource.p}")
    private String my_disnet_layers_datasource_p;

    @Value("${my.disnet.layers.datasource.pheno}")
    private String my_disnet_layers_datasource_pheno;

    @GetMapping("/autocompleteEndPoint")
    public Map<String, List<String>> DiseaseAutocompleteEndPoint(@RequestParam String sourceId,
                                                                 @RequestParam String date,
                                                                 @RequestParam String query) throws SQLException {
        String url = this.my_disnet_layers_datasource_pheno;
        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT DISTINCT(name) " +
                "FROM disease JOIN has_disease hd on disease.disease_id = hd.disease_id " +
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

        conn.close();
        return json;
    }

    @GetMapping("/search-by-symptom-autocompleteEndPoint")
    public Map<String, List<String>> SymptomAutocompleteEndPoint(@RequestParam String query) throws SQLException {
        String url = this.my_disnet_layers_datasource_pheno;
        Connection conn = DriverManager.getConnection(url, this.my_disnet_layers_datasource_u, this.my_disnet_layers_datasource_p);
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT DISTINCT sym.name symptom_name FROM symptom sym  " +
//                "        # TEXT  " +
                "        INNER JOIN has_symptom hs on sym.cui = hs.cui  " +
                "        INNER JOIN has_text ht on hs.text_id = ht.text_id  " +
//                "        # DOCUMENT  " +
                "        INNER JOIN has_section h on ht.document_id = h.document_id and ht.date = h.date and ht.section_id = h.section_id  " +
                "        INNER JOIN document doc on h.document_id = doc.document_id and h.date = doc.date  " +
//                "        # DISEASE  " +
                "        INNER JOIN has_disease hd on doc.document_id = hd.document_id and doc.date = hd.date  " +
                "        INNER JOIN disease dis on hd.disease_id = dis.disease_id  " +
                "    WHERE dis.relevant = 1  " +
                "    AND hs.validated = 1  " +
                "    AND sym.name LIKE ?;"); // ONLY DISPLAY SYMPTOMS THAT HAVE A DISEASE ASSOCIATED TODO: preguntar

        preparedStatement.setString(1,query+"%");

        ResultSet diseasesResultSet = preparedStatement.executeQuery();
        List<String> diseases = new ArrayList<>();

        while (diseasesResultSet.next()){
            diseases.add(diseasesResultSet.getString("symptom_name"));
        }
        Map<String, List<String>> json = new HashMap<>();
        json.put("options",diseases);

        conn.close();
        return json;
    }
}
