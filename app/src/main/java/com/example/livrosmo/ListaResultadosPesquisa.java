package com.example.livrosmo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaResultadosPesquisa extends RecyclerView.Adapter<ListaResultadosPesquisa.PesquisaViewHolder> {
    List<ResultadosPesquisaActivity.Pesquisa> pesquisas;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    public ListaResultadosPesquisa(List<ResultadosPesquisaActivity.Pesquisa> pesquisas) {
        this.pesquisas = pesquisas;
    }

    @NonNull
    @Override
    public ListaResultadosPesquisa.PesquisaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resultados_pesquisa, parent, false);
        return new ListaResultadosPesquisa.PesquisaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaResultadosPesquisa.PesquisaViewHolder holder, int position) {
        ResultadosPesquisaActivity.Pesquisa item = pesquisas.get(position);
        holder.titleTextView.setText(item.getTitle());

        holder.itemView.setActivated(selectedItemPosition == position);

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            selectedItemPosition = holder.getAdapterPosition();
        });
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    @Override
    public int getItemCount() {
        return pesquisas.size();
    }

    public class PesquisaViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public PesquisaViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }
    }
}

