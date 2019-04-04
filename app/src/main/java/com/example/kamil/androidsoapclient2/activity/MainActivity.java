package com.example.kamil.androidsoapclient2.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kamil.androidsoapclient2.R;
import com.example.kamil.androidsoapclient2.outputFormatting.XmlPrettyFormatter;
import com.example.kamil.androidsoapclient2.requestCreating.AddMessageRequestBuilder;
import com.example.kamil.androidsoapclient2.requestCreating.GetAllMessagesRequestBuilder;
import com.example.kamil.androidsoapclient2.requestCreating.GetMessageRequestBuilder;
import com.example.kamil.androidsoapclient2.requestCreating.RemoveAllMessagesRequestBuilder;
import com.example.kamil.androidsoapclient2.requestCreating.RemoveMessageRequestBuilder;
import com.example.kamil.androidsoapclient2.requestCreating.UpdateMessageRequestBuilder;

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

        //hl shows builded request in text view
//        GetMessageRequestBuilder request = new GetMessageRequestBuilder(1);
//                String soapEnvelope = request.getRequest();
//                textView.setText(soapEnvelope +"\n\n"+"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:getMessage><arg0>1</arg0></ns1:getMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>");
//                textView.setText(Boolean.toString(soapEnvelope.equals("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:getMessage><arg0>1</arg0></ns1:getMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>")));
    }

    private class SoapCaller extends AsyncTask {

        String result;

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                URL url = new URL("http://fc6dfd6d.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

//                String soapEnvelope = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:getMessage><arg0>1</arg0></ns1:getMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>";
                RemoveAllMessagesRequestBuilder request = new RemoveAllMessagesRequestBuilder();
                String soapEnvelope = request.getRequest();

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
            //textView.setText(result);
        }
    }

}
/*hl all of the working methods
 private static void removeMessage() {
        try {
            URL url = new URL("http://localhost:11080/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String soapEnvelope = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:removeMessage><arg0>3</arg0></ns1:removeMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>";

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
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static void removeAllMessages() {
        try {
            URL url = new URL("http://localhost:11080/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String soapEnvelope = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:removeAllMessages/></SOAP-ENV:Body></SOAP-ENV:Envelope>";

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
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static void editMessage() {
        try {
            URL url = new URL("http://localhost:11080/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            String soapEnvelope = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:updateMessage><arg0><author>Kot</author><creationDate>2019-04-02T15:52:04.441+02:01</creationDate><id>1</id><messageContent>Miau EDITED from java simple request</messageContent></arg0></ns1:updateMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>";

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
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static void addMessageAndPrintResponse() {
        try {
            URL url = new URL("http://localhost:11080/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            String soapEnvelope = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:addMessage><arg0><author>Kot</author><creationDate>2019-04-02T15:52:04.441+02:01</creationDate><id>1</id><messageContent>Miau from simple java request</messageContent></arg0></ns1:addMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>";

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
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static void getMessageAndPrint(int messageId) {
        try {
            URL url = new URL("http://localhost:11080/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String soapEnvelope = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://webService/\"><SOAP-ENV:Body><ns1:getMessage><arg0>1</arg0></ns1:getMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>";

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
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void getAllMessagesAndPrint(){
        try {
            URL url = new URL("http://localhost:11080/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String soapEnvelope = "<v:Envelope xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\"><v:Header /><v:Body><getAllMessages xmlns=\"http://webService/\"/></v:Body></v:Envelope>";

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
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
  */
