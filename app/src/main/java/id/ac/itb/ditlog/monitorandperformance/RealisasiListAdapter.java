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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Shows how to implement a simple Adapter for a RecyclerView.
 * Demonstrates how to add a click handler for each item in the ViewHolder.
 */
public class RealisasiListAdapter extends RecyclerView.Adapter<RealisasiListAdapter.ApprovalViewHolder> {

    private final JSONArray mMilestoneList;
    private final LayoutInflater mInflater;

    class ApprovalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView dateItemView;
        public final TextView percentageItemView;
        public final TextView keteranganItemView;
        final RealisasiListAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views for the RecyclerView.
         */
        public ApprovalViewHolder(View itemView, RealisasiListAdapter adapter) {
            super(itemView);
            dateItemView = (TextView) itemView.findViewById(R.id.dateApproval);
            percentageItemView = (TextView) itemView.findViewById(R.id.percentageApproval);
            keteranganItemView = (TextView) itemView.findViewById(R.id.keteranganApproval);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // All we do here is prepend "Clicked! " to the text in the view, to verify that
            // the correct item was clicked. The underlying data does not change.

        }
    }
    // insert CONSTRUCTOR here
    public RealisasiListAdapter(Context context, JSONArray milestoneList) {
        mInflater = LayoutInflater.from(context);
        this.mMilestoneList = milestoneList;
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
    public ApprovalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(R.layout.date_percentage_approval, parent, false);
        return new ApprovalViewHolder(mItemView, this);
    }

    /**
     * Sets the contents of an item at a given position in the RecyclerView.
     * Called by RecyclerView to display the data at a specificed position.
     *
     * @param holder The view holder for that position in the RecyclerView.
     * @param position The position of the item in the RecycerView.
     */
    @Override
    public void onBindViewHolder(ApprovalViewHolder holder, int position) {
        // Retrieve the data for that position.
        JSONObject mCurrent = null;
        try {
            mCurrent = mMilestoneList.getJSONObject(position);
            holder.dateItemView.setText(mCurrent.getString("tglRencana"));
            Integer persentaseRencana = mCurrent.getInt("persentaseRencana");
            holder.percentageItemView.setText(persentaseRencana.toString());
            holder.keteranganItemView.setText(mCurrent.getString("keteranganRencana"));

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


