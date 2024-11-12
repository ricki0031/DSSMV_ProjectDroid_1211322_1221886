package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

public class ResultadosPesquisaActivity  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListaResultadosPesquisa adapter;

    public class Pesquisa{
        private String title;
        private String byStatement;private String description;
        private int numberOfPages;private String publishDate;

        public String getTitle(){return title;}
        public String getByStatement(){return byStatement;}
        public int getNumberOfPages(){return numberOfPages;}
        public String getPublishDate(){return publishDate;}
        public String getDescription(){return description;}
    }


    public interface RequestService {
        @GET("/v1/search")
        Call<List<Pesquisa>> search(
                @Query("query") String search
        );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resul_pesquisa);
        Intent intent = getIntent();
        String pesquisa = intent.getStringExtra("pesquisa");
        recyclerView = findViewById(R.id.recyclerView5);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaResultadosPesquisa(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources().getColor(R.color.black), 4));

        Button sair = (Button) findViewById(R.id.button15);

        fetchRequisicoes(pesquisa);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void fetchRequisicoes(String search){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestService service = retrofit.create(RequestService.class);

        Log.d("Pesquisa", "Search query: " + search);

        Call<List<Pesquisa>> call = service.search(search);
        Log.d("Pesquisa", "URL: " + call.request().url().toString());
        call.enqueue(new Callback<List<Pesquisa>>() {
            @Override
            public void onResponse(Call<List<Pesquisa>> call, Response<List<Pesquisa>> response) {
                if (response.isSuccessful()) {
                    List<Pesquisa> searches = response.body();
                    if (searches != null && !searches.isEmpty()) {
                        adapter = new ListaResultadosPesquisa(searches);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("Pesquisa", "Não foram encontradas endereços para a pesquisa.");
                        Toast.makeText(ResultadosPesquisaActivity.this, "Erro na pesquisa", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Pesquisa", "Pesquisa não sucedida: " + response.message());
                    Toast.makeText(ResultadosPesquisaActivity.this, "Pesquisa não sucedida", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pesquisa>> call, Throwable t) {
                Log.e("Pesquisa", "Pesquisa falhou: " + t.getMessage());
            }
        });
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = (MenuInflater) getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu_pesquisa, menu);
        menu.setHeaderTitle("Seleciona uma opção");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((ListaResultadosPesquisa) recyclerView.getAdapter()).getSelectedItemPosition();
        if (position != RecyclerView.NO_POSITION) {
            Pesquisa pesquisa = adapter.pesquisas.get(position);
            int itemId = item.getItemId();
            if (itemId == R.id.descricao) {
                Intent resToDet = new Intent(ResultadosPesquisaActivity.this, DetalhesLivroActivity.class);
                resToDet.putExtra("title", pesquisa.getTitle());
                resToDet.putExtra("authors", pesquisa.getByStatement());
                resToDet.putExtra("Description", pesquisa.getDescription());
                resToDet.putExtra("numberOfPages", pesquisa.getNumberOfPages());
                resToDet.putExtra("publishDate", pesquisa.getPublishDate());
                startActivity(resToDet);
            }
        }
        return super.onContextItemSelected(item);
    }
}
