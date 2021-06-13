package gachon.mpclass.final_mobile_project.Show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
 import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

 import gachon.mpclass.final_mobile_project.Main.MainActivity;

import gachon.mpclass.final_mobile_project.R;
  import gachon.mpclass.final_mobile_project.reservation.Date_reservation;
import gachon.mpclass.final_mobile_project.reservation.ReservationActivity;
import gachon.mpclass.final_mobile_project.useperformancedata.ProcessOpenData;

public class DetailShowActivity extends AppCompatActivity {

    public void setListViewHeightBasedOnItems(ListView listView) {

        // Get list adpter of listview;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)  return;

        int numberOfItems = listAdapter.getCount();

        // Get total height of all items.
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() *  (numberOfItems - 1);

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        //Log.v("h",Integer.toString(params.height));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    Intent intent1;
    Intent intent2;
    ImageView poster;
    TextView name;
    TextView genre;
    TextView place;
    TextView time;
    TextView age;
    TextView ticketprice;
    TextView duration;
    TextView cast;
    TextView crew;
    TextView start;
    TextView end;
    ListView introduction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show);
        poster=findViewById(R.id.imageView2);
        name=findViewById(R.id.tv_detailTitle);
        genre=findViewById(R.id.tv_detailGenre);
        place=findViewById(R.id.tv_detailPlace);
        time=findViewById(R.id.tv_time);
        age=findViewById(R.id.tv_age);
        ticketprice=findViewById(R.id.tv_detailPrice);
        duration=findViewById(R.id.tv_detailDuration);
        cast=findViewById(R.id.tv_cast);
        crew=findViewById(R.id.tv_crew);
        start=findViewById(R.id.tv_detailStart);
        end=findViewById(R.id.tv_detailEnd);
        introduction= findViewById(R.id.recycler3);
        intent1 = new Intent(getBaseContext(), MainActivity.class);
        intent2 = new Intent(getBaseContext(), Date_reservation.class);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation2);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent getIntent = getIntent();
        String id = getIntent.getStringExtra("id");
        Log.v("id",id);
        ProcessOpenData pd= new ProcessOpenData();
        String data = pd.viewDataDetail(id);
        ArrayList<String> imagelist= new ArrayList<>();
        String[] dataArray = data.split("\n");
        for (int i = 0; i < dataArray.length; i = i + 1) {
            if (dataArray[i].contains("포스터이미지경로 :")) {
                Glide.with(this).load(dataArray[i].replace("포스터이미지경로 :","")).into(poster);
            }
            if (dataArray[i].contains("공연명 :"))
            {
                intent2.putExtra("title",dataArray[i].replace("공연명 :",""));
                name.setText(dataArray[i].replace("공연명 :",""));
            }
            if (dataArray[i].contains("장르 :"))
            {

                genre.setText(dataArray[i]);
            }
            if (dataArray[i].contains("장소 :"))
            {

                intent2.putExtra("place",dataArray[i].replace("장소 :",""));
                place.setText(dataArray[i]);
            }
            if (dataArray[i].contains("공연시간 :"))
            {
                time.setText(dataArray[i]);
            }
            if (dataArray[i].contains("관람연령 :"))
            {
                age.setText(dataArray[i]);
            }
            if (dataArray[i].contains("티켓 가격 :"))
            {
                intent2.putExtra("price",dataArray[i].replace("티켓 가격 :",""));
                ticketprice.setText(dataArray[i]);
            }
            if (dataArray[i].contains("공연 출연진 :"))
            {
                cast.setText(dataArray[i]);
            }
            if (dataArray[i].contains("공연 제작진 :"))
            {
                crew.setText(dataArray[i]);
            }
            if (dataArray[i].contains("공연 시작일 :"))
            {
                start.setText(dataArray[i]);
            }
            if (dataArray[i].contains("공연 종료일 :"))
            {
                end.setText(dataArray[i]);
            }
            if (dataArray[i].contains("소개이미지 :"))
            {
                Log.v("image",dataArray[i]);
                imagelist.add(dataArray[i].replace("소개이미지 :",""));
            }
        }
        ImageAdapter adapter = new ImageAdapter(this,imagelist ) ;
        introduction.setAdapter(adapter);
        setListViewHeightBasedOnItems(introduction);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.reserve_navigation_1:

                    startActivity(intent1);
                    return true;
                    case R.id.reserve_navigation_2:
                        startActivity(intent2);
                return true;
            }
            return false;
        }
    };
}