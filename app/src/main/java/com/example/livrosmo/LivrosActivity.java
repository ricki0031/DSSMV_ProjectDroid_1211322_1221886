

package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import retrofit2.http.Path;


public class LivrosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListaLivros adapter;
    private String idUtili;
    private String idBiblio;

    public class Livro {
        private class Book{

            private String title;private String isbn;private String byStatement;private String description;
            private String numberOfPages;private String publishDate;

            public String getBookTitle(){return title;}
            public String getBookIsbn(){return isbn;}
            public String getBookByStatement(){return byStatement;}
            public String getBookNumberOfPages(){return numberOfPages;}
            public String getBookPublishDate(){return publishDate;}
            public String getBookDescription(){return description;}

        }
        private String stock;private String available; private String isbn;
        private Book book;

        public String getStock(){return stock;}
        public String getAvailable() {return available;}
        public String getTitle(){return book.getBookTitle();}
        public String getIsbn(){return isbn; /*book.getBookIsbn();*/}
        public String getBookByStatement(){return book.getBookByStatement();}
        public String getNumberOfPages(){return book.getBookNumberOfPages();}
        public String getBookPublishDate(){return book.getBookPublishDate();}
        public String getBookDescription(){return book.getBookDescription();}

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);
        Intent intent = getIntent(); // usado para aceder aos dados que foram passados qnd começou a activity (extras)
        idBiblio = intent.getStringExtra("idBiblioteca");
        idUtili = intent.getStringExtra("idUtilizador");

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaLivros(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);

        fetchLibraryBooks(idBiblio);
    }


    public interface LibraryBookService {
        @GET("/v1/library/{libraryId}/book")
        Call<List<Livro>> getBooks(@Path("libraryId") String libraryId);
    }

    public void fetchLibraryBooks(String idBiblio){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/library/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LibraryBookService service = retrofit.create(LibraryBookService.class);

        Call<List<Livro>> call = service.getBooks(idBiblio);
        Log.d("Request", "URL: " + call.request().url().toString());
        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful()) {
                    List<Livro> livros = response.body();
                    if (livros != null && !livros.isEmpty()) {
                        adapter = new ListaLivros(livros);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("Livros", "Não foram encontrados livros.");
                    }
                } else {
                    Log.e("Livros", "Resposta não sucedida: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.e("Livros", "Books Request failed: " + t.getMessage());
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = (MenuInflater) getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu_livros, menu);
        menu.setHeaderTitle("Seleciona uma opção");
    }


}