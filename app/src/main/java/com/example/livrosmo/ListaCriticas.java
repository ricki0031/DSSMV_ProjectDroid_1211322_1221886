package com.example.livrosmo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListaCriticas extends RecyclerView.Adapter<ListaCriticas.CriticasViewHolder> {
    List<CriticasActivity.Review> reviews;
    private int selectedItemPosition = RecyclerView.NO_POSITION;

    public ListaCriticas(List<CriticasActivity.Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public CriticasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_criticas, parent, false);
        return new CriticasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CriticasViewHolder holder, int position) {
        CriticasActivity.Review item = reviews.get(position);
        if (item != null) {
            holder.reviewIdTextView.setText("ID da crítica: " + item.getId());
            holder.reviewerTextView.setText("Nome do crítico: " + item.getReviewer());
            holder.isbnTextView.setText("Isbn: " + item.getIsbn());
            holder.recommendedTextView.setText("Recomenda?: " + (item.isRecommended() ? "Sim" : "Não"));
            holder.reviewTextView.setText("Crítica: " + item.getReview().trim());
            holder.createdDateTextView.setText("Data de criação: " + item.getCreatedDate());
        }

        holder.itemView.setActivated(selectedItemPosition == position);

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            selectedItemPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class CriticasViewHolder extends RecyclerView.ViewHolder {
        TextView reviewIdTextView;
        TextView reviewerTextView;
        TextView isbnTextView;
        TextView recommendedTextView;
        TextView reviewTextView;
        TextView createdDateTextView;

        public CriticasViewHolder(View itemView) {
            super(itemView);
            reviewIdTextView = itemView.findViewById(R.id.reviewIdTextView);
            reviewerTextView = itemView.findViewById(R.id.reviewerTextView);
            isbnTextView = itemView.findViewById(R.id.isbnTextView);
            recommendedTextView = itemView.findViewById(R.id.recommendedTextView);
            reviewTextView = itemView.findViewById(R.id.reviewTextView);
            createdDateTextView = itemView.findViewById(R.id.createdDateTextView);
        }
    }
}
