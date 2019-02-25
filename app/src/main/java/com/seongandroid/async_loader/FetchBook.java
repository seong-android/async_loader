package com.seongandroid.async_loader;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> textViewTitle;
    private WeakReference<TextView> textViewAuthor;

    FetchBook(TextView titleText, TextView authorText) {
        this.textViewTitle = new WeakReference<>(titleText);
        this.textViewAuthor = new WeakReference<>(authorText);
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
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;

            while (i < itemsArray.length() &&
                    authors == null && title == null) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch(Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            if (title != null && authors != null) {
                textViewTitle.get().setText(title);
                textViewAuthor.get().setText(authors);
            } else {
                textViewTitle.get().setText("No results");
                textViewAuthor.get().setText("");
            }

        } catch(JSONException e) {
            textViewTitle.get().setText("No results");
            textViewAuthor.get().setText("");
            e.printStackTrace();
        }



    }

}
