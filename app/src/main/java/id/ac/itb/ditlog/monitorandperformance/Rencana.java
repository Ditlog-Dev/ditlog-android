package id.ac.itb.ditlog.monitorandperformance;

import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
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
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

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

        //final Calendar myCalendar = Calendar.getInstance();
        final Date date = new Date();

        EditText edittext= (EditText) findViewById(R.id.editDate);
        final DatePickerDialog.OnDateSetListener datePick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                date.setYear(year);
                date.setMonth(monthOfYear);
                date.setDate(dayOfMonth);
                mRecyclerView.getAdapter().notifyItemInserted(mMilestoneList.length());

            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Rencana.this, datePick,
                        date.getYear(), date.getMonth(), date.getDay() ).show();


            }
        });





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

        try {
            String spmkid = "632";
            URL url = new URL(BuildConfig.WEBSERVICE_URL+"/rencana/" + spmkid);
            Log.e("test", String.valueOf(url));
            //URL url = new URL("http://localhost:8080" +"/rencana/" + "632");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.e("test ", "a");
            urlConnection.setRequestMethod("GET");
            Log.e("test ", "b");
            urlConnection.setReadTimeout(1500);
            Log.e("test ", "c");
            urlConnection.setConnectTimeout(1500);
            Log.e("test ", "d");
            urlConnection.setDoOutput(true);
            Log.e("test ", "e");
            urlConnection.setDoInput(true);
            Log.e("test ", "f");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            Log.e("test ", "g");
            String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJva2kiLCJpZFVzZXIiOjEwNDAyLCJpZFJlc3BvbnNpYmlsaXR5Ijo3OSwiaWRWZW5kb3IiOjAsImV4cCI6MTUyMjc2OTU4N30.p_5dMPljD493mkqOrz6IFg5QDpwyjDikP241dsI5cuyuTQHHeg6G6KR3l9ALL7hpR0Gh7ArunvzC1k2TiQL94A";
            Log.e("test ", "h");
            urlConnection.setRequestProperty("Authorization", "Bearer "+token);
            Log.e("test ", "i");
            int responseStatusCode = urlConnection.getResponseCode();
            Log.e("test ", String.valueOf(responseStatusCode));


            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                Log.e("test ", "HTTP_ok");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
                JSONObject response = new JSONObject(result);

                int statusCode = response.getInt("code");
                Log.e("test ", String.valueOf(statusCode));
                if (statusCode == 200) {

                    mMilestoneList = response.getJSONArray("payload");

                    //checker
                    JSONObject sample = mMilestoneList.getJSONObject(0);
                    String tglRencana = sample.getString("tglRencana");
                    Log.e("test ", tglRencana);


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
                textKeterangan.setText("Belum sampai 100 %");
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
            itemA.put("tglRencana", "23/3/2018");
            itemA.put("persentaseRencana", "20");
            itemA.put("keteranganRencana", "servis mesin");
            itemA.put("statusRencana", "0");

            JSONObject itemB = new JSONObject();
            itemB.put("tglRencana", "23/4/2018");
            itemB.put("persentaseRencana", "40");
            itemB.put("keteranganRencana", "ganti ban");

            mMilestoneList.put(itemA);
            mMilestoneList.put(itemB);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
