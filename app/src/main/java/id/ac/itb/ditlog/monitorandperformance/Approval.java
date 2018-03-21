package id.ac.itb.ditlog.monitorandperformance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Approval extends AppCompatActivity {

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);

        inflater = LayoutInflater.from(getApplicationContext());

        LinearLayout linearLayoutRencana = (LinearLayout) findViewById(R.id.layoutRencana);
        LinearLayout linearLayout1 = (LinearLayout) inflater.inflate(R.layout.date_percentage_approval, null);
        TextView date1 = (TextView) linearLayout1.findViewById(R.id.date);
        date1.setText("25/3/2018");

        TextView percentage1 = (TextView) linearLayout1.findViewById(R.id.percentage);
        percentage1.setText("10%");
        linearLayoutRencana.addView(linearLayout1);

        LinearLayout linearLayout2 = (LinearLayout) inflater.inflate(R.layout.date_percentage_approval, null);
        TextView date2 = (TextView) linearLayout2.findViewById(R.id.date);
        date2.setText("27/3/2018");

        TextView percentage2 = (TextView) linearLayout2.findViewById(R.id.percentage);
        percentage2.setText("50%");
        linearLayoutRencana.addView(linearLayout2);


    }
}
