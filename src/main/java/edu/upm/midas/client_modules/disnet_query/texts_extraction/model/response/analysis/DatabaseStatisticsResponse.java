package edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.ResponseFather;

import java.util.List;

/**
 * Created by gerardo on 16/11/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_rest
 * @className DatabaseStatisticsResponse
 * @see
 */
public class DatabaseStatisticsResponse extends ResponseFather {

    private int sourceCount;
    private List<Source> sources;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int numDiseaseSynonyms;
    private int numVocabularies;
    private List<String> vocabularies;



    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public int getSourceCount() {
        return sourceCount;
    }

    public void setSourceCount(int sourceCount) {
        this.sourceCount = sourceCount;
    }

    public int getNumVocabularies() {
        return numVocabularies;
    }

    public void setNumVocabularies(int numVocabularies) {
        this.numVocabularies = numVocabularies;
    }

    public List<String> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(List<String> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public int getNumDiseaseSynonyms() {
        return numDiseaseSynonyms;
    }

    public void setNumDiseaseSynonyms(int numDiseaseSynonyms) {
        this.numDiseaseSynonyms = numDiseaseSynonyms;
    }

    @Override
    public String toString() {
        return "DatabaseStatisticsResponse{" + "\n" +
                "   sourceCount=" + sourceCount + "\n" +
                "   , sources=" + sources + "\n" +
                "   , numVocabularies=" + numVocabularies + "\n" +
                "   , vocabularies=" + vocabularies + "\n" +
                "   , numDiseaseSynonyms=" + numDiseaseSynonyms + "\n" +
                '}';
    }
}
