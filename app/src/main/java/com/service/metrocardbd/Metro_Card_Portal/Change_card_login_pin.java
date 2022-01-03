package com.service.metrocardbd.Metro_Card_Portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
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

public class Change_card_login_pin extends AppCompatActivity {

    SessionManager sessionManager;
    AppCompatButton update_Password_btn;
    AppCompatEditText editText_old_password,editText_new_password;
    private static final String URL_CHANGE_PIN= APIs.getUrlChangePin();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_card_login_pin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        update_Password_btn = findViewById(R.id.update_Password_btn);
        editText_old_password=findViewById(R.id.editText_old_password);
        editText_new_password=findViewById(R.id.editText_new_password);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Change Login PIN");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getuserData();
        String user_id = user.get(SessionManager.CARD_USER_ID);
        String card_no = user.get(SessionManager.CARD_NO);


        update_Password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_pin=editText_old_password.getText().toString();
                String new_pin=editText_new_password.getText().toString();

                if(checkPinInput(old_pin,new_pin))
                {
                    chnagePIN(old_pin,new_pin,card_no);
                    //Toast.makeText(Change_card_login_pin.this, "Input Validated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void chnagePIN(String old_pin, String new_pin,String card_no) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHANGE_PIN,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("status");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        if (success.equals("1")) {

                            String message = jsonObject.getString("message");
                            Toast.makeText(Change_card_login_pin.this, message, Toast.LENGTH_SHORT).show();
                            onBackPressed();


                        } else {

                            String message = jsonObject.getString("message");
                            Toast.makeText(Change_card_login_pin.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {


                        e.printStackTrace();
                        Toast.makeText(Change_card_login_pin.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {


            Toast.makeText(Change_card_login_pin.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("old_pin", old_pin);
                params.put("new_pin", new_pin);
                params.put("card_no",card_no);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Change_card_login_pin.this);
        requestQueue.add(stringRequest);
    }

    private boolean checkPinInput(String old_password, String new_password) {

        if(old_password.isEmpty())
        {
            Toast.makeText(this, "Old Password required", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if(new_password.isEmpty())
        {
            Toast.makeText(this, "New Password required", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else
        {
            return  true;
        }
    }


}