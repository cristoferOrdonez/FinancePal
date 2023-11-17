package com.example.financepal.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financepal.R;
import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.UsuarioCategoriasGasto;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.List;

public class CustomAdapterCategGastos extends BaseAdapter {

    Context context;
    List<UsuarioCategoriasGasto> lst;

    private DbGastos db;

    public CustomAdapterCategGastos(Context context, List<UsuarioCategoriasGasto> lst) {
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
        TextView textViewDescCat;
        TextView textViewNombreCat;

        view= LayoutInflater.from(context).inflate(R.layout.custom_list_view_catgastos, null);

        UsuarioCategoriasGasto g = lst.get(i);
        textViewNombreCat = view.findViewById(R.id.textViewNombreCat);
        textViewDescCat = view.findViewById(R.id.textViewDescCat);

        textViewNombreCat.setText(g.getNombrecatgasto());
        textViewDescCat.setText(g.getDesccatgasto());

        return view;
    }
}
