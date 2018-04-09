package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ASUS on 01/03/2018.
 */

public class RecyclerChooseIndicator extends RecyclerView.Adapter<RecyclerChooseIndicator.ViewHolder> {
    private static final String TAG = "RecyclerChooseIndicator";
    private ArrayList<IndicatorEntity> mParam;
    private int[] chosenId = new int[30];
    private int nChosen = 0;
    private int[] checkUpdate = new int[30];
    private int checkLength = 0;
    private int[] uncheckUpdate = new int[30];
    private int uncheckLength = 0;

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
                    //Log.d(TAG, "Element " + getPosition() + " clicked.");
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
    public RecyclerChooseIndicator(ArrayList<IndicatorEntity> dataSet, int[] chosenId, int nChosen) {
        this.mParam = dataSet;
        this.chosenId = chosenId;
        this.nChosen = nChosen;
        this.checkUpdate = chosenId;
        this.checkLength = nChosen;
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

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getParamIndicator().setText(mParam.get(position).name);

        int idIndicator = mParam.get(position).id;
        boolean checked = false;
        if (isChoosen(idIndicator)) {
            checked = true;
        }
        viewHolder.mCheckBox.setChecked(checked);

        //in some cases, it will prevent unwanted situations
        viewHolder.mCheckBox.setOnCheckedChangeListener(null);

        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = ((CheckBox) buttonView).isChecked();

                // Check which checkbox was clicked
                switch (buttonView.getId()) {
                    case R.id.checkbox_indicator:
                        if (checked) {
                            Log.d("cekbox", "cekbox " + mParam.get(position).id + "dicheck");
                            check(mParam.get(position).id);
                            for (int i=0; i<checkLength; i++) {
                                Log.d("cekbox", "cekbox " + checkUpdate[i]);
                            }
                        }
                        else {
                            Log.d("cekbox", "cekbox " + mParam.get(position).id + "diuncheck");
                            uncheck(mParam.get(position).id);
                            for (int i=0; i<checkLength; i++) {
                                Log.d("cekbox", "cekbox " + checkUpdate[i]);
                            }
                        }
                        break;

                }
            }

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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mParam.size();
    }

    public boolean isChoosen(int id) {
        int i = 0;
        boolean choose = false;
        while (!choose && (i < nChosen)) {
            if (chosenId[i] == id) {
                choose = true;
            } else {
                i++;
            }
        }
        return choose;
    }

    public void check(int id) {
        checkUpdate[checkLength] = id;
        checkLength++;
    }

    public void uncheck(int id) {
        boolean deleteId = false;
        int i = 0;

        while (!deleteId && (i < checkLength)) {
            if (checkUpdate[i] == id) {
                deleteId = true;
            } else {
                i++;
            }
        }

        for (int j = i; j < (checkLength-1); j++) {
            checkUpdate[j] = checkUpdate[j+1];
        }
        checkLength--;
    }

    public String getCheck() {
        String ind = "[";
        if (checkLength != 0) {
            ind = ind + Integer.toString(checkUpdate[0]);
        }
        for (int i = 1; i < checkLength; i++) {
            ind = ind + ", " + Integer.toString(checkUpdate[i]);
        }
        ind = ind + "]";
        return ind;
    }

    public boolean isDelete(int id) {
        int i = 0;
        boolean choose = false;
        while (!choose && (i < checkLength)) {
            if (checkUpdate[i] == id) {
                choose = true;
            } else {
                i++;
            }
        }
        return (!choose);
    }

    public String getUncheck() {
        for (int a = 0; a < nChosen; a++) {
            if (isDelete(chosenId[a])) {
                uncheckUpdate[uncheckLength] = chosenId[a];
                uncheckLength++;
            }
        }

        String ind = "[";
        if (uncheckLength != 0) {
            ind = ind + Integer.toString(uncheckUpdate[0]);
        }
        for (int k = 1; k < uncheckLength; k++) {
            ind = ind + ", " + Integer.toString(uncheckUpdate[k]);
        }
        ind = ind + "]";
        return ind;
    }
}
