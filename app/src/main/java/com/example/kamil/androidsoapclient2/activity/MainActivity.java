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
    private TextView textView;
    private Button button;
    public static final String INTENT_FILTER_SOAP_CALLER = "intent-filter-soap-caller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(INTENT_FILTER_SOAP_CALLER));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent soapCallerIntent = new Intent(MainActivity.this, SoapCallerIntentService.class);
                soapCallerIntent.putExtra(SoapCallerIntentService.PARAM_IN_METHOD_ID, SoapCallerIntentService.METHOD_ID_GET_ALL_MESSAGES);
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
}