package com.example.livrosmo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_USER = 1;
    private static final int REQUEST_CODE_BIBLIOTECA = 2;
    String utiliAtual = "NONE";
    String biblioAtualNome = "NONE";
    String biblioAtualId = "";

    public String getUtiliAtual() {
        return utiliAtual;
    }
    public String getBiblioAtualNome(){
        return biblioAtualNome;
    }
    public String getBiblioAtualId(){
        return biblioAtualId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button livros = findViewById(R.id.button3);
        livros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLivros = new Intent(MainActivity.this, LivrosActivity.class);
                intentLivros.putExtra("idBiblioteca", biblioAtualId);
                intentLivros.putExtra("idUtilizador",utiliAtual);
                startActivity(intentLivros);
            }
        });

        Button user = findViewById(R.id.button4);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUser = new Intent(MainActivity.this, UtilizadorActivity.class);
                startActivityForResult(intentUser, REQUEST_CODE_USER);
            }
        });

        Button biblioteca = findViewById(R.id.button2);
        biblioteca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentBiblio = new Intent(MainActivity.this, BibliotecaActivity.class);
                startActivityForResult(intentBiblio, REQUEST_CODE_BIBLIOTECA);
            }
        });

        Button livrosRequi = findViewById(R.id.button5);
        livrosRequi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainToReq = new Intent(MainActivity.this, RequisicoesActivity.class);
                mainToReq.putExtra("idUtilizador", utiliAtual);
                startActivity(mainToReq);
            }
        });

        Button peqsquisa = findViewById(R.id.button6);
        peqsquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainToPes= new Intent(MainActivity.this, PesquisaActivity.class);
                startActivity(mainToPes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // esta funcao ja existia em java exatamente para o proposito de confirmaçao de resultados
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_USER) { // THIS IS RELATED TO UtilizadorActivity
            if (resultCode == RESULT_OK) {
                String userId = data.getStringExtra("userId");

                if (userId != null && !userId.isEmpty()) {
                    utiliAtual = userId;
                } else {
                    utiliAtual = "NONE";
                }
            } else if (resultCode == RESULT_CANCELED) {
                utiliAtual = "NONE";
            }
        } else if (requestCode == REQUEST_CODE_BIBLIOTECA) {  // THIS IS RELATED TO BiblitecaActivity
            if (resultCode == RESULT_OK) {
                String nomeBiblioteca = data.getStringExtra("nomeBiblioteca");
                String idBiblioteca = data.getStringExtra("idBiblioteca");

                if (nomeBiblioteca != null && !nomeBiblioteca.isEmpty()) {
                    biblioAtualNome = nomeBiblioteca;
                    biblioAtualId = idBiblioteca;
                } else {
                    biblioAtualNome = "NONE";
                    biblioAtualId = "";
                }
            } else if (resultCode == RESULT_CANCELED) {
                biblioAtualNome = "NONE";
                biblioAtualId = "";
            }
        }

        TextView textView = findViewById(R.id.textView3);
        textView.setText(utiliAtual);

        TextView textView1 = findViewById(R.id.textView5);
        textView1.setText(biblioAtualNome);
    }
}




