package com.example.kamil.androidsoapclient2.callingWebService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.kamil.androidsoapclient2.activity.MainActivity;
import com.example.kamil.androidsoapclient2.callingWebService.buildingRequest.builder.SoapRequestCreator;
import com.example.kamil.androidsoapclient2.callingWebService.dataModel.Message;
import com.example.kamil.androidsoapclient2.callingWebService.parsingResponse.SoapResponseParser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SoapCallerIntentService extends IntentService {
    public final static String PARAM_OUT_SOAP_RESPONSE = "soap-response";
    public final static String PARAM_IN_METHOD_ID = "method-id";
    public final static int METHOD_ID_GET_ALL_MESSAGES = 1;
    public final static int METHOD_ID_GET_MESSAGE = 2;
    private HttpURLConnection connection;
    public SoapCallerIntentService() {
        super("SoapCallerIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initializeConnetion();
        //TODO make message Parcelabe, so it can be passed in an intent
        //TODO rewrite getExtra code, so it can get necessary data
        int serviceMethodId = intent.getIntExtra(PARAM_IN_METHOD_ID, 0);
        String soapEnvelope = generateSoapEnvelope(serviceMethodId);
        sendRequest(soapEnvelope);
        String rawSoapResponse = getResponse();
        String parsedResponse = parseResponse(rawSoapResponse);
        sendServiceResponseToActivity(parsedResponse);
    }
    private void initializeConnetion() {
        try {
            URL url = new URL("http://56763298.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            this.connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String generateSoapEnvelope(int serviceMethodId) {
        String soapEnvelope;
        SoapRequestCreator requestCreator = new SoapRequestCreator();
        switch (serviceMethodId) {
            case METHOD_ID_GET_ALL_MESSAGES:
            default:
                soapEnvelope = requestCreator.returnGetAllMessagesRequest();
                break;
            case METHOD_ID_GET_MESSAGE:
                //TODO remove hardcoded id value
                soapEnvelope = requestCreator.returnGetMessageRequest(0);
                break;
        }
        return soapEnvelope;
    }
    private void sendRequest(String request) {
        try {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(request);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getResponse() {
        StringBuffer response = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
    private String parseResponse(String rawResponse){
            SoapResponseParser responseParser = new SoapResponseParser();
            Object parsedResponse =  responseParser.parseResponse(rawResponse);
            String stringResponse = "";
            if(parsedResponse instanceof String)
                stringResponse = (String)parsedResponse;
            else if (parsedResponse instanceof List)
            {
                StringBuilder sb = new StringBuilder();
                for (Message messageToShow : (List<Message>)parsedResponse)
                {
                    sb.append(messageToShow.getId() + ": " + messageToShow.getMessageContent() + ": " + messageToShow.getAuthor() + ": " + messageToShow.getCreationDate()+"\n");
                }
                stringResponse = sb.toString();
            }
            return stringResponse;
    }
    private void sendServiceResponseToActivity(String soapResponse) {
        Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_SOAP_CALLER);
        sendResponseIntent.putExtra(PARAM_OUT_SOAP_RESPONSE, soapResponse);
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendResponseIntent);
    }
}