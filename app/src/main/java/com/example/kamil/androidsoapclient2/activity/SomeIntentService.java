package com.example.kamil.androidsoapclient2.activity;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.kamil.androidsoapclient2.buildingRequest.builder.SoapRequestCreator;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SomeIntentService extends IntentService {
    public final static String PARAM_OUT_SOAP_RESPONSE = "soap-response";
    public final static String PARAM_IN_METHOD_ID = "method-id";
    public final static int METHOD_ID_GET_ALL_MESSAGES = 1;
    public final static int METHOD_ID_GET_MESSAGE = 2;
    private HttpURLConnection connection;
    String soapResponse;
    SomeIntentService() {
        super("SomeIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("SDDSFSDFFSD","UGABUGA");
        initializeConnetion();
        //TODO make message Parcelabe, so it can be passed in an intent
        //TODO rewrite getExtra code, so it can get necessary data
        int serviceMethodId = intent.getIntExtra(PARAM_IN_METHOD_ID, 0);
        String soapEnvelope = generateSoapEnvelope(serviceMethodId);
        sendRequest(soapEnvelope);
        soapResponse = getResponse();
        sendServiceResponseToActivity(soapResponse);
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
    private void sendServiceResponseToActivity(String soapResponse) {
        Intent sendResponseIntent = new Intent(MainActivity.INTENT_FILTER_SOAP_CALLER);
        sendResponseIntent.putExtra(PARAM_OUT_SOAP_RESPONSE, soapResponse);
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendResponseIntent);
    }
    private void initializeConnetion() {
        try {
            URL url = new URL("http://72249d3e.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            this.connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}