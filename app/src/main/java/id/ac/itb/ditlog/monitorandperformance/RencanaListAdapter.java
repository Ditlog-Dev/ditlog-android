/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.ac.itb.ditlog.monitorandperformance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

/**
 * Shows how to implement a simple Adapter for a RecyclerView.
 * Demonstrates how to add a click handler for each item in the ViewHolder.
 */
public class RencanaListAdapter extends RecyclerView.Adapter<RencanaListAdapter.RencanaViewHolder> {

    private final JSONArray mMilestoneList;
    private final LayoutInflater mInflater;
    private Context context;
    private RencanaListAdapter rencanaListAdapter = this;


    private void  deleteItem(int index) {
        mMilestoneList.remove(index);
        notifyItemRemoved(index);

        for (int i=0; i < mMilestoneList.length(); i++){
            try {
                JSONObject temp = mMilestoneList.getJSONObject(i);
                temp.put("id", i);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    class RencanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final EditText dateItemView;
        public final EditText percentageItemView;
        public final EditText keteranganItemView;
        int index;
        JSONObject jsonObject;
        Tanggal tanggal = new Tanggal();

        final RencanaListAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views for the RecyclerView.
         */
        public RencanaViewHolder(View itemView, RencanaListAdapter adapter) {
            super(itemView);
            dateItemView = (EditText) itemView.findViewById(R.id.editDate);
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
            tanggal.setTanggal(cal);
            final DatePickerDialog.OnDateSetListener datePick = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    tanggal.setTanggal(dayOfMonth,monthOfYear,year);
                    dateItemView.setText(tanggal.getTanggal());


                }

            };

            dateItemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(context, datePick,
                            tanggal.getYear(), tanggal.getMonth(), tanggal.getDate() ).show();
                }
            });




            percentageItemView = (EditText) itemView.findViewById(R.id.editPercentage);
            percentageItemView.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
            keteranganItemView = (EditText) itemView.findViewById(R.id.editKeterangan);

            ImageButton deleteButton = itemView.findViewById(R.id.deleteMilestone);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                        Integer _index =  Integer.parseInt(jsonObject.get("id").toString());
                        Log.d("Cek", "remove Index" + _index);

                        rencanaListAdapter.deleteItem(_index);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                        // Scroll to the bottom.
                }
            });


            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // All we do here is prepend "Clicked! " to the text in the view, to verify that
            // the correct item was clicked. The underlying data does not change.

        }


        public  Tanggal getTanggal(){
            return tanggal;
        }

        public void setDateItem(String inputTanggal){
            tanggal.setTanggal(inputTanggal);
            dateItemView.setText(tanggal.getTanggal());

        }

        public void setDateItem(Tanggal inputTanggal){
            tanggal= inputTanggal;
            dateItemView.setText(tanggal.getTanggal());

        }

        public void setIndex(int _index){
            index = _index;
        }

        public void setJSONObject(JSONObject _jsonObject){
            jsonObject =_jsonObject;
        }
    }
        // insert CONSTRUCTOR here
    public RencanaListAdapter(Context _context, JSONArray milestoneList) {
        mInflater = LayoutInflater.from(_context);
        this.mMilestoneList = milestoneList;
        context = _context;
    }

    /**
     * Inflates an item view and returns a new view holder that contains it.
     * Called when the RecyclerView needs a new view holder to represent an item.
     *
     * @param parent The view group that holds the item views.
     * @param viewType Used to distinguish views, if more than one type of item view is used.
     * @return a view holder.
     */
    @Override
    public RencanaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(R.layout.date_percentage_rencana, parent, false);
        return new RencanaViewHolder(mItemView, this);
    }

    /**
     * Sets the contents of an item at a given position in the RecyclerView.
     * Called by RecyclerView to display the data at a specificed position.
     *
     * @param holder The view holder for that position in the RecyclerView.
     * @param position The position of the item in the RecycerView.
     */
    @Override
    public void onBindViewHolder(RencanaViewHolder holder, int position) {
        // Retrieve the data for that position.
        JSONObject mCurrent = null;
        try {
            mCurrent = mMilestoneList.getJSONObject(position);
            mCurrent.put("id", position);
            holder.setIndex(position);
            holder.setJSONObject(mCurrent);
            holder.setDateItem(mCurrent.getString("tglRencana"));
            Integer persentaseRencana = mCurrent.getInt("persentaseRencana");
            holder.percentageItemView.setText(persentaseRencana.toString());
            //holder.keteranganItemView.setText(mCurrent.getString("keteranganRencana"));
            Integer pos = position;
            holder.keteranganItemView.setText(pos.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Add the data to the view holder.

    }

    /**
     * Returns the size of the container that holds the data.
     *
     * @return Size of the list of data.
     */
    @Override
    public int getItemCount() {
        return mMilestoneList.length();
    }
}

