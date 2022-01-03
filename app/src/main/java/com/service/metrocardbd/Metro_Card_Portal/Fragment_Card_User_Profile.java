package com.service.metrocardbd.Metro_Card_Portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.service.metrocardbd.R;
import com.service.metrocardbd.SessionManager;

import java.util.HashMap;


public class Fragment_Card_User_Profile extends Fragment {

    TextView name, phone, account_no, change_password;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_card_portal_user_profile, container, false);

        sessionManager=new SessionManager(requireContext());

        name = view.findViewById(R.id.profile_name_text_view);
        phone = view.findViewById(R.id.profile_phone_text_view);
        account_no = view.findViewById(R.id.profile_account_text_view);
        change_password = view.findViewById(R.id.change_password_label_text_view);


        sessionManager = new SessionManager(requireContext());
        HashMap<String, String> user = sessionManager.getuserData();
        String name1 = user.get(SessionManager.NAME);
        String phone1 = user.get(SessionManager.PHONE_NO);
        String account1 = user.get(SessionManager.ACCOUNT_NO);


        name.setText(name1);
        phone.setText(phone1);
        account_no.setText(account1);

        change_password.setOnClickListener(v -> {
            Intent cngPass=new Intent(requireContext(), Change_card_login_pin.class);
            startActivity(cngPass);
        });

        return view;
    }

}