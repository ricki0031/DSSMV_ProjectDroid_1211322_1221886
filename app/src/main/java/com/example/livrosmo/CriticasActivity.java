package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.ArrayList;
import java.util.List;

public class CriticasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListaCriticas adapter;
    String livroIsbn;

    public static class Review {
        private String id;
        private String reviewer;
        private String isbn;
        private boolean recommended;
        private String review;
        private String createdDate;

        public String getId(){return id;}
        public String getReviewer(){return reviewer;}
        public String getIsbn(){return isbn;}
        public boolean isRecommended(){return recommended;}
        public String getReview(){return review;}
        public String getCreatedDate(){return createdDate;}
    }

    public interface ReviewService {
        @GET("/v1/book/{isbn}/review")
        Call<List<Review>> getReviews(
                @Path("isbn") String isbn
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criticas);
        Intent intent = getIntent();
        livroIsbn = intent.getStringExtra("isbn");
        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaCriticas(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources().getColor(R.color.black), 4));
        fetchReviews(livroIsbn);
    }

    public void fetchReviews(String isbn){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/v1/book/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReviewService service = retrofit.create(ReviewService.class);

        Call<List<Review>> call = service.getReviews(isbn);
        Log.d("Request", "URL: " + call.request().url().toString());
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    if (reviews != null && !reviews.isEmpty()) {
                        adapter = new ListaCriticas(reviews);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("Criticas", "Não foram encontradas críticas.");
                    }
                } else {
                    Log.e("Criticas", "Resposta não sucedida: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e("Criticas", "Obtenção de críticas falhou: " + t.getMessage());
            }
        });
    }

}
