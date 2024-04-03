package edu.harrisburgu.cisc349.simplelistpictures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView listView;
    TextView textView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LayoutInflater factory = getLayoutInflater();
        final View myView = factory.inflate(R.layout.my_list, null);
        textView = (TextView) myView.findViewById(R.id.textView);

        listView = findViewById(R.id.listView);
        listItem = getResources().getStringArray(R.array.array_technology);

        for (String s : listItem) {
            Log.d(TAG, s);
        }
        ArrayList<User> arrayofUsers = new ArrayList<>();
        arrayofUsers.add(new User("Eve", "777-777-7777", "https://i.guim.co.uk/img/media/6ee83331d88b3ac46588feb40f107acad7d7c3d7/260_116_1585_951/master/1585.jpg?width=700&quality=85&auto=format&fit=max&s=d723decc9698d07cc8cb6abc52533c49", "Has 13 cats at home"));
        arrayofUsers.add(new User("John", "317-739-2787", "https://img.freepik.com/free-photo/close-up-portrait-happy-charming-african-guy-smiling-boyfriend-waiting-date-head-hunter-dream-standing-white-wall_176420-12540.jpg?size=626&ext=jpg&ga=GA1.1.1395880969.1709596800&semt=ais", "Is originally from Italy"));
        arrayofUsers.add(new User("Mark", "443-867-7900", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8ZmFjZXxlbnwwfHwwfHx8MA%3D%3D", "Loves to cook and bake"));
        arrayofUsers.add(new User("Kelly", "190-285-3981", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQS66M8RJqgmkSTPWfTV5aZ3WmSHliJqmRgXB49DMLiKA&s", "Loves to trailing and going on long hikes with friends"));
        arrayofUsers.add(new User("Adam", "717-727-7007", "https://i.pinimg.com/564x/32/38/6c/32386c72c7f2a8b5c1a10fc51c149cb1.jpg", "Has a fear of water"));
        arrayofUsers.add(new User("Mary", "810-248-78001", "https://media.istockphoto.com/id/1466995518/photo/business-woman-and-worker-portrait-at-office-desk-as-administration-executive-company-manager.jpg?s=612x612&w=0&k=20&c=NvKeG6Fh0_VVfH_N0Ka-5j8284XJhL2VTJfe6IwDkWQ=", "Can speak 3 different languages: French, German, and Spanish"));
        arrayofUsers.add(new User("Olivia", "710-877-3891", "https://cdn.quizly.co/wp-content/uploads/2017/11/23004918/smiling-person.jpg", "Addicted to coffee"));
        arrayofUsers.add(new User("Josh", "728-451-5460", "https://qph.cf2.quoracdn.net/main-thumb-1642431735-200-dfzdxwydubpjgoxzkniexomuqypsoxgl.jpeg", "Once fought off a bear"));


        UserAdapter adapter = new UserAdapter(this, arrayofUsers);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(adapter);
    }
}