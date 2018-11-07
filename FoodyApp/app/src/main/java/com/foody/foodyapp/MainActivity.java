package com.foody.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    TextView result;
    Button scan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan = (Button) findViewById(R.id.scan);
        result = (TextView) findViewById(R.id.scan_result);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Vibrator vib = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                vib.vibrate(500);*/

                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator
                        .setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES)
                        .setOrientationLocked(false)
                        .setPrompt("Scan")
                        .setCameraId(0)
                        .setBeepEnabled(true)
                        .setBarcodeImageEnabled(false)
                        .initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(scanResult != null) {
            Log.i("SCAN", "scan result: " + scanResult);
            result.setText(scanResult.toString());
        } else
            Log.e("SCAN", "Scan fail");
    }



}
