package gachon.mpclass.final_mobile_project.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import gachon.mpclass.final_mobile_project.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    Fragment menu1Fragment;
    Fragment menu2Fragment;
    Fragment menu3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        menu1Fragment = new FragmentMypage();
        menu2Fragment = new FragmentHome();
        menu3Fragment = new FragmentSearch();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,menu1Fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_1: getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,menu1Fragment).commit();
                return true; case R.id.navigation_2: getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,menu2Fragment).commit();
                return true; case R.id.navigation_3: getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,menu3Fragment).commit();
                return true;
            }
                return false;
        }
    };

}