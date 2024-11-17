package com.example.livrosmo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_library);

        EditText nameInput = findViewById(R.id.editName);
        EditText addressInput = findViewById(R.id.editAddress);
        EditText openDaysInput = findViewById(R.id.editOpenDays);
        EditText openTimeInput = findViewById(R.id.editOpenTime);
        EditText closeTimeInput = findViewById(R.id.editCloseTime);
        Button createButton = findViewById(R.id.btnCreateLibrary);

        createButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String address = addressInput.getText().toString();
            String openDays = openDaysInput.getText().toString();
            String openTime = openTimeInput.getText().toString();
            String closeTime = closeTimeInput.getText().toString();

            if (name.isEmpty() || address.isEmpty() || openDays.isEmpty() || openTime.isEmpty() || closeTime.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            createLibrary(new BibliotecaRequest(name, address, openDays, openTime, closeTime));
        });
    }

    private void createLibrary(BibliotecaRequest request) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://193.136.62.24/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BibliotecaActivity.BibliotecaService service = retrofit.create(BibliotecaActivity.BibliotecaService.class);

        Call<Void> call = service.createLibrary(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateLibraryActivity.this, "Biblioteca criada com sucesso!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Log.e("CreateLibrary", "Erro ao criar biblioteca: " + response.message());
                    Toast.makeText(CreateLibraryActivity.this, "Erro: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("CreateLibrary", "Falha na conexão", t);
                Toast.makeText(CreateLibraryActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
