package com.service.metrocardbd.Metro_Card_Portal;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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


public class Fragment_Card_Home extends Fragment {

    AppCompatButton recharge_Btn, view_QR_code_Btn, logout_Btn;
    View view;
    SessionManager sessionManager;
    ProgressBar progressBar;
    TextView card_user_name_textview,card_balance_textview;
    private static final String URL_GET_CARD_BALANCE = APIs.getUrlGetCardBalance();
    SwipeRefreshLayout swipe_to_refresh_home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_card_portal_home, container, false);

        recharge_Btn = view.findViewById(R.id.recharge_balance_Btn);
        view_QR_code_Btn = view.findViewById(R.id.view_QR_code_Btn);
        logout_Btn = view.findViewById(R.id.logout_Btn);


        progressBar = view.findViewById(R.id.balance_retrieve_progress);
        card_balance_textview = view.findViewById(R.id.card_balance);
        card_user_name_textview=view.findViewById(R.id.card_user_name);

        sessionManager = new SessionManager(requireContext());
        HashMap<String, String> user = sessionManager.getuserData();
        String name = user.get(SessionManager.NAME);
        card_user_name_textview.setText("Hey! "+name);


        setupBUttonClickEvent();





        return view;
    }

    private void retrieve_account_balance(String account_no) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_CARD_BALANCE,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("status");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        if (success.equals("1")) {


                            String balance = jsonObject.getString("balance")+" Tk";
                            card_balance_textview.setText(balance);
                            progressBar.setVisibility(View.INVISIBLE);
                            card_balance_textview.setVisibility(View.VISIBLE);


                        } else {

                            Toast.makeText(getContext(), "Unable to retrieve balance..", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(getContext(), "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {

            Toast.makeText(getContext(), " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("account_no", account_no);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

    }



    private void setupBUttonClickEvent() {

        recharge_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), Card_Recharge_Balance.class);
                startActivity(i);

//                Toast.makeText(getContext(), "Recharge Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        view_QR_code_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),GenerateQRCode.class);
                startActivity(i);
//                Toast.makeText(getContext(), "View QR Code Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logOut();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        HashMap<String, String> user = sessionManager.getuserData();
        String name = user.get(SessionManager.NAME);
        String user_id = user.get(SessionManager.CARD_USER_ID);
        String account_no = user.get(SessionManager.ACCOUNT_NO);

        retrieve_account_balance(account_no);

    }
}

