package edu.harrisburgu.cisc349.simplelistpictures;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private Context context;
    private ArrayList<User> arrayList;

    private ImageLoader imageLoader;
    public static String EXTRA_SELECTED_ITEM = "my.selected.item";


    public UserAdapter(Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        RequestQueue queue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {return mCache.get(url);}

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);

            }
        });
    }

    @Override
    public int getCount() {return arrayList.size();}

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {return position;}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.phone = convertView.findViewById(R.id.phone);
            holder.image = convertView.findViewById(R.id.networkImageView);
            holder.description = convertView.findViewById(R.id.description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = arrayList.get(position);
        holder.name.setText(user.getName());
        holder.phone.setText(user.getPhone());
        holder.image.setImageUrl(user.getImageUR(), imageLoader);
        holder.description.setText(user.getDescription());

        // Log the image URL
        Log.d("UserAdapter", "Image URL for user " + user.getName() + ": " + user.getImageUR());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView phone;
        TextView description;
        NetworkImageView image;
    }



    public void populateView(View view, int index)
    {
        User picked = arrayList.get(index);

        TextView tv = view.findViewById(R.id.user_name);
        tv.setText(picked.getName());

        tv = view.findViewById(R.id.user_phone);
        tv.setText(picked.getPhone());

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.userImageView);
        image.setImageUrl(picked.getImageUR(), imageLoader);

        tv = view.findViewById(R.id.user_description);
        tv.setText(picked.getDescription());

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = UserActivity.newIntent(adapterView.getContext(),
                this);
        intent.putExtra(EXTRA_SELECTED_ITEM, i);
        adapterView.getContext().startActivity(intent);

    }
}
