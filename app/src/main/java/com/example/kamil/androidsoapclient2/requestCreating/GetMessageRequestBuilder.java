package com.example.kamil.androidsoapclient2.requestCreating;

public class GetMessageRequestBuilder extends MessageServiceRequestBuilder {

private long id;

    public GetMessageRequestBuilder(long messageId) {
        this.id = messageId;
    }

    @Override
    public String generateRequest() {
        StringBuilder request = new StringBuilder();
        request.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"")
            .append(envelopeURL + "\" ")
            .append("xmlns:ns1=\"")
            .append(webServiceNamespace + "\">")
            .append("<SOAP-ENV:Body><ns1:getMessage><arg0>")
            .append(id)
            .append("</arg0></ns1:getMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        return request.toString();
    }
}
