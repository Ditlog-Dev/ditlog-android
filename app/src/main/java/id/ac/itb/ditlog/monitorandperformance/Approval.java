package id.ac.itb.ditlog.monitorandperformance;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Approval extends AppCompatActivity {

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);

        inflater = LayoutInflater.from(getApplicationContext());

        LinearLayout linearLayoutRencana = (LinearLayout) findViewById(R.id.layoutApproval);
        LinearLayout linearLayout1 = (LinearLayout) inflater.inflate(R.layout.date_percentage_approval, null);
        TextView date1 = (TextView) linearLayout1.findViewById(R.id.dateApproval);
        date1.setText("25/3/2018");

        TextView percentage1 = (TextView) linearLayout1.findViewById(R.id.percentageApproval);
        percentage1.setText("10%");
        linearLayoutRencana.addView(linearLayout1);

        LinearLayout linearLayout2 = (LinearLayout) inflater.inflate(R.layout.date_percentage_approval, null);
        TextView date2 = (TextView) linearLayout2.findViewById(R.id.dateApproval);
        date2.setText("27/3/2018");

        TextView percentage2 = (TextView) linearLayout2.findViewById(R.id.percentageApproval);
        percentage2.setText("50%");
        linearLayoutRencana.addView(linearLayout2);


        //-----------------------------------------------------
        //Click Button
        Button approve = findViewById(R.id.acceptButtonApproval);
        Button reject = findViewById(R.id.rejectButtonApproval);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haveConnection()) {
                    ApproveTask task = new ApproveTask();
                    task.approved = true;
                    task.execute();
                } else
                    Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haveConnection()) {
                    ApproveTask task = new ApproveTask();
                    task.approved = false;
                    task.execute();
                } else
                    Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean haveConnection() {
        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifi.isConnectedOrConnecting() || mobile.isConnectedOrConnecting());
    }

    private class ApproveTask extends AsyncTask<Void, Void, Boolean> {

        boolean approved;

        @Override
        protected void onPreExecute() {
            Toast.makeText(Approval.this, "Please wait", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
//                JSONObject request= new JSONObject();
//                request.put("idSkpm", 1);
//                if (approved)
//                    request.put("status", "APPROVED");
//                else
//                    request.put("status", "REJECTED");
//
//                URL url = new URL(BuildConfig.WEBSERVICE_URL + "/skpm");
//
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setReadTimeout(1500);
//                urlConnection.setConnectTimeout(1500);
//                urlConnection.setDoOutput(true);
//                urlConnection.setDoInput(true);
//                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//
//                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
//                wr.writeBytes(request.toString());
//                wr.flush();
//                wr.close();
//
//                int responseStatusCode = urlConnection.getResponseCode();
//                if (responseStatusCode == HttpURLConnection.HTTP_OK){
//                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                    String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
//                    JSONObject response = new JSONObject(result);
//
//                    int statusCode = response.getInt("code");
//
//                    if (statusCode == 200) {
//                        return approved;
//                    }
//                    else{
//                        return null;
//                    }
//                } else {
//                    System.out.println("WEBSERVICE_ERROR : " + urlConnection.getResponseCode());
//                    InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
//                    String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
//                    System.out.println("WEBSERVICE_ERROR : " + result);
//                    return null;
//                }

                //test tanpa webservice
                return approved;

            } catch (Exception e) {
                Log.e("ApprovalActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean approved) {
            if (approved == null) {
                Handler error = new Handler(getApplicationContext().getMainLooper());
                error.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            } else {
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Approval.this);

                if (approved) {
                    Handler toast = new Handler(getApplicationContext().getMainLooper());
                    toast.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "SPMK is approved", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    dlgAlert.setMessage("SPMK is approved");
                } else {
                    Handler toast = new Handler(getApplicationContext().getMainLooper());
                    toast.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "SPMK is rejected", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    dlgAlert.setMessage("SPMK is rejected");
                }
//                dlgAlert.setPositiveButton("OK", null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.create().show();
//                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
                finish();
            }
        }
    }
}
