package id.ac.itb.ditlog.monitorandperformance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userID = sharedPreferences.getString("userid", "-1");
        if (userID.equals("-1")){
            Intent myIntent = new Intent(this, Login.class);
            startActivity(myIntent);
        }
        else
            Toast.makeText(MainActivity.this, "userid: " + userID, Toast.LENGTH_SHORT).show();
    }
}
