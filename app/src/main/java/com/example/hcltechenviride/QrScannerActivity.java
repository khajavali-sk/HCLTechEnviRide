package com.example.hcltechenviride;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hcltechenviride.databinding.ActivityQrScannerBinding;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrScannerActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private TextView scannedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        scannedTextView = findViewById(R.id.scannedText);
        Button scanButton = findViewById(R.id.scanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQrCodeScanner();
            }
        });

        // Check and request camera permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start QR code scanning
                startQrCodeScanner();
            } else {
                // Permission denied, show a message or handle accordingly

                Toast.makeText(this, "Camera permission is required," +
                        "Please enable it in app Settings", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startQrCodeScanner() {
        // Initialize QR code scanner

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false); // Allow both portrait and landscape scanning
        integrator.setPrompt("Scan a QR code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Scan cancelled or failed
                Log.d("MainActivity", "Scan cancelled or failed");
            } else {
                // QR code scanned successfully, handle the result (e.g., display it)
                String scannedText = result.getContents();
                Log.d("MainActivity", "Scanned text: " + scannedText);
                scannedTextView.setText("Scanned text: " + scannedText);
                scannedTextView.setVisibility(View.VISIBLE); // Set the TextView visible
                Toast.makeText(this, "Scanned text: " + scannedText, Toast.LENGTH_LONG).show();

                // Call a method to request the cycle corresponding to the scanned ID
                requestCycle(scannedText);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void requestCycle(String scannedText) {
        // TODO: Implement logic to request the cycle corresponding to the scanned ID
        // This method will be called when a QR code is successfully scanned
        // You can implement the logic to handle the cycle request here
    }
}