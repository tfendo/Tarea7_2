package com.hugoguillin.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> titulo;
    private WeakReference<TextView> autor;

    FetchBook(TextView title, TextView author){
        titulo = new WeakReference<>(title);
        autor = new WeakReference<>(author);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray array = jsonObject.getJSONArray("items");
            int i = 0;
            String title = null, author = null;

            while (i < array.length() && (author == null && title == null)){
                JSONObject libro = array.getJSONObject(i);
                JSONObject volumeInfo = libro.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    author = volumeInfo.getString("authors");
                }catch (Exception e){
                    e.printStackTrace();
                }
                i++;
            }
            if (title != null && author != null){
                titulo.get().setText(title);
                autor.get().setText(author);
            }else{
                titulo.get().setText(R.string.no_results);
                autor.get().setText("");
            }
        }catch (Exception e){
            titulo.get().setText(R.string.no_results);
            autor.get().setText("");
            e.printStackTrace();
        }
    }
}
