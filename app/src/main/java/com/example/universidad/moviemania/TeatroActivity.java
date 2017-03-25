package com.example.universidad.moviemania;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class TeatroActivity extends AppCompatActivity {
    JSONObject teatro;
    PresentacionListAdapter adapter = new PresentacionListAdapter();

    NetworkImageView imagen;
    TextView nombre;
    TextView horario;
    TextView direccion;
    TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teatros);

        this.imagen = (NetworkImageView) findViewById(R.id.imagen_teatro);
        this.nombre = (TextView) findViewById(R.id.nombre_teatro);
        this.horario = (TextView) findViewById(R.id.horario_teatro);
        this.direccion = (TextView) findViewById(R.id.direccion_teatro);
        this.link = (TextView) findViewById(R.id.link_teatro);

        try {
            this.teatro = new JSONObject(getIntent().getStringExtra("Teatro"));

            this.imagen.setImageUrl(getIntent().getStringExtra("Imagen"), VolleySingleton.getInstance(this).getImageLoader());
            this.nombre.setText(this.teatro.getString("name"));
            this.horario.setText("Abierto " + this.teatro.getString("opens") + " - " + this.teatro.getString("closes"));
            this.direccion.setText("Dirección: " + this.teatro.getString("address"));
            this.link.setText("Web: " + this.teatro.getString("link"));
        } catch (JSONException exception) {
        }

        View view = findViewById(R.id.horarios);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(this.adapter);
        }
    }
}
