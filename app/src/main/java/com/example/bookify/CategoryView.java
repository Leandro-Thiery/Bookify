package com.example.bookify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bookify.homenav.library.LibraryRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryView extends AppCompatActivity {

    LibraryRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Book> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        Intent intent = getIntent();
        String category = (String) intent.getSerializableExtra("Category");
        books = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewcateogry);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new LibraryRecyclerViewAdapter(this, books);
        recyclerView.setAdapter(adapter);

        inputData(category);

    }

    private void inputData(String category) {
        Query categoryref = null;
        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("books");

        if (!(category.equals("ALL"))) {
            categoryref = FirebaseDatabase.getInstance().getReference("books").orderByChild("category").equalTo(category);
        } else{
            categoryref = bookRef;
        }
        // Path reference to books database


        categoryref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Book book;
                for (DataSnapshot snapshot1 : snapshot.getChildren() ){
                    book = snapshot1.getValue(Book.class);
                    books.add(book);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }


}