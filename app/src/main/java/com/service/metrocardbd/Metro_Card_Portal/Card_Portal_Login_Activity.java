package com.service.metrocardbd.Metro_Card_Portal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.service.metrocardbd.APIs;
import com.service.metrocardbd.ForgotPIN.Forgot_PIN_getPhone;
import com.service.metrocardbd.R;
import com.service.metrocardbd.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card_Portal_Login_Activity extends AppCompatActivity {

    AppCompatButton Metro_Card_Portal_Login_Btn;
    AppCompatEditText login_card_no, login_pin;
    private static final String URL_CHECK_LOGIN = APIs.getUrlCheckLogin();
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    TextView forgot_pin_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_portal_login);

        Metro_Card_Portal_Login_Btn = findViewById(R.id.metro_card_portal_login_btn);
        login_card_no = findViewById(R.id.login_card_no);
        login_pin = findViewById(R.id.login_pin);
        forgot_pin_btn = findViewById(R.id.forgot_pin_btn);

        sessionManager = new SessionManager(this);
        if (sessionManager.is_Logged_IN()) {
            go_To_Metro_Card_Portal();
        }
        /*sessionManager.is_Logged_IN();*/

        progressDialog = new ProgressDialog(Card_Portal_Login_Activity.this);
        progressDialog.setMessage("Checking Login...");
        progressDialog.setCancelable(false);

        Metro_Card_Portal_Login_Btn.setOnClickListener(v -> {

            String login_Card_Number = Objects.requireNonNull(login_card_no.getText()).toString();
            String login_Pin = Objects.requireNonNull(login_pin.getText()).toString();

            if (checkLoginDetails(login_Card_Number, login_Pin)) {
                checkLogin(login_Card_Number, login_Pin);
                //Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            }

        });


        forgot_pin_btn.setOnClickListener(v -> startActivity(new Intent(Card_Portal_Login_Activity.this, Forgot_PIN_getPhone.class)));

    }

    private boolean checkLoginDetails(String login_card_number, String login_pin) {

        if (TextUtils.isEmpty(login_card_number)) {
            Toast.makeText(this, "Please Input Card Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(login_pin)) {
            Toast.makeText(this, "Please Input PIN", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void checkLogin(String login_card_number, String login_pin) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_LOGIN,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("status");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        if (success.equals("1")) {
                            progressDialog.dismiss();

                            String card_user_id = jsonObject.getString("card_user_id");
                            String account_no = jsonObject.getString("account_no");
                            String name = jsonObject.getString("name");
                            String phone = jsonObject.getString("phone");

                            sessionManager.Create_Session(name, card_user_id, account_no, login_card_number, phone);
                            go_To_Metro_Card_Portal();

                            Toast.makeText(Card_Portal_Login_Activity.this, "Login Succeed. \n " + "Name :" + name + "Card User ID :" + card_user_id + " Account No :" + account_no, Toast.LENGTH_LONG).show();
                            /*onBackPressed();*/

                        } else {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("message");
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Card_Portal_Login_Activity.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            progressDialog.dismiss();
            Toast.makeText(Card_Portal_Login_Activity.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("login_card_no", login_card_number);
                params.put("login_pin", login_pin);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog.show();

    }

    private void go_To_Metro_Card_Portal() {

        Intent i = new Intent(Card_Portal_Login_Activity.this, Card_Portal_Layout_Manager.class);
        startActivity(i);
        finish();
    }


}