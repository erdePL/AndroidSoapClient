package com.example.kamil.androidsoapclient2.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.kamil.androidsoapclient2.R;
import com.example.kamil.androidsoapclient2.model.Message;
import com.example.kamil.androidsoapclient2.responseParser.SoapResponseParser;
import com.example.kamil.androidsoapclient2.requestBuilder.builder.SoapRequestCreator;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        SoapCaller soapCaller = new SoapCaller();
        SoapRequestCreator soapRequestCreator = new SoapRequestCreator();
        String soapEnvelope = soapRequestCreator.returnGetAllMessagesRequest();
//        String soapEnvelope = soapRequestCreator.returnGetMessageRequest(6);
//        String soapEnvelope = soapRequestCreator.returnAddMessageRequest("Miau from Android", "El Kocurro");
//        String soapEnvelope = soapRequestCreator.returnUpdateMessageRequest(9,"Miau EDITED from Android", "El Kocurro");
//        String soapEnvelope = soapRequestCreator.returnRemoveMessageRequest(8);
//        String soapEnvelope = soapRequestCreator.returnRemoveAllMessagesRequest();
        soapCaller.execute(soapEnvelope);
    }

    private class SoapCaller extends AsyncTask<String, Object, String> {
        private HttpURLConnection connection;
        String result;
        @Override
        protected String doInBackground(String[] params) {
            initializeConnetion();
            sendRequest(params[0]);
            result = getResponse();
            return result;
        }
        private void initializeConnetion(){
            try {
                URL url = new URL("http://fbe47ec8.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
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
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            SoapResponseParser responseParser = new SoapResponseParser();
            Object parsedResponse = responseParser.parseResponse(result);
            if(parsedResponse instanceof String)
                textView.setText((String)parsedResponse);
            else if (parsedResponse instanceof List)
            {
                StringBuilder sb = new StringBuilder();
                for (Message messageToShow : (List<Message>)parsedResponse)
                {
                    sb.append(messageToShow.getId() + ": " + messageToShow.getMessageContent() + ": " + messageToShow.getAuthor() + ": " + messageToShow.getCreationDate()+"\n");
                }
                textView.setText(sb.toString());
            }
        }
    }
}
