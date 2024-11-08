package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListaBibliotecas adapter;

    public class Biblioteca {
        private String name;
        private String id;
        public String getName() {
            return name;
        }
        public String getId(){
            return id;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotecas);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaBibliotecas(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        fetchLibraries();
    }

    public interface BibliotecaService {
        @GET("/v1/library/")
        Call<List<BibliotecaActivity.Biblioteca>> getLibraries();
    }

    public void fetchLibraries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/library/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BibliotecaActivity.BibliotecaService service = retrofit.create(BibliotecaActivity.BibliotecaService.class);

        Call<List<BibliotecaActivity.Biblioteca>> call = service.getLibraries();
        call.enqueue(new Callback<List<BibliotecaActivity.Biblioteca>>() {
            @Override
            public void onResponse(Call<List<BibliotecaActivity.Biblioteca>> call, Response<List<BibliotecaActivity.Biblioteca>> response) {
                if (response.isSuccessful()) {
                    List<BibliotecaActivity.Biblioteca> bibliotecas = response.body();
                    if (bibliotecas != null && !bibliotecas.isEmpty()) {
                        adapter = new ListaBibliotecas(bibliotecas);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("Bibliotecas", "Não foram encontradas bibliotecas.");
                    }
                } else {
                    Log.e("Bibliotecas", "Resposta não sucedida: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BibliotecaActivity.Biblioteca>> call, Throwable t) {
                Log.e("Bibliotecas", "Library Request failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = (MenuInflater) getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu_bibliotecas, menu);
        menu.setHeaderTitle("Seleciona uma opção");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((ListaBibliotecas) recyclerView.getAdapter()).getSelectedItemPosition();
        if (position != RecyclerView.NO_POSITION) {
            BibliotecaActivity.Biblioteca biblioteca = adapter.bibliotecas.get(position);
            int itemId = item.getItemId();
            if (itemId == R.id.biblioSeleci) { //ao selecionar biblio, retrieve position and retrieve biblioteca name and id to send to MainActivity
                String nomeBiblio = biblioteca.getName();
                String idBiblio = biblioteca.getId();
                Intent intent = new Intent(BibliotecaActivity.this, MainActivity.class);
                intent.putExtra("nomeBiblioteca", nomeBiblio);
                intent.putExtra("idBiblioteca", idBiblio);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return super.onContextItemSelected(item);
    }
}