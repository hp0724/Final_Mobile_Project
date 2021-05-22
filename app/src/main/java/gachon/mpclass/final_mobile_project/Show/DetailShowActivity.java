package gachon.mpclass.final_mobile_project.Show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

import gachon.mpclass.final_mobile_project.Bookmark.BookmarkActivity;
import gachon.mpclass.final_mobile_project.Manager.DBManager;
import gachon.mpclass.final_mobile_project.Manager.ImageFileManager;
import gachon.mpclass.final_mobile_project.Manager.NetworkManager;
import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.Review.AddReviewActivity;
import gachon.mpclass.final_mobile_project.Review.ListReviewActivity;
import gachon.mpclass.final_mobile_project.reservation.ReservationActivity;

public class DetailShowActivity extends AppCompatActivity {

    public static final String TAG = "DetailShowActivity";

    TextView title;
    TextView place;
    TextView area;
    TextView startDate;
    TextView endDate;
    TextView price;
    TextView link;
    TextView phone;
    TextView placeAddr;
    TextView placeUrl;
    ImageView image;
    Button btn_bookmark;

    DBManager dbManager;
    NetworkManager networkManager;
    ImageFileManager imgFileManager;
    gachon.mpclass.final_mobile_project.Show.ShowXmlParser parser;
    gachon.mpclass.final_mobile_project.Show.ShowDto detail;

    PendingIntent pendingIntent = null;
    AlarmManager alarmManager = null;

    private GoogleMap mGoogleMap;
    private Marker centerMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show);

        parser = new gachon.mpclass.final_mobile_project.Show.ShowXmlParser();
        networkManager = new NetworkManager(this);
        imgFileManager = new ImageFileManager(this);
        dbManager = new DBManager(this);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        createNotificationChannel();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detail_map);
        mapFragment.getMapAsync(mapReadyCallBack);

        title = findViewById(R.id.tv_detailTitle);
        place = findViewById(R.id.tv_detailPlace);
        area = findViewById(R.id.tv_detailArea);
        startDate = findViewById(R.id.tv_detailStart);
        endDate = findViewById(R.id.tv_detailEnd);
        price = findViewById(R.id.tv_detailPrice);
        link = findViewById(R.id.tv_detailLink); //예매처
        phone = findViewById(R.id.tv_detailPhone);
        placeAddr = findViewById(R.id.tv_detailAddr);
        placeUrl = findViewById(R.id.tv_detailURL); //공연장소 URL
        image = findViewById(R.id.imageView2);

        detail = (gachon.mpclass.final_mobile_project.Show.ShowDto) getIntent().getSerializableExtra("detailDto");

        btn_bookmark = findViewById(R.id.btn_add_bookmark);
        if (dbManager.existingBookmark(detail.getSeq())) {
            btn_bookmark.setText("즐겨찾기 해제");
        }

        tvSetText();

        // 예매처 URL 클릭시 해당 페이지로 이동
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = link.getText().toString().substring(5);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // 공연 장소 URL 클릭시 해당 페이지로 이동
        placeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = placeUrl.getText().toString().substring(9);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    public void tvSetText() {
        title.setText(detail.getTitle(false));
        place.setText("장소: " + detail.getPlace());
        area.setText("(" + detail.getArea() + ")");

        String s = detail.getStartDate();
        String startD = s.substring(0, 4) + "." + s.substring(4, 6) + "." + s.substring(6, 8);
        startDate.setText("시작일: " + startD);

        String e = detail.getEndDate();
        String endD = e.substring(0, 4) + "." + e.substring(4, 6) + "." + e.substring(6, 8);
        endDate.setText("종료일: " + endD);

        price.setText("티켓 가격: " + detail.getPrice());

        link.setText("예매처: " + detail.getTicketLink());
        String ticketLink = link.getText().toString();
        SpannableString spannableStr1 = new SpannableString(ticketLink);
        String word = ticketLink.substring(5);
        int start = ticketLink.indexOf(word);
        int end = start + word.length();
        spannableStr1.setSpan(new ForegroundColorSpan(Color.parseColor("#013ADF")),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        link.setText(spannableStr1);

        phone.setText("문의 번호: " + detail.getPhone());

        if (detail.getLatitude() != null && detail.getLongitude() != null) {
            placeAddr.setText("'" + detail.getPlace() + "' 주소: " + detail.getPlaceAddr());
        } else {
            placeAddr.setText("'" + detail.getPlace() +"' 주소: 정보 없음");
        }

        placeUrl.setText("공연장 URL: " + detail.getPlaceUrl());
        String pUrl = placeUrl.getText().toString();
        SpannableString spannableStr2 = new SpannableString(pUrl);
        word = pUrl.substring(9);
        start = pUrl.indexOf(word);
        end = start + word.length();
        spannableStr2.setSpan(new ForegroundColorSpan(Color.parseColor("#013ADF")),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        placeUrl.setText(spannableStr2);

        Bitmap savedBitmap = imgFileManager.getBitmapFromTemporary(detail.getImageLink());
        if (savedBitmap != null)
            image.setImageBitmap(savedBitmap);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_review:
                Intent intent = new Intent(this, AddReviewActivity.class);
                intent.putExtra("title", detail.getTitle(false));
                startActivity(intent);
                break;
            case R.id.btn_add_bookmark:
                if (btn_bookmark.getText().equals("즐겨찾기")) {
                    boolean result = dbManager.addBookmark(detail);
                    if (result) {
                        Toast.makeText(this, "즐겨찾기 추가 성공", Toast.LENGTH_SHORT).show();
                        btn_bookmark.setText("즐겨찾기 해제");
                    } else {
                        Toast.makeText(this, "즐겨찾기 추가 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean result = dbManager.removeBookmark(detail.getSeq());
                    if (result) {
                        Toast.makeText(this, "즐겨찾기 해제 성공", Toast.LENGTH_SHORT).show();
                        btn_bookmark.setText("즐겨찾기");
                    } else {
                        Toast.makeText(this, "즐겨찾기 해제 실패", Toast.LENGTH_SHORT).show();
                    }
                }

            case R.id.btn_reservation:
                Intent intent2 = new Intent(this,  ReservationActivity.class);
                intent2.putExtra("title", detail.getTitle(false));
                intent2.putExtra("price", detail.getPrice());
                intent2.putExtra("PlaceUrl", detail.getPlaceUrl( ));
                intent2.putExtra("TicketLink", detail.getTicketLink());

                startActivity(intent2);
                break;
        }

    }

    // notification 채널 만드는 코드
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.CHANNEL_ID), name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;

            LatLng detailLoc;
            if (detail.getLatitude() != null && detail.getLongitude() != null) {
                detailLoc = new LatLng(detail.getLatitude(), detail.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(detailLoc, 16));

                MarkerOptions options = new MarkerOptions();
                options.position(detailLoc);
                options.title(detail.getTitle(false));
                options.snippet(detail.getPlaceAddr());
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                centerMarker = mGoogleMap.addMarker(options);
            } else {
                // 공연장의 위도, 경도 정보가 없을 경우 서울의 중심 좌표가 중심이 됨
                detailLoc = new LatLng(37.564214, 127.001699);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(detailLoc, 13));
            }

        }
    };

}