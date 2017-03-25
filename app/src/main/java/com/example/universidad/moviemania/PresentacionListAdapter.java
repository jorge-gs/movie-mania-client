package com.example.universidad.moviemania;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by universidad on 3/24/17.
 */

public class PresentacionListAdapter extends RecyclerView.Adapter<PresentacionListAdapter.ViewHolder> {
    public static JSONArray presentaciones;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hora;
        TextView precio;

        public ViewHolder(View view) {
            super(view);
            this.hora = (TextView) view.findViewById(R.id.hora);
            this.precio = (TextView) view.findViewById(R.id.precio);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject presentacion = presentaciones.getJSONObject(position);
            holder.hora.setText(presentacion.getString("date"));
            holder.precio.setText("L. " + presentacion.getString("price"));
        } catch (JSONException exception) {
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.presentacion_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return presentaciones.length();
    }
}
