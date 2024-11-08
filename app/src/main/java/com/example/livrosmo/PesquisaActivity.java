package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class PesquisaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        Button pesquisar = (Button) findViewById(R.id.button13);
        Button cancelar = (Button) findViewById(R.id.button14);
        EditText searchEditText = (EditText) findViewById(R.id.editTextText3);
    }
}
