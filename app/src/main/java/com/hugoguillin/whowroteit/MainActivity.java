package com.hugoguillin.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager manager = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(manager != null){
            networkInfo = manager.getActiveNetworkInfo();
        }
        if(networkInfo != null && networkInfo.isConnected()
            && busqueda.length()!=0) {

            new FetchBook(titulo, autor).execute(libroBuscado);
            autor.setText("");
            titulo.setText(R.string.loading);
        }else{
            if(busqueda.length() == 0){
                autor.setText("");
                titulo.setText(getString(R.string.no_search_term));
            }else{
                autor.setText("");
                titulo.setText(getString(R.string.sin_conexion));
            }
        }
    }
}
