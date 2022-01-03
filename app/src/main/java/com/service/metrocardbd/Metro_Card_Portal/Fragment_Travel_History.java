package com.service.metrocardbd.Metro_Card_Portal;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.service.metrocardbd.APIs;
import com.service.metrocardbd.R;
import com.service.metrocardbd.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_Travel_History extends Fragment {

    ProgressBar progressBar;
    SwipeRefreshLayout swipe_for_history;
    SessionManager sessionManager;

    private RecyclerView travel_history_recyclerView;
    private List<Travel_History_POJO_Class> travel_history_List;
    private Travel_History_Adapter travel_history_adapter;
    private static  final String URl_RETRIEV_TRAVEL_HISTORY=APIs.getURl_RETRIEV_TRAVEL_HISTORY();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_travel_history, container, false);;

        progressBar=view.findViewById(R.id.travel_historyProgressbar);
        swipe_for_history=view.findViewById(R.id.swipe_for_travel_history);

        travel_history_recyclerView = view.findViewById(R.id.travel_history_recycler_view);
        travel_history_recyclerView.setHasFixedSize(true);
        travel_history_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        travel_history_List = new ArrayList<>();

        sessionManager = new SessionManager(requireContext());
        HashMap<String, String> user = sessionManager.getuserData();
        String user_id = user.get(SessionManager.CARD_USER_ID);
        String card_no = user.get(SessionManager.CARD_NO);

        retrive_travel_history(card_no);

        swipe_for_history.setOnRefreshListener(() -> {
            swipe_for_history.setEnabled(true);
            swipe_for_history.setRefreshing(true);
            retrive_travel_history(card_no);
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void retrive_travel_history(String card_no) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URl_RETRIEV_TRAVEL_HISTORY,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("status");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        if (success.equals("1")) {

                            travel_history_List.clear();

                            try {

                                JSONArray historyArray = jsonObject.getJSONArray("history");
                                //now looping through all the elements of the json array
                                for (int i = 0; i < historyArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject historyData = historyArray.getJSONObject(i);

                                    Travel_History_POJO_Class history = new Travel_History_POJO_Class();

                                    history.setLineName(historyData.getString("linename"));
                                    history.setRouteData(historyData.getString("stationname")+"=>"+historyData.getString("nextstationname"));
                                    history.setDistance(historyData.getString("distance")+" Km");
                                    history.setJourney_start(historyData.getString("created_at"));
                                    history.setJourney_end(historyData.getString("updated_at"));
                                    history.setFareAmount(historyData.getString("charged_fare")+" Tk");

                                    travel_history_List.add(history);
                                }

                                progressBar.setVisibility(View.INVISIBLE);


                            } catch (JSONException e) {

                                swipe_for_history.setRefreshing(false);
                                progressBar.setVisibility(View.INVISIBLE);
                                e.printStackTrace();

                            }

                            travel_history_adapter = new Travel_History_Adapter(getContext(), travel_history_List);
                            travel_history_recyclerView.setAdapter(travel_history_adapter);
                            travel_history_adapter.notifyDataSetChanged();
                            swipe_for_history.setRefreshing(false);


                        } else {
                            swipe_for_history.setRefreshing(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "No History Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {

                        swipe_for_history.setRefreshing(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        e.printStackTrace();
                        Toast.makeText(getContext(), "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {

            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("card_no", card_no);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}