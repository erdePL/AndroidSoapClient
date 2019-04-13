package com.example.kamil.androidsoapclient2.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.kamil.androidsoapclient2.R;
import com.example.kamil.androidsoapclient2.outputFormatting.XmlPrettyFormatter;
import com.example.kamil.androidsoapclient2.requestBuilding.builder.SoapRequestCreator;
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

        //hl execute doInBackground() of SoapCaller
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.execute();

        //hl sets textview content
//        textView.setText("some text");
    }

    private class SoapCaller extends AsyncTask {
        String result;

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                URL url = new URL("http://d9b9c5c4.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                //hl working soap requests
                SoapRequestCreator soapRequestCreator = new SoapRequestCreator();
//                String soapEnvelope = soapRequestCreator.returnGetAllMessagesRequest();
//                String soapEnvelope = soapRequestCreator.returnGetMessageRequest(12);
                String soapEnvelope = soapRequestCreator.returnAddMessageRequest("Miau from android", "El Kocurro");
//                String soapEnvelope = soapRequestCreator.returnUpdateMessageRequest(15,"Miau from android EDITED !", "El Kocurro");
//                String soapEnvelope = soapRequestCreator.returnRemoveMessageRequest(12);
//                String soapEnvelope = soapRequestCreator.returnRemoveAllMessagesRequest();

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(soapEnvelope);
                out.flush();
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                result = response.toString();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            textView.setText(XmlPrettyFormatter.prettyFormat(result, 2));
        }
    }
}
