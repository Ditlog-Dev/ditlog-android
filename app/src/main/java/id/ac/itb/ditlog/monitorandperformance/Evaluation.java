package id.ac.itb.ditlog.monitorandperformance;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Evaluation extends Fragment {

    private Toast toast;
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60; // menampilkan data sebanyak value

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
	private RecyclerEvaluation.OnArtikelClickListener mOnArtikelClickListener;

    protected RecyclerView evaluationRecyclerView;
    protected RecyclerEvaluation evaluationAdapter;
    protected RecyclerView.LayoutManager evaluationLayoutManager;
    protected String[] mDataset, mDataset2;

    String [] indicatorEvaluation = {"Kampus Mahasiswa Indicator Test Yang Panjang Apakah Hilang Tenggelam YA Ku Ga Tahu Lah Yaw","Kampus Mahasiswa Indicator Test","Kampus Mahasiswa Indicator Test"};
    String [] grade = {"80","100","90"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }
    /*
        public DaftarProdukFragment() {
            // Required empty public constructor
        }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evaluation, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        evaluationRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewEvaluation);
	
	inisialisasiListener();	

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        evaluationLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        evaluationAdapter = new RecyclerEvaluation(mDataset,mDataset2);
	evaluationAdapter.setOnArtikelClickListener(mOnArtikelClickListener);

        // Set CustomAdapter as the adapter for RecyclerView.
        evaluationRecyclerView.setAdapter(evaluationAdapter);

        // END_INCLUDE(initializeRecyclerView)

        return rootView;
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_evaluation_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        String indicator = editText.getText().toString();
//                        String status = "fail";
//                        try {
//                            status = new AsyncAddIndicator(indicator, getContext(), getActivity(), token).execute(indicator).get();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                        if (status.equals("Indikator berhasil ditambahkan")) {
//                            new ChooseIndicator.indicatorGetter(token).execute();
//                        }
                    }
                })
                .setNegativeButton("BATAL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPositionEvaluation = 0;

        // If a layout manager has already been set, get current scroll position.
        if (evaluationRecyclerView.getLayoutManager() != null) {
            scrollPositionEvaluation = ((LinearLayoutManager) evaluationRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                evaluationLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                evaluationLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                evaluationLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        evaluationRecyclerView.setLayoutManager(evaluationLayoutManager);

        evaluationRecyclerView.scrollToPosition(scrollPositionEvaluation);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        mDataset = new String[indicatorEvaluation.length];
        mDataset2 = new String[grade.length];
        for (int i = 0; i < indicatorEvaluation.length; i++) {
            mDataset[i] = indicatorEvaluation[i];
            mDataset2[i] = grade[i];
        }
    }

	private void inisialisasiListener() {
        mOnArtikelClickListener = new RecyclerEvaluation.OnArtikelClickListener() {
            @Override
            public void onClick(int posisi) {
                try {
                    if (toast != null) {
                        toast.cancel();
                    }

                    Log.d("KLIK Posisi : ",posisi + "");

                    switch (posisi) {
                        case 0:
                            showInputDialog();
                            break;

                        case 1:
                            showInputDialog();
                            break;

                        case 2:
                            showInputDialog();
                            break;

                     }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
