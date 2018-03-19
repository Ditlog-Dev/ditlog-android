package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class ViewContractPerformance extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private DrawerLayout drawerLayout;
    private RecyclerView contractList;
    //private RecyclerView.Adapter contractAdapter;
    private RecyclerView.LayoutManager contractLayoutManager;
    private ArrayList<ContractEntity> contracts = new ArrayList<>();
    private HttpURLConnection connection;
    protected SwipeRefreshLayout swipeContainer;
    protected ContractAdapter contractAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contract_performance);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        contractList = findViewById(R.id.contract_list);
        contractList.setHasFixedSize(false);
        contractLayoutManager = new LinearLayoutManager(this);
        contractList.setLayoutManager(contractLayoutManager);
        /*initContracts();
        contractAdapter = new ContractAdapter(contracts,this);
        contractList.setAdapter(contractAdapter);*/

        if (!haveNetworkConnection()) {
            Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        } else {
            new AsyncGetContracts("2018", this, this).execute();
        }
    }

    @Override
    public void onRefresh() {
        new AsyncGetContracts("2018", this, this).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initContracts(){
        contracts.add(new ContractEntity("45/kontrak","Pengadaan test","Tetran, CV","01/12/2017",80));
        contracts.add(new ContractEntity("35/spk-tinta/2018","Pengadaan tinta 2018","CV. LOMBOK ABADI","30/10/2017",80));
        contracts.add(new ContractEntity("43/kontrak/2018", "Pengadaan jasa desain seragam batik","RUMAH BATIK KOMAR","20/04/2018",0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        MaterialSearchView searchView = findViewById(R.id.search_view);
        searchView.setMenuItem(item);

        getMenuInflater().inflate(R.menu.year_spinner, menu);

        MenuItem spn = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(spn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        return true;
    }

    public class AsyncGetContracts extends AsyncTask<Void, Void, ArrayList<ContractEntity>>{
        public String auth = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZXAiLCJyb2xlSWQiOjQyMiwiZXhwIjoxNTIyMzM2Mzg1fQ.3nai_tQNWLObap18t8YjZ-RrtisOhlPLq7kI_zDgy1Gq99VNdWTicQ5o-c8BPTh2ZPRxBOhIqumAaCc-8F9-2A";
        private static final String SERVER_URL = "159.65.131.168:8080";
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECTION_TIMEOUT = 15000;
        private String year;
        ArrayList<ContractEntity> params = new ArrayList<>();
        Context context;
        Activity act;

        public AsyncGetContracts(String year, Context context, Activity act) {
            this.year = year;
            this.context = context;
            this.act = act;
        }

        @Override
        protected ArrayList<ContractEntity> doInBackground(Void... voids) {
            String method = "GET";
            try {
                String rawUrl = "http://" + SERVER_URL + "/?tahun=" + year;
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
                Log.d("logd", "logd " + e);
                Toast.makeText(context, "Server tidak tersedia", Toast.LENGTH_LONG).show();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                return params;
            }
        }

        protected void parsePayload(JsonReader jsReader) throws IOException {

            String name;
            jsReader.beginArray();
            while(jsReader.hasNext()) {
                jsReader.beginObject();
                String number = "";
                String name_contract = "";
                String vendor = "";
                String date = "";
                float eval = 0;

                while (jsReader.hasNext()) {
                    name = jsReader.nextName();
                    switch (name){
                        case "noKontrak":
                            number = jsReader.nextString();
                            break;
                        case "nmHPS":
                            name_contract = jsReader.nextString();
                            break;
                        case "namaPenyedia":
                            vendor = jsReader.nextString();
                            break;
                        case "tanggalKontrak":
                            date = jsReader.nextString();
                            break;
                        case "nilaiRataRata":
                            eval = Float.parseFloat(jsReader.nextString());
                            break;
                        default:
                            jsReader.skipValue();
                    }
                }
                params.add(new ContractEntity(number,name_contract,vendor,date,eval));
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
        protected void onPostExecute(ArrayList<ContractEntity> params) {
            super.onPostExecute(params);

            contractAdapter = new ContractAdapter(params, act);

            // Set CustomAdapter as the adapter for RecyclerView.
            contractList.setAdapter(contractAdapter);
            //swipeContainer.setRefreshing(false);
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
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
