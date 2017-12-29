package com.example.darwish.moviesapp.moveDetailes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.darwish.adapter.MyAdpter;
import com.example.darwish.adapter.ReviewAdapter;
import com.example.darwish.adapter.TrailListAdabter;
import com.example.darwish.data.ReviewObject;
import com.example.darwish.data.TrailObject;
import com.example.darwish.data.jsonObject;
import com.example.darwish.helper.ConnectionHelper;
import com.example.darwish.moviesapp.MoveFragment;
import com.example.darwish.moviesapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoveDetailFragment extends Fragment {
    private TrailListAdabter moviesAdapter;
    private ReviewAdapter reviewAdapter;
    ArrayList<TrailObject> data = new ArrayList<>();
    ArrayList<ReviewObject> reviewData = new ArrayList<>();
    private final String classTAG = getClass().getName();
    String movieDetailes;
    String movieReview;
    //API name
    final String apiKey = "api_key";
    //Api value
    final String ApiValue="a12135aa5f06f79599ddbc879dd1ff5d";
    int favourit=0;


    public MoveDetailFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Bundle arguments=getArguments();
        View view=inflater.inflate(R.layout.fragment_move_detail,container,false);
        final jsonObject object;
        if(arguments != null) {
            object = arguments.getParcelable("JsonObject");

            //set move name
            TextView movieName = (TextView) view.findViewById(R.id.movie_name);
            movieName.setBackgroundResource(R.color.backgroundcolor);
            movieName.setText(object.getTitle());
            //set movie poster
            ImageView movieImage = (ImageView) view.findViewById(R.id.movie_image);
            Picasso.with(this.getActivity()).load("http://image.tmdb.org/t/p/w342/" + object.getPoster()).into(movieImage);
            //set relase date
            TextView relase_date = (TextView) view.findViewById(R.id.relase_date);
            relase_date.setText(object.getRelease_date());
            //set time
            TextView move_time = (TextView) view.findViewById(R.id.movie_time);
            move_time.setText("not define");
            //set movie rate
            TextView movie_rate = (TextView) view.findViewById(R.id.movie_rate);
            movie_rate.setText(object.getVote_average().concat("/10"));
            //mark it button
            //get favourit movie from sharedPreferances

                     favourit =prefs.getInt(object.getID(),0);

            final Button button=(Button)view.findViewById(R.id.bt_rate_movie);
            if(favourit==0) {
                button.setText("mark as \n favourit");
                button.setBackgroundResource(R.color.white);
            }else {
                button.setText("favourit");
                button.setBackgroundResource(R.color.backgroundcolor);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favourit==0){
                        prefs.edit().putInt(object.getID(),Integer.parseInt(object.getID())).apply();
                        favourit =prefs.getInt(object.getID(),0);
                        button.setText("favourit");
                        button.setBackgroundResource(R.color.backgroundcolor);
                    }else {
                        prefs.edit().remove(object.getID()).apply();
                        favourit =prefs.getInt(object.getID(),0);
                        button.setText("mark as \n favourit");
                        button.setBackgroundResource(R.color.white);
                    }

                }
            });
            //set overview
            TextView movie_overview = (TextView) view.findViewById(R.id.movie_review);
            movie_overview.setText(object.getOverview());
            //trail process
            getTrail(object.getID());
            moviesAdapter = new TrailListAdabter(getActivity(), R.layout.trailelement, data);
            ListView trailAdapter = (ListView) view.findViewById(R.id.movie_traile);
            trailAdapter.setAdapter(moviesAdapter);
            trailAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TrailObject trail;
                    trail = moviesAdapter.getItem(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse("https://www.youtube.com/watch").buildUpon().appendQueryParameter("v", trail.getKey()).build();
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            //review process
            getReview(object.getID());
            reviewAdapter = new ReviewAdapter(getActivity(), R.layout.reviwelement, reviewData);
            ListView reviewListview = (ListView) view.findViewById(R.id.movie_reviwer);
            reviewListview.setAdapter(reviewAdapter);

        }
        return view;
    }

    public void getTrail(String id){

        String url="http://api.themoviedb.org/3/movie/"+id+"/videos";
        Uri builtUri = Uri.parse(url).buildUpon()
                .appendQueryParameter(apiKey,ApiValue)
                .build();
        ///////////////////////////////////////////////////////////
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                builtUri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        ArrayList<TrailObject> trailObjects= getTrailInfo(response);
                        if (!trailObjects.isEmpty()) {

                            //SharedPreferences prefs = PreferenceManager.se;
                            //prefs
                            moviesAdapter.clear();

                            for ( TrailObject c : trailObjects) {

                                data.add(c);
                                //moviesAdapter.add(c);


                            }
                        }

                        //gridView.setAdapter(moviesAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Please Check network connection",Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);


    }
    public void getReview(String id){
        String url="http://api.themoviedb.org/3/movie/"+id+"/reviews";
        Uri builtUri = Uri.parse(url).buildUpon()
                .appendQueryParameter(apiKey,ApiValue)
                .build();
        ///////////////////////////////////////////////////////////
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                builtUri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        ArrayList<ReviewObject> reviewObjects= getReviewInfo(response);
                        if (!reviewObjects.isEmpty()) {

                            //SharedPreferences prefs = PreferenceManager.se;
                            //prefs
                            reviewAdapter.clear();

                            for ( ReviewObject c : reviewObjects) {

                                reviewData.add(c);
                                //moviesAdapter.add(c);


                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"cPlease Check network connection",Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);


    }
    /////extrac trail data from json
    public ArrayList<TrailObject> getTrailInfo(String jsonStr){
        final String ID="id",RESULT="results",KEY="key",NAME="name";
        ArrayList<TrailObject> list=new ArrayList<TrailObject>();
        TrailObject object=new TrailObject();
        try {
            JSONObject objectJson = new JSONObject(jsonStr);
            object.setId(objectJson.getString(ID));
            JSONArray ArrayJson = objectJson.getJSONArray(RESULT);
            //object of class JasonObject In pakage data
            JSONObject dataInJsonObj;
            for (int i = 0; i < ArrayJson.length(); i++) {
                dataInJsonObj = ArrayJson.getJSONObject(i);
                object.setKey(dataInJsonObj.getString(KEY));
                object.setName(dataInJsonObj.getString(NAME));
                list.add(object);

            }
            return list;
        }catch (Exception e){

            Log.e(classTAG, "json error", e);
            return null;
        }
    }
    /////extrac trail data from json
    public ArrayList<ReviewObject> getReviewInfo(String jsonStr){
        final String ID="id",RESULT="results",author="author",content="content";
        ArrayList<ReviewObject> list=new ArrayList<ReviewObject>();
        ReviewObject object=new ReviewObject();
        try {
            JSONObject objectJson = new JSONObject(jsonStr);
            object.setId(objectJson.getString(ID));
            JSONArray ArrayJson = objectJson.getJSONArray(RESULT);
            //object of class JasonObject In pakage data
            JSONObject dataInJsonObj;
            for (int i = 0; i < ArrayJson.length(); i++) {
                dataInJsonObj = ArrayJson.getJSONObject(i);
                object.setAuthor(dataInJsonObj.getString(author));
                object.setContent(dataInJsonObj.getString(content));
                list.add(object);

            }
            return list;
        }catch (Exception e){

            Log.e(classTAG, "json error", e);
            return null;
        }
    }
    }



