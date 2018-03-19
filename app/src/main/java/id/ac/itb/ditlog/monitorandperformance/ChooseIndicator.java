package id.ac.itb.ditlog.monitorandperformance;


import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
public class ChooseIndicator extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private FloatingActionButton fab;
    private HttpURLConnection connection = null;

    @Override
    public void onRefresh() {
        new indicatorGetter().execute();
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView recyclerViewIndicator;
    protected RecyclerChooseIndicator indicatorAdapter;
    protected RecyclerView.LayoutManager indicatorLayoutManager;
    protected ArrayList<IndicatorEntity> mParam = new ArrayList<>();
    protected SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.new_indicator_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("TAMBAH", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String indicator = editText.getText().toString();
                        String status = "fail";
                        try {
                            status = new AsyncAddIndicator(indicator, getContext(), getActivity()).execute(indicator).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        if (status.equals("Indikator berhasil ditambahkan")) {
                            new indicatorGetter().execute();
                        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_choose_indicator, container, false);

        rootView.setTag(TAG);

        //fab tambah indikator baru
        fab = (FloatingActionButton) rootView.findViewById(R.id.tambah);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                showInputDialog();
            }
        });

        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerViewIndicator = (RecyclerView) rootView.findViewById(R.id.recyclerViewIndicator);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        indicatorLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        swipeContainer = rootView.findViewById(R.id.Swipe_container);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent,R.color.colorPrimary);
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                new indicatorGetter().execute();
            }
        });

        // END_INCLUDE(initializeRecyclerView)

        if (!haveNetworkConnection()) {
            Toast.makeText(getContext(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPositionParamIndicator = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerViewIndicator.getLayoutManager() != null) {
            scrollPositionParamIndicator = ((LinearLayoutManager) recyclerViewIndicator.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                indicatorLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                indicatorLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                indicatorLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerViewIndicator.setLayoutManager(indicatorLayoutManager);

        recyclerViewIndicator.scrollToPosition(scrollPositionParamIndicator);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onCheckboxClicked(View view) {
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

    public class indicatorGetter extends AsyncTask<Void, Void, ArrayList<IndicatorEntity>>{

        public String auth = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZXAiLCJyb2xlSWQiOjQyMiwiZXhwIjoxNTIyMzM2Mzg1fQ.3nai_tQNWLObap18t8YjZ-RrtisOhlPLq7kI_zDgy1Gq99VNdWTicQ5o-c8BPTh2ZPRxBOhIqumAaCc-8F9-2A";
        public static final String SERVER_URL = "159.65.131.168:8080";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        public int pageNumber=0;
        public int itemLimit=20;
        public String direction="asc";
        public String sortingKey="id";

        ArrayList<IndicatorEntity> params = new ArrayList<>();
        int totalPages=-1;
        int totalElements=-1;
        int numberOfElements=-1;
        int number=-1;

        @Override
        protected ArrayList<IndicatorEntity> doInBackground(Void... voids) {
            String method = "GET";
            try {
                String rawUrl = "http://" + SERVER_URL + "/indicators";
                rawUrl += "?page=" + String.valueOf(pageNumber);
                rawUrl += "&limit=" + String.valueOf(itemLimit);
                rawUrl +="&dir=" + direction;
                rawUrl +="&sort=" + sortingKey;
                URL url = new URL(rawUrl);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", auth);
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
            jsReader.beginObject();
            while(jsReader.hasNext()) {
                name = jsReader.nextName();
                if(name.equals("content")) {
                    parseContent(jsReader);
                }
                else if(name.equals("totalPages")){
                    totalPages = jsReader.nextInt();
                }
                else if(name.equals("totalElements")){
                    totalElements = jsReader.nextInt();
                }
                else if(name.equals("numberOfElements")){
                    numberOfElements = jsReader.nextInt();
                }
                else if(name.equals("number")){
                    number = jsReader.nextInt();
                }
                else{
                    jsReader.skipValue();
                }
            }
            jsReader.endObject();

            Log.d("totalPages", String.valueOf(totalPages));
            Log.d("totalElements", String.valueOf(totalElements));
            Log.d("numberOfElements", String.valueOf(numberOfElements));
            Log.d("number", String.valueOf(number));
        }

        protected void parseContent(JsonReader jsReader)throws IOException{
            String name;
            jsReader.beginArray();
            while (jsReader.hasNext()) {
                jsReader.beginObject();
                int id=-1;
                String indName="";
                int idUser=-1;

                while (jsReader.hasNext()) {
                    name = jsReader.nextName();
                    switch (name){
                        case "id":
                            id = jsReader.nextInt();
                            break;
                        case "name":
                            indName = jsReader.nextString();
                            break;
                        case "idUser":
                            try {
                                idUser = jsReader.nextInt();
                            } catch (RuntimeException e) {
                                jsReader.skipValue();
                            }
                            break;
                        default:
                            jsReader.skipValue();
                    }
                }
                params.add(new IndicatorEntity(id,indName,idUser));
                jsReader.endObject();
            }
            jsReader.endArray();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeContainer.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(ArrayList<IndicatorEntity> mParam) {
            super.onPostExecute(mParam);

            indicatorAdapter = new RecyclerChooseIndicator(mParam);

            // Set CustomAdapter as the adapter for RecyclerView.
            recyclerViewIndicator.setAdapter(indicatorAdapter);
            swipeContainer.setRefreshing(false);
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