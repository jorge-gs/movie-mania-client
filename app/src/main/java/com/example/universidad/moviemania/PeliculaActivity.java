package com.example.universidad.moviemania;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PeliculaActivity extends AppCompatActivity {
    JSONObject pelicula;

    NetworkImageView imagen;
    TextView titulo;
    TextView directores;
    TextView estudios;
    TextView generos;
    TextView publicacion;
    TextView duracion;
    RatingBar estrellas;
    TextView edad;
    Button trailer;
    TextView sinopsis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);

        this.imagen = (NetworkImageView) findViewById(R.id.imagen);
        this.titulo = (TextView) findViewById(R.id.titulo);
        this.directores = (TextView) findViewById(R.id.directores);
        this.estudios = (TextView) findViewById(R.id.estudios);
        this.generos = (TextView) findViewById(R.id.generos);
        this.publicacion = (TextView) findViewById(R.id.publicacion);
        this.duracion = (TextView) findViewById(R.id.duracion);
        this.estrellas = (RatingBar) findViewById(R.id.estrellas);
        this.edad = (TextView) findViewById(R.id.edad);
        this.trailer = (Button) findViewById(R.id.trailer);
        this.sinopsis = (TextView) findViewById(R.id.sinopsis);

        try {
            this.pelicula = new JSONObject(getIntent().getStringExtra("Pelicula"));

            this.imagen.setImageUrl(this.pelicula.getString("photo"), VolleySingleton.getInstance(this).getImageLoader());
            this.titulo.setText(this.pelicula.getString("title"));
            String directoresTexto = "";
            JSONArray directors = pelicula.getJSONArray("directors");
            for (int i = 0; i < directors.length(); i++) {
                JSONObject director = directors.getJSONObject(i);
                directoresTexto += (i == 0 ? "" : ", ") + director.getString("name");
            }
            String estudiosTexto = "";
            JSONArray studios = pelicula.getJSONArray("studios");
            for (int i = 0; i < studios.length(); i++) {
                String studio = studios.getString(i);
                estudiosTexto += (i == 0 ? "" : ", ") + studio;
            }
            String generosTexto = "";
            JSONArray genres = pelicula.getJSONArray("genres");
            for (int i = 0; i < genres.length(); i++) {
                String genero = genres.getString(i);
                generosTexto += (i == 0 ? "" : ", ") + genero;
            }
            this.directores.setText(directoresTexto);
            this.estudios.setText(estudiosTexto);
            this.generos.setText(generosTexto);
            this.publicacion.setText(this.pelicula.getString("release_date"));
            this.duracion.setText(this.pelicula.getString("length"));
            this.estrellas.setRating(this.pelicula.getInt("rating"));
            this.edad.setText(this.pelicula.getString("classification"));

            this.sinopsis.setText(this.pelicula.getString("synopsis"));
        } catch (JSONException exception) {

        }
    }
}
