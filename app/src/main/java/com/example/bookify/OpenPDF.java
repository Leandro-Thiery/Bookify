package com.example.bookify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class OpenPDF extends AppCompatActivity {
    private PDFView pdfView;
    private Book book;
    private ProgressBar progressBar;
    int pageNumber;
    private String url;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("Bookpdf");
        Toast.makeText(this, book.getPdf_url(), Toast.LENGTH_SHORT).show();
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        pdfView = findViewById(R.id.pdfView);
        pdfView.setVisibility(View.INVISIBLE);
        url = book.getPdf_url();
        new PDFStream().execute(url);


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
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .defaultPage(0)
                    .password(null)
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            pageNumber = page;
                            setTitle(String.format("%s %s / %s", book.getTitle(), page + 1, pageCount));
                        }
                    })
                    .scrollHandle(new DefaultScrollHandle(context))
                    .enableAntialiasing(true)
                    .spacing(10)
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            progressBar.setVisibility(View.GONE);
                            pdfView.setVisibility(View.VISIBLE);
                        }
                    })
                    .load();
            progressBar.setVisibility(View.GONE);
        }

    }


}