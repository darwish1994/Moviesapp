package com.example.darwish.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.darwish.data.jsonObject;
import com.example.darwish.moviesapp.moveDetailes.MoveDetail;
import com.example.darwish.moviesapp.moveDetailes.MoveDetailFragment;


public class MainActivity extends AppCompatActivity implements MoveFragment.Callback {
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.detailesMovieFragment) != null) {
            isTablet = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detailesMovieFragment, new MoveDetailFragment()).commit();
                Toast.makeText(this, "tablet view", Toast.LENGTH_LONG).show();

            }


        } else {
            isTablet = false;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onItemSelected(jsonObject movie) {
        if (isTablet) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("JsonObject", movie);
            MoveDetailFragment detailFragment = new MoveDetailFragment();
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailesMovieFragment,detailFragment)
                    .commit();
        }else{
            Intent intent = new Intent(this,MoveDetail.class).putExtra("JsonObject", movie);
            startActivity(intent);

        }
    }
}
