package com.example.universidad.moviemania;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PeliculasActivity extends AppCompatActivity {
    PeliculasListAdapter adapter = new PeliculasListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);

        JsonObjectRequest moviesRequest = new JsonObjectRequest("http://www.moviemania.com/json.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray peliculas = response.getJSONArray("movies");
                    for (int i = 0; i < peliculas.length(); i++) {
                        Pelicula.peliculas.add(peliculas.getJSONObject(i));
                        adapter.notifyItemInserted(i);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PeliculasActivity", error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(moviesRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();

        View view = findViewById(R.id.peliculas_list);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(this.adapter);
        }
    }
}
