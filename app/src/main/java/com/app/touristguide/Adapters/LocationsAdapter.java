package com.app.touristguide.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.touristguide.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    Context context;
    ArrayList<String>  cat;
    ArrayList<String>  description;
    ArrayList<String>  images;
    HashMap<Integer,Boolean> hs;

    public LocationsAdapter(Context ctx, ArrayList<String> cat, ArrayList<String> desc, ArrayList<String> images) {
        this.context = ctx;
        this.cat = cat;
        this.description = desc;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.locations_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.card, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.card, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(holder.descrl.getVisibility() == View.VISIBLE){
                            holder.tv.setVisibility(View.VISIBLE);
                            holder.descrl.setVisibility(View.GONE);
                            holder.in.setVisibility(View.VISIBLE);
                        }else {
                            holder.tv.setVisibility(View.GONE);
                            holder.descrl.setVisibility(View.VISIBLE);
                            holder.in.setVisibility(View.GONE);
                        }
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });

        holder.descrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.card, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.card, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(holder.descrl.getVisibility() == View.VISIBLE){
                            holder.tv.setVisibility(View.VISIBLE);
                            holder.descrl.setVisibility(View.GONE);
                            holder.in.setVisibility(View.VISIBLE);
                        }else {
                            holder.tv.setVisibility(View.GONE);
                            holder.descrl.setVisibility(View.VISIBLE);
                            holder.in.setVisibility(View.GONE);
                        }
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });

        holder.tv.setText(cat.get(position));
        holder.desc.setText(description.get(position));

        if(images != null && images.get(position) != null)
            new DownloadImageTask(holder.im, holder.pg).execute(images.get(position));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView desc;
        ImageView im;
        ImageView in;
        CardView card;
        RelativeLayout descrl;
        ProgressBar pg;
        public ViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.category);
            desc = view.findViewById(R.id.description);
            im = view.findViewById(R.id.catimage);
            in = view.findViewById(R.id.info);
            card = view.findViewById(R.id.card);
            descrl = view.findViewById(R.id.descrl);
            pg = view.findViewById(R.id.progressBar);
        }
    }
}
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    ProgressBar pg;

    public DownloadImageTask(ImageView bmImage, ProgressBar pg) {
        this.bmImage = bmImage;
        this.pg = pg;
        pg.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        pg.setVisibility(View.GONE);
        bmImage.setImageBitmap(result);
    }
}