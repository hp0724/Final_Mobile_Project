package gachon.mpclass.final_mobile_project.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import gachon.mpclass.final_mobile_project.Bookmark.BookmarkActivity;
import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.Review.ListReviewActivity;
import gachon.mpclass.final_mobile_project.Show.SearchShowActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTitle = findViewById(R.id.tv_mainTitle);

        String title = mainTitle.getText().toString();
        SpannableString spannableStr = new SpannableString(title);
        String word = title.substring(0,5);
        int start = title.indexOf(word);
        int end = start + word.length();
        spannableStr.setSpan(new ForegroundColorSpan(Color.parseColor("#EFA00B")),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mainTitle.setText(spannableStr);

    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
//            지도 보여주기
//            case R.id.btn_map:
//                intent = new Intent(this, MainMapActivity.class);
//                break;
//                공연 찾기
            case R.id.btn_showSearch:
                intent = new Intent(this, SearchShowActivity.class);
                break;
            case R.id.btn_mainBm:
                intent = new Intent(this, BookmarkActivity.class);
                break;
//                리뷰
            case R.id.btn_main_review:
                intent = new Intent(this, ListReviewActivity.class);
                break;
        }

        if (intent != null) startActivity(intent);
    }

}