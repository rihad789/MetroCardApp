package com.service.metrocardbd.Metro_Card_Portal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.service.metrocardbd.R;
import java.util.List;

public class Travel_History_Adapter extends RecyclerView.Adapter<Travel_History_Adapter.TravelHistoryViewHolder> {

    private final Context context;
    private final List<Travel_History_POJO_Class> travelHistoryList;


    //Contact Retriver Adapter Constructor
    public Travel_History_Adapter(Context context, List<Travel_History_POJO_Class> travelHistoryList) {
        this.context = context;
        this.travelHistoryList = travelHistoryList;
    }


    @NonNull
    public Travel_History_Adapter.TravelHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view=inflater.inflate(R.layout.travel_history_recycler_layout,parent,false);
        return new Travel_History_Adapter.TravelHistoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Travel_History_Adapter.TravelHistoryViewHolder holder, int position) {


        final Travel_History_POJO_Class histories = travelHistoryList.get(position);
        holder.lineName.setText(histories.getLineName());
        holder.routeData.setText(histories.getRouteData());
        holder.distance.setText(histories.getDistance());
        holder.journey_start.setText(histories.getJourney_start());
        holder.journey_end.setText(histories.getJourney_end());
        holder.fareAmount.setText(histories.getFareAmount());


    }



    @Override
    public int getItemCount() {

        if (travelHistoryList == null) {
            return 0;
        } else {
            return travelHistoryList.size();
        }
    }

    public static class TravelHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView lineName,routeData,distance,journey_start,journey_end,fareAmount;

        public TravelHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            lineName=itemView.findViewById(R.id.lineName);
            routeData=itemView.findViewById(R.id.routeData);
            distance=itemView.findViewById(R.id.distance);
            journey_start=itemView.findViewById(R.id.journey_start);
            journey_end=itemView.findViewById(R.id.journey_end);
            fareAmount=itemView.findViewById(R.id.fareAmount);

        }
    }
}
