package edu.harrisburgu.cisc349.dynamic_list_view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AlbumActivity extends AppCompatActivity {

    static HolidaySongsAdapter adapter;

    public static Intent newIntent(Context packageContext, HolidaySongsAdapter adapterRef) {
        Intent i = new Intent(packageContext, AlbumActivity.class);
        adapter = adapterRef;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        int index = getIntent().getIntExtra(HolidaySongsAdapter.EXTRA_SELECTED_ITEM, -1);
        if (index >= 0)
        {
            adapter.populateView(findViewById(R.id.albumlayout), index);
        }

        Button mShowAnswer = (Button) findViewById(R.id.done_button);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });


    }
}
