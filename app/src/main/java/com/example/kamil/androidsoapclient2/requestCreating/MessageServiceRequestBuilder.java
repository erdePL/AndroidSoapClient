package com.example.kamil.androidsoapclient2.requestCreating;

public abstract class MessageServiceRequestBuilder {
    protected final String envelopeURL = "http://schemas.xmlsoap.org/soap/envelope/";
    protected final String webServiceNamespace= "http://webService/";
    public  String getRequest(){
        String request = generateRequest();
        return request;
    };
    protected abstract String generateRequest();
}
