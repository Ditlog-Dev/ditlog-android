package id.ac.itb.ditlog.monitorandperformance;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 04/03/2018.
 */

public class AsyncAddIndicator extends AsyncTask<String,Void,String> {
    public static final String SERVER_URL = "159.65.131.168:8080";
    public static final int READ_TIMEOUT = 3000;
    public static final int CONNECTION_TIMEOUT = 3000;
    private String indicator;
    private String status = "init";
    private Context mContext;

    public AsyncAddIndicator(String indicator, final Context context) {
        mContext = context;
        this.indicator = indicator;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        try {
            // build jsonObject
            JSONObject ind = new JSONObject();
            ind.put("name", indicator);

            URL url = new URL("http://" + SERVER_URL + "/indicators");
            urlConnection = (HttpURLConnection) url.openConnection();
            // read response
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //urlConnection.getOutputStream();
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(ind.toString());
            wr.flush();
            wr.close();
            // try to get response
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                status = "Indikator berhasil ditambahkan";
            } else {
                status = "Operasi gagal";
            }
        }
        catch(Exception e)
        {
            status = "Operasi gagal";
            e.printStackTrace();
        }
        finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return status;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (!status.equals("init")) {
            Toast.makeText(mContext, status, Toast.LENGTH_SHORT).show();
        }
    }
}
