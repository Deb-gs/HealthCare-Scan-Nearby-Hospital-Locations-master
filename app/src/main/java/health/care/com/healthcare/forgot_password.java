package health.care.com.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    EditText email_editText;
    Button resetbtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_editText=findViewById(R.id.forgot_email);
        resetbtn=findViewById(R.id.reset_btn);
        mAuth=FirebaseAuth.getInstance();

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_editText.getText().toString().trim();
                sendPasswordResetEmail(email);
            }
        });
    }
    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgot_password.this,"Password reset email has been sent to your email",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(forgot_password.this,Login.class));
                    finish();
                }
                else{
                    Toast.makeText(forgot_password.this,"Failed to send reset email",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}