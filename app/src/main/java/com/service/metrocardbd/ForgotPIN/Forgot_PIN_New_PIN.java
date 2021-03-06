package com.service.metrocardbd.ForgotPIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.service.metrocardbd.APIs;
import com.service.metrocardbd.Metro_Card_Portal.Card_Portal_Login_Activity;
import com.service.metrocardbd.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Forgot_PIN_New_PIN extends AppCompatActivity {

    AppCompatEditText editText_new_pin;
    AppCompatButton forgot_pin_change_pin_btn;
    ProgressDialog progressDialog;
    private static  final String URL_UPDATE_CARD_PIN= APIs.getUrlUpdateCardPin();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin_new_pin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        editText_new_pin = findViewById(R.id.editText_new_pin);
        forgot_pin_change_pin_btn = findViewById(R.id.forgot_pin_change_pin_btn);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Forogt PIN");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        progressDialog = new ProgressDialog(Forgot_PIN_New_PIN.this);
        progressDialog.setMessage("Updating New Pin..");
        progressDialog.setCancelable(false);

        Bundle bundle = getIntent().getExtras();

        String card_no=null;
        if (bundle.getString("card_no") !=null) {
            card_no=bundle.getString("card_no");
        }

        String finalCard_no = card_no;
        forgot_pin_change_pin_btn.setOnClickListener(v -> {
            String new_PIN= Objects.requireNonNull(editText_new_pin.getText()).toString();

            if(new_PIN.isEmpty())
            {
                Toast.makeText(Forgot_PIN_New_PIN.this, "Please Enter New PIN", Toast.LENGTH_SHORT).show();
            }
            else
            {
                change_forgotten_pin(finalCard_no,new_PIN);
                //Toast.makeText(Forgot_PIN_New_PIN.this, "New PIN Entered SUccessfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void change_forgotten_pin(String card_no,String pin) {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_CARD_PIN,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("status");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        if (success.equals("1")) {
                            progressDialog.dismiss();
                            Intent i=new Intent(Forgot_PIN_New_PIN.this, Card_Portal_Login_Activity.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(Forgot_PIN_New_PIN.this, card_no, Toast.LENGTH_SHORT).show();

                        } else {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("message");
                            Toast.makeText(Forgot_PIN_New_PIN.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(Forgot_PIN_New_PIN.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {

            {
                progressDialog.dismiss();
                Toast.makeText(Forgot_PIN_New_PIN.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("card_no", card_no);
                params.put("new_pin", pin);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Forgot_PIN_New_PIN.this);
        requestQueue.add(stringRequest);
    }
}                                                                                                               