package gachon.mpclass.final_mobile_project.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import gachon.mpclass.final_mobile_project.Main.MainActivity;
import gachon.mpclass.final_mobile_project.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button mLoginBtn;
    TextView mResigettxt;
    TextView mPasswordResettxt;
    EditText mEmailText, mPasswordText;
    private String tokenValue;

    private FirebaseAuth firebaseAuth;
    private RelativeLayout loaderLayout;


    // Firebase - Realtime Database








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
//        initFirebaseDatabase();


        firebaseAuth =  FirebaseAuth.getInstance();
        //버튼 및 텍스트 등록하기
        mResigettxt = findViewById(R.id.register_t2);
         mLoginBtn = findViewById(R.id.login_btn);
        mEmailText = findViewById(R.id.emailEt);
        mPasswordText = findViewById(R.id.passwordEdt);
        loaderLayout = findViewById(R.id.loaderLayout);

        //가입 버튼이 눌리면
        mResigettxt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(LoginActivity.this,  gachon.mpclass.final_mobile_project.login.RegisterActivity.class));

            }
        });





        //로그인 버튼이 눌리면
        mLoginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                loaderLayout.setVisibility(View.VISIBLE);
                String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                if (email.length() > 0 && pwd.length() > 0 ) {
                    firebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loaderLayout.setVisibility(View.GONE);


                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        loaderLayout.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



}
