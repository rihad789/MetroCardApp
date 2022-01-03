package com.service.metrocardbd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterComplaintActivity extends AppCompatActivity {

    private static final String URL_REGISTER_COMPLAINT = APIs.getUrlRegisterComplaint();
    AppCompatButton submit_complaint;
    EditText complainer_name, complainer_phone;
    String name, phone, complaint_type;
    ProgressDialog progressdialog;
    AutoCompleteTextView complain_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);

        /* Assigning Feilds to ID */
        Toolbar toolbar = findViewById(R.id.toolbar);
        submit_complaint = findViewById(R.id.submit_complaint_button);
        complainer_name = findViewById(R.id.complainer_name);
        complainer_phone = findViewById(R.id.complainer_phone);
        complain_type = findViewById(R.id.complain_type);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Register Complaint");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        // get reference to the string array that we just created

        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.complain_type_dropdown, getResources().getStringArray(R.array.complain_type));
        // get reference to the autocomplete text view
        // set adapter to the autocomplete tv to the arrayAdapter
        complain_type.setAdapter(arrayAdapter);

        /* Implementating Progress Dialog */
        progressdialog = new ProgressDialog(RegisterComplaintActivity.this);
        progressdialog.setMessage("Complaint registering...");
        progressdialog.setCancelable(false);

        /* Implementating Click Listener */
        submit_complaint.setOnClickListener(v -> submitComplaint());
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    private void submitComplaint() {

        name = complainer_name.getText().toString();
        phone = complainer_phone.getText().toString();
        complaint_type = complain_type.getText().toString();

        String country_code = "880";
        if (check_inputs(name, phone, complaint_type)) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_COMPLAINT,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");

                            /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                            if (success.equals("1")) {
                                progressdialog.dismiss();
                                Toast.makeText(RegisterComplaintActivity.this, "Complaint registered Successfully.Thanks for your cooperation.", Toast.LENGTH_LONG).show();
                                onBackPressed();

                            } else {
                                Toast.makeText(this, "We are extreamly sorry .Server is busy right now", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressdialog.dismiss();
                            Toast.makeText(RegisterComplaintActivity.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                        }

                    }, error -> {
                progressdialog.dismiss();
                Toast.makeText(RegisterComplaintActivity.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("complain_type", complaint_type);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            progressdialog.show();
        }

    }

    private boolean check_inputs(String name, String phone, String complaint) {

        boolean status = true;

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Input your name", Toast.LENGTH_SHORT).show();
            status = false;
        }
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            Toast.makeText(this, "Invalid Phone", Toast.LENGTH_SHORT).show();
            status = false;
        }
        if (complaint.equals("Complain Type")) {
            Toast.makeText(this, "Please Select Complaint Type", Toast.LENGTH_SHORT).show();
            status = false;
        }
        return status;
    }
}

