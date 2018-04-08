package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 04/03/2018.
 */

public class AsyncAddIndicator extends AsyncTask<String,Void,String> {
    public String auth = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZXAiLCJyb2xlSWQiOjQyMiwiZXhwIjoxNTIyMzM2Mzg1fQ.3nai_tQNWLObap18t8YjZ-RrtisOhlPLq7kI_zDgy1Gq99VNdWTicQ5o-c8BPTh2ZPRxBOhIqumAaCc-8F9-2A";
    public static final String SERVER_URL = BuildConfig.WEBSERVICE_URL;
    public static final int READ_TIMEOUT = 1500;
    public static final int CONNECTION_TIMEOUT = 1500;
    private String indicator;
    private String status = "init";
    private Context mContext;
    private Activity act;

    public AsyncAddIndicator(String indicator, final Context context, Activity act) {
        mContext = context;
        this.indicator = indicator;
        this.act = act;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        if (!haveNetworkConnection()) {
            status = "Tidak ada koneksi internet";
            return "Tidak ada koneksi internet";
        } else {
            try {
                // build jsonObject
                JSONObject ind = new JSONObject();
                ind.put("name", indicator);

                URL url = new URL(SERVER_URL + "/indicators");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", auth);
                // read response
                urlConnection.setRequestMethod("POST");
                urlConnection.setReadTimeout(READ_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(ind.toString());
                wr.flush();
                wr.close();
                // try to get response
                int statusCode = urlConnection.getResponseCode();
                if(String.valueOf(urlConnection.getResponseCode()).startsWith("2")) {
                    status = "Indikator berhasil ditambahkan";
                } else if (String.valueOf(urlConnection.getResponseCode()).startsWith("4")){
                    status = "Akses tidak diotorisasi";
                } else {
                    status = "Server tidak tersedia";
                }
            } catch (Exception e) {
                status = "Server tidak tersedia";
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                return status;
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (!status.equals("init")) {
            Toast.makeText(mContext, status, Toast.LENGTH_LONG).show();
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) act.getSystemService(mContext.CONNECTIVITY_SERVICE);
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
