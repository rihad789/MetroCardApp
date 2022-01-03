package com.service.metrocardbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Fare_And_Route_Activity extends AppCompatActivity {

    private static final String URL_GET_STATIONS = APIs.getUrlGetStations();
    private static final String URL_GET_FARE_AND_ROUTES = APIs.getUrlGetFareAndRoutes();
    private static final String URL_GET_TRAIN_LINE=APIs.getUrlGetTrainLine();

    ProgressDialog retrieve_line_data,retrieve_station_data,query_fare_routes_data;
    ArrayList<Stations_Pojo_CLass> stationList;
    ArrayList<Line_Pojo_Class> lineList;
    Spinner line_spinner,station_spinner,next_station_spinner;
    String line_id="",station_id="",destination_station_id="";
    AppCompatButton submit_fare_and_route_query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fair_and_route);

        Toolbar toolbar = findViewById(R.id.toolbar);

        line_spinner=findViewById(R.id.line_spinner);
        station_spinner = findViewById(R.id.station_spinner);
        next_station_spinner=findViewById(R.id.next_station_spinner);
        submit_fare_and_route_query=findViewById(R.id.submit_fare_and_route_query);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Fare and Route");
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        //Initializing Progress Dialog
        initializeProgressDialog();

        getTrainLineData();

        submit_fare_and_route_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_station_data_input(station_id,destination_station_id))
                {
                   retrieve_fare_and_routes(line_id,station_id,destination_station_id);
//                    Toast.makeText(Fare_And_Route_Activity.this, "Station data are correct.", Toast.LENGTH_SHORT).show();



                    //Toast.makeText(Fare_And_Route_Activity.this, "Line ID: "+line_id+","+"Station ID: "+station_id+"Next Station ID:"+destination_station_id, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initializeProgressDialog() {

        retrieve_line_data = new ProgressDialog(Fare_And_Route_Activity.this);
        retrieve_line_data.setMessage("Retrieving Line Data...");
        retrieve_line_data.setCancelable(false);


        retrieve_station_data = new ProgressDialog(Fare_And_Route_Activity.this);
        retrieve_station_data.setMessage("Retrieving Stations Data...");
        retrieve_station_data.setCancelable(false);

        query_fare_routes_data=new ProgressDialog(Fare_And_Route_Activity.this);
        query_fare_routes_data.setMessage("Retrieving fare and routes Data...");
        query_fare_routes_data.setCancelable(false);
    }

    private void retrieve_fare_and_routes(String line_id, String station_id, String destination_station_id) {

        query_fare_routes_data.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_FARE_AND_ROUTES,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String route = jsonObject.getString("Route");
                        String fare = jsonObject.getString("Fare");
                        String distance=jsonObject.getString("Distance");

                        /*Toast.makeText(RegisterComplaintActivity.this, "Json Response: " + success + "," + et + "," + cm, Toast.LENGTH_SHORT).show();*/

                        query_fare_routes_data.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Fare and Route");
                        builder.setMessage("Route: "+route +"\t"+"\n"+"Distance: "+distance+" Km"+"\n"+"Fare: "+fare+" Tk");

                        // add a button
                        builder.setPositiveButton("OK", null);

                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

/*                        Toast.makeText(Fare_And_Route_Activity.this, "Route: "+route+" , "+"Distance: "+distance, Toast.LENGTH_LONG).show();*/


                    } catch (JSONException e) {
                        e.printStackTrace();
                        query_fare_routes_data.dismiss();
                        Toast.makeText(Fare_And_Route_Activity.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            query_fare_routes_data.dismiss();
            Toast.makeText(Fare_And_Route_Activity.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("train_line_id", line_id);
                params.put("station_id", station_id);
                params.put("next_station_id", destination_station_id);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private boolean check_station_data_input(String station_id, String destination_station_id) {

        if(line_id.equals(""))
        {
            Toast.makeText(this, "Please Select A Train Line", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if(station_id.equals(""))
        {
            Toast.makeText(this, "Please Select Start Station", Toast.LENGTH_SHORT).show();
            return  false;
        } else if(destination_station_id.equals(""))
        {
            Toast.makeText(this, "Please Select Destination Station", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if(station_id.equals(destination_station_id))
        {
            Toast.makeText(this, "You cannot travel from same station to same station", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }

    }

    public void getTrainLineData() {
        retrieve_line_data.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_GET_TRAIN_LINE, null, response -> {

            lineList = new ArrayList<>();
            lineList.add(new Line_Pojo_Class("","Select Train Line"));

            try {

                JSONArray lineArray = response.getJSONArray("trainLineData");
                //now looping through all the elements of the json array
                for (int i = 0; i < lineArray.length(); i++) {
                    //getting the json object of the particular index inside the array
                    JSONObject lineData = lineArray.getJSONObject(i);
                    String id=lineData.getString("id");
                    String name=lineData.getString("name");
                    lineList.add(new Line_Pojo_Class(id,name));
                }

                //creating custom adapter object
                setValueToTrainLineSpinner();
                retrieve_line_data.dismiss();

            } catch (JSONException e) {
                retrieve_line_data.dismiss();
                e.printStackTrace();
            }
        }, error -> {
            retrieve_line_data.dismiss();
            error.printStackTrace();
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void setValueToTrainLineSpinner() {

        ArrayAdapter<Line_Pojo_Class> lineAdapter = new ArrayAdapter<Line_Pojo_Class>(Fare_And_Route_Activity.this, android.R.layout.simple_spinner_dropdown_item, lineList);
        line_spinner.setAdapter(lineAdapter);

        line_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Line_Pojo_Class lineData = (Line_Pojo_Class) parent.getItemAtPosition(position);
                line_id=lineData.id;

                //Toast.makeText(Fare_And_Route_Activity.this, "Line ID:" + lineData.id, Toast.LENGTH_SHORT).show();

                if(!lineData.id.equals(""))
                {
                    getStationData(lineData.id);
                }
                else
                {
                    station_spinner.setVisibility(View.INVISIBLE);
                    next_station_spinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getStationData(String LineID) {

        retrieve_station_data.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_STATIONS,
                response -> {

                        stationList = new ArrayList<Stations_Pojo_CLass>();
                        stationList.add(new Stations_Pojo_CLass("","Select Station"));

                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray stationArray = obj.getJSONArray("stationData");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < stationArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject stationData = stationArray.getJSONObject(i);
                                String id=stationData.getString("id");
                                String name=stationData.getString("name");
                                stationList.add(new Stations_Pojo_CLass(id,name));

                            }

                            //creating custom adapter object
                            setValueToStationSpinner();
                            retrieve_station_data.dismiss();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        retrieve_station_data.dismiss();
                        Toast.makeText(Fare_And_Route_Activity.this, "JSONException:" + e, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            retrieve_station_data.dismiss();
            Toast.makeText(Fare_And_Route_Activity.this, " Volley Error:" + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", LineID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void setValueToStationSpinner() {

        ArrayAdapter<Stations_Pojo_CLass> stationAdapter = new ArrayAdapter<Stations_Pojo_CLass>(Fare_And_Route_Activity.this, android.R.layout.simple_spinner_dropdown_item, stationList);
        station_spinner.setAdapter(stationAdapter);
        station_spinner.setVisibility(View.VISIBLE);

        station_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stations_Pojo_CLass stationData = (Stations_Pojo_CLass) parent.getItemAtPosition(position);
                station_id=stationData.id;

                //Toast.makeText(Fare_And_Route_Activity.this, "Station ID:" + station_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<Stations_Pojo_CLass> nextstationAdapter = new ArrayAdapter<Stations_Pojo_CLass>(Fare_And_Route_Activity.this, android.R.layout.simple_spinner_dropdown_item, stationList);
        next_station_spinner.setAdapter(nextstationAdapter);
        next_station_spinner.setVisibility(View.VISIBLE);

        next_station_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stations_Pojo_CLass stationData = (Stations_Pojo_CLass) parent.getItemAtPosition(position);
                destination_station_id=stationData.id;

                //Toast.makeText(Fare_And_Route_Activity.this, "Destination Station ID:" + destination_station_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

