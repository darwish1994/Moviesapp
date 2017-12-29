package com.example.darwish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.darwish.data.ReviewObject;
import com.example.darwish.moviesapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darwish on 9/16/2016.
 */
public class ReviewAdapter extends ArrayAdapter<ReviewObject> {
    ArrayList<ReviewObject> myresorces=new ArrayList<>();
    private Context contextThere;
    public ReviewAdapter(Context context, int resource, ArrayList<ReviewObject> objects) {
        super(context, resource, objects);
        contextThere=context;
        myresorces=objects;

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.reviwelement, null);
        }
        ReviewObject tempObject = myresorces.get(position);
        TextView reviewAuthor=(TextView)v.findViewById(R.id.movie_review_author);
        reviewAuthor.setText(tempObject.getAuthor());
        TextView reviewContent=(TextView)v.findViewById(R.id.movie_review_content);
        reviewContent.setText(tempObject.getContent());

        return v;
    }
}
