package com.example.darwish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.darwish.data.jsonObject;
import com.example.darwish.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by darwish on 8/12/2016.
 */

public class MyAdpter extends ArrayAdapter <jsonObject> {
    ArrayList<jsonObject> myresorces=new ArrayList<>();
    private Context contextThere;
    public MyAdpter(Context context, int resource,ArrayList<jsonObject> temp) {
        super(context, resource,temp);
        contextThere=context;
        myresorces=temp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.moveshap, null);
        }

        jsonObject tempObject = myresorces.get(position);
        ImageView  imageView= (ImageView) v.findViewById(R.id.imageView_list);
        Picasso.with(contextThere).load("http://image.tmdb.org/t/p/w342/"+tempObject.getPoster()).into(imageView);


        return v;
    }

}