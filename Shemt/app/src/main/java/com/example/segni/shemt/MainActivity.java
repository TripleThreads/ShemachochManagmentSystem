package com.example.segni.shemt;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this.getApplicationContext())) {

            } else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        CreateNewWifiApNetwork();
    }

    public void QRScan(View view) {
        new IntentIntegrator(MainActivity.this)
                .setCaptureActivity(QRScanActivity.class)
                .setBeepEnabled(true)
                .initiateScan();
    }

    public void CaptureImage(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    public void CreateNewWifiApNetwork() {

        ApManager ap = new ApManager(this.getApplicationContext());
        ap.createNewNetwork("SolutionBits","SolutionBits");
        ap.turnWifiApOn();
    }
}
