package com.example.bookify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class OpenPDF extends AppCompatActivity {
    PDFView pdfView;
    ProgressBar progressBar;
    WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        Intent intent = getIntent();
        final Book book = (Book) intent.getSerializableExtra("Bookpdf");
        Toast.makeText(this, book.getPdf_url(), Toast.LENGTH_SHORT).show();
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
//        webView = findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + book.getPdf_url());
//        progressBar.setVisibility(View.GONE);

        pdfView = findViewById(R.id.pdfView);
        url = book.getPdf_url();
        new PDFStream().execute(url);

//        webView = findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(book.pdf_url);
//        progressBar.setVisibility(View.GONE);
        //gak bisa yang ini

        //TODO Load PDF from URL Here, url from book.pdf_url


    }

    class PDFStream extends AsyncTask<String,Void,InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try{
                URL urlx = new URL(strings[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) urlx.openConnection();
                if(urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .password(null)
                    .enableAntialiasing(true)
                    .spacing(10)
                    .load();;
        }
    }


}