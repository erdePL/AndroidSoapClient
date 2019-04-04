package com.example.kamil.androidsoapclient2.requestCreating;

public class AddMessageRequestBuilder extends MessageServiceRequestBuilder {

    private String messageContent;
    private String author;

    public AddMessageRequestBuilder(String messageContent, String author) {
        this.messageContent = messageContent;
        this.author = author;
    }

    @Override
    protected String generateRequest() {
        StringBuilder request = new StringBuilder();
        request.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"")
                .append(envelopeURL + "\" ")
                .append("xmlns:ns1=\"")
                .append(webServiceNamespace + "\">")
                .append("<SOAP-ENV:Body><ns1:addMessage><arg0>")
                .append("<id>0</id>")
                .append("<messageContent>" + messageContent + "</messageContent>")
                .append("<creationDate>2019-04-02T15:52:04.441+02:01</creationDate>")
                .append("<author>" + author + "</author>")
                .append("</arg0></ns1:addMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        return request.toString();
    }
}
