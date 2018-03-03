package com.example.grigo.ab4;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by grigo on 03-Mar-18.
 */

public class CustomListView extends ArrayAdapter<String> {

    private String[] names;
    private Integer[] imageId;
    private Activity context;

    public CustomListView(Activity context, String[] names, Integer[] imageId) {
        super(context, R.layout.listview_layout, names);
        this.context = context;
        this.names = names;
        this.imageId = imageId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) r.getTag();
        }
        viewHolder.imageSource.setImageResource(imageId[position]);
        viewHolder.textview1.setText(names[position]);
        return r;
    }

    class ViewHolder {
        TextView textview1;
        ImageView imageSource;

        ViewHolder(View v) {
            textview1 = v.findViewById(R.id.name);
            imageSource = v.findViewById(R.id.profilePicture);
        }


    }
}
