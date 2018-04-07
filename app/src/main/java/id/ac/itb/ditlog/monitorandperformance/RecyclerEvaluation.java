package id.ac.itb.ditlog.monitorandperformance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 02/04/2018.
 */

public class RecyclerEvaluation extends RecyclerView.Adapter<RecyclerEvaluation.ViewHolder> {
    private static final String TAG = "RecyclerEvaluation";

    private String[] mParam, mParam2;

    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView evalIndicator;

        private TextView grade;
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
            evalIndicator = (TextView) v.findViewById(R.id.param_indicator_evaluation);
            grade = (TextView) v.findViewById(R.id.grade_indicator_evaluation);
        }

        public TextView getEvalIndicator() {
            return evalIndicator;
        }

        public TextView getGradeIndicator() {
            return grade;
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public RecyclerEvaluation(String[] dataSet, String[] dataSet2) {
        this.mParam = dataSet;
        this.mParam2 = dataSet2;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerEvaluation.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_view_evaluation, viewGroup, false);

        return new RecyclerEvaluation.ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerEvaluation.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getEvalIndicator().setText(mParam[position]);

        viewHolder.getGradeIndicator().setText(mParam2[position]);


        //final ObjectIncome objIncome = myItems.get(position);

        //if true, your checkbox will be selected, else unselected
        //viewHolder.mCheckBox.setChecked(viewHolder.getParamIndicator().setText(mParam[position]).isSelected());

    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mParam.length;
    }
}
