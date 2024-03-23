package edu.harrisburgu.cisc349.dynamic_list_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class HolidaySongsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final int IMAGE_SIZE = 256;
    private Context context;
    private ArrayList<HolidaySongs> holidaySongs;

    private ImageLoader imageLoader;

    public static final String EXTRA_SELECTED_ITEM =
            "edu.harrisburgu.cisc349.dynamiclist.selecteditem";

    public HolidaySongsAdapter(Context context, ArrayList<HolidaySongs>holidaySongs, RequestQueue queue)
    {
        this.context = context;
        this.holidaySongs = holidaySongs;

        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {return mCache.get(url);}

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);

            }
        });
    }

    @Override
    public int getCount() {
        if(null == holidaySongs) return 0;
        else return holidaySongs.size();
    }

    @Override
    public Object getItem(int i) {
        if (null == holidaySongs) return null;
        else return holidaySongs.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (null == holidaySongs) return 0;
        else return holidaySongs.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_list_item,
                viewGroup, false);

        HolidaySongs album = holidaySongs.get(i);

        TextView tv = view.findViewById(R.id.albumName);
        tv.setText(album.getAlbum_name());

        tv = view.findViewById(R.id.artistName);
        tv.setText(album.getArtist_name());

        tv = view.findViewById(R.id.danceability);
        tv.setText(String.format("%s: %.3f", "Danceability",
                album.getDanceability()));

        tv = view.findViewById(R.id.duration);
        tv.setText(String.format("%d:%d", (album.getDuration_ms()/1000) /60,
                (album.getDuration_ms()/1000) % 60));

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.albumImageView);

        image.setImageUrl(album.getAlbum_img(), imageLoader);

        return view;
    }


    public void populateView(View view, int index)
    {
        HolidaySongs album = holidaySongs.get(index);

        TextView tv = view.findViewById(R.id.albumDisplayName);
        tv.setText(album.getAlbum_name());

        tv = view.findViewById(R.id.artistDisplayName);
        tv.setText(album.getArtist_name());

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.albumDisplayImageView);
        image.setImageUrl(album.getAlbum_img(), imageLoader);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = AlbumActivity.newIntent(adapterView.getContext(),
                this);
        intent.putExtra(EXTRA_SELECTED_ITEM, i);
        adapterView.getContext().startActivity(intent);
    }
}
