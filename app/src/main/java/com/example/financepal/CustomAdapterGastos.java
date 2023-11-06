package com.example.financepal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterGastos extends BaseAdapter {

    Context context;
    List<InfoGasto> lst;

    public CustomAdapterGastos(Context context, List<InfoGasto> lst) {
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
        TextView editTextNombreCrearModificarGastos;
        TextView editTextMontoCrearModificarIGastos;
        TextView editTextRecurrenciaCrearModificarGastos;

        InfoGasto g = lst.get(i);

        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view, null);
        }

        editTextNombreCrearModificarGastos = view.findViewById(R.id.editTextNombreCrearModificarGastos);
        editTextMontoCrearModificarIGastos = view.findViewById(R.id.editTextMontoCrearModificarIGastos);
        editTextRecurrenciaCrearModificarGastos = view.findViewById(R.id.editTextRecurrenciaCrearModificarGastos);

        editTextNombreCrearModificarGastos.setText(g.nombre);
        editTextMontoCrearModificarIGastos.setText(g.monto);
        editTextRecurrenciaCrearModificarGastos.setText(g.recurrencia);
        return view;
    }
}
