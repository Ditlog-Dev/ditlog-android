package id.ac.itb.ditlog.monitorandperformance;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by root on 17/03/18.
 */

public class ContractEntity {
    public String name;
    public String vendor;
    public String date;
    public float grade;

    public ContractEntity(){
        name="";
        vendor = "";
        date = "";
        grade = 0;
    }

    public ContractEntity(String name, String vendor, String date, float grade){
        this.name = name;
        this.vendor = vendor;
        this.date = date;
        this.grade = grade;
    }

    public ConstraintLayout inflate(View parent, Context activity){
        ConstraintLayout contract;

        LayoutInflater inflater = LayoutInflater.from(activity);
        contract = (ConstraintLayout) inflater.inflate(R.layout.item_contract_details, (ViewGroup) parent,false);

        TextView name = contract.findViewById(R.id.name);
        name.setText((CharSequence) name);
        TextView vendor = contract.findViewById(R.id.vendor);
        vendor.setText((CharSequence) vendor);
        TextView dateView = contract.findViewById(R.id.date);
        dateView.setText(date.toString());
        ((ViewGroup) parent).addView(contract);

        return contract;
    }
}
