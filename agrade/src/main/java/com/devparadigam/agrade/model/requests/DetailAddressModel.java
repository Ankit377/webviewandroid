package com.devparadigam.agrade.model.requests;

public class DetailAddressModel {

    private boolean individual;
    private String address;
    private String streetno;
    private String buildingname;
    private String flatno;

    public DetailAddressModel(boolean individual, String address, String streetno, String buildingname, String flatno) {
        this.individual = individual;
        this.address = address;
        this.streetno = streetno;
        this.buildingname = buildingname;
        this.flatno = flatno;
    }

    public boolean isIndividual() {
        return individual;
    }

    public void setIndividual(boolean individual) {
        this.individual = individual;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetno() {
        return streetno;
    }

    public void setStreetno(String streetno) {
        this.streetno = streetno;
    }

    public String getBuildingname() {
        return buildingname;
    }

    public void setBuildingname(String buildingname) {
        this.buildingname = buildingname;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }
}
