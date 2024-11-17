package com.example.livrosmo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaBibliotecas extends RecyclerView.Adapter<ListaBibliotecas.ViewHolder> {
    List<BibliotecaActivity.Biblioteca> bibliotecas;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ListaBibliotecas(List<BibliotecaActivity.Biblioteca> bibliotecas) {
        this.bibliotecas = bibliotecas;
    }

    public int getSelectedItemPosition() {
        return selectedPosition;
    }

    public void removeItem(int position) {
        bibliotecas.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biblioteca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BibliotecaActivity.Biblioteca biblioteca = bibliotecas.get(position);

        holder.nomeBiblioteca.setText(biblioteca.getName());
        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            return false; // Permite o menu de contexto abrir
        });
    }

    @Override
    public int getItemCount() {
        return bibliotecas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomeBiblioteca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeBiblioteca = itemView.findViewById(R.id.nomeBiblioteca); // Verifique se o ID está correto
        }
    }
}
