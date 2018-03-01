package id.ac.itb.ditlog.monitorandperformance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 01/03/2018.
 */

public class RecyclerChooseIndicator extends RecyclerView.Adapter<RecyclerChooseIndicator.ViewHolder> {
    private static final String TAG = "RecyclerChooseIndicator";

    private String[] mParam;

    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView paramIndicator;

        private CheckBox mCheckBox;
        //private LinearLayout mParent;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            paramIndicator = (TextView) v.findViewById(R.id.param_indicator);
            mCheckBox = (CheckBox) v.findViewById(R.id.checkbox_indicator);
        }

        public TextView getParamIndicator() {
            return paramIndicator;
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public RecyclerChooseIndicator(String[] dataSet) {
        this.mParam = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_view_indicator, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getParamIndicator().setText(mParam[position]);


        //final ObjectIncome objIncome = myItems.get(position);

        //in some cases, it will prevent unwanted situations
        viewHolder.mCheckBox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        //viewHolder.mCheckBox.setChecked(viewHolder.getParamIndicator().setText(mParam[position]).isSelected());

        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = ((CheckBox) buttonView).isChecked();

                // Check which checkbox was clicked
                switch (buttonView.getId()) {
                    case R.id.checkbox_indicator:
                        if (checked) {

                        }
                        else {

                        }
                        break;

                }
            }

            /*@Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            //set your object's last status
                            //objIncome.setSelected(isChecked);
                        }*/
            public void onCheckboxClicked(CompoundButton view, boolean isChecked) {
                // Is the view now checked?
                boolean checked = ((CheckBox) view).isChecked();

                // Check which checkbox was clicked
                switch (view.getId()) {
                    case R.id.checkbox_indicator:
                        if (checked) {

                        }
                        else {

                        }
                        break;

                }
            }
        });

    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mParam.length;
    }
}
