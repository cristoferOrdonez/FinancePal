package com.example.financepal.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financepal.R;
import com.example.financepal.VerMeta;
import com.example.financepal.entidades.MetasInfo;

import java.util.ArrayList;

public class ListaMetasAdapter extends RecyclerView.Adapter<ListaMetasAdapter.MetaViewHolder> {

    ArrayList<MetasInfo> listaMetas;

    public ListaMetasAdapter( ArrayList<MetasInfo> listaMetas){
        this.listaMetas=listaMetas;
    }
    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_meta, null, false);
        return new MetaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        holder.viewNombreMeta.setText(listaMetas.get(position).getNombreMeta());
        holder.viewFechaMeta.setText(listaMetas.get(position).getFechaMeta());
        holder.viewMontoMeta.setText(String.valueOf(listaMetas.get(position).getMontoMeta()));


    }

    @Override
    public int getItemCount() {
        return listaMetas.size();

    }

    public class MetaViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombreMeta, viewFechaMeta, viewMontoMeta;

        public MetaViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombreMeta=itemView.findViewById(R.id.viewNombreMeta);
            viewFechaMeta=itemView.findViewById(R.id.viewFechaMeta);
            viewMontoMeta=itemView.findViewById(R.id.viewMontoMeta);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent =new Intent(context, VerMeta.class );
                    intent.putExtra("ID", listaMetas.get(getAdapterPosition()).getId());
                    context.startActivity(intent );
                }
            });


        }
    }
}
