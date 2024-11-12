package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CriticaActivity extends AppCompatActivity {
    String utiliAtual = "NONE";

    private static class ReviewRequestBody { //corpo da review no pedido de criação da critica
        private boolean recommended;
        private String review;
        public ReviewRequestBody(boolean recommended, String review) {
            this.recommended = recommended;
            this.review = review;
        }
    }

    public interface ReviewRequestService {
        @POST("/v1/book/{isbn}/review")
        Call<Void> createReview(
                @Path("isbn") String isbn,
                @Body ReviewRequestBody requestBody,
                @Query("userId") String userId
        );
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critica);
        Intent intent = getIntent(); // usado para aceder aos dados que foram passados qnd começou a activity (extras)
        utiliAtual = intent.getStringExtra("idUtili");
        String isbn = intent.getStringExtra("isbn");
        Button Confirmacao = findViewById(R.id.button9);
        Button Cancelar = findViewById(R.id.button10);
        EditText Critica = findViewById(R.id.editTextText2);
        Switch Recomendado = findViewById(R.id.switch1);
        TextView textView10 = findViewById(R.id.textView10);
        TextView textView14 = findViewById(R.id.textView14);

        if ((utiliAtual == null) || utiliAtual.isEmpty() || utiliAtual.equals("NONE")) {
            textView14.setText("Por favor inicie sessão como utilizador");
            textView10.setText(utiliAtual);
            Toast.makeText(this, "ERRO!!! : Por favor inicie sessão como utilizador", Toast.LENGTH_LONG).show();
        } else {
            textView10.setText(utiliAtual);
            textView14.setText("");

            Confirmacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String reviewText = Critica.getText().toString().trim(); //texto escrito pelo cliente (Critica é o que vem do EditText) / .trim é para remover espaços em branco (antes e depois da string) ou enters deixados pelo cliente sem querer
                    boolean recommended = Recomendado.isChecked();;

                    if (!reviewText.isEmpty()) {
                        createReview(recommended, reviewText);
                        finish();
                    } else {
                        Toast.makeText(CriticaActivity.this, "Por favor insira texto antes de continuar", Toast.LENGTH_SHORT).show();
                    }
                }

                private void createReview(boolean recommended, String review){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://193.136.62.24/v1/book/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ReviewRequestService service = retrofit.create(ReviewRequestService.class);

                    Call<Void> creteReviewcall = service.createReview(isbn, new ReviewRequestBody(recommended, review), utiliAtual);
                    Log.d("Request", "URL: " + creteReviewcall.request().url().toString());
                    creteReviewcall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(CriticaActivity.this, "Crítica criada com sucesso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CriticaActivity.this, "Erro na criação da crítica", Toast.LENGTH_SHORT).show();
                                Log.e("Crítica", "Resposta não sucedida: " + response.code() + " " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(CriticaActivity.this, "Failed to create review: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}