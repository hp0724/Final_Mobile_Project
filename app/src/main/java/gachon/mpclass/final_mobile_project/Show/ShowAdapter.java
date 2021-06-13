package gachon.mpclass.final_mobile_project.Show;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gachon.mpclass.final_mobile_project.R;

public class ShowAdapter extends BaseAdapter {

    public static final String TAG = "ShowAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<gachon.mpclass.final_mobile_project.Show.ShowDto> list;


    public ShowAdapter(Context context, int resource, ArrayList<gachon.mpclass.final_mobile_project.Show.ShowDto> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = view.findViewById(R.id.tv_title);
            viewHolder.tvArea = view.findViewById(R.id.tv_area);
            viewHolder.tvPlace = view.findViewById(R.id.tv_place);
            viewHolder.tvRealmName = view.findViewById(R.id.tv_realmName);
            viewHolder.ivImage = view.findViewById(R.id.imageView);
             view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        gachon.mpclass.final_mobile_project.Show.ShowDto dto = list.get(position);

        viewHolder.tvTitle.setText(dto.getTitle(false));
        viewHolder.tvArea.setText("(" + dto.getArea() + ")");
        viewHolder.tvPlace.setText(dto.getPlace());
        viewHolder.tvRealmName.setText(dto.getRealmName());


        if (dto.getImageLink() == null) {
            viewHolder.ivImage.setImageResource(R.mipmap.ic_launcher);
            return view;
        }



        return view;
    }

    public void setList(ArrayList<gachon.mpclass.final_mobile_project.Show.ShowDto> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public TextView tvTitle = null;
        public TextView tvArea = null;
        public TextView tvPlace = null;
        public TextView tvRealmName = null;
        public ImageView ivImage = null;
        public Button btnBookMark = null;
    }

    class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ViewHolder viewHolder;
        String imageAddress;

        public GetImageAsyncTask(ViewHolder holder) {
            viewHolder = holder;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageAddress = params[0];
            Bitmap result = null;


            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                viewHolder.ivImage.setImageBitmap(bitmap);
             }

        }

    }

}
