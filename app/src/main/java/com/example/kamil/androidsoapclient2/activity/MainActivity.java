package com.example.kamil.androidsoapclient2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.kamil.androidsoapclient2.R;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    public static final String INTENT_FILTER_SOAP_CALLER = "intent-filter-soap-caller";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(INTENT_FILTER_SOAP_CALLER));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent soapCallerIntent = new Intent(MainActivity.this, SomeIntentService.class);
                soapCallerIntent.putExtra(SomeIntentService.PARAM_IN_METHOD_ID, SomeIntentService.METHOD_ID_GET_ALL_MESSAGES);
                startService(soapCallerIntent);
            }
        });
//
//        SomeIntentService soapCaller = new SomeIntentService();
//        SoapRequestCreator soapRequestCreator = new SoapRequestCreator();
//        String soapEnvelope = soapRequestCreator.returnGetAllMessagesRequest();
////        String soapEnvelope = soapRequestCreator.returnGetMessageRequest(6);
////        String soapEnvelope = soapRequestCreator.returnAddMessageRequest("Miau from Android", "El Kocurro");
////        String soapEnvelope = soapRequestCreator.returnUpdateMessageRequest(9,"Miau EDITED from Android", "El Kocurro");
////        String soapEnvelope = soapRequestCreator.returnRemoveMessageRequest(8);
////        String soapEnvelope = soapRequestCreator.returnRemoveAllMessagesRequest();
//        soapCaller.execute(soapEnvelope);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(SomeIntentService.PARAM_OUT_SOAP_RESPONSE);
            textView.setText(message);
        }
    };

//    private class SomeIntentService extends AsyncTask<String, Object, String> {
//        public final static String PARAM_OUT_SOAP_RESPONSE = "soap-response";
//        private HttpURLConnection connection;
//        String result;
//        @Override
//        protected String doInBackground(String[] params) {
//            initializeConnetion();
//            sendRequest(params[0]);
//            result = getResponse();
//            return result;
//        }
//        private void initializeConnetion(){
//            try {
//                URL url = new URL("http://72249d3e.ngrok.io/SoapMessageService-1.0-SNAPSHOT/MessageService?wsdl");
//                this.connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setDoOutput(true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        private void sendRequest(String request){
//            try {
//                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                out.writeBytes(request);
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        private String getResponse(){
//            StringBuffer response = null;
//            try {
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//                response = new StringBuffer();
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return response.toString();
//        }
//        @Override
//        protected void onPostExecute(String o) {
//            super.onPostExecute(o);
//            SoapResponseParser responseParser = new SoapResponseParser();
//            Object parsedResponse =  responseParser.parseResponse(result);
//            String stringResponse = "";
//            if(parsedResponse instanceof String)
//                stringResponse = (String)parsedResponse;
//            else if (parsedResponse instanceof List)
//            {
//                StringBuilder sb = new StringBuilder();
//                for (Message messageToShow : (List<Message>)parsedResponse)
//                {
//                    sb.append(messageToShow.getId() + ": " + messageToShow.getMessageContent() + ": " + messageToShow.getAuthor() + ": " + messageToShow.getCreationDate()+"\n");
//                }
//                stringResponse = sb.toString();
//            }
//            Intent intent = new Intent(MainActivity.INTENT_FILTER_SOAP_CALLER);
//            intent.putExtra(PARAM_OUT_SOAP_RESPONSE, stringResponse );
//            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
//        }
//    }
}
