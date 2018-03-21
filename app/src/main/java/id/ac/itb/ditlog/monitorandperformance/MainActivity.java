package id.ac.itb.ditlog.monitorandperformance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
