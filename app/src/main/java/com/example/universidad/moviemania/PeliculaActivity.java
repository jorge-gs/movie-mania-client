package com.example.universidad.moviemania;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
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
    LinearLayout teatros;

    String urlTrailer;

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
        this.teatros = (LinearLayout) findViewById(R.id.teatros);

        try {
            this.pelicula = new JSONObject(getIntent().getStringExtra("Pelicula"));

            final String imagenURL = this.pelicula.getString("photo");
            this.imagen.setImageUrl(imagenURL, VolleySingleton.getInstance(this).getImageLoader());
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
            this.directores.setText("Dirigida por: " + directoresTexto);
            this.estudios.setText("Producida por: " + estudiosTexto);
            this.generos.setText("Generos: " + generosTexto);
            this.publicacion.setText("Publicada: " + this.pelicula.getString("release_date"));
            this.duracion.setText("Duración: " + this.pelicula.getString("length"));
            this.estrellas.setRating(this.pelicula.getInt("rating"));
            this.edad.setText("Clasificación: " + this.pelicula.getString("classification") + "+");
            this.urlTrailer = this.pelicula.getString("trailer");

            this.sinopsis.setText(this.pelicula.getString("synopsis"));

            JSONArray theaters = this.pelicula.getJSONArray("theaters");
            for (int i  = 0; i < theaters.length(); i++) {
                final JSONObject teatro = theaters.getJSONObject(i);
                TextView textoTeatro = new TextView(this);
                textoTeatro.setText(teatro.getString("name"));
                textoTeatro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            PresentacionListAdapter.presentaciones = teatro.getJSONArray("screenings");
                        } catch (JSONException exception) { }
                        Intent intent = new Intent(PeliculaActivity.this, TeatroActivity.class);
                        intent.putExtra("Teatro", teatro.toString());
                        intent.putExtra("Imagen", imagenURL);
                        startActivity(intent);
                    }
                });
                this.teatros.addView(textoTeatro, i);
            }
        } catch (JSONException exception) {

        }
    }

    public void onTrailer(View view) {
        if (URLUtil.isValidUrl(this.urlTrailer)) {
            Uri uri = Uri.parse(this.urlTrailer);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
