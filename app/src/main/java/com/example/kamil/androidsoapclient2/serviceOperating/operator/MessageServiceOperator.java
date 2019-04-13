package com.example.kamil.androidsoapclient2.serviceOperating.operator;

import android.os.AsyncTask;

import com.example.kamil.androidsoapclient2.serviceOperating.requestBuilder.builder.SoapRequestCreator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//HL EXECUTE METHODS IN ASYNC TASK
public class MessageServiceOperator extends AsyncTask<Object, Object, String>{

    private HttpURLConnection connection;

    public MessageServiceOperator() {
        initializeConnetion();
    }

    private void initializeConnetion(){
        try {
            URL url = new URL("http://d9b9c5c4.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            this.connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(String request){
        try {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(request);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getResponse(){
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

    @Override
    public String doInBackground(Object... object) {
        //hl working soap requests
        SoapRequestCreator soapRequestCreator = new SoapRequestCreator();
        String soapEnvelope = soapRequestCreator.returnGetAllMessagesRequest();
        sendRequest(soapEnvelope);
        String result = getResponse();
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
