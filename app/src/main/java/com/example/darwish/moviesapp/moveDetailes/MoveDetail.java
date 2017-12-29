package com.example.darwish.moviesapp.moveDetailes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darwish.data.jsonObject;
import com.example.darwish.moviesapp.R;
import com.squareup.picasso.Picasso;

public class MoveDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            Bundle arguments=new Bundle();
            jsonObject c=getIntent().getExtras().getParcelable("JsonObject");

            arguments.putParcelable("JsonObject",c);
            MoveDetailFragment detailFragment=new MoveDetailFragment();
            detailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detailesMovieFragment,detailFragment).commit();
        }
    }


}
