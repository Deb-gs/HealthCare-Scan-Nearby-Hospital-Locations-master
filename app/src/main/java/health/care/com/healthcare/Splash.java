package health.care.com.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    TextView text;
    private static int splash_timeduration=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        text = findViewById(R.id.txt);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
                if(ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(Splash.this,permission_activity.class));
                }
                if(currentUser==null){
                    startActivity(new Intent(Splash.this,permission_activity.class));
                    finish();
                }else{
                    startActivity(new Intent(Splash.this,MainActivity.class));
                }

                finish();
            }
        },splash_timeduration);
        Animation myAnimation = AnimationUtils.loadAnimation(Splash.this,R.anim.animation1);
        text.startAnimation(myAnimation);
    }
}