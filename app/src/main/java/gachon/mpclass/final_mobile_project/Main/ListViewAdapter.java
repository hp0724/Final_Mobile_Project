package gachon.mpclass.final_mobile_project.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//import gachon.mpclass.final_mobile_project.Manager.ImageFileManager;

import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import gachon.mpclass.final_mobile_project.R;
import gachon.mpclass.final_mobile_project.Show.DetailShowActivity;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    public ArrayList<Performance> mData = null ;
    private OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1 ;
        TextView textView1;
        TextView textView2;
        TextView textView4;
        ViewHolder(View itemView) {
            super(itemView) ;
            imageView1 = itemView.findViewById(R.id.imageView) ;
            textView1 = itemView.findViewById(R.id.tv_title);
            textView2 = itemView.findViewById(R.id.tv_place);
            textView4 = itemView.findViewById(R.id.tv_realmName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    ListViewAdapter(ArrayList<Performance> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.listview_show, parent, false) ;
        ListViewAdapter.ViewHolder vh = new ListViewAdapter.ViewHolder(view);
        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ListViewAdapter.ViewHolder holder, int position) {
        Glide.with(holder.imageView1.getContext()).load(mData.get(position).image).into(holder.imageView1);
        holder.textView1.setText(mData.get(position).title);
        holder.textView2.setText(mData.get(position).facility);
        holder.textView4.setText(mData.get(position).category);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}