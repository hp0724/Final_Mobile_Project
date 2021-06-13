package gachon.mpclass.final_mobile_project.Show;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import java.io.BufferedInputStream;
  import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import gachon.mpclass.final_mobile_project.R;

public class ImageAdapter extends ArrayAdapter {
    public ImageAdapter(Context context, ArrayList urls) {
        super(context, 0, urls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_view, parent, false);
        }

        // Get the data item for this position
        String url = (String) getItem(position);

        // Lookup view for data population
        ImageView imageview = (ImageView) convertView.findViewById(R.id.imageView3);
        // Populate the data into the template view using the data object
        Bitmap bmp = null;
        try {
            URL img = new URL(url);
            URLConnection conn = img.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            imageview.setImageBitmap(bm);
        } catch (Exception e) {
        }
        // Return the completed view to render on screen
        return convertView;
    }
}