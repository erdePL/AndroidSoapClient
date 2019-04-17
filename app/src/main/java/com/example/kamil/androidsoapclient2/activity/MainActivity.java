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
import com.example.kamil.androidsoapclient2.callingWebService.dataModel.Message;
import com.example.kamil.androidsoapclient2.callingWebService.SoapCallerIntentService;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    public static final String INTENT_FILTER_SOAP_CALLER = "intent-filter-soap-caller";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(INTENT_FILTER_SOAP_CALLER));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent soapCallerIntent = createGetAllMessagesIntent();
//                Intent soapCallerIntent = createGetMessageIntent(1);
//                Intent soapCallerIntent = createRemoveMessageIntent(1);
//                Intent soapCallerIntent = createRemoveAllMessagesIntent();
//                Intent soapCallerIntent = createAddMessageIntent("Miau from ANDROID !", "El Kocurro");
//                Intent soapCallerIntent = createUpdateMessageIntent(5, "Miau EDITED from ANDROID !", "El Kocurro");
                startService(soapCallerIntent);
            }
        });
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(SoapCallerIntentService.PARAM_OUT_SOAP_RESPONSE);
            textView.setText(message);
        }
    };
    private Intent createGetAllMessagesIntent(){
        return new Intent(MainActivity.this, SoapCallerIntentService.class)
                .putExtra(SoapCallerIntentService.PARAM_IN_METHOD_ID, SoapCallerIntentService.METHOD_ID_GET_ALL_MESSAGES);
    }
    private Intent createGetMessageIntent(int id){
        return new Intent(MainActivity.this, SoapCallerIntentService.class)
                .putExtra(SoapCallerIntentService.PARAM_IN_METHOD_ID, SoapCallerIntentService.METHOD_ID_GET_MESSAGE)
                .putExtra(SoapCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, id);
    }
    private Intent createRemoveMessageIntent(int id){
        return new Intent(MainActivity.this, SoapCallerIntentService.class)
                .putExtra(SoapCallerIntentService.PARAM_IN_METHOD_ID, SoapCallerIntentService.METHOD_ID_REMOVE_MESSAGE)
                .putExtra(SoapCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, id);
    }
    private Intent createRemoveAllMessagesIntent(){
        return new Intent(MainActivity.this, SoapCallerIntentService.class)
                .putExtra(SoapCallerIntentService.PARAM_IN_METHOD_ID, SoapCallerIntentService.METHOD_ID_REMOVE_ALL_MESSAGES);
    }
    private Intent createAddMessageIntent(String messageContent, String author) {
        return new Intent(MainActivity.this, SoapCallerIntentService.class)
                .putExtra(SoapCallerIntentService.PARAM_IN_METHOD_ID, SoapCallerIntentService.METHOD_ID_ADD_MESSAGE)
                .putExtra(SoapCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, new Message(0, messageContent, author));
    }
    private Intent createUpdateMessageIntent(long id, String messageContent, String author) {
        return new Intent(MainActivity.this, SoapCallerIntentService.class)
                .putExtra(SoapCallerIntentService.PARAM_IN_METHOD_ID, SoapCallerIntentService.METHOD_ID_UPDATE_MESSAGE)
                .putExtra(SoapCallerIntentService.PARAM_IN_ADDITIONAL_CONTENT, new Message(id, messageContent, author));
    }
}