package com.example.livrosmo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaDetalhes extends RecyclerView.Adapter<ListaDetalhes.DetalhesViewHolder> {
    List<DetalhesLivroActivity.DetalhesLivro> detalhesLivros;
    private int selectedItemPosition = RecyclerView.NO_POSITION;

    public ListaDetalhes(List<DetalhesLivroActivity.DetalhesLivro> detalhesLivros) {
        this.detalhesLivros = detalhesLivros;
    }

    @NonNull
    @Override
    public DetalhesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detalhes, parent, false);
        return new DetalhesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalhesViewHolder holder, int position) {
        DetalhesLivroActivity.DetalhesLivro item = detalhesLivros.get(position);

        holder.titleTextView.setText("Título: " + item.getTitle());
        holder.authorsTextView.setText("Autores: " + item.getByStatement().substring(3));
        holder.publishDateTextView.setText("Data de Publicação: " + item.getPublishDate());
        holder.numberOfPagesTextView.setText("Número de Páginas: " + item.getNumberOfPages());
        holder.descriptionTextView.setText("Descrição: " + item.getDescription());


        holder.itemView.setActivated(selectedItemPosition == position);

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            selectedItemPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }
    public void addDetalhesLivro(DetalhesLivroActivity.DetalhesLivro detalhesLivro) {
        detalhesLivros.add(detalhesLivro);
        notifyDataSetChanged();
    }
    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    @Override
    public int getItemCount() {
        return detalhesLivros.size();
    }

    public class DetalhesViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorsTextView;
        TextView publishDateTextView;
        TextView numberOfPagesTextView;
        TextView descriptionTextView;

        public DetalhesViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorsTextView = itemView.findViewById(R.id.authorNameTextView);
            publishDateTextView = itemView.findViewById(R.id.publishDateTextView);
            numberOfPagesTextView = itemView.findViewById(R.id.numberOfPagesTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
