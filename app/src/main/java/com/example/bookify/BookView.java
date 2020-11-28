package com.example.bookify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BookView extends AppCompatActivity {
    TextView textTitle, textCategory, textDescription;
    ImageView imageView;
    Button buttonRead;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        Intent intent = getIntent();
        final Book book = (Book) intent.getSerializableExtra("Book");
        textTitle = findViewById(R.id.bookTitleText);
        textCategory = findViewById(R.id.bookCategoryText);
        textDescription = findViewById(R.id.bookDescriptionText);
        imageView = findViewById(R.id.bookImageView);
        buttonRead = findViewById(R.id.buttonRead);

        textTitle.setText(book.getTitle());
        textCategory.setText(book.getCategory());
        textDescription.setText(book.getDescription());


        Picasso.get().load(book.getCover_url()).error(R.mipmap.ic_launcher).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(BookView.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookView.this, OpenPDF.class);
                intent1.putExtra("Book", book);
                startActivity(intent1);
            }
        });


    }
}