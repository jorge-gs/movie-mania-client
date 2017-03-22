package com.example.universidad.moviemania;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

public class PeliculasActivity extends AppCompatActivity {
    PeliculasListAdapter adapter = new PeliculasListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);

        JsonArrayRequest moviesRequest = new JsonArrayRequest("", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Pelicula.peliculas.add(response.getJSONObject(i));
                        adapter.notifyItemInserted(i);
                    }
                } catch (JSONException exception) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
