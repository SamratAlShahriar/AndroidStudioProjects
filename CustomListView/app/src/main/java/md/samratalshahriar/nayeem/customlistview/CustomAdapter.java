package md.samratalshahriar.nayeem.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<MessageModel> msgList;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, List<MessageModel> msgList) {
        this.context = context;
        this.msgList = msgList;
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflater.inflate(R.layout.sample_view, parent, false);
        }

        TextView text = convertView.findViewById(R.id.textTab1);
        ImageButton fab = convertView.findViewById(R.id.FavoriteId1);
        ImageButton copy = convertView.findViewById(R.id.copyId1);
        ImageButton share = convertView.findViewById(R.id.shareId1);
        ImageButton send = convertView.findViewById(R.id.sendId1);

        text.setText(msgList.get(position).getMessage());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked on fab", Toast.LENGTH_SHORT).show();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked on fab", Toast.LENGTH_SHORT).show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked on fab", Toast.LENGTH_SHORT).show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked on fab", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
