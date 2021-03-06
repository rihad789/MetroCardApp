package com.service.metrocardbd.ForgotPIN;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.service.metrocardbd.R;

import java.util.Objects;
import java.util.Random;

public class Forgot_PIN_verifyPhone extends AppCompatActivity {

    RadioButton numberChecked;
    TextView card_user_phone_no;
    AppCompatButton forgot_pin_send_verification_btn, forgot_pin_verify_phone_btn;
    AppCompatEditText editText_phone_verifcation_code;
    String verification_code = null;
    private static final int SEND_SMS_PERMISSION_CODE = 100;

    PendingIntent SentPI, DeliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliverReceiver;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin_verify_phone);

        Toolbar toolbar = findViewById(R.id.toolbar);
        numberChecked = findViewById(R.id.numberChecked);
        card_user_phone_no = findViewById(R.id.card_user_phone_no);
        forgot_pin_send_verification_btn = findViewById(R.id.forgot_pin_send_verification_btn);
        forgot_pin_verify_phone_btn = findViewById(R.id.forgot_pin_verify_phone_btn);
        editText_phone_verifcation_code = findViewById(R.id.editText_phone_verifcation_code);

        progressDialog = new ProgressDialog(Forgot_PIN_verifyPhone.this);
        progressDialog.setMessage("Sending Verification Code");
        progressDialog.setCancelable(false);


        numberChecked.setChecked(true);

        Bundle bundle = getIntent().getExtras();

        String phone = null, card_no = null;
        if (bundle.getString("phone") != null && bundle.getString("card_no") != null) {

            phone = bundle.getString("phone");
            card_no = bundle.getString("card_no");
            phone = "88" + phone;
            card_user_phone_no.setText(phone);
        }

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Verify Phone");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        String finalPhone = card_user_phone_no.getText().toString();
        forgot_pin_send_verification_btn.setOnClickListener(v -> {

            verification_code = generate_verification_code();
            send_verification_code(finalPhone, verification_code);
            editText_phone_verifcation_code.setText(verification_code);

        });

        String finalCard_no = card_no;
        forgot_pin_verify_phone_btn.setOnClickListener(v -> {

            String input_verification_code = Objects.requireNonNull(editText_phone_verifcation_code.getText()).toString();

            if (input_verification_code.isEmpty()) {
                Toast.makeText(Forgot_PIN_verifyPhone.this, "Verification Code Required", Toast.LENGTH_SHORT).show();
            } else {
                if (input_verification_code.equals(verification_code)) {
                    Intent i = new Intent(Forgot_PIN_verifyPhone.this, Forgot_PIN_New_PIN.class);
                    i.putExtra("card_no", finalCard_no);
                    startActivity(i);
                } else {
                    Toast.makeText(Forgot_PIN_verifyPhone.this, "Verification Code doesn't Match", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private String generate_verification_code() {
        Random r = new Random();
        int code = r.nextInt(999999 - 100000 + 1) + 100000;
        return String.valueOf(code);
    }

    public void send_verification_code(String phone, String code) {
        //Creating an Alert Dialog

        progressDialog.show();

        // Toast.makeText(Forgot_PIN_verifyPhone.this, info, Toast.LENGTH_SHORT).show();
        //Checking Message Permissions
        if (ContextCompat.checkSelfPermission(Forgot_PIN_verifyPhone.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Forgot_PIN_verifyPhone.this, new String[]{Manifest.permission.SEND_SMS}
                    , SEND_SMS_PERMISSION_CODE);

        } else {
            //Sending SOS Mesage

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, "Metro Card Verification code: " + code, SentPI, DeliveredPI);

            new Handler().postDelayed(() -> {
                // This method will be executed once the timer is over
                progressDialog.dismiss();
            }, 500);

//               progressDialog.dismiss();

        }
    }

}

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                