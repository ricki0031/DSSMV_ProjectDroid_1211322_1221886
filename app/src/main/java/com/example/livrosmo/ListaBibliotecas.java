package com.example.livrosmo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListaBibliotecas extends RecyclerView.Adapter<ListaBibliotecas.BibliotecaViewHolder> {

    List<BibliotecaActivity.Biblioteca> bibliotecas;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    public ListaBibliotecas(List<BibliotecaActivity.Biblioteca> bibliotecas) {
        this.bibliotecas = bibliotecas;
    }

    @NonNull
    @Override
    public BibliotecaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_biblioteca, parent, false);
        return new BibliotecaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BibliotecaViewHolder holder, int position) {
        BibliotecaActivity.Biblioteca biblioteca = bibliotecas.get(position);
        holder.bibliotecaNameTextView.setText(biblioteca.getName() != null ? biblioteca.getName() : "Biblioteca não disponível");
        holder.itemView.setActivated(selectedItemPosition == position);

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            selectedItemPosition = holder.getAdapterPosition();
        });
    }

    public void setSelectedItemPosition(int position) {
        selectedItemPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    @Override
    public int getItemCount() {
        return bibliotecas.size();
    }

    public class BibliotecaViewHolder extends RecyclerView.ViewHolder {
        TextView bibliotecaNameTextView;

        public BibliotecaViewHolder(View itemView) {
            super(itemView);
            bibliotecaNameTextView = itemView.findViewById(R.id.bibliotecaNameTextView);
        }
    }

}
