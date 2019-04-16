package com.hugoguillin.whowroteit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText busqueda;
    private TextView titulo;
    private TextView autor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        busqueda = findViewById(R.id.bookInput);
        titulo = findViewById(R.id.titleText);
        autor = findViewById(R.id.authorText);
    }

    public void searchBooks(View view) {
        String libroBuscado = busqueda.getText().toString();
        new FetchBook(titulo, autor).execute(libroBuscado);
    }
}
