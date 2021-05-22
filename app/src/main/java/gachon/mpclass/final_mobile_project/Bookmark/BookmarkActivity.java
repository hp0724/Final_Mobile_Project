package gachon.mpclass.final_mobile_project.Bookmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import gachon.mpclass.final_mobile_project.Manager.DBManager;
import gachon.mpclass.final_mobile_project.Show.DetailShowActivity;
import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.Review.ListReviewActivity;
import gachon.mpclass.final_mobile_project.Show.ShowAdapter;
import gachon.mpclass.final_mobile_project.Show.ShowDto;

public class BookmarkActivity extends AppCompatActivity {

    public static final String TAG = "BookmarkActivity";

    ListView lvList;
    ShowAdapter adapter;
    DBManager manager;

    ArrayList<ShowDto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        lvList = findViewById(R.id.bookmark_lvList);
        manager = new DBManager(this);

        list = manager.getAllBookmarkList();

        adapter = new ShowAdapter(this, R.layout.listview_show, list);
        lvList.setAdapter(adapter);

        //공연 상세정보 액티비티로 이동
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowDto dto = manager.getBookmarkById(id);
                Intent intent = new Intent(BookmarkActivity.this, DetailShowActivity.class);
                intent.putExtra("detailDto", dto);
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bmSearch:
                //즐겨찾기한 공연의 공연장 위치 확인
                if (list != null) {
                    Intent intent = new Intent(this, gachon.mpclass.final_mobile_project.Bookmark.BookmarkMapActivity.class);
                    intent.putExtra("bookmarkList", list);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "즐겨찾기한 공연이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        list = manager.getAllBookmarkList();
        adapter.setList(list);
    }
}