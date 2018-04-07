package id.ac.itb.ditlog.monitorandperformance;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
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

private OnArtikelClickListener mOnArtikelClickListener;

public RecyclerEvaluation(Context context) {
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mViewContainer;
	    public CardView mCardViewContainer;

	    public TextView evalIndicator;
        public TextView grade;
        //private LinearLayout mParent;

        public ViewHolder(View v) {
            super(v);
            mViewContainer = v;
            mCardViewContainer = (CardView) itemView.findViewById(R.id.penilaian_container);
            evalIndicator = (TextView) v.findViewById(R.id.param_indicator_evaluation);
            grade = (TextView) v.findViewById(R.id.grade_indicator_evaluation);
            // Define click listener for the ViewHolder's View.
            
            
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
    public void onBindViewHolder(RecyclerEvaluation.ViewHolder holder, final int position) {
        //Log.d(TAG, "Element " + position + " set.");

        final int posisi = getItemViewType(position);
        final ViewHolder viewHolderArticle1 = (ViewHolder) holder;
            final int posisiAdapter2 = holder.getAdapterPosition();
            // Get element from your dataset at this position and replace the contents of the view
            // with that element

        switch (posisi) {
            case 0:
                viewHolderArticle1.getEvalIndicator().setText(mParam[position]);

                viewHolderArticle1.getGradeIndicator().setText(mParam2[position]);
                break;
            case 1:
                viewHolderArticle1.getEvalIndicator().setText(mParam[position]);

                viewHolderArticle1.getGradeIndicator().setText(mParam2[position]);
                break;
            case 2:
                viewHolderArticle1.getEvalIndicator().setText(mParam[position]);

                viewHolderArticle1.getGradeIndicator().setText(mParam2[position]);
                break;

        }
        viewHolderArticle1.mViewContainer.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (mOnArtikelClickListener != null) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mOnArtikelClickListener != null)
                                            mOnArtikelClickListener.onClick(posisiAdapter2);
                                    }
                                }, 250);
                            }
                        }
                    }
            );



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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

	//JIKA CONTAINER DI KLIK
    public interface OnArtikelClickListener {
        void onClick(int posisi);
    }
	public void setOnArtikelClickListener(OnArtikelClickListener onArtikelClickListener) {
        mOnArtikelClickListener = onArtikelClickListener;
    }
}
