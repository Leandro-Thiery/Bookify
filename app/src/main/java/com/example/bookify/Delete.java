package com.example.bookify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Delete extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DeleteRecyclerViewAdapter adapter;
    private ArrayList<Book> books;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        books = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewdelete);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new DeleteRecyclerViewAdapter(this, books);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
        adapter.notifyDataSetChanged();
    }

    private void showList() {
        books.clear();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query deleteRef = FirebaseDatabase.getInstance().getReference("Contributor").child(UserID).orderByChild("title");
        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("books");

        deleteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()){
                    String key = dsp.getKey();
                    DatabaseReference keyBookRef = bookRef.child(key);
                    keyBookRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Book book;
                            book = snapshot.getValue(Book.class);
                            books.add(book);
                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}