package gachon.mpclass.final_mobile_project.Main;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import gachon.mpclass.final_mobile_project.Manager.ImageFileManager;
import gachon.mpclass.final_mobile_project.Manager.NetworkManager;
import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.Show.DetailShowActivity;
import gachon.mpclass.final_mobile_project.Show.ShowDto;
import gachon.mpclass.final_mobile_project.useperformancedata.ProcessOpenData;

public class FragmentSearch extends Fragment {
    static final SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMdd");
    static final SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy/MM/dd");
    public static final String TAG = "FragmentSearch";
    EditText etPlace;
    Button btn_search;
    Button btn_start;
    Button btn_end;
    String pname;
    int rows=10;
    int cpage=1;
    Calendar c1 = Calendar.getInstance();

    int sYear = c1.get(Calendar.YEAR);
    int sMon = c1.get(Calendar.MONTH);
    int sDay = c1.get(Calendar.DAY_OF_MONTH);
    Calendar c2 = Calendar.getInstance();
    int eYear = c2.get(Calendar.YEAR);
    int eMon = c2.get(Calendar.MONTH);
    int eDay = c2.get(Calendar.DAY_OF_MONTH);
    String startDate = getFirstDayOfMonth2();
    String endDate = getFirstDayOfNextMonth2();
    ListViewAdapter adapter;

    RecyclerView recyclerView;

    public String getFirstDayOfNextMonth2()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,1);
        cal.set(Calendar.DATE,1);
        return dateformat1.format(cal.getTime());
    }
    public String getFirstDayOfMonth2()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE,1);
        return dateformat1.format(cal.getTime());
    }
    public String getFirstDayOfNextMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,1);
        cal.set(Calendar.DATE,1);
        return dateformat2.format(cal.getTime());
    }
    public String getFirstDayOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE,1);
        return dateformat2.format(cal.getTime());
    }

    public ArrayList<Performance> Search()
    {
        ProcessOpenData pd = new ProcessOpenData();
        pname = etPlace.getText().toString();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        ArrayList<String> list5 = new ArrayList<>();
        ArrayList<Performance> list = new ArrayList<>();
        String data = pd.searchDataWithName(startDate,endDate,Integer.toString(rows),Integer.toString(cpage),pname);
        String[] dataArray = data.split("\n");
        for (int i = 0; i < dataArray.length; i = i + 1) {
            if (dataArray[i].contains("공연포스터경로 :")) {
                list1.add(dataArray[i].replace("공연포스터경로 :",""));
            }
            if (dataArray[i].contains("공연명 :"))
            {
                list2.add(dataArray[i].replace("공연명 :",""));
            }
            if (dataArray[i].contains("공연 ID :"))
            {
                list3.add(dataArray[i].replace("공연 ID :",""));
            }
            if (dataArray[i].contains("공연 장르명 :"))
            {
                list4.add(dataArray[i].replace("공연 장르명  :",""));
            }
            if (dataArray[i].contains("공연시설명 :"))
            {
                list5.add(dataArray[i].replace("공연시설명  :",""));
            }
        }
        for(int i = 0; i < list1.size(); i = i + 1) {
            Performance temp = new Performance((String) list2.get(i), (String) list1.get(i),(String) list3.get(i),list4.get(i),list5.get(i));
            list.add(temp);
        }
        return list;
    }
    @Override
    public void onStart() {
        super.onStart();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new ListViewAdapter(Search());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(v.getContext(), DetailShowActivity.class);
                //intent.putExtra("id", adapter.mData.get(position).id );
                startActivity(intent);
            }

        });

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView= view.findViewById(R.id.recycler2);
        btn_search=view.findViewById(R.id.btn_search);
        btn_start=view.findViewById(R.id.btn_start);
        btn_end=view.findViewById(R.id.btn_end);
        etPlace=view.findViewById(R.id.et_word);
        btn_start.setText(getFirstDayOfMonth());
        btn_end.setText(getFirstDayOfNextMonth());


        DatePickerDialog.OnDateSetListener sDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        c1.set(Calendar.DATE,dayOfMonth);
                        c1.set(Calendar.MONTH,monthOfYear);
                        c1.set(Calendar.YEAR,year);
                        startDate=dateformat1.format(c1.getTime());
                      btn_start.setText(String.valueOf(year) +"/"+ String.valueOf(monthOfYear+1) +"/"+ String.valueOf(dayOfMonth));
                      Toast.makeText(getContext(), startDate, Toast.LENGTH_SHORT).show();
                    }
                };

        DatePickerDialog sDialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                sDateSetListener, sYear, sMon, sDay);


        DatePickerDialog.OnDateSetListener eDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        c2.set(Calendar.DATE,dayOfMonth);
                        c2.set(Calendar.MONTH,monthOfYear);
                        c2.set(Calendar.YEAR,year);
                        endDate=dateformat1.format(c2.getTime());
                        btn_end.setText(String.valueOf(year) +"/"+ String.valueOf(monthOfYear+1) +"/"+ String.valueOf(dayOfMonth));
                        Toast.makeText(getContext(), endDate, Toast.LENGTH_SHORT).show();
                    }
                };
        DatePickerDialog eDialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                eDateSetListener, eYear, eMon, eDay);

       btn_search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
               adapter = new ListViewAdapter(Search());
               recyclerView.setAdapter(adapter);
               adapter.setOnItemClickListener(new ListViewAdapter.OnItemClickListener() {
                   @Override
                   public void onItemClick(View v, int position) {
                       Intent intent = new Intent(v.getContext(), DetailShowActivity.class);
                       intent.putExtra("id", adapter.mData.get(position).id );
                       startActivity(intent);
                   }

               });
           }
       } );
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sDialog.show();
            }
        } );
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eDialog.show();
            }
        } );
        return view;
    }

}
