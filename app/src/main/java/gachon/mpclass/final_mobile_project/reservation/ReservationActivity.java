package gachon.mpclass.final_mobile_project.reservation;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import gachon.mpclass.final_mobile_project.R;


public class ReservationActivity extends AppCompatActivity {
    TextView Title;
    TextView price;
    TextView place;
    TextView etTitle4;
    TextView etContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Title = findViewById(R.id.reservation_detailTitle);
        price = findViewById(R.id.reservation_detailPrice);
        place = findViewById(R.id.reservation_detailAddr);
        etTitle4 = findViewById(R.id.reservation_detailURL);
//        etContent = findViewById(R.id.et_write_review);

//        manager = new DBManager(this);

        //인텐트에서 꺼낸 값이 널이 아닐 경우에만 공연 제목 자동으로 넣어주고 아니면 직접 입력하게 하기
        String str = getIntent().getStringExtra("title");
        if (str != null) {
            Title.setText(str);
        }
        String str2 = getIntent().getStringExtra("price");
        if (str != null) {
            price.setText(str2);
        }
        String str3 = getIntent().getStringExtra("PlaceUrl");
        if (str != null) {
            place.setText(str3);
        }
        String str4 = getIntent().getStringExtra("TicketLink");
        if (str != null) {
            etTitle4.setText(str4);
        }


    }


}
