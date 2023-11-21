package com.example.financepal.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import java.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Locale;

import com.example.financepal.R;
import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.List;

public class CustomAdapterGastos extends BaseAdapter {

    Context context;
    List<UsuarioGastos> lst;
    private DbGastos db;


    public CustomAdapterGastos(Context context, List<UsuarioGastos> lst) {
        this.context = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        db = new DbGastos(context);
        TextView textViewNombreGastos;
        TextView textViewMontoGastos;
        TextView textViewCategoriaGastos;
        TextView textViewRecurrenciaGastos;
        TextView textViewFechaGastos;

        Locale locale = new Locale("es", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);


        view= LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view_gastos, null);

        UsuarioGastos g = lst.get(i);

        textViewNombreGastos = view.findViewById(R.id.textViewNombreGastos);
        textViewMontoGastos = view.findViewById(R.id.textViewMontoGastos);
        textViewCategoriaGastos = view.findViewById(R.id.textViewCategoriaGastos);
        textViewRecurrenciaGastos = view.findViewById(R.id.textViewRecurrenciaGastos);
        textViewFechaGastos = view.findViewById(R.id.textViewFechaGastos);

        textViewNombreGastos.setText(g.getNombregasto());
        textViewMontoGastos.setText(currencyFormatter.format(g.getMontogasto())+" COP");

        String categoria = "Categoria: "+db.mostrarNombreCategoria(g);
        textViewCategoriaGastos.setText(categoria);
        String prioridad="Prioridad: "+String.valueOf(db.mostrarNombrePrioridad(g));
        textViewRecurrenciaGastos.setText(prioridad);
        String fecha = "Fecha: " + g.getFechamesgasto()+"/"+g.getFechaanogasto();
        textViewFechaGastos.setText(fecha);
        return view;
    }
}
