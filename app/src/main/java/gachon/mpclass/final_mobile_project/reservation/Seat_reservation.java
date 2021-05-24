package gachon.mpclass.final_mobile_project.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gachon.mpclass.final_mobile_project.R;

public class Seat_reservation extends AppCompatActivity {

    TextView seat_date,seat_time,seat_num,seat_info;
    Button m,p,reserve;

    int count = 0;

    //좌석 수
    String seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_reservation);


        seat_date = findViewById(R.id.seat_date);
        seat_time = findViewById(R.id.seat_time);
        seat_num = findViewById(R.id.seat_count);
        seat_info = findViewById(R.id.seat_info);

        m = findViewById(R.id.minus);
        p = findViewById(R.id.plus);
        reserve = findViewById(R.id.reserve);

        Intent i = getIntent();
        seat_date.setText(i.getStringExtra("date"));


        //좌석 빼기(-1)
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count > 0) {
                    count--;
                    seat_num.setText(String.valueOf(count));
                }
            }

        });

        //좌석 추가(+1)
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                seat_num.setText(String.valueOf(count));
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication(),Date_reservation.class);
                intent.putExtra("count",count);
                startActivity(intent);
            }
        });

    }

    //좌석수 받아오기
    public void get_seat_num(){
        seat = String.valueOf(count);
    }

}