package id.ac.itb.ditlog.monitorandperformance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

  Button button;
  EditText username;
  EditText password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      this.getSupportActionBar().hide();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    setContentView(R.layout.activity_login);
    button = findViewById(R.id.button);
    password = findViewById(R.id.password);

    password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId
            == EditorInfo.IME_ACTION_DONE)) {
          //do what you want on the press of 'done'
          button.performClick();
        }
        return false;
      }
    });

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        if (haveConnection()) {
          if (validate(username.getText().toString(), password.getText().toString())) {
            LoginTask task = new LoginTask();
            task.execute();
          }
        } else {
          Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
        }

      }
    });
  }

  private boolean validate(String username, String password) {
    if (username == null || username.trim().length() == 0) {
      Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (password == null || password.trim().length() == 0) {
      Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  private boolean haveConnection() {
    final ConnectivityManager connMgr = (ConnectivityManager) this
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    return (wifi.isConnectedOrConnecting() || mobile.isConnectedOrConnecting());

  }

  private class LoginTask extends AsyncTask<Void, Void, LoginWrapper> {

    @Override
    protected void onPreExecute() {
      Toast.makeText(Login.this, "Please wait", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected LoginWrapper doInBackground(Void... voids) {
      try {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        LoginInfo logininfo = new LoginInfo();
        logininfo.setUsername(username.getText().toString());
        logininfo.setPassword(password.getText().toString());

        JSONObject request = new JSONObject();
        request.put("username", logininfo.getUsername());
        request.put("password", logininfo.getPassword());

        URL url = new URL(BuildConfig.WEBSERVICE_URL + "/login");

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setReadTimeout(1500);
        urlConnection.setConnectTimeout(1500);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(request.toString());
        wr.flush();
        wr.close();

        int responseStatusCode = urlConnection.getResponseCode();
        if (responseStatusCode == HttpURLConnection.HTTP_OK) {
          InputStream in = new BufferedInputStream(urlConnection.getInputStream());
          String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
          JSONObject response = new JSONObject(result);

          int statusCode = response.getInt("code");

          if (statusCode == HttpURLConnection.HTTP_OK) {
            JSONObject payload = response.getJSONObject("payload");
            long userid = payload.getLong("idUser");
            long roleid = payload.getLong("idResponsibility");
            String token = payload.getString("jwtToken");
            UserPayload userpayload = new UserPayload(userid, roleid, token);
            return new LoginWrapper(logininfo, userpayload);
          } else {
            return new LoginWrapper();
          }
        } else if (responseStatusCode == HttpURLConnection.HTTP_FORBIDDEN) {
          return new LoginWrapper();
        } else {
          System.out.println("WEBSERVICE_ERROR : " + urlConnection.getResponseCode());
          InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
          String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
          System.out.println("WEBSERVICE_ERROR : " + result);
          return null;
        }

      } catch (Exception e) {
        Log.e("LoginActivity", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(LoginWrapper loginresponse) {
      if (loginresponse == null) {
        Handler error = new Handler(getApplicationContext().getMainLooper());
        error.post(new Runnable() {
          public void run() {
            Toast.makeText(getApplicationContext(), "Can't connect to server", Toast.LENGTH_SHORT)
                .show();
          }
        });
        return;
      }
      if (loginresponse.getPayload().idUser != -1) {
        SharedPreferences sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        UserPayload p = loginresponse.getPayload();
        editor.putLong("userid", p.idUser);
        editor.putLong("roleid", p.roleId);
        editor.putString("token", p.jwtToken);
        editor.putString("username", loginresponse.getLoginInfo().getUsername());
        editor.apply();
        editor.commit();

        Intent intent_name = new Intent();
        intent_name.setClass(getApplicationContext(), HomeActivity.class);
        startActivity(intent_name);
        finish();
      } else {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Login.this);

        dlgAlert.setMessage("Incorrect username or password");
        dlgAlert.setTitle("Try Again");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {

          }
        });
      }
    }

  }
}
