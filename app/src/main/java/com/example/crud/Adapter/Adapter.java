package com.example.crud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.crud.R;
import com.example.crud.model.Comida;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Comida> {
    public Adapter(Context context, ArrayList<Comida> datos) {
        super(context, R.layout.ly_item, datos);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.ly_item, null);

        TextView lblnombre = (TextView)item.findViewById(R.id.LblNombre);
        TextView lblacompa = (TextView)item.findViewById(R.id.Lblcacomp);
        TextView lblprescio = (TextView)item.findViewById(R.id.LblPrecio);
        TextView lbldisp = (TextView)item.findViewById(R.id.lblDispo);
        //.error(R.drawable.imgnotfound)
        lblnombre.setText("Nombre:"+getItem(position).getNombre());
        lblacompa.setText("Acompañado:"+getItem(position).getAcompaniado());
        lblprescio.setText("$"+getItem(position).getPrecio());
        lbldisp.setText("El producto "+getItem(position).getDisponible() + " Disponible");
        return(item);
    }
}

