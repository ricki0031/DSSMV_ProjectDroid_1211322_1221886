package com.example.livrosmo;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListaLivros extends RecyclerView.Adapter<ListaLivros.LivrosViewHolder> {
    List<LivrosActivity.Livro> livros;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    public ListaLivros(List<LivrosActivity.Livro> livros) {
        this.livros = livros;
    }
    @NonNull
    @Override
    public LivrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_livro, parent, false);
        return new LivrosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LivrosViewHolder holder, int position) {
        LivrosActivity.Livro livro = livros.get(position);
        holder.livroNameTextView.setText(livro.getTitle() != null ? livro.getTitle() : "Livro não disponível");
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
        return livros.size();
    }

    public class LivrosViewHolder extends RecyclerView.ViewHolder {
        TextView livroNameTextView;

        public LivrosViewHolder(View itemView) {
            super(itemView);
            livroNameTextView = itemView.findViewById(R.id.livroNameTextView);
        }
    }



}


