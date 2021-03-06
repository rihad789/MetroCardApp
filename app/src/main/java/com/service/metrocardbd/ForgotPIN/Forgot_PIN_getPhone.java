package com.service.metrocardbd.ForgotPIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.service.metrocardbd.APIs;
import com.service.metrocardbd.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Forgot_PIN_getPhone extends AppCompatActivity {

    AppCompatEditText editText_card_no;
    AppCompatButton forgot_pin_continue_btn;
    private static final String URL_CARD_USER_PHONE = APIs.getUrlCardUserPhone();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin_get_phone);

        Toolbar toolbar = findViewById(R.id.toolbar);
        editText_card_no = findViewById(R.id.editText_card_no);
        forgot_pin_continue_btn = findViewById(R.id.forgot_pin_continue_btn);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Forogt PIN");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        progressDialog = new ProgressDialog(Forgot_PIN_getPhone.this);
        progressDialog.setMessage("Checking card no...");
        progressDialog.setCancelable(false);


        forgot_pin_continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String card_no = editText_card_no.getText().toString();
                if (card_no.isEmpty()) {
                    Toast.makeText(Forgot_PIN_getPhone.this, "Card No is Required", Toast.LENGTH_SHORT).show();
                } else {
                    getCardUserPhone(card_no);
                }
            }
        });

    }

    private void getCardUserPhone(String card_no) {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CARD_USER_PHONE,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("status");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        if (success.equals("1")) {
                            progressDialog.dismiss();
                            String phone = jsonObject.getString("phone");
                            Toast.makeText(Forgot_PIN_getPhone.this, phone, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Forgot_PIN_getPhone.this, Forgot_PIN_verifyPhone.class);
                            i.putExtra("phone", phone);
                            i.putExtra("card_no",card_no);
                            startActivity(i);


                        } else {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("message");
                            Toast.makeText(Forgot_PIN_getPhone.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        progressDialog.dismiss();

                        e.printStackTrace();
                        Toast.makeText(Forgot_PIN_getPhone.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {

            {
                progressDialog.dismiss();
                Toast.makeText(Forgot_PIN_getPhone.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("card_no", card_no);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Forgot_PIN_getPhone.this);
        requestQueue.add(stringRequest);
    }
}

                                                        