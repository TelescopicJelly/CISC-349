package edu.harrisburgu.cisc349.cameratest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PictureListAdapter extends BaseAdapter {

    private List<ImageData> imageDataList;
    private Context context;

    public PictureListAdapter(Context context, List<ImageData> imageDataList) {
        this.context = context;
        this.imageDataList = imageDataList;
    }

    @Override
    public int getCount() {
        if (null == imageDataList) return 0;
        else return imageDataList.size();
    }

    @Override
    public Object getItem(int i) {
        if (null == imageDataList) return null;
        else return imageDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (null == imageDataList) return 0;
        else return imageDataList.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item,
                viewGroup, false);

        Log.d("PictureListAdapter", "image index " + i);

        ImageData imageData = imageDataList.get(i);

        ImageView image = (ImageView) view.findViewById(R.id.listImageView);
        image.setImageBitmap(imageData.getImage());

        TextView text = (TextView) view.findViewById(R.id.listImageInfo);
        text.setText("Image index " + i);


        return view;
    }
}
