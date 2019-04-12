package com.example.kamil.androidsoapclient2.old.requestCreating;

public class RemoveAllMessagesRequestBuilder extends MessageServiceRequestBuilder {
    @Override
    protected String generateRequest() {
        StringBuilder request = new StringBuilder();
        request.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"")
                .append(envelopeURL + "\" ")
                .append("xmlns:ns1=\"")
                .append(webServiceNamespace + "\">")
                .append("<SOAP-ENV:Body><ns1:removeAllMessages/></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        return request.toString();
    }

//    @Override
//    protected String generateRequest() {
//        return "<Envelopexmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"><Body><removeAllMessagesxmlns=\"http://webService/\"/></Body></Envelope>";
//    }
}
