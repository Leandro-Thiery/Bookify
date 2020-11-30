package com.example.bookify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import javax.xml.validation.Validator;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class BookView extends AppCompatActivity {
    TextView textTitle, textCategory, textDescription;
    ImageView imageView, imageViewBack;
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
        imageViewBack = findViewById(R.id.bookViewBack);
        buttonRead = findViewById(R.id.buttonRead);

        textTitle.setText(book.getTitle());
        textCategory.setText(book.getCategory());
        textDescription.setText(book.getDescription());


        MultiTransformation<Bitmap> multi = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(25, 0, RoundedCornersTransformation.CornerType.ALL)
        );

        Glide.with(BookView.this).load(book.getCover_url())
                .transform(multi).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        }).placeholder(R.drawable.ic_launcher_foreground).into(imageView);

        Glide.with(this).load(book.getCover_url())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .placeholder(R.drawable.ic_launcher_foreground).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imageViewBack);


//        Picasso.get().load(book.getCover_url()).placeholder(R.drawable.ic_launcher_background).error(R.mipmap.ic_launcher).into(imageView, new Callback() {
//            @Override
//            public void onSuccess() {
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Toast.makeText(BookView.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
//            }
//        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookView.this, OpenPDF.class);
                intent1.putExtra("Bookpdf", book);
                startActivity(intent1);
            }
        });


    }
}