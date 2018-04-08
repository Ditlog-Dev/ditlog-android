package id.ac.itb.ditlog.monitorandperformance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MonitorActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 1;

    // tags used to attach the activities
    private static final String TAG_WELCOME = "fhome";

    public static String CURRENT_TAG = TAG_WELCOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles = {"Home", "Monitor", "Kinerja"};

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        SharedPreferences sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(getApplicationContext());
        Long userID = sharedPreferences.getLong("userid", -1);
        if (userID == -1) {
          Intent myIntent = new Intent(this, Login.class);
          startActivity(myIntent);
          finish();
        } else {
          TextView hello = findViewById(R.id.hello);
          hello.setText("Hello " + sharedPreferences.getString("username", "-1"));
          Button logout = findViewById(R.id.logout);
          logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              SharedPreferences sharedPreferences = PreferenceManager
                  .getDefaultSharedPreferences(getApplicationContext());
              SharedPreferences.Editor editor = sharedPreferences.edit();
              editor.clear();
              editor.apply();
              Intent myIntent = new Intent(getApplicationContext(), Login.class);
              startActivity(myIntent);
              finish();
            }
          });

            Button viewrealisasi = findViewById(R.id.viewrealisasi_button);

            viewrealisasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_name = new Intent();
                    intent_name.setClass(getApplicationContext(), ViewRealisasi.class);
                    startActivity(intent_name);
                }
            });

          Button approval = findViewById(R.id.approval_button);

          approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent_name = new Intent();
              intent_name.setClass(getApplicationContext(), Approval.class);
              startActivity(intent_name);
            }
          });

          Button rencana = findViewById(R.id.rencana_button);

          rencana.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
              Intent intent_name = new Intent();
              intent_name.setClass(getApplicationContext(), Rencana.class);
              startActivity(intent_name);
              }
          });
        }

        // load toolbar titles from string resources
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_WELCOME;
            loadHomeFragment();
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
            drawer.closeDrawers();
            return;
        }

        //Closing drawer on item click
        drawer.closeDrawers();

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

                        startActivity(new Intent(MonitorActivity.this, HomeActivity.class));
                        overridePendingTransition(0,0);
                        drawer.closeDrawers();
                        finish();

                        return true;
                    case R.id.realisasi:
                        navItemIndex = 1;
                        drawer.closeDrawers();

                        return true;

                    case R.id.penilaian:
                        navItemIndex = 2;
                        startActivity(new Intent(MonitorActivity.this, ViewContractPerformance.class));
                        overridePendingTransition(0,0);
                        finish();

                        drawer.closeDrawers();

                        return true;

                    default:
                        navItemIndex = 0;
                        drawer.closeDrawers();
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


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        startActivity(new Intent(MonitorActivity.this, HomeActivity.class));
        overridePendingTransition(0,0);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
