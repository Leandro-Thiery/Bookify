package com.example.bookify.homenav.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookify.Book;
import com.example.bookify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    LibraryRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Book> books;
    String UserID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        books = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recyclerviewlib);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 3));
        adapter = new LibraryRecyclerViewAdapter(root.getContext(), books);
        recyclerView.setAdapter(adapter);

        inputData();
        return root;
    }

    private void inputData(){
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference libRef = FirebaseDatabase.getInstance().getReference("Library").child(UserID);
        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("books");

        libRef.addValueEventListener(new ValueEventListener() {
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
                            adapter.notifyDataSetChanged();;
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