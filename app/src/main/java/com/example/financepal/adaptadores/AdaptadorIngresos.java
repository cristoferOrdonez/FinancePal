package com.example.financepal.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financepal.entidades.Ingreso;
import com.example.financepal.R;

import java.util.List;

public class AdaptadorIngresos extends BaseAdapter {

    public Context context;
    public List<Ingreso> list;

    public AdaptadorIngresos(Context context, List<Ingreso> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView textViewNombre;
        TextView textViewMonto;
        TextView textViewFecha;

        Ingreso ing = list.get(i);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.listview_ingreso, null);

        textViewNombre = view.findViewById(R.id.textViewNombreINGRESOS);
        textViewMonto = view.findViewById(R.id.textViewMontoINGRESOS);
        textViewFecha = view.findViewById(R.id.textViewFechaINGRESOS);

        textViewNombre.setText(ing.getNombre());
        textViewMonto.setText(ing.getMonto());
        textViewFecha.setText("Fecha: " + ing.getFecha());

        return view;
    }
}
