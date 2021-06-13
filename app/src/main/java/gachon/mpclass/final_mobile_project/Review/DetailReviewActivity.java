//package gachon.mpclass.final_mobile_project.Review;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import gachon.mpclass.final_mobile_project.Manager.DBManager;
//import gachon.mpclass.final_mobile_project.R;
//
//public class DetailReviewActivity extends AppCompatActivity {
//
//    public static final String TAG = "DetailReviewActivity";
//
//    DBManager manager;
//
//    EditText etTitle;
//    EditText etContent;
//    TextView tvName;
//
//    gachon.mpclass.final_mobile_project.Review.ReviewDto dto;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_review);
//
//        manager = new DBManager(this);
//        dto = (gachon.mpclass.final_mobile_project.Review.ReviewDto) getIntent().getSerializableExtra("detailDto");
//
//        etTitle = findViewById(R.id.et_detail_title);
//        etContent = findViewById(R.id.et_detail_content);
//        tvName = findViewById(R.id.tv_detail);
//
//        etTitle.setText(dto.getTitle());
//        etContent.setText(dto.getContent());
//        tvName.setText("'" + dto.getTitle() + "' 리뷰");
//
//    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_detail_update:
//                String title = etTitle.getText().toString();
//                String content = etContent.getText().toString();
//                tvName.setText("'" + dto.getTitle() + "' 리뷰");
//
//                if (!title.isEmpty() && !content.isEmpty()) {
//                    dto.setTitle(title);
//                    dto.setContent(content);
//
//                    if (manager.modifyReview(dto)) {
//                        Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    if (title.isEmpty()) {
//                        Toast.makeText(this, "공연 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
//                    } else if (content.isEmpty()) {
//                        Toast.makeText(this, "리뷰를 작성하세요.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//            case R.id.btn_detail_cancel:
//                finish();
//                break;
//            case R.id.btn_review_share:
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                String review = etContent.getText().toString();
//                intent.putExtra(Intent.EXTRA_TEXT, review);
//                Intent share = Intent.createChooser(intent, "공유하기");
//                startActivity(share);
//                break;
//        }
//    }
//}