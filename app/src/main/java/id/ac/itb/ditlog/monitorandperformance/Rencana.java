package id.ac.itb.ditlog.monitorandperformance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Rencana extends AppCompatActivity {

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rencana);

        inflater = LayoutInflater.from(getApplicationContext());

        LinearLayout linearLayoutRencana = (LinearLayout) findViewById(R.id.layoutRencana);
        LinearLayout linearLayout1 = (LinearLayout) inflater.inflate(R.layout.date_percentage_rencana, null);
        EditText date1 = (EditText) linearLayout1.findViewById(R.id.editDate);
        date1.setText("25/3/2018");

        EditText percentage1 = (EditText) linearLayout1.findViewById(R.id.editPercentage);
        percentage1.setText("10%");
        linearLayoutRencana.addView(linearLayout1);

        LinearLayout linearLayout2 = (LinearLayout) inflater.inflate(R.layout.date_percentage_rencana, null);
        EditText date2 = (EditText) linearLayout2.findViewById(R.id.editDate);
        date2.setText("27/3/2018");

        EditText percentage2 = (EditText) linearLayout2.findViewById(R.id.editPercentage);
        percentage2.setText("50%");
        linearLayoutRencana.addView(linearLayout2);


    }
}
