package id.ac.itb.ditlog.monitorandperformance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
            finish();
        }
        else {
            Toast.makeText(MainActivity.this, "Welcome " + sharedPreferences.getString("username", "-1"), Toast.LENGTH_SHORT).show();
//            Toast.makeText(MainActivity.this, "userid: " + userID, Toast.LENGTH_SHORT).show();
//            Toast.makeText(MainActivity.this, "token: " + sharedPreferences.getString("token", "-1"), Toast.LENGTH_SHORT).show();
            TextView hello = (TextView) findViewById(R.id.hello);
            hello.setText("Hello " + sharedPreferences.getString("username", "-1"));
            Button logout = (Button) findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent myIntent = new Intent(getApplicationContext(), Login.class);
                    startActivity(myIntent);
                    finish();
                }
            });
        }


    }

    //Logout setiap keluar dari aplikasi
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//    }

}
