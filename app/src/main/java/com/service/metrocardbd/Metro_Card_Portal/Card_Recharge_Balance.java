package com.service.metrocardbd.Metro_Card_Portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.service.metrocardbd.APIs;
import com.service.metrocardbd.R;
import com.service.metrocardbd.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Card_Recharge_Balance extends AppCompatActivity {

    AppCompatButton submit_recharge_Btn;
    AppCompatEditText paymentFromNumber, rechargeAmount;

    ProgressDialog progressDialog;
    private static final String URL_RECHARGE_BALANCE = APIs.getUrlRechargeBalance();
    SessionManager sessionManager;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    AutoCompleteTextView payment_method_Auto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_balance);

        Toolbar toolbar = findViewById(R.id.toolbar);
        submit_recharge_Btn = findViewById(R.id.submit_recharge_btn);
        rechargeAmount = findViewById(R.id.input_recharge_amount);

        paymentFromNumber = findViewById(R.id.input_payment_Number);
        payment_method_Auto = findViewById(R.id.payment_method_auto);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Recharge Balance");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getuserData();
        String user_id = user.get(SessionManager.CARD_USER_ID);
        String account_no = user.get(SessionManager.ACCOUNT_NO);


        /* Implementating Progress Dialog */
        progressDialog = new ProgressDialog(Card_Recharge_Balance.this);
        progressDialog.setMessage("Payment processing..");
        progressDialog.setCancelable(false);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.complain_type_dropdown, getResources().getStringArray(R.array.payment_method));
        // get reference to the autocomplete text view
        // set adapter to the autocomplete tv to the arrayAdapter
        payment_method_Auto.setAdapter(arrayAdapter);

        submit_recharge_Btn.setOnClickListener(v -> {

            String amount = Objects.requireNonNull(rechargeAmount.getText()).toString();
            String paymentFrom = Objects.requireNonNull(paymentFromNumber.getText()).toString();
            String payment_method = payment_method_Auto.getText().toString();

            if (payment_method.equals("Payment Method")) {
                Toast.makeText(Card_Recharge_Balance.this, "Please Select Payment Method", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(paymentFrom)) {
                Toast.makeText(Card_Recharge_Balance.this, "Please Enter Payment Accounnt Number", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(amount)) {
                Toast.makeText(Card_Recharge_Balance.this, "Please Enter Recharge Amount", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Card_Recharge_Balance.this, "Amount :" + amount, Toast.LENGTH_SHORT).show();

                String transaction_id = getRandomString();

                updateRechargeData(amount, payment_method, user_id, transaction_id, account_no, paymentFrom);
            }

        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onBackPressed();
    }


    private void updateRechargeData(String amount, String paymentMethod, String user_id, String transaction_id, String account_no, String paymentNumber) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RECHARGE_BALANCE,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("status");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        if (success.equals("1")) {
                            progressDialog.dismiss();
                            Toast.makeText(Card_Recharge_Balance.this, "Payment Successfull..", Toast.LENGTH_LONG).show();
                            onBackPressed();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(this, "We are extreamly sorry .Server is busy right now", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(Card_Recharge_Balance.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {

            progressDialog.dismiss();
            Toast.makeText(Card_Recharge_Balance.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("account_no", account_no);
                params.put("recharge_amount", amount);
                params.put("recharge_method", paymentMethod);
                params.put("paymentNumber", paymentNumber);
                params.put("transaction_id", transaction_id);
                params.put("recharged_by", user_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog.show();
    }

    private static String getRandomString() {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

}

