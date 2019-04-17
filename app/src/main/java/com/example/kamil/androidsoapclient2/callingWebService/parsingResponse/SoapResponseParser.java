package com.example.kamil.androidsoapclient2.callingWebService.parsingResponse;

import com.example.kamil.androidsoapclient2.callingWebService.dataModel.Message;
import com.thoughtworks.xstream.XStream;
import java.util.ArrayList;
import java.util.List;

public class SoapResponseParser {
    public  Object parseResponse(String serviceResponse)
    {
        Object parsedResponse = new Object();
        if (serviceResponse.contains("id"))
        {
            parsedResponse = parseMessages(serviceResponse);
        } else if(serviceResponse.contains("return"))
        {
            parsedResponse = parseString(serviceResponse);
        } else
        {
            parsedResponse = "Nothing to show";
        }
        return parsedResponse;
    }
    private  List<Message> parseMessages(String serviceResponse)
    {
        XStream xStream = new XStream();
        xStream.alias("return", Message.class);
        List<Message> messagesList = new ArrayList<>();
        while (serviceResponse.contains("<return>"))
        {
            int beginIndex = serviceResponse.indexOf("<return>");
            int endIndex = serviceResponse.indexOf("</return>") + "</return>".length();
            String messageSubstring = serviceResponse.substring(beginIndex, endIndex);
            Message messageToAdd = (Message) xStream.fromXML(messageSubstring);
            messagesList.add(messageToAdd);
            serviceResponse = serviceResponse.substring(endIndex);
        }
        return messagesList;
    }
    private  String parseString(String serviceResponse)
    {
        int beginIndex = serviceResponse.indexOf("<return>") + "<return>".length();
        int endIndex = serviceResponse.indexOf("</return>");
        String messageSubstring = serviceResponse.substring(beginIndex, endIndex);
        return messageSubstring;
    }
}
