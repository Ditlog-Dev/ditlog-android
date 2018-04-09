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
    private ArrayList<EvaluationEntity> param;

    private Context mContext;

    private OnArtikelClickListener mOnArtikelClickListener;

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
    public RecyclerEvaluation(Context ctx, ArrayList<EvaluationEntity> dataSet) {
        this.mContext = ctx;
        this.param = dataSet;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final int posisiAdapter = holder.getAdapterPosition();
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
        holder.getEvalIndicator().setText(param.get(position).getParamEvaluation());
        holder.getGradeIndicator().setText(param.get(position).getGradeEvaluation());

        holder.mViewContainer.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (mOnArtikelClickListener != null) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mOnArtikelClickListener != null)
                                            mOnArtikelClickListener.onClick(posisiAdapter);
                                    }
                                }, 250);
                            }
                        }
                    }
            );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return param.size();
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
