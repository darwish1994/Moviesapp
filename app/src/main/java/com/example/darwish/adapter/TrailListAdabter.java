package com.example.darwish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darwish.data.TrailObject;
import com.example.darwish.data.jsonObject;
import com.example.darwish.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darwish on 9/16/2016.
 */
public class TrailListAdabter extends ArrayAdapter<TrailObject> {
    ArrayList<TrailObject> myresorces = new ArrayList<>();
    private Context contextThere;

    public TrailListAdabter(Context context, int resource, ArrayList<TrailObject> objects) {
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
            v = vi.inflate(R.layout.trailelement, null);
        }
        TrailObject tempObject = myresorces.get(position);
        TextView traILName=(TextView)v.findViewById(R.id.trail_text_element);
        traILName.setText(tempObject.getName());

        return v;
    }

}
