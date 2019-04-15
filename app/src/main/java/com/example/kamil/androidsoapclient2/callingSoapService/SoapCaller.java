//package com.example.kamil.androidsoapclient2.callingSoapService;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.support.v4.content.LocalBroadcastManager;
//
//import com.example.kamil.androidsoapclient2.activity.MainActivity;
//import com.example.kamil.androidsoapclient2.model.Message;
//import com.example.kamil.androidsoapclient2.parsingResponse.SoapResponseParser;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//public class SoapCaller extends AsyncTask<String, Object, String> {
//    private HttpURLConnection connection;
//    String result;
//    @Override
//    protected String doInBackground(String[] params) {
//        initializeConnetion();
//        sendRequest(params[0]);
//        result = getResponse();
//        return result;
//    }
//    private void initializeConnetion(){
//        try {
//            URL url = new URL("http://fbe47ec8.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
//            this.connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setDoOutput(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private void sendRequest(String request){
//        try {
//            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//            out.writeBytes(request);
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private String getResponse(){
//        StringBuffer response = null;
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//            response = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response.toString();
//    }
//    @Override
//    protected void onPostExecute(String o) {
//        super.onPostExecute(o);
//        SoapResponseParser responseParser = new SoapResponseParser();
//        Object parsedResponse = responseParser.parseResponse(result);
//        String stringResponse = "";
//        if(parsedResponse instanceof String)
//            stringResponse = (String)parsedResponse;
//        else if (parsedResponse instanceof List)
//        {
//            StringBuilder sb = new StringBuilder();
//            for (Message messageToShow : (List<Message>)parsedResponse)
//            {
//                sb.append(messageToShow.getId() + ": " + messageToShow.getMessageContent() + ": " + messageToShow.getAuthor() + ": " + messageToShow.getCreationDate()+"\n");
//            }
//            stringResponse = sb.toString();
//        }
//        Intent intent = new Intent("custom-event-name");
//        intent.putExtra("SoapResponse", stringResponse );
//        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
//    }
//}
