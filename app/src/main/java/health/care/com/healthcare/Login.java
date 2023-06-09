package health.care.com.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    TextView createAccountBtnTextView,loginUsingPhNobtnTextview,forgotPassTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText=findViewById(R.id.email_edit_txt);
        passwordEditText=findViewById(R.id.password_edit_txt);
        loginBtn=findViewById(R.id.login_btn);
        progressBar=findViewById(R.id.progress_bar);
        createAccountBtnTextView=findViewById(R.id.create_account_text_view_btn);
        loginUsingPhNobtnTextview=findViewById(R.id.login_using_phNo_textview_btn);
        forgotPassTextView=findViewById(R.id.forgot_pass_edittxt_btn);

        loginBtn.setOnClickListener((v)-> loginUser());
        createAccountBtnTextView.setOnClickListener((v)-> startActivity(new Intent(Login.this,Create_account.class)));
        loginUsingPhNobtnTextview.setOnClickListener((v)->startActivity(new Intent(Login.this,mobileLogin_activity.class)));
        forgotPassTextView.setOnClickListener((v)->startActivity(new Intent(Login.this,forgot_password.class)));
    }
    void loginUser(){
        String email=emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        boolean isValidated=validateData(email,password);
        if(!isValidated){
            return;
        }

        loginAccountInFirebase(email,password);
    }

    void loginAccountInFirebase(String email,String password){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //go to ,main activity
                        startActivity(new Intent(Login.this,MainActivity.class));
                    }else{
                        Utility.showToast(Login.this,"Email not varified, Please verify your email");
                    }
                }else{
                    Utility.showToast(Login.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }

    }
    boolean validateData(String email,String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is invalid");
            return false;
        }
        if(password.length()<6){
            passwordEditText.setError("Password length is Invalid");
            return false;
        }
        return true;
    }
}