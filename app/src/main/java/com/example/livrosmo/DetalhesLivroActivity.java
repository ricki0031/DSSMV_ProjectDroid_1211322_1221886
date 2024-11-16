package com.example.livrosmo;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetalhesLivroActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListaDetalhes adapter;

    public class DetalhesLivro{
        private String title;
        private String byStatement;private String description;
        private int numberOfPages;private String publishDate;

        public String getTitle(){return title;}
        public String getByStatement(){return byStatement;}
        public int getNumberOfPages(){return numberOfPages;}
        public String getPublishDate(){return publishDate;}
        public String getDescription(){return description;}

        public void setTitle(String title){this.title = title;}
        public void setByStatement(String byStatement){this.byStatement = byStatement;}
        public void setNumberOfPages(int numberOfPages){this.numberOfPages = numberOfPages;}
        public void setPublishDate(String publishDate){this.publishDate = publishDate;}
        public void setDescription(String description){this.description = description;}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Button sair = findViewById(R.id.button);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String authors = intent.getStringExtra("authors");
        String publishDate = intent.getStringExtra("publishDate");
        int numberOfPages = intent.getIntExtra("numberOfPages", 0);
        String description = intent.getStringExtra("Description");

        recyclerView = findViewById(R.id.recyclerView6);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaDetalhes(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources().getColor(R.color.black), 4));

        DetalhesLivro sampleDetalhesLivro = new DetalhesLivro();
        sampleDetalhesLivro.setTitle(title);
        sampleDetalhesLivro.setByStatement("By " + authors);
        sampleDetalhesLivro.setPublishDate(publishDate);
        sampleDetalhesLivro.setNumberOfPages(numberOfPages);
        sampleDetalhesLivro.setDescription(description);

        adapter.addDetalhesLivro(sampleDetalhesLivro);
    }
}
