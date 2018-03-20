package id.ac.itb.ditlog.monitorandperformance;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.hanks.htextview.typer.TyperTextView;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 5000; // 5 seconds delay
    private ImageView logoItb;
    private TextView textDitLog;
    private TextView textItb;
    private TyperTextView textPrepare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoItb = findViewById(R.id.logoITB);
        textDitLog = findViewById(R.id.ditlog);
        textItb = findViewById(R.id.itb);
        textPrepare = findViewById(R.id.preparing);

        logoItb.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));
        textDitLog.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fromleft));
        textItb.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fromright));
        textPrepare.setAnimation(AnimationUtils.loadAnimation(this, R.anim.frombottom));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textPrepare.animateText(getString(R.string.preparing));
            }
        }, 1500);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Nav-Drawer. */
                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);

                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
