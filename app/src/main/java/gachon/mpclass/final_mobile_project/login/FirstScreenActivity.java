package gachon.mpclass.final_mobile_project.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gachon.mpclass.final_mobile_project.R;

public class FirstScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        Button but_join=(Button)findViewById(R.id.first_join );
        Button but_login=(Button)findViewById(R.id.first_login);

        but_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstScreenActivity.this, gachon.mpclass.final_mobile_project.login.RegisterActivity.class));

            }
        });
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstScreenActivity.this,  gachon.mpclass.final_mobile_project.login.LoginActivity.class));

            }
        });
    }
}
