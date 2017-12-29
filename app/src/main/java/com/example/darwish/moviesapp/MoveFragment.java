package com.example.darwish.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.darwish.adapter.MyAdpter;
import com.example.darwish.data.jsonObject;
import com.example.darwish.helper.ConnectionHelper;
import com.example.darwish.moviesapp.moveDetailes.MoveDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoveFragment extends Fragment {
    //get class name
    private final String classTAG = getClass().getName();
    //custom Adapter
    private MyAdpter moviesAdapter;
    //Array lis of movies json
    ArrayList<jsonObject> data = new ArrayList<>();
    //API name
    final String apiKey = "api_key";
    //Api value
    final String ApiValue="a12135aa5f06f79599ddbc879dd1ff5d";
    //saved jason url in sharedPreferances
    String jasonUrl;
    //string json that will save in shared prefrances
    String jsonSavedPref;
    //gridview
    GridView gridView;
    //flag change mode
    boolean modeChanged=false;
    //poston of last element clicked
    int mPostion;

    public MoveFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPostion!= gridView.INVALID_POSITION) {
            outState.putInt("elementPostion", mPostion);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        updateView();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        moviesAdapter =
                new MyAdpter(getActivity(), R.layout.moveshap, data);
        gridView= (GridView) view.findViewById(R.id.movgrid);
        gridView.setAdapter(moviesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jsonObject x = moviesAdapter.getItem(i);
                ((Callback)getActivity()).onItemSelected(x);
                mPostion=i;
            }
        });
        if(savedInstanceState !=null && savedInstanceState.containsKey("elementPostion")){

            mPostion=savedInstanceState.getInt("elementPostion");
            gridView.setSelection(mPostion);
        }
        return view;
    }

    public interface Callback {
        public void onItemSelected(jsonObject movie);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_settings:
                startActivity(new Intent(getActivity(), Setting.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void updateView() {

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String url = prefs.getString(getString(R.string.modeKey),
                getString(R.string.deafoult));
        Uri builtUri = Uri.parse(url).buildUpon()
                .appendQueryParameter(apiKey,ApiValue)
                .build();
        jasonUrl=builtUri.toString();
        ///////////////////////////////////////////////////////////
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                builtUri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updataAraList(response);
                        jsonSavedPref=response;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String jason=prefs.getString(jasonUrl,null);
                if(jason.length()==0 && jason == null ){
                    Toast.makeText(getActivity(),"please check network connecion",Toast.LENGTH_LONG).show();
                }else {
                    updataAraList(jason);
                }
            }
        });
        queue.add(stringRequest);

    }

    @Override
    public void onDestroy() {

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (jsonSavedPref.length()>0) {

                prefs.edit().putString(jasonUrl, jsonSavedPref).apply();
            }
        super.onDestroy();
    }

    public void updataAraList(String response){
        ArrayList<jsonObject> x= formatMoviesJson(response);
        if (!x.isEmpty()) {

            moviesAdapter.clear();
            data.clear();

            for (jsonObject c : x) {

                data.add(c);

            }
        }
    }
    public ArrayList formatMoviesJson(String json) {
        ArrayList<jsonObject> moveinfo = new ArrayList<>();
        jsonObject move;
        final String resultArray = "results";
        //the sort of json object
        final String ID = "id";
        final String poster = "poster_path";
        final String overview = "overview";
        final String release_date = "release_date";
        final String title = "title";
        final String language = "original_language";
        final String popularity = "popularity";
        final String vote_count = "vote_count";
        final String vote_average = "vote_average";
        try {
            JSONObject objectJson = new JSONObject(json);
            JSONArray ArrayJson = objectJson.getJSONArray(resultArray);
            //object of class JasonObject In pakage data
            JSONObject dataInJsonObj;
            for (int i = 0; i < ArrayJson.length(); i++) {
                dataInJsonObj = ArrayJson.getJSONObject(i);
                move = new jsonObject();
                move.setID(dataInJsonObj.getString(ID));
                move.setPoster(dataInJsonObj.getString(poster));
                move.setOverview(dataInJsonObj.getString(overview));
                move.setRelease_date(dataInJsonObj.getString(release_date));
                move.setTitle(dataInJsonObj.getString(title));
                move.setPopularity(dataInJsonObj.getString(popularity));
                move.setVote_count(dataInJsonObj.getString(vote_count));
                move.setVote_average(dataInJsonObj.getString(vote_average));
                moveinfo.add(move);

            }
            return moveinfo;
        } catch (Exception e) {
            Log.e(classTAG, "json error", e);
            return null;
        }

    }





}
