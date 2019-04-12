package com.example.kamil.androidsoapclient2.requestBuilding.markups;

public class RequestBlueprint {
    private String xmlnsEnvelope;
    private String xmlnsService;
    private Body body;
    public RequestBlueprint() {
        this.body = new Body();
        this.xmlnsEnvelope = "http://schemas.xmlsoap.org/soap/envelope/";
        this.xmlnsService = "http://webService/";
    }

    public Body getBody() {
        return body;
    }

}
