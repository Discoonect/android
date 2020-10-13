package com.test.naverapi.model;

public class Results {


    String translatedText;
    String txtKor;

    public Results(){
    }

    public Results(String translatedText, String txtKor) {
        this.translatedText = translatedText;
        this.txtKor = txtKor;
    }


    public String getTxtKor() {
        return txtKor;
    }

    public void setTxtKor(String txtKor) {
        this.txtKor = txtKor;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
