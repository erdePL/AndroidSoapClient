package com.example.kamil.androidsoapclient2.callingWebService.buildingRequest.builder;

import com.example.kamil.androidsoapclient2.callingWebService.dataModel.Message;
import com.example.kamil.androidsoapclient2.callingWebService.buildingRequest.markups.Body;
import com.example.kamil.androidsoapclient2.callingWebService.buildingRequest.markups.RequestBlueprint;
import com.example.kamil.androidsoapclient2.callingWebService.buildingRequest.markups.ServiceMethod;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class SoapRequestCreator {
    private XStream xstream;
    private RequestBlueprint request;
    public SoapRequestCreator(){
        this.xstream = new XStream(new StaxDriver());
        initializeXstreamConfig();
    }
    private void initializeXstreamConfig() {
        request = new RequestBlueprint();
        xstream.alias("ENV:Envelope", RequestBlueprint.class);
        xstream.aliasField("ENV:Body", RequestBlueprint.class, "body");
        xstream.useAttributeFor(RequestBlueprint.class, "xmlnsEnvelope");
        xstream.aliasField("xmlns:ENV", RequestBlueprint.class, "xmlnsEnvelope");
        xstream.useAttributeFor(RequestBlueprint.class, "xmlnsService");
        xstream.aliasField("xmlns:ns1", RequestBlueprint.class, "xmlnsService");
        xstream.aliasField("arg0", ServiceMethod.class, "id");
        xstream.aliasField("arg0", ServiceMethod.class, "message");
    }
    public String returnRemoveAllMessagesRequest(){
        request = new RequestBlueprint();
        xstream.aliasField("ns1:removeAllMessages", Body.class, "serviceMethod");
        String stringSoapRequest = xstream.toXML(request);
        return stringSoapRequest;
    }
    public String returnGetAllMessagesRequest(){
        request = new RequestBlueprint();
        xstream.aliasField("ns1:getAllMessages", Body.class, "serviceMethod");
        String stringSoapRequest = xstream.toXML(request);
        return stringSoapRequest;
    }
    public String returnGetMessageRequest(int id){
        request = new RequestBlueprint();
        xstream.aliasField("ns1:getMessage", Body.class, "serviceMethod");
        this.request.getBody().getServiceMethod().setId(id);
        String stringSoapRequest = xstream.toXML(request);
        return stringSoapRequest;
    }
    public String returnRemoveMessageRequest(int id){
        request = new RequestBlueprint();
        xstream.aliasField("ns1:removeMessage", Body.class, "serviceMethod");
        this.request.getBody().getServiceMethod().setId(id);
        String stringSoapRequest = xstream.toXML(request);
        return stringSoapRequest;
    }
    public String returnAddMessageRequest(String messageContent, String author){
        request = new RequestBlueprint();
        xstream.aliasField("ns1:addMessage", Body.class, "serviceMethod");
        this.request.getBody().getServiceMethod().setMessage(new Message(0,messageContent,author));
        String stringSoapRequest = xstream.toXML(request);
        return stringSoapRequest;
    }
    public String returnUpdateMessageRequest(int id, String messageContent, String author){
        request = new RequestBlueprint();
        xstream.aliasField("ns1:updateMessage", Body.class, "serviceMethod");
        this.request.getBody().getServiceMethod().setMessage(new Message(id, messageContent, author));
        String stringSoapRequest = xstream.toXML(request);
        return stringSoapRequest;
    }
}