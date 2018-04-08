package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by root on 17/03/18.
 */

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ViewHolder> {
    private ArrayList<ContractEntity> contracts;
    private ContractPerformancePreference performancePreference = new ContractPerformancePreference();
    private Activity act;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contract_details, parent, false);

        return new ViewHolder(v, act, performancePreference, contracts);
    }

    public ContractAdapter(ArrayList<ContractEntity> contracts, Activity act){
        this.contracts = contracts;
        this.act = act;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.number.setText(contracts.get(position).number);
        holder.name.setText(contracts.get(position).name);
        holder.vendor.setText(contracts.get(position).vendor);
        holder.grade.setText(String.valueOf(contracts.get(position).grade));
    }

    @Override
    public int getItemCount() {
        return contracts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView number;
        public TextView name;
        public TextView vendor;
        public TextView grade;
        public ContractPerformancePreference performancePreference;
        public Activity act;
        public ArrayList<ContractEntity> contracts;
        public ViewHolder(View view, Activity act, ContractPerformancePreference performancePreference, ArrayList<ContractEntity> contracts){
            super(view);
            view.setOnClickListener(this);
            number = view.findViewById(R.id.number);
            name = view.findViewById(R.id.name);
            vendor = view.findViewById(R.id.vendor);
            grade = view.findViewById(R.id.grade);
            this.act = act;
            this.performancePreference = performancePreference;
            this.contracts = contracts;
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            performancePreference.setNumber(view.getContext(), contracts.get(pos).number);
            performancePreference.setTitle(view.getContext(), contracts.get(pos).name);
            performancePreference.setVendor(view.getContext(), contracts.get(pos).vendor);
            performancePreference.setDate(view.getContext(), contracts.get(pos).date);
            performancePreference.setEval(view.getContext(), contracts.get(pos).grade);
            performancePreference.setContractId(view.getContext(), contracts.get(pos).id);
            Intent evaluation = new Intent(view.getContext(), Indicator.class);
            view.getContext().startActivity(evaluation);
            act.overridePendingTransition(0,0);
        }
    }

}
