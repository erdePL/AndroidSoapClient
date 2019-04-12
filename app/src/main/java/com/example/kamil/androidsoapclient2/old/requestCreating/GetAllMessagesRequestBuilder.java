package com.example.kamil.androidsoapclient2.old.requestCreating;

public class GetAllMessagesRequestBuilder extends MessageServiceRequestBuilder {
    @Override
    protected String generateRequest() {
        StringBuilder request = new StringBuilder();
        request.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"")
                .append(envelopeURL + "\" ")
                .append("xmlns:ns1=\"")
                .append(webServiceNamespace + "\">")
                .append("<SOAP-ENV:Body><ns1:getAllMessages/></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        return request.toString();
    }
}
