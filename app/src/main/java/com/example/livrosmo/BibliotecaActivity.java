package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;

public class BibliotecaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListaBibliotecas adapter;

    public class Biblioteca {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotecas);

        // Configuração do RecyclerView
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaBibliotecas(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView); // Registrar para menus contextuais

        // Configuração do botão "Criar Biblioteca"
        Button createLibraryButton = findViewById(R.id.createLibraryButton);
        createLibraryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateLibraryActivity.class);
            startActivity(intent); // Inicia a atividade para criar uma nova biblioteca
        });

        // Buscar bibliotecas do servidor
        fetchLibraries();
    }

    // Interface Retrofit para chamadas de API
    public interface BibliotecaService {

        @GET("/v1/library/")
        Call<List<BibliotecaActivity.Biblioteca>> getLibraries();

        @DELETE("v1/library/{id}")
        Call<Void> deleteLibrary(@Path("id") String id);

        @POST("/v1/library/")
        Call<Void> createLibrary(@Body BibliotecaRequest request);
    }

    public void fetchLibraries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/library/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BibliotecaService service = retrofit.create(BibliotecaService.class);

        Call<List<Biblioteca>> call = service.getLibraries();
        call.enqueue(new Callback<List<Biblioteca>>() {
            @Override
            public void onResponse(Call<List<Biblioteca>> call, Response<List<Biblioteca>> response) {
                if (response.isSuccessful()) {
                    List<Biblioteca> bibliotecas = response.body();
                    if (bibliotecas != null && !bibliotecas.isEmpty()) {
                        adapter = new ListaBibliotecas(bibliotecas);
                        recyclerView.setAdapter(adapter); // Atualizar o adaptador com novas bibliotecas
                    } else {
                        Log.e("Bibliotecas", "Não foram encontradas bibliotecas.");
                    }
                } else {
                    Log.e("Bibliotecas", "Resposta não sucedida: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Biblioteca>> call, Throwable t) {
                Log.e("Bibliotecas", "Falha ao buscar bibliotecas: " + t.getMessage());
            }
        });
    }

    // Configurar menu de contexto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu_bibliotecas, menu);
        menu.setHeaderTitle("Seleciona uma opção");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((ListaBibliotecas) recyclerView.getAdapter()).getSelectedItemPosition();
        if (position != RecyclerView.NO_POSITION) {
            Biblioteca biblioteca = adapter.bibliotecas.get(position);
            int itemId = item.getItemId();
            if (itemId == R.id.biblioSeleci) { // Selecionar biblioteca
                String nomeBiblio = biblioteca.getName();
                String idBiblio = biblioteca.getId();
                Intent intent = new Intent(BibliotecaActivity.this, MainActivity.class);
                intent.putExtra("nomeBiblioteca", nomeBiblio);
                intent.putExtra("idBiblioteca", idBiblio);
                setResult(RESULT_OK, intent);
                finish();
            } else if (itemId == R.id.biblioRemover) {
                String idBiblio = biblioteca.getId();
                removeLibrary(idBiblio, position);
            }
        }
        return super.onContextItemSelected(item);
    }

    // Remover biblioteca do servidor e da interface
    private void removeLibrary(String id, int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/library/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BibliotecaService service = retrofit.create(BibliotecaService.class);

        Call<Void> call = service.deleteLibrary(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("Bibliotecas", "Biblioteca removida com sucesso.");
                    adapter.removeItem(position); // Removendo o item localmente
                } else {
                    Log.d("Bibliotecas", "Tentando remover biblioteca com ID: " + id);
                    Log.e("Bibliotecas", "Erro ao remover biblioteca: Código " + response.code() + " - Mensagem: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Bibliotecas", "Falha na remoção: " + t.getMessage());
            }
        });
    }
}
