package com.example.financepal.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financepal.R;
import com.example.financepal.entidades.EntidadHistorico;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdaptadorHistorico extends BaseAdapter {

    Context context;
    List<EntidadHistorico> lst;

    public AdaptadorHistorico(Context context, List<EntidadHistorico> lst) {
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
        TextView viewFechaHistorico;
        TextView viewBalanceHistorico;
        TextView viewGastosMensualesHistorico;
        TextView viewIngresosMensualesHistorico;

        EntidadHistorico ent = lst.get(i);
        Locale locale = new Locale("es", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.lista_item_historico, null);

        viewFechaHistorico = view.findViewById(R.id.viewFechaHistorico);
        viewBalanceHistorico= view.findViewById(R.id.viewBalanceHistorico);
        viewGastosMensualesHistorico= view.findViewById(R.id.viewGastosMensualesHistorico);
        viewIngresosMensualesHistorico= view.findViewById(R.id.viewIngresosMensualesHistorico);

        String fechaHistorico = ent.getFechaHistorico();

        viewFechaHistorico.setText("Periodo: " + MetodosComunes.obtenerPrefijoMes(Integer.parseInt(fechaHistorico.substring(0,fechaHistorico.indexOf("/")))) + " " + fechaHistorico.substring(fechaHistorico.indexOf("/") + 1));
        viewBalanceHistorico.setText("Balance: "+currencyFormatter.format(ent.getIngresoTotalHistorico()-ent.getGastoTotalHistorico())+" COP");
        viewGastosMensualesHistorico.setText("Gasto Mensual: "+currencyFormatter.format(ent.getGastoTotalHistorico())+" COP");
        viewIngresosMensualesHistorico.setText("Ingreso Mensual: "+currencyFormatter.format(ent.getIngresoTotalHistorico())+" COP");

        return view;
    }
}
