package com.example.kamil.androidsoapclient2.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.kamil.androidsoapclient2.R;
import com.example.kamil.androidsoapclient2.old.outputFormatting.XmlPrettyFormatter;
import com.example.kamil.androidsoapclient2.old.requestCreating.AddMessageRequestBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        //hl call soap service with given request
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.execute();
    }

    private class SoapCaller extends AsyncTask {
        String result;

        @Override
        protected Object doInBackground(Object[] params) {
            HttpURLConnection con =null;
            try {
                URL url = new URL("http://d9b9c5c4.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AddMessageRequestBuilder request = new AddMessageRequestBuilder("Message from Android App 4", "El Kocurro");
//            RemoveAllMessagesRequestBuilder request = new RemoveAllMessagesRequestBuilder();
            String soapEnvelope = request.getRequest();
            try(    DataOutputStream out = new DataOutputStream(con.getOutputStream())  ){
                out.writeBytes(soapEnvelope);
                out.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
            String stringResponse = null;
            try(    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())) ){
                String inputLine;
                StringBuffer rawResponse = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    rawResponse.append(inputLine);
                    result = rawResponse.toString();
                    stringResponse = rawResponse.toString();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return stringResponse;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            textView.setText(XmlPrettyFormatter.prettyFormat(result, 2));
        }
    }
}