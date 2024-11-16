package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.List;


public class RequisicoesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListaRequisicoes adapter;
    String userId;
    String checkoutId;

    public static class EstadoLivro {
        private static class OLivro {
            private String title;
            private String isbn;

            public String getBookTitle(){return title;}
            public String getBookIsbn(){return isbn;}

        }
        private String id; private boolean active; private String dueDate; private String createTimestamp; private String userId;
        private String updateTimestamp; private String libraryName; private String libraryAddress; private String libraryOpenTime; private String libraryCloseTime;
        private OLivro book;

        public EstadoLivro(String userId) {
            this.userId = userId;
        }
        public String getId(){return id;}
        public String getUserId(){return userId;}
        public boolean isActive(){return active;}
        public String getDueDate(){return dueDate;}
        public String getCreateTimestamp(){return createTimestamp;}
        public String getUpdateTimestamp(){return updateTimestamp;}
        public String getLibraryName(){return libraryName;}
        public String getLibraryAddress(){return libraryAddress;}
        public String getLibraryOpenTime(){return libraryOpenTime;}
        public String getLibraryCloseTime(){return libraryCloseTime;}
        public String getTitle(){return book.getBookTitle();}
        public String getIsbn(){return book.getBookIsbn();}

    }


    public interface RequestService {
        @GET("/v1/user/checked-out")
        Call<List<EstadoLivro>> getCheckedOutBooks(
                @Query("userId") String userId
        );

        @POST("/v1/checkout/{id}/extend")
        Call<Void> extendCheckout(
                @Path("id") String checkoutId
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisicoes);
        Intent intent = getIntent();
        userId = intent.getStringExtra("idUtilizador");
        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaRequisicoes(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        if(userId.equals("NONE")){
            Toast.makeText(RequisicoesActivity.this, "Por favor inicie sessão de utilizador", Toast.LENGTH_LONG).show();
        }else{fetchRequisicoes(userId);}
    }

    public void fetchRequisicoes(String userId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestService service = retrofit.create(RequestService.class);

        Call<List<EstadoLivro>> call = service.getCheckedOutBooks(userId);
        Log.d("Request", "URL: " + call.request().url().toString());
        call.enqueue(new Callback<List<EstadoLivro>>() {
            @Override
            public void onResponse(Call<List<EstadoLivro>> call, Response<List<EstadoLivro>> response) {
                if (response.isSuccessful()) {
                    List<EstadoLivro> estadoLivros = response.body();
                    if (estadoLivros != null && !estadoLivros.isEmpty()) {
                        adapter = new ListaRequisicoes(estadoLivros);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("Requisicoes", "Não foram encontradas requisições.");
                    }
                } else {
                    Log.e("Requisicoes", "Resposta não sucedida: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<EstadoLivro>> call, Throwable t) {
                Log.e("Requisicoes", "Requisições falharam: " + t.getMessage());
            }
        });
    }

    public void fetchAdiamento(String checkoutId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/checkout/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestService service = retrofit.create(RequestService.class);

        Call<Void> call = service.extendCheckout(checkoutId);
        Log.d("Request", "URL: " + call.request().url().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.e("Adiamento", "Adiamento bem sucedido");
                    Toast.makeText(RequisicoesActivity.this, "Adiamento realizado com sucesso", Toast.LENGTH_SHORT).show();
                } else {Log.e("Adiamento", "Adiamento não sucedido.");}
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {Log.e("Adiamento", "Adiamento falhou");}
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = (MenuInflater) getMenuInflater();
        menuInflater.inflate(R.menu.contexmenu_requisicoes, menu);
        menu.setHeaderTitle("Seleciona uma opção");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((ListaRequisicoes) recyclerView.getAdapter()).getSelectedItemPosition();
        if (position != RecyclerView.NO_POSITION) {
            EstadoLivro estadoLivro = adapter.estadoLivros.get(position);
            checkoutId = estadoLivro.getId();
            int itemId = item.getItemId();
            if (itemId == R.id.adiamento) {
                fetchAdiamento(checkoutId);
            }
        }
        return super.onContextItemSelected(item);
    }
}
