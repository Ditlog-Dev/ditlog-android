package id.ac.itb.ditlog.monitorandperformance;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class Rencana extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RencanaListAdapter mAdapter;
    private JSONArray mMilestoneList = new JSONArray();
    private LayoutInflater inflater;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rencana);


        // Create recycler view.
        //loadData();
        dummyData();

        inflater = LayoutInflater.from(getApplicationContext());
        inflateKeterangan();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewRencana);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new RencanaListAdapter(this, mMilestoneList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add a floating action click handler for creating new entries.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add a new word to the wordList.

                JSONObject addBlank = new JSONObject();

                mMilestoneList.put(addBlank);
                // Notify the adapter, that the data has changed.
                mRecyclerView.getAdapter().notifyItemInserted(mMilestoneList.length());
                // Scroll to the bottom.
                mRecyclerView.smoothScrollToPosition(mMilestoneList.length());

            }
        });
    }

    private void loadData(){

        //URL url = new URL(BuildConfig.WEBSERVICE_URL+"/rencana/" + spmkid);
        try {
            URL url = new URL("http://localhost:8080" +"/rencana/" + "632");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1500);
            urlConnection.setConnectTimeout(1500);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJva2kiLCJpZFVzZXIiOjEwNDAyLCJpZFJlc3BvbnNpYmlsaXR5Ijo3OSwiaWRWZW5kb3IiOjAsImV4cCI6MTUyMjc2OTU4N30.p_5dMPljD493mkqOrz6IFg5QDpwyjDikP241dsI5cuyuTQHHeg6G6KR3l9ALL7hpR0Gh7ArunvzC1k2TiQL94A";
            urlConnection.setRequestProperty("Authorization", "Bearer "+token);

            int responseStatusCode = urlConnection.getResponseCode();
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
                JSONObject response = new JSONObject(result);

                int statusCode = response.getInt("code");

                if (statusCode == 200) {

                    mMilestoneList = response.getJSONArray("payload");

                } else {

                }
            } else {
                System.out.println("WEBSERVICE_ERROR : " + urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
                System.out.println("WEBSERVICE_ERROR : " + result);

            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void inflateKeterangan(){

        try {
            JSONObject sample = mMilestoneList.getJSONObject(0);

            if (sample.getInt("statusRencana") ==0 ){
                LinearLayout layoutKeterangan = (LinearLayout) inflater.inflate(R.layout.keterangan_rencana, null);
                TextView textKeterangan = (TextView) layoutKeterangan.findViewById(R.id.textKeterangan);
                //textKeterangan.setText(sample.getInt("keterangan"));
                LinearLayout linearLayoutRencana;
                textKeterangan.setText("ada keterangan");
                linearLayoutRencana = (LinearLayout) findViewById(R.id.keteranganRencanaContainer);
                linearLayoutRencana.addView(layoutKeterangan);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void dummyData(){

        try {
            JSONObject itemA = new JSONObject();
            itemA.put("tglRencana", "1510041600000");
            itemA.put("persentaseRencana", "20");
            itemA.put("keteranganRencana", "ket1");
            itemA.put("statusRencana", "0");

            JSONObject itemB = new JSONObject();
            itemB.put("tglRencana", "1510042600000");
            itemB.put("persentaseRencana", "40");
            itemB.put("keteranganRencana", "ket2");

            mMilestoneList.put(itemA);
            mMilestoneList.put(itemB);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
