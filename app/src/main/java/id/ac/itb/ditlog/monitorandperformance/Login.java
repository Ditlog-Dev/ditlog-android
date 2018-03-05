package id.ac.itb.ditlog.monitorandperformance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;

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
        }
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.button);
        password = (EditText) findViewById(R.id.password);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    button.performClick();
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = (EditText) findViewById(R.id.username);
                password = (EditText) findViewById(R.id.password);
                if (validate(username.getText().toString(), password.getText().toString())) {
                    LoginTask task = new LoginTask();
                    task.execute();
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

    private class LoginTask extends AsyncTask<Void, Void, LoginWrapper> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(Login.this, "wait a minute....", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected LoginWrapper doInBackground(Void... voids) {
            try {
                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);

                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.reset();
                digest.update(password.getText().toString().getBytes());
                byte[] a = digest.digest();
                int len = a.length;
                StringBuilder sb = new StringBuilder(len << 1);
                for(int i=0;i<len;i++) {
                    sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                    sb.append(Character.forDigit(a[i] & 0x0f, 16));
                }

                String url = "";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                LoginInfo logininfo = new LoginInfo();
                logininfo.setUsername(username.getText().toString());
                logininfo.setPassword(sb.toString());
//                LoginResponse loginresponse = restTemplate.postForObject(url, logininfo, LoginResponse.class);
//                return loginresponse;

                //testdummy
                LoginResponse loginresponsedummy = new LoginResponse();
                loginresponsedummy.setSuccess("true");
                loginresponsedummy.setUserID("10");
                loginresponsedummy.setToken(sb.toString());

                LoginWrapper loginWrapper = new LoginWrapper(logininfo, loginresponsedummy);
                return loginWrapper;

            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(LoginWrapper loginresponse) {
            if (loginresponse.getLoginResponse().getSuccess().equals("true")){
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userid", loginresponse.getLoginResponse().getUserID());
                editor.putString("token", loginresponse.getLoginResponse().getToken());
                editor.putString("username", loginresponse.getLoginInfo().getUsername());
                editor.apply();

                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent_name);
                finish();
            }
            else{
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Login.this);

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
