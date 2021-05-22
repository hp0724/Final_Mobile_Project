package gachon.mpclass.final_mobile_project.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.useperformancedata.ProcessOpenData;

public class FragmentHome extends Fragment {

    TextView mainTitle;
    RecyclerView recyclerView;
    TextView text;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView= view.findViewById(R.id.recycler1);
        TextView text = (TextView) view.findViewById(R.id.text);
        mainTitle = view.findViewById(R.id.tv_mainTitle);

        String title = mainTitle.getText().toString();
        SpannableString spannableStr = new SpannableString(title);
        String word = title.substring(0,5);
        int start = title.indexOf(word);
        int end = start + word.length();
        spannableStr.setSpan(new ForegroundColorSpan(Color.parseColor("#EFA00B")),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mainTitle.setText(spannableStr);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        ProcessOpenData pd = new ProcessOpenData();

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<Boxoffice> list = new ArrayList<>();
        String data = "";
        data = pd.boxofficeService("day", pd.getLastMonth());
        String[] dataArray = data.split("\n");
        for (int i = 0; i < dataArray.length; i = i + 1) {
            if (dataArray[i].contains("upload")) {
                list1.add("http://kopis.or.kr" + dataArray[i]);
            }
            if (dataArray[i].contains("공연명 :"))
            {
                list2.add(dataArray[i].replace("공연명 :",""));
            }
        }
        for(int i = 0; i < list1.size(); i = i + 1) {
            Boxoffice temp = new Boxoffice((String) list2.get(i), (String) list1.get(i));
            list.add(temp);
        }
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        simpleImageAdapter adapter = new simpleImageAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
