package id.ac.itb.ditlog.monitorandperformance;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;

public class ViewContractPerformance extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView contractList;
    private RecyclerView.Adapter contractAdapter;
    private RecyclerView.LayoutManager contractLayoutManager;
    private ArrayList<ContractEntity> contracts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contract_performance);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.navigation_menu);
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
        contractAdapter = new ContractAdapter(contracts);
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
        contracts = new ArrayList<>();
        contracts.add(new ContractEntity("contract 1","vendor1",new Date(1997,4,18),1.36f));
        contracts.add(new ContractEntity("contract 2","vendor2",new Date(1997,8,27),0.19f));
        contracts.add(new ContractEntity("contract 3","vendor3",new Date(1997,5,5),0.67f));
    }
}
