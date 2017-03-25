package com.example.universidad.moviemania;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by universidad on 3/18/17.
 */

public class PeliculasListAdapter extends RecyclerView.Adapter<PeliculasListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView imageView;
        TextView textView;

        public ViewHolder (View view) {
            super(view);

            this.textView = (TextView) view.findViewById(R.id.text_view);
            this.imageView = (NetworkImageView) view.findViewById(R.id.image_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            JSONObject jsonObject = Pelicula.peliculas.get(position);
            String imageURL = jsonObject.getString("photo");
            String title = jsonObject.getString("title");

            holder.imageView.setImageUrl(imageURL, VolleySingleton.getInstance(holder.itemView.getContext()).getImageLoader());
            holder.textView.setText(title);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), PeliculaActivity.class);
                    intent.putExtra("Pelicula", Pelicula.peliculas.get(position).toString());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        } catch (JSONException exception) {

        }
    }

    @Override
    public int getItemCount() {
        return Pelicula.peliculas.size();
    }
}
