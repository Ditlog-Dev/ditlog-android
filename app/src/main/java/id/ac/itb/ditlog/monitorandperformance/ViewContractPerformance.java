package id.ac.itb.ditlog.monitorandperformance;

import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class ViewContractPerformance extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView contractList;
    private RecyclerView.Adapter contractAdapter;
    private RecyclerView.LayoutManager contractLayoutManager;
    private ArrayList<ContractEntity> contracts = new ArrayList<>();

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
        initContracts();
        contractAdapter = new ContractAdapter(contracts,this);
        contractList.setAdapter(contractAdapter);
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

    public void addContract(String number, String title, String vendor, String date, Float eval) {
        contracts.add(new ContractEntity(number,title,vendor,date,eval));
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

}
