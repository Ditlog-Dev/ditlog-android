package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Indicator extends AppCompatActivity {
    private ContractPerformancePreference performancePreference = new ContractPerformancePreference();
    public String auth = "";
    public int contractId = 0;
    public Activity act = this;
    public Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        auth = performancePreference.getToken(context);
        contractId = performancePreference.getContractId(context);
        TextView number = findViewById(R.id.noKontrak);
        TextView title = findViewById(R.id.judulKontrak);
        TextView vendor = findViewById(R.id.namaVendor);
        TextView date = findViewById(R.id.tglKontrak);
        number.setText(performancePreference.getNumber(this));
        title.setText(performancePreference.getTitle(this));
        vendor.setText(performancePreference.getVendor(this));
        date.setText(performancePreference.getDate(this));
        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.evaluation));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.indicator));
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Using PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        // This is another example of the adapter pattern.
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(0,0);
        finish();
        super.onBackPressed();
    }
}
