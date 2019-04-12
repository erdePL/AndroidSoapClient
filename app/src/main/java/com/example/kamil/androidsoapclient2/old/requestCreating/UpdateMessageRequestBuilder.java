package com.example.kamil.androidsoapclient2.old.requestCreating;

public class UpdateMessageRequestBuilder extends MessageServiceRequestBuilder {

    private long id;
    private String messageContent;
    private String author;

    public UpdateMessageRequestBuilder(long id, String messageContent,  String author) {
        this.id = id;
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
                .append("<SOAP-ENV:Body><ns1:updateMessage><arg0>")
                .append("<id>" + id + "</id>")
                .append("<messageContent>" + messageContent + "</messageContent>")
                .append("<creationDate>2019-04-02T15:52:04.441+02:01</creationDate>")
                .append("<author>" + author + "</author>")
                .append("</arg0></ns1:updateMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        return request.toString();
    }
}
