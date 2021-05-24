package gachon.mpclass.final_mobile_project.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.login.FirstScreenActivity;
import gachon.mpclass.final_mobile_project.useperformancedata.ProcessOpenData;

public class FragmentMypage extends Fragment {
    DocumentReference docRef;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String date;
    String name;
    String phone;
    TextView show_date;
    TextView show_name;
    TextView show_phone;
    Button logout;
    Button book;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        getFirebase(view);
        logout=view.findViewById(R.id.logoutBtn);
        book=view.findViewById(R.id.bookBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getActivity(), FirstScreenActivity.class);
                startActivity(intent1);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getActivity(),MybookActivity.class);
                startActivity(intent2);
            }
        });
        return view;
    }



    public void getFirebase(View view){
        firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        docRef = firebaseFirestore.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        date = document.getData().get("date").toString();
                        name =document.getData().get("name").toString();
                        phone=document.getData().get("phoneNumber").toString();
                        show_name=(TextView)view.findViewById(R.id.myName2);
                        show_name.setText(name);
                        show_date=(TextView)view.findViewById(R.id.myBirth);
                        show_date.setText(date);
                        show_phone=(TextView)view.findViewById(R.id.myNumber2);
                        show_phone.setText(phone);
                        Log.d("TAG", date);
                        Log.d("TAG", name);
                        Log.d("TAG", phone);

                    }
                }
            }

        });

    }
}


