package edu.tufts.cs.tripsharing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/* The sidebar (the secondary functionality, not completed). */

public class CustomListAdapter2 extends ArrayAdapter<String>{
    String[] username;
    String[] usercontacts;
    public CustomListAdapter2(@NonNull Context context, String[] username, String[] usercontacts) {
        super(context, R.layout.list_item, username);
        this.usercontacts = usercontacts;
        this.username = username;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.list_item, parent, false);

        TextView textView1 = (TextView) customView.findViewById(R.id.text1);
        TextView textView2 = (TextView) customView.findViewById(R.id.text2);

        textView1.setText(username[position] + ":");
        textView2.setText(usercontacts[position]);
        return customView;
    }
}
