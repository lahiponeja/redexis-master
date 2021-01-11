package com.liferay.gasBudget.internal.dto;

public class SelectedSolarExtrasRequest {

    private Integer extraPanels;
    private String triphasicExtra;
    private String roofExtra;
    private String pergolaExtra;
    private String pipelineUnderground;
    private Integer battery;
    private String carCharger;
    
    public Integer getExtraPanels() {
    return extraPanels;
    }
    
    public void setExtraPanels(Integer extraPanels) {
    this.extraPanels = extraPanels;
    }
    
    public String getTriphasicExtra() {
    return triphasicExtra;
    }
    
    public void setTriphasicExtra(String triphasicExtra) {
    this.triphasicExtra = triphasicExtra;
    }
    
    public String getRoofExtra() {
    return roofExtra;
    }
    
    public void setRoofExtra(String roofExtra) {
    this.roofExtra = roofExtra;
    }
    
    public String getPergolaExtra() {
    return pergolaExtra;
    }
    
    public void setPergolaExtra(String pergolaExtra) {
    this.pergolaExtra = pergolaExtra;
    }
    
    public String getPipelineUnderground() {
    return pipelineUnderground;
    }
    
    public void setPipelineUnderground(String pipelineUnderground) {
    this.pipelineUnderground = pipelineUnderground;
    }
    
    public Integer getBattery() {
    return battery;
    }
    
    public void setBattery(Integer battery) {
    this.battery = battery;
    }
    
    public String getCarCharger() {
    return carCharger;
    }
    
    public void setCarCharger(String carCharger) {
    this.carCharger = carCharger;
    }
    
}