package com.example.livrosmo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaRequisicoes extends RecyclerView.Adapter<ListaRequisicoes.RequisicoesViewHolder> {
    List<RequisicoesActivity.EstadoLivro> estadoLivros;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    public ListaRequisicoes(List<RequisicoesActivity.EstadoLivro> estadoLivros) {
        this.estadoLivros = estadoLivros;
    }

    @NonNull
    @Override
    public RequisicoesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requisicao, parent, false);
        return new RequisicoesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequisicoesViewHolder holder, int position) {
        RequisicoesActivity.EstadoLivro item = estadoLivros.get(position);
        if (item != null) {
            holder.pedidoIdTextView.setText("Identificação do pedido: " + item.getId());
            holder.userIdTextView.setText("Nome do utilizador: " + item.getUserId());
            holder.activeTextView.setText("Pedido ativo?:  " + (item.isActive() ? "Sim" : "Não"));
            holder.createTimestampTextView.setText("Data de criação: " + item.getCreateTimestamp());
            holder.updateTimestampTextView.setText("Última atualização: " + item.getUpdateTimestamp());
            holder.dueDateTextView.setText("Data de entrega" + item.getDueDate());
            holder.libraryNameTextView.setText("Nome da biblioteca: " + item.getLibraryName());
            holder.libraryAddressTextView.setText("Endereço da biblioteca: " + item.getLibraryAddress());
            holder.libraryOpenTimeTextView.setText("Abertura da biblioteca: " + item.getLibraryOpenTime());
            holder.libraryCloseTimeTextView.setText("Encerramento da biblioteca: " + item.getLibraryCloseTime());
            holder.bookIsbnTextView.setText("isbn do livro: " + item.getIsbn());
            holder.bookTitleTextView.setText("Título do livro: " + item.getTitle());
        }else {holder.bookIsbnTextView.setText("O livro não foi recebido");}

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
        return estadoLivros.size();
    }

    public class RequisicoesViewHolder extends RecyclerView.ViewHolder {
        TextView pedidoIdTextView;
        TextView userIdTextView;
        TextView activeTextView;
        TextView createTimestampTextView;
        TextView updateTimestampTextView;
        TextView dueDateTextView;
        TextView libraryNameTextView;
        TextView libraryAddressTextView;
        TextView libraryOpenTimeTextView;
        TextView libraryCloseTimeTextView;
        TextView bookIsbnTextView;
        TextView bookTitleTextView;

        public RequisicoesViewHolder(View itemView) {
            super(itemView);
            pedidoIdTextView= itemView.findViewById(R.id.pedidoIdTextView);
            userIdTextView = itemView.findViewById(R.id.UserIdTextView);
            activeTextView = itemView.findViewById(R.id.activeTextView);
            createTimestampTextView = itemView.findViewById(R.id.createTimestampTextView);
            updateTimestampTextView = itemView.findViewById(R.id.updateTimestampTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            libraryNameTextView = itemView.findViewById(R.id.libraryNameTextView);
            libraryAddressTextView = itemView.findViewById(R.id.libraryAddressTextView);
            libraryOpenTimeTextView = itemView.findViewById(R.id.libraryOpenTimeTextView);
            libraryCloseTimeTextView = itemView.findViewById(R.id.libraryCloseTimeTextView);
            bookIsbnTextView = itemView.findViewById(R.id.bookIsbnTextView);
            bookTitleTextView = itemView.findViewById(R.id.bookTitleTextView);
        }
    }
}
