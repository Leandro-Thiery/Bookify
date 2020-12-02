package com.example.bookify.homenav.dashboard;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.core.app.RemoteInput;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookify.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class DashboardFragment extends Fragment {

    private ImageButton mSearchButton;
    private EditText mSearchText;

    private RecyclerView mResultList;
    private DatabaseReference mSearchDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mSearchDatabase = FirebaseDatabase.getInstance().getReference("books");

        mSearchText = (EditText) root.findViewById(R.id.searchText);
        mSearchButton = (ImageButton) root.findViewById(R.id.searchButton);

        mResultList = (RecyclerView) root.findViewById(R.id.resultList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchInput = mSearchText.getText().toString();
                firebaseSearch(searchInput);
            }
        });
        return root;
    }



    private void firebaseSearch(String searchInput){

        Query firebaseSearchQuery = mSearchDatabase.orderByChild("title").startAt(searchInput).endAt(searchInput + "\uf8ff");
        FirebaseRecyclerOptions<VerticalRecyclerViewSearch> options =
                new FirebaseRecyclerOptions.Builder<VerticalRecyclerViewSearch>()
                    .setQuery(firebaseSearchQuery, VerticalRecyclerViewSearch.class)
                    .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<VerticalRecyclerViewSearch, SearchViewHolder>(options) {
            @NonNull
            @Override
            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_view, parent,false);

                return new SearchViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull VerticalRecyclerViewSearch model) {
                holder.setDetails(getActivity().getApplicationContext(),model.getTitle(),model.getCover_url(),model.getAuthor());
            }

        };

        mResultList.setAdapter(adapter);
        adapter.startListening();
    }

    // View Holder Class

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String showTitle, String showCover, String showAuthor){

            TextView show_title = (TextView) mView.findViewById(R.id.searchTitle);
            TextView show_author = (TextView) mView.findViewById(R.id.searchAuthor);
            ImageView show_cover = (ImageView) mView.findViewById(R.id.searchCover);

            show_title.setText(showTitle);
            show_author.setText(showAuthor);

            Glide.with(ctx).load(showCover).into(show_cover);
        }

    }
}