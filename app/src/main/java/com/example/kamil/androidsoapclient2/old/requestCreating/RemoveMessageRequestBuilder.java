package com.example.kamil.androidsoapclient2.old.requestCreating;

public class RemoveMessageRequestBuilder extends MessageServiceRequestBuilder {

    private long id;

    public RemoveMessageRequestBuilder(long id) {
        this.id = id;
    }

    @Override
    protected String generateRequest() {
        StringBuilder request = new StringBuilder();
        request.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"")
                .append(envelopeURL + "\" ")
                .append("xmlns:ns1=\"")
                .append(webServiceNamespace + "\">")
                .append("<SOAP-ENV:Body><ns1:removeMessage><arg0>")
                .append(id)
                .append("</arg0></ns1:removeMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        return request.toString();
    }
}
