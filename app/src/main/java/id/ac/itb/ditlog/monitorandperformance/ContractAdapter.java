package id.ac.itb.ditlog.monitorandperformance;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 17/03/18.
 */

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ViewHolder> {
    private ArrayList<ContractEntity> contracts;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contract_details, parent, false);

        return new ViewHolder(v);
    }

    public ContractAdapter(ArrayList<ContractEntity> contracts){
        this.contracts = contracts;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(contracts.get(position).name);
        holder.vendor.setText("by " + contracts.get(position).vendor);
        Date dateVal = contracts.get(position).date;
        String dateString = String.valueOf(dateVal.getDate()) + "/";
        dateString += String.valueOf(dateVal.getMonth()) + "/";
        dateString += String.valueOf(dateVal.getYear());
        holder.date.setText(dateString);
        holder.grade.setText(String.valueOf(contracts.get(position).grade));
    }

    @Override
    public int getItemCount() {
        return contracts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView vendor;
        public TextView date;
        public TextView grade;
        public ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            vendor = view.findViewById(R.id.vendor);
            date = view.findViewById(R.id.date);
            grade = view.findViewById(R.id.grade);
        }
    }

}
