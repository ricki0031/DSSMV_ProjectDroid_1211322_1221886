
package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RequisicaoActivity extends AppCompatActivity {

    public interface RequestService {
        @POST("/v1/library/{libraryId}/book/{bookId}/checkout")
        Call<Void> createRequest(
                @Path("bookId") String isbn,
                @Path ("libraryId") String libraryId,
                @Query("userId") String userId
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisicao);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("idUtili");
        String isbn = intent.getStringExtra("isbn");
        String title =intent.getStringExtra("title");
        String idBiblio =intent.getStringExtra("libraryId");

        TextView textView12 = findViewById(R.id.textView12);
        TextView textView13 = findViewById(R.id.textView13);
        Button requisitar = findViewById(R.id.button11);
        Button cancelar = findViewById(R.id.button12);

        if(title == null || title.isEmpty()){
            textView13.setText("ERRO | Título do livro não detetado");
        } else textView12.setText("Tem a certeza que quer requisitar " + title + "?");

        requisitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCheckOut(isbn,idBiblio, userId);
            }

            private void createCheckOut(String isbn, String libraryId, String userId) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://193.136.62.24/v1/library/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestService service = retrofit.create(RequestService.class);

                Call<Void> createRequestCall = service.createRequest(isbn, libraryId, userId);
                Log.d("Request", "URL: " + createRequestCall.request().url().toString());
                createRequestCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RequisicaoActivity.this, "Requisição criada com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RequisicaoActivity.this, "Erro na criação da requisição", Toast.LENGTH_SHORT).show();
                            Log.e("Requisição", "Resposta não sucedida: " + response.code() + " " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(RequisicaoActivity.this, "Falha ao criar a requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
