package com.example.livrosmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UtilizadorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        EditText userIdEditText = (EditText) findViewById(R.id.editTextText);
        Button confirmarButton = (Button) findViewById(R.id.button8);
        Button cancelarButton = (Button) findViewById(R.id.button7);

        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userIdEditText.getText().toString().trim(); //passa o texto para string e poe na string userId

                Intent intent = new Intent(UtilizadorActivity.this, MainActivity.class); //cria um intent para passar a string userId da UtilizadorActivity para MainActivity
                intent.putExtra("userId", userId);

                setResult(RESULT_OK, intent); //funcao para, em MainActivity, saber se o resultado do envio dos dados foi bem sucedido ou não
                finish(); //termina UtilizadorActivity
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED); //Se "Cancelar" for selecionado, termina UtilizadorActivity sem passar userId
                finish();
            }
        });
    }
}
