package com.example.financepal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financepal.InfoGasto;
import com.example.financepal.R;
import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterGastos extends BaseAdapter {

    Context context;
    ArrayList<UsuarioGastos> lst;
    private DbGastos db;


    public CustomAdapterGastos(Context context, ArrayList<UsuarioGastos> lst) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        db = new DbGastos(context);
        TextView textViewNombreGastos;
        TextView textViewMontoGastos;
        TextView textViewCategoriaGastos;
        TextView textViewRecurrenciaGastos;

        UsuarioGastos g = lst.get(i);

        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view, null);
        }

        textViewNombreGastos = view.findViewById(R.id.textViewNombreGastos);
        textViewMontoGastos = view.findViewById(R.id.textViewMontoGastos);
        textViewCategoriaGastos = view.findViewById(R.id.textViewCategoriaGastos);
        textViewRecurrenciaGastos = view.findViewById(R.id.textViewRecurrenciaGastos);

        textViewNombreGastos.setText(g.getNombregasto());
        textViewMontoGastos.setText(g.getMontogasto());
        textViewCategoriaGastos.setText(db.mostrarNombreCategoria(g.getIdcatgasto(),g.getCorreogasto()));
        textViewRecurrenciaGastos.setText(g.getRecurrenciagasto());
        return view;
    }
}
