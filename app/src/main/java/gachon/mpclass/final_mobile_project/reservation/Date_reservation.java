package gachon.mpclass.final_mobile_project.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import gachon.mpclass.final_mobile_project.Main.FragmentMypage;
import gachon.mpclass.final_mobile_project.Main.MybookActivity;
import gachon.mpclass.final_mobile_project.R;

public class Date_reservation extends AppCompatActivity {

    TextView theater, performance, date,price,place;
    Button time1, calendar,reservation;
    private String getThaeter,getPrice,getPlace,getDate;
    private int Year, Month, Day,hour,min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_reservation);



        theater=findViewById(R.id.date_performName);
        price=findViewById(R.id.price_theater);
        place=findViewById(R.id.theart_addr);
        date = findViewById(R.id.date);
        calendar = findViewById(R.id.calender);
        time1=findViewById(R.id.time_selector);
        reservation=findViewById(R.id.btn_book);


         getThaeter = getIntent().getStringExtra("title");
        Log.d("Tag",getThaeter);
        if (getThaeter != null) {
            theater.setText(getThaeter);
        }

        getPrice = getIntent().getStringExtra("price");
        Log.d("Tag",getPrice);
        if (getPrice != null) {
            price.setText(getPrice);
        }
        getPlace = getIntent().getStringExtra("place");
        Log.d("Tag",getPlace);
        if (getPlace != null) {
            place.setText(getPlace);
        }


//        book.put("date",date);
//        book.put("time",time);
//        book.put("seat",seat);

//        팝업창 띄우기
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });

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


        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Seat_reservation.class);
                startActivity(intent);
            }
        });

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
        getDate=Year + "년 " + (Month + 1) + "월 " + Day + "일";
    }

//    //달력팝업
//    private void showTime() {
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int hour, int min ) {
//
//                today();
//            }
//        };
//
//        new DatePickerDialog(this,dateSetListener, Year,Month,Day).show();
//
//
//
//    }
//
//    //날짜 설정
//    void time() {
//        date.setText(Year + "년 " + (Month + 1) + "월 " + Day + "일");
//    }

    void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("자세히보기");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Map<String,Object>book=new HashMap<>();
        book.put("name",getThaeter);
        book.put("price",getPrice);
        book.put("place",getPlace);
        book.put("date",getDate);
//        book.put("date",date);

        //타이틀설정
        String tv_text = theater.getText().toString();
        builder.setMessage(tv_text);
        //내용설정
        builder.setPositiveButton("예매",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"예매완료",Toast.LENGTH_LONG).show();

                        db.collection("book").document(user.getUid()).set(book);


                    }
                });

        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소완료",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

}