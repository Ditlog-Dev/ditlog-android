package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class ViewContractPerformance extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView contractList;
    private RecyclerView.LayoutManager contractLayoutManager;
    private HttpURLConnection connection;
    private SwipeRefreshLayout swipeContainer;
    protected ContractAdapter contractAdapter;
    private String chosenYear="2018";
    private String auth = "";
    Context context = this;
    Activity act = this;

    // index to identify current nav menu item
    public static int navItemIndex = 2;

    // tags used to attach the activities
    private static final String TAG_WELCOME = "welcome";

    public static String CURRENT_TAG = TAG_WELCOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles = {"Home", "Monitor", "Kinerja"};

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contract_performance);

        ContractPerformancePreference preference = new ContractPerformancePreference();
        auth = preference.getToken(context);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_WELCOME;
            loadHomeFragment();
        }

        contractList = findViewById(R.id.contract_list);
        contractList.setHasFixedSize(false);
        contractLayoutManager = new LinearLayoutManager(this);
        contractList.setLayoutManager(contractLayoutManager);

        swipeContainer = findViewById(R.id.Swipe_container);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent,R.color.colorPrimary);
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                new AsyncGetContracts(chosenYear, getParent(), getParent(), auth).execute();
            }
        });

        if (!haveNetworkConnection()) {
            Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout.closeDrawers();
            return;
        }

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.rumah:
                        navItemIndex = 0;

                        startActivity(new Intent(ViewContractPerformance.this, HomeActivity.class));
                        overridePendingTransition(0,0);
                        drawerLayout.closeDrawers();
                        finish();

                        return true;
                    case R.id.realisasi:
                        navItemIndex = 1;
                        startActivity(new Intent(ViewContractPerformance.this, MonitorActivity.class));
                        overridePendingTransition(0,0);
                        drawerLayout.closeDrawers();
                        finish();

                        return true;

                    case R.id.penilaian:
                        navItemIndex = 2;
                        drawerLayout.closeDrawers();

                        return true;

                    default:
                        navItemIndex = 0;
                        drawerLayout.closeDrawers();
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        startActivity(new Intent(ViewContractPerformance.this, HomeActivity.class));
        overridePendingTransition(0,0);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        new AsyncGetContracts(chosenYear, this, this, auth).execute();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        MaterialSearchView searchView = findViewById(R.id.search_view);
        searchView.setMenuItem(item);

        getMenuInflater().inflate(R.menu.year_spinner, menu);

        MenuItem spn = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(spn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        chosenYear = (parent.getItemAtPosition(position)).toString();
                        new AsyncGetContracts(chosenYear, context, act, auth).execute();
                        //spinner.setOnItemSelectedListener(this);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //showToast("Spinner1: unselected");
                    }
                });
        return true;
    }

    public class AsyncGetContracts extends AsyncTask<Void, Void, ArrayList<ContractEntity>>{
        public String auth = "";
        private static final String SERVER_URL = BuildConfig.WEBSERVICE_URL;
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECTION_TIMEOUT = 15000;
        private String year;
        ArrayList<ContractEntity> params = new ArrayList<>();
        Context context;
        Activity act;

        public AsyncGetContracts(String year, Context context, Activity act, String auth) {
            this.year = year;
            this.context = context;
            this.act = act;
            this.auth = auth;
        }

        @Override
        protected ArrayList<ContractEntity> doInBackground(Void... voids) {
            String method = "GET";
            try {
                String rawUrl = SERVER_URL + "/contracts?tahun=" + year;
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
            swipeContainer.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(ArrayList<ContractEntity> params) {
            super.onPostExecute(params);

            contractAdapter = new ContractAdapter(params, act);

            // Set CustomAdapter as the adapter for RecyclerView.
            contractList.setAdapter(contractAdapter);
            swipeContainer.setRefreshing(false);
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
