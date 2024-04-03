package edu.harrisburgu.cisc349.simplelistpictures;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserActivity extends AppCompatActivity {

    static UserAdapter adapter;

    public static Intent newIntent(Context packageContext, UserAdapter adapterRef) {
        Intent i = new Intent(packageContext, UserActivity.class);
        adapter = adapterRef;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        int index = getIntent().getIntExtra(UserAdapter.EXTRA_SELECTED_ITEM, -1);
        if (index >= 0)
        {
            adapter.populateView(findViewById(R.id.userLayout), index);
        }
    }
}