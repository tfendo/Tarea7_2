package com.hugoguillin.whowroteit;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String URL_DE_BUSQUEDA = "https://www.googleapis.com/books/v1/volumes?";
    private static final String TITULO = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String TIPO = "printType";

    static String getBookInfo(String consulta){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String respuesta = null;

        try {
            Uri crearURI = Uri.parse(URL_DE_BUSQUEDA).buildUpon()
                    .appendQueryParameter(TITULO, consulta)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(TIPO, "books")
                    .build();
            URL urlConsulta = new URL(crearURI.toString());

            urlConnection = (HttpURLConnection) urlConsulta.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String linea;
            while ((linea = reader.readLine())!=null){
                builder.append(linea);
                builder.append("\n");
            }
            if(builder.length() == 0){
                return null;
            }
            respuesta = builder.toString();
        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, respuesta);
        return respuesta;
    }
}
