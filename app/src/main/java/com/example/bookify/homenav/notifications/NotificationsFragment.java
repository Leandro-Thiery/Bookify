package com.example.bookify.homenav.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookify.Book;
import com.example.bookify.BookView;
import com.example.bookify.R;
import com.example.bookify.homenav.search.SearchFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    LibraryRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Book> books;
    String UserID;
    private ImageButton searchButton;
    private EditText searchText;
    private Button searchClear;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        books = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recyclerviewlib);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 3));
        adapter = new LibraryRecyclerViewAdapter(root.getContext(), books);
        recyclerView.setAdapter(adapter);

        searchButton = root.findViewById(R.id.librarySearchButton);
        searchClear = root.findViewById(R.id.librarySearchClear);
        searchText = root.findViewById(R.id.librarySearchText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchInput = searchText.getText().toString();
                firebaseSearch(searchInput);
            }
        });

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event == null || event.getAction() != KeyEvent.ACTION_DOWN)
                    return false;
                String searchInput = searchText.getText().toString();
                firebaseSearch(searchInput);
                return true;
            }
        });

        searchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textInput = searchText.getText().toString();
                if (textInput.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Text is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    searchText.setText("");
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        inputData();
        adapter.notifyDataSetChanged();
    }

    private void firebaseSearch(String searchInput) {
        books.clear();
        adapter.notifyDataSetChanged();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference libRef = FirebaseDatabase.getInstance().getReference("Library").child(UserID);
        Query query = libRef.orderByChild("title").startAt(searchInput).endAt(searchInput + "\uf8ff");
        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("books");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
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

    private void inputData(){
        books.clear();
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