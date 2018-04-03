package id.ac.itb.ditlog.monitorandperformance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SharedPreferences sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(getApplicationContext());
    Long userID = sharedPreferences.getLong("userid", -1);
    if (userID == -1) {
      Intent myIntent = new Intent(this, Login.class);
      startActivity(myIntent);
      finish();
    } else {
      TextView hello = findViewById(R.id.hello);
      hello.setText("Hello " + sharedPreferences.getString("username", "-1"));
      Button logout = findViewById(R.id.logout);
      logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          SharedPreferences sharedPreferences = PreferenceManager
              .getDefaultSharedPreferences(getApplicationContext());
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.clear();
          editor.apply();
          Intent myIntent = new Intent(getApplicationContext(), Login.class);
          startActivity(myIntent);
          finish();
        }
      });

      Button approval = findViewById(R.id.button);

      approval.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent_name = new Intent();
            intent_name.setClass(getApplicationContext(), Approval.class);
            startActivity(intent_name);
        }
      });
    }
  }
}
