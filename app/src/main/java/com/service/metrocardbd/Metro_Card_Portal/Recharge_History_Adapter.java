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

public class Recharge_History_Adapter extends RecyclerView.Adapter<Recharge_History_Adapter.TravelHistoryViewHolder> {

    private final Context context;
    private final List<Recharge_History_POJO_Class> travelHistoryList;


    //Contact Retriver Adapter Constructor
    public Recharge_History_Adapter(Context context, List<Recharge_History_POJO_Class> travelHistoryList) {
        this.context = context;
        this.travelHistoryList = travelHistoryList;
    }


    @NonNull
    @Override
    public Recharge_History_Adapter.TravelHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view=inflater.inflate(R.layout.recharge_history_recycler_layout,parent,false);
        return new TravelHistoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Recharge_History_Adapter.TravelHistoryViewHolder holder, int position) {

        final Recharge_History_POJO_Class histories = travelHistoryList.get(position);
        holder.recharge_Amount.setText(histories.getRecharge_Amount());
        holder.rechargeMethod.setText(histories.getRechargeMethod());
        holder.accountNo.setText(histories.getAccountNo());
        holder.recharge_date.setText(histories.getRecharge_date());
        holder.txn_id.setText(histories.getTxn_id());

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

        TextView recharge_Amount,rechargeMethod,accountNo,recharge_date,txn_id;

        public TravelHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            recharge_Amount=itemView.findViewById(R.id.rechargeAmount);
            rechargeMethod=itemView.findViewById(R.id.payment_method);
            accountNo=itemView.findViewById(R.id.paymentFrom);
            recharge_date=itemView.findViewById(R.id.recharge_date);
            txn_id=itemView.findViewById(R.id.txn_id);

        }
    }
}

