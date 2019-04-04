package com.example.kamil.androidsoapclient2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kamil.androidsoapclient2.xmlSoapRequests.AddMessageXmlSoapRequest;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SoapCaller soapCaller = new SoapCaller();
//        soapCaller.execute();
        XmlParser xmlParser = new XmlParser();
        xmlParser.doSomeXmlParsing();
    }

    private class SoapCaller extends AsyncTask {

        //hl a21aa656.ngrok.io
        @Override
        protected Object doInBackground(Object[] params) {

            try {
                URL url = new URL("http://a21aa656.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
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

            return null;
        }
    }

    private class XmlParser{
        public void doSomeXmlParsing(){
            Serializer serializer = new Persister();
            AddMessageXmlSoapRequest addMessageXmlSoapRequest = new AddMessageXmlSoapRequest("Kot", new Date(), 1, "Message from Android !");
            StringWriter stringWriter = new StringWriter();
            try {
                serializer.write(addMessageXmlSoapRequest, stringWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String result = stringWriter.toString();
            Log.e("",result);
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
