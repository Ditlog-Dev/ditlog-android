package id.ac.itb.ditlog.monitorandperformance;


import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Evaluation extends Fragment{

    private Toast toast;
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private HttpURLConnection connection = null;
    public String token = "";
    public int contractId = 0;
    public Indicator indicator;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
	private RecyclerEvaluation.OnArtikelClickListener mOnArtikelClickListener;
    protected RecyclerView evaluationRecyclerView;
    protected RecyclerEvaluation evaluationAdapter;
    protected RecyclerView.LayoutManager evaluationLayoutManager;
    protected SwipeRefreshLayout swipeContainer;

    ArrayList<EvaluationEntity> param = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evaluation, container, false);

        rootView.setTag(TAG);
        indicator = (Indicator) getActivity();
        token = indicator.auth;
        contractId = indicator.contractId;

        // BEGIN_INCLUDE(initializeRecyclerView)
        evaluationRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewEvaluation);
	
	    evaluationListener();

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

        //param.add(new EvaluationEntity(2, "test1", 10));
        //param.add(new EvaluationEntity(22, "test2", 15));

        evaluationAdapter = new RecyclerEvaluation(param);
        evaluationAdapter.setOnArtikelClickListener(mOnArtikelClickListener);
        evaluationRecyclerView.setAdapter(evaluationAdapter);

        if (!haveNetworkConnection()) {
            Toast.makeText(getContext(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }

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

	private void evaluationListener() {
        mOnArtikelClickListener = new RecyclerEvaluation.OnArtikelClickListener() {
            @Override
            public void onClick(int posisi) {
                try {
                    if (toast != null) {
                        toast.cancel();
                    }
                    showInputDialog();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public class evaluationGetter extends AsyncTask<Void, Void, ArrayList<EvaluationEntity>> {

        public String auth = "";
        public int contractId = 0;
        public static final String SERVER_URL = BuildConfig.WEBSERVICE_URL;
        public static final int READ_TIMEOUT = 10000;
        public static final int CONNECTION_TIMEOUT = 10000;

        int indicator_id = 0;
        String indicator_name = "default";
        int indicator_eval = 0;

        ArrayList<EvaluationEntity> params = new ArrayList<>();

        public evaluationGetter(String auth, int contractId) {
            this.auth = auth;
            this.contractId = contractId;
        }

        @Override
        protected ArrayList<EvaluationEntity> doInBackground(Void... voids) {
            String method = "GET";

            try {
                String rawUrl = SERVER_URL + "/contracts/"+ contractId +"/indicators";
                URL url = new URL(rawUrl);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Bearer "+auth);
                connection.setRequestMethod(method);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                InputStream is = connection.getInputStream();
                if(String.valueOf(connection.getResponseCode()).startsWith("2")){
                    InputStreamReader isReader = new InputStreamReader(is,"UTF-8");

                    JsonReader jsReader = new JsonReader(isReader);

                    jsReader.beginObject();
                    while(jsReader.hasNext()){
                        String name = jsReader.nextName();
                        if(name.equals("payload")){
                            parsePayload(jsReader);
                        }
                        else{
                            jsReader.skipValue();
                        }
                    }
                    jsReader.endObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Server tidak tersedia", Toast.LENGTH_LONG).show();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                return params;
            }
        }

        protected void parsePayload(JsonReader jsReader) throws IOException{
            String name;
            jsReader.beginArray();
            while (jsReader.hasNext()) {
                jsReader.beginObject();
                while (jsReader.hasNext()) {
                    name = jsReader.nextName();
                    switch (name){
                        case "idIndicator":
                            indicator_id = jsReader.nextInt();
                            break;
                        case "nilai":
                            indicator_eval = jsReader.nextInt();
                            break;
                        case "namaIndikator":
                            indicator_name = jsReader.nextString();
                            break;
                        default:
                            jsReader.skipValue();
                    }
                }
                params.add(new EvaluationEntity(indicator_id,indicator_name,indicator_eval));
                jsReader.endObject();
            }
            jsReader.endArray();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //swipeContainer.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(ArrayList<EvaluationEntity> mParam) {
            super.onPostExecute(mParam);
            evaluationAdapter = new RecyclerEvaluation(param);
            evaluationAdapter.setOnArtikelClickListener(mOnArtikelClickListener);
            evaluationRecyclerView.setAdapter(evaluationAdapter);
            //swipeContainer.setRefreshing(false);
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
