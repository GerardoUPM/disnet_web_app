package edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis;

/**
 * Created by gerardo on 16/11/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_rest
 * @className Snapshot
 * @see
 */
public class Snapshot {

    private String date;
    private int numTotDiseases;
    private int numDisease;
    private int numTotMedicalTerms;
    private int numMedicalTerms;//Validated medical terms
    private int numTotTexts;
    private int numTexts;
    private int numDiseaseWithCodes;
    private int numDiseaseCodes;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumTotDiseases() {
        return numTotDiseases;
    }

    public void setNumTotDiseases(int numTotDiseases) {
        this.numTotDiseases = numTotDiseases;
    }

    public int getNumDisease() {
        return numDisease;
    }

    public void setNumDisease(int numDisease) {
        this.numDisease = numDisease;
    }

    public int getNumTotMedicalTerms() {
        return numTotMedicalTerms;
    }

    public void setNumTotMedicalTerms(int numTotMedicalTerms) {
        this.numTotMedicalTerms = numTotMedicalTerms;
    }

    public int getNumMedicalTerms() {
        return numMedicalTerms;
    }

    public void setNumMedicalTerms(int numMedicalTerms) {
        this.numMedicalTerms = numMedicalTerms;
    }

    public int getNumTotTexts() {
        return numTotTexts;
    }

    public void setNumTotTexts(int numTotTexts) {
        this.numTotTexts = numTotTexts;
    }

    public int getNumTexts() {
        return numTexts;
    }

    public void setNumTexts(int numTexts) {
        this.numTexts = numTexts;
    }

    public int getNumDiseaseWithCodes() {
        return numDiseaseWithCodes;
    }

    public void setNumDiseaseWithCodes(int numDiseaseWithCodes) {
        this.numDiseaseWithCodes = numDiseaseWithCodes;
    }

    public int getNumDiseaseCodes() {
        return numDiseaseCodes;
    }

    public void setNumDiseaseCodes(int numDiseaseCodes) {
        this.numDiseaseCodes = numDiseaseCodes;
    }

    @Override
    public String toString() {
        return "        Snapshot{" + '\n' +
                "           date='" + date + '\n' +
                "           , numTotDiseases=" + numTotDiseases + '\n' +
                "           , numDisease=" + numDisease + '\n' +
                "           , numTotMedicalTerms=" + numTotMedicalTerms + '\n' +
                "           , numMedicalTerms=" + numMedicalTerms + '\n' +
                "           , numTotTexts=" + numTotTexts + '\n' +
                "           , numTexts=" + numTexts + '\n' +
                "           , numDiseaseWithCodes=" + numDiseaseWithCodes + '\n' +
                "           , numDiseaseCodes=" + numDiseaseCodes +
                "       }";
    }
}
