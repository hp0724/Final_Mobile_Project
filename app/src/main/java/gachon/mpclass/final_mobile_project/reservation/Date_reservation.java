package gachon.mpclass.final_mobile_project.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import gachon.mpclass.final_mobile_project.R;

public class Date_reservation extends AppCompatActivity {

    TextView theater, performance, date,price,place;
    Button time1, calendar;

    private int Year, Month, Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_reservation);

        theater=findViewById(R.id.date_performName);
        price=findViewById(R.id.price_theater);
        place=findViewById(R.id.theart_addr);
        performance = findViewById(R.id.date_performName);
        date = findViewById(R.id.date);
        calendar = findViewById(R.id.calender);
        time1=findViewById(R.id.time_selector);


        String getThaeter = getIntent().getStringExtra("title");
        Log.d("Tag",getThaeter);
        if (getThaeter != null) {
            theater.setText(getThaeter);
        }

        String getprice = getIntent().getStringExtra("price");
        Log.d("Tag",getprice);
        if (getprice != null) {
            price.setText(getprice);
        }
        String getPlace = getIntent().getStringExtra("Place");
        Log.d("Tag",getPlace);
        if (getPlace != null) {
            place.setText(getPlace);
        }


        //api로 받아서 극 이름,극장정보,시간 넣기
       /* performance.setText();
        theater.setText();
        time1.setText()*/

        //날짜선택
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDate();
            }
        });
        //시간선택
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                today();
            }
        });

        /*//시간누르면 좌석페이지로 이동
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),seat.class);
                startActivity(intent);
            }
        });*/

        //오늘 날짜
        Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DATE);

        today();

    }

    //달력팝업
    private void showDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Year = year;
                Month = month;
                Day = dayOfMonth;
                today();
            }
        };

        new DatePickerDialog(this,dateSetListener, Year,Month,Day).show();



    }

    //날짜 설정
    void today() {
        date.setText(Year + "년 " + (Month + 1) + "월 " + Day + "일");
    }

}