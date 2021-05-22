package gachon.mpclass.final_mobile_project.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import gachon.mpclass.final_mobile_project.Manager.ImageFileManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import gachon.mpclass.final_mobile_project.Manager.ImageFileManager;
import gachon.mpclass.final_mobile_project.R;

public class simpleImageAdapter extends RecyclerView.Adapter<simpleImageAdapter.ViewHolder> {

    private ArrayList<Boxoffice> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1 ;
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            imageView1 = itemView.findViewById(R.id.imageView) ;
            textView = itemView.findViewById(R.id.textView);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    simpleImageAdapter(ArrayList<Boxoffice> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public simpleImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.main_listview_show, parent, false) ;
        simpleImageAdapter.ViewHolder vh = new simpleImageAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(simpleImageAdapter.ViewHolder holder, int position) {
        Glide.with(holder.imageView1.getContext()).load(mData.get(position).image).into(holder.imageView1);
        holder.textView.setText(mData.get(position).title);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}