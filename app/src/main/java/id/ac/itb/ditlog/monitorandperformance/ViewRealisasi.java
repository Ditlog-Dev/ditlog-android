package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewRealisasi extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RealisasiListAdapter mAdapter;
    private EditRealisasiListAdapter mEditAdapter;
    private JSONArray mMilestoneList = new JSONArray();
    Button submit;

    String token;
    Long role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realisasi);

        submit = findViewById(R.id.submit);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "");
        role = sharedPreferences.getLong("roleid", 0);

        dummyData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewRealisasi);
        // Create an adapter and supply the data to be displayed.
        if (role != 118) {
            submit.setVisibility(View.GONE);
            mAdapter = new RealisasiListAdapter(this, mMilestoneList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mEditAdapter = new EditRealisasiListAdapter(this, mMilestoneList);
            mRecyclerView.setAdapter(mEditAdapter);
        }
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 0;
                for (int i=0; i < mMilestoneList.length(); i++){
                    try {
                        x = x + mMilestoneList.getJSONObject(i).getInt("persentaseRencana");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (x > 100)
                    Toast.makeText(getApplicationContext(), "Invalid percentage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(){

        try {
            String spmkid = "632";
            URL url = new URL(BuildConfig.WEBSERVICE_URL+"/realisasi/" + spmkid);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1500);
            urlConnection.setConnectTimeout(1500);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
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

    private void dummyData(){

        try {
            JSONObject itemA = new JSONObject();
            itemA.put("tglRencana", "1/3/2018");
            itemA.put("persentaseRencana", "10");
            itemA.put("keteranganRencana", "ket1");
            itemA.put("statusRencana", "0");

            JSONObject itemB = new JSONObject();
            itemB.put("tglRencana", "3/4/2018");
            itemB.put("persentaseRencana", "");
            itemB.put("keteranganRencana", "");

            mMilestoneList.put(itemA);
            mMilestoneList.put(itemB);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
