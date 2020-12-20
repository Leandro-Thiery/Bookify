package com.example.bookify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javax.xml.validation.Validator;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class BookView extends AppCompatActivity {
    TextView textTitle, textCategory, textDescription, textAuthor;
    ImageView imageView, imageViewBack;
    Button buttonRead, buttonAddLib, buttonDelete;
    String url, userId;
    static Boolean exist, contributor;

    DatabaseReference databaseLibrary, databaseContributor;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        Intent intent = getIntent();
        final Book book = (Book) intent.getSerializableExtra("Book");
        textTitle = findViewById(R.id.bookTitleText);
        textCategory = findViewById(R.id.bookCategoryText);
        textAuthor = findViewById(R.id.bookAuthorText);
        textDescription = findViewById(R.id.bookDescriptionText);
        imageView = findViewById(R.id.bookImageView);
        imageViewBack = findViewById(R.id.bookViewBack);
        buttonRead = findViewById(R.id.buttonRead);
        buttonAddLib = findViewById(R.id.buttonAdd);
        buttonDelete = findViewById(R.id.buttonDelete);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        databaseLibrary = FirebaseDatabase.getInstance().getReference("Library").child(userId);
        Query query = databaseLibrary.orderByChild("title").equalTo(book.getTitle());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    exist = true;
                    buttonAddLib.setText("Remove");
                } else {
                    exist = false;
                    buttonAddLib.setText("Add to Library");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseContributor = FirebaseDatabase.getInstance().getReference("Contributor").child(userId);
        Query queryContributor = databaseContributor.orderByChild("title").equalTo(book.getTitle());
        queryContributor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    contributor = true;
                    buttonDelete.setVisibility(View.VISIBLE);
                } else {
                    contributor = false;
                    buttonDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textTitle.setText(book.getTitle());
        textCategory.setText(book.getCategory());
        textDescription.setText(book.getDescription());
        textAuthor.setText(book.getAuthor());






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

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookView.this, OpenPDF.class);
                intent1.putExtra("Bookpdf", book);
                startActivity(intent1);
            }
        });

        buttonAddLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookTitle = book.getTitle();
                String bookID = book.getBook_id();
                if (!exist) {
                    saveTitle(bookTitle, bookID);
                    exist = true;
                    buttonAddLib.setText("Remove");
                } else {
                    removeTitle(bookID);
                    exist = false;
                    buttonAddLib.setText("Add to Library");
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookView.this);
                builder.setMessage("Are you sure you want to delete " + book.getTitle())
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userId = FirebaseAuth.getInstance().getUid();
                                Toast.makeText(BookView.this, "Deleting " + book.getTitle(), Toast.LENGTH_SHORT).show();
                                DatabaseReference databaseContributor = FirebaseDatabase.getInstance().getReference("Contributor").child(userId);
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("books").child(book.getBook_id());
                                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                                databaseContributor.child(book.book_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                                databaseContributor = FirebaseDatabase.getInstance().getReference("Library").child(userId);
                                databaseContributor.child(book.book_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });

                                finish();

                            }
                        })
                        .setNegativeButton("No, Nevermind", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }

    private void removeTitle(String bookID) {
        databaseLibrary.child(bookID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(BookView.this, "Successfully Removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTitle(String bookTitle, String bookID){
        Library library = new Library(bookTitle, bookID);
        databaseLibrary.child(bookID).setValue(library).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(BookView.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}