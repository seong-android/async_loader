package com.seongandroid.async_loader;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView mTextViewTitle;
    private TextView mTextViewAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set the content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // grab the view elements
        mEditText = (EditText) findViewById(R.id.editText);
        mTextViewTitle = (TextView) findViewById(R.id.textView_result_title);
        mTextViewAuthor = (TextView) findViewById(R.id.textView_result_author);
    }

    public void searchBooks(View view) {
        String queryString = mEditText.getText().toString();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        Toast.makeText(this, "Query: " + queryString, Toast.LENGTH_SHORT).show();
        new FetchBook(mTextViewTitle, mTextViewAuthor).execute(queryString);

        mTextViewTitle.setText("Loading...");
        mTextViewAuthor.setText("");
    }
}
