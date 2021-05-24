package gachon.mpclass.final_mobile_project.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import gachon.mpclass.final_mobile_project.R;

public class MybookActivity extends AppCompatActivity {

    TextView theater, place, date,price,time,seat;
    Button back_button;
    DocumentReference docRef;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String book_name=null;
    String book_place=null;
    String book_price=null;
    String book_date=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybook);



        time=findViewById(R.id.myBook_time);
        seat = findViewById(R.id.myBook_seat);
        back_button=findViewById(R.id.back_but);

        getFirebase();




        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });




    }

    public void getFirebase(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        docRef = firebaseFirestore.collection("book").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        book_name = document.getData().get("name").toString();
                        book_place=document.getData().get("place").toString();
                        book_price =document.getData().get("price").toString();
                        book_date=document.getData().get(("date")).toString();


                        theater=findViewById(R.id.myBook_performName_name);
                        theater.setText(book_name);
                        place=findViewById(R.id.myBook_theart_addr);
                        place.setText(book_price);
                        price=findViewById(R.id.myBook_price_theater);
                        price.setText(book_place);
                        date = findViewById(R.id.myBook_date);
                        date.setText(book_date);


                    }
                }
            }

        });

    }
}
