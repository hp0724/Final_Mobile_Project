package gachon.mpclass.final_mobile_project.Main;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import gachon.mpclass.final_mobile_project.Manager.ImageFileManager;
import gachon.mpclass.final_mobile_project.Manager.NetworkManager;
import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.Show.DetailShowActivity;
import gachon.mpclass.final_mobile_project.Show.ShowDto;

public class FragmentSearch extends Fragment {

    public static final String TAG = "FragmentSearch";

    EditText etPlace;
    ListView lvList;
    Button btn_search;
    String apiAddress;

    String query;

    gachon.mpclass.final_mobile_project.Show.ShowAdapter adapter;
    ArrayList<gachon.mpclass.final_mobile_project.Show.ShowDto> resultList;
    gachon.mpclass.final_mobile_project.Show.ShowXmlParser parser;
    NetworkManager networkManager;
    ImageFileManager imgFileManager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etPlace = view.findViewById(R.id.et_place);
        lvList = view.findViewById(R.id.lvList);

        btn_search = view.findViewById(R.id.btn_search);

        resultList = new ArrayList();
        adapter = new gachon.mpclass.final_mobile_project.Show.ShowAdapter(getContext(), R.layout.listview_show, resultList);
        lvList.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.api_url);
        parser = new gachon.mpclass.final_mobile_project.Show.ShowXmlParser();
        networkManager = new NetworkManager(getActivity());
        imgFileManager = new ImageFileManager(getActivity());
//       리스트 눌르면 전환
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailShowActivity.class);
                gachon.mpclass.final_mobile_project.Show.ShowDto dto = resultList.get(position);
                intent.putExtra("detailDto", dto);
                startActivity(intent);
            }
        });
       btn_search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               query = etPlace.getText().toString();
               try {
                   new NetworkAsyncTask().execute(apiAddress + URLEncoder.encode(query, "UTF-8"));
               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
               }


           }
       } );

        return view;
    }




    class NetworkAsyncTask extends AsyncTask<String, Integer, ArrayList<ShowDto>> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(), "Wait", "Searching...");
        }

        @Override
        protected ArrayList<gachon.mpclass.final_mobile_project.Show.ShowDto> doInBackground(String... strings) {
            String address = strings[0];
            String result = null;

            result = (String) networkManager.download(address, false);
            if (result == null) {
                return null;
            }

            resultList = parser.placeParse(result);

            for (int i = 0; i < resultList.size(); i++) {
                apiAddress = getResources().getString(R.string.detail_api_url) + resultList.get(i).getSeq();
                result = (String) networkManager.download(apiAddress, false);
                if (result == null) {
                    return null;
                }

                parser.detailParse(result, resultList.get(i));
            }

            for (gachon.mpclass.final_mobile_project.Show.ShowDto dto : resultList) {
                Bitmap savedBitmap = imgFileManager.getBitmapFromTemporary(dto.getImageLink());

                if (savedBitmap == null) {
                    Bitmap bitmap = (Bitmap) networkManager.download(dto.getImageLink(), true);
                    if (bitmap != null) {
                        imgFileManager.saveBitmapToTemporary(bitmap, dto.getImageLink());
                    }
                }
            }
            return resultList;
        }

        @Override
        protected void onPostExecute(ArrayList<ShowDto> resultList) {
            if (!resultList.isEmpty()) {
                adapter.setList(resultList);
            } else {
                Toast.makeText(getContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
}
