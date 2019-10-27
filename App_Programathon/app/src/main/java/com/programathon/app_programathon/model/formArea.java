package com.programathon.app_programathon.model;

public class formArea {
    private String formId;
    private String areaId;
    private String minValue;
    private String maxValue;



    private String name;

    public formArea(String formId, String areaId, String minValue, String maxValue) {
        this.formId = formId;
        this.areaId = areaId;
        this.minValue = minValue;
        this.maxValue = maxValue;

        switch(areaId) {
            case "1":
                name = "Comunicacion";
                break;
            case "2":
                name = "Motora gruesa";
                break;
            case "3":
                name = "Motora fina";
                break;
            case "4":
                name = "Resolucion de problemas";
                break;
            case "5":
                name = "Socio-individual";
                break;
        }
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
