package com.app.touristguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.touristguide.R;

import java.util.ArrayList;

public class HotelsAdapter extends BaseAdapter {

    Context context;
    ArrayList<String>  cat;
    ArrayList<String>  star;
    ArrayList<String>  description;
    ArrayList<String>  distance;

    public HotelsAdapter(Context ctx, ArrayList<String> cat, ArrayList<String> star, ArrayList<String> description, ArrayList<String> distance) {
        this.context = ctx;
        this.cat = cat;
        this.star = star;
        this.description = description;
        this.distance = distance;
    }

    @Override
    public int getCount() {
        return cat.size();
    }

    @Override
    public Object getItem(int position) {
        return cat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, @NonNull ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            assert mInflater != null;
            view = mInflater.inflate(R.layout.hotels_item, null);
        }

        TextView name = view.findViewById(R.id.name);
        TextView sta = view.findViewById(R.id.star);
        TextView desc = view.findViewById(R.id.details);
        TextView dis = view.findViewById(R.id.distance);

        name.setText(cat.get(position));
        sta.setText(star.get(position));
        desc.setText(description.get(position));
        dis.setText(distance.get(position) + " km");

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Distance is from city center", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}