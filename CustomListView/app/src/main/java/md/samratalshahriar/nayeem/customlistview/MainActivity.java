package md.samratalshahriar.nayeem.customlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter ca;
    private List<MessageModel> mdl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listview);

        mdl = new ArrayList<>();

        mdl.add(new MessageModel(3, "Hello"));
        mdl.add(new MessageModel(5, "World"));
        ca = new CustomAdapter(getApplicationContext(), mdl);
        listView.setAdapter(ca);
    }
}
