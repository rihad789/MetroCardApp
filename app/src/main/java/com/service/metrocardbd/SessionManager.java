package com.service.metrocardbd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.service.metrocardbd.Metro_Card_Portal.Card_Portal_Layout_Manager;
import com.service.metrocardbd.Metro_Card_Portal.Card_Portal_Login_Activity;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;

    private static final String PREF_NAME="LOGIN";
    private static final String LOGIN="IS_LOGIN";
    public static final String NAME="NAME";
    public static final String CARD_USER_ID="CARD_USER_ID";
    public static final String CARD_NO="CARD_NO";
    public static final String ACCOUNT_NO="ACCOUNT_NO";
    public static final String PHONE_NO="PHONE_NO";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void Create_Session(String name,String card_user_id,String account_no,String card_no,String phone_no)
    {
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(CARD_USER_ID,card_user_id);
        editor.putString(ACCOUNT_NO,account_no);
        editor.putString(CARD_NO,card_no);
        editor.putString(PHONE_NO,phone_no);
        editor.apply();
    }

    public boolean is_Logged_IN()
    {
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void Check_Login()
    {
        if(!this.is_Logged_IN())
        {
            Intent goToLogin=new Intent(context, Card_Portal_Login_Activity.class);
            context.startActivity(goToLogin);
            ((Card_Portal_Layout_Manager)context).finish();
        }
    }

    public HashMap<String,String> getuserData()
    {
        HashMap<String,String>user=new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(CARD_USER_ID,sharedPreferences.getString(CARD_USER_ID,null));
        user.put(ACCOUNT_NO,sharedPreferences.getString(ACCOUNT_NO,null));
        user.put(CARD_NO,sharedPreferences.getString(CARD_NO,null));
        user.put(PHONE_NO,sharedPreferences.getString(PHONE_NO,null));
        return user;
    }

    public void logOut()
    {
        editor.clear();
        editor.commit();
        Intent goToLogin=new Intent(context, Card_Portal_Login_Activity.class);
        context.startActivity(goToLogin);
        ((Card_Portal_Layout_Manager)context).finish();

    }
}
