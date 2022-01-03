package com.service.metrocardbd.Metro_Card_Portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.service.metrocardbd.R;
import com.service.metrocardbd.RegisterComplaintActivity;
import com.service.metrocardbd.SessionManager;

import java.util.HashMap;
import java.util.Objects;

public class GenerateQRCode extends AppCompatActivity {

    SessionManager sessionManager;
    AppCompatImageView QR_code_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);

        sessionManager = new SessionManager(GenerateQRCode.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        QR_code_imageView=findViewById(R.id.QR_code_imageView);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Generate QR Code");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        initQRCode();

    }

    private void initQRCode() {

        HashMap<String, String> user = sessionManager.getuserData();
        String card_no = user.get(SessionManager.CARD_NO);

        StringBuilder textToSend = new StringBuilder();
        textToSend.append(card_no);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textToSend.toString(), BarcodeFormat.QR_CODE, 800, 800);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            QR_code_imageView.setImageBitmap(bitmap);


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}

