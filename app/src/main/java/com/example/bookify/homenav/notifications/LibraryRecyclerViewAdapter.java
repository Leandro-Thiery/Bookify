package com.example.bookify.homenav.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookify.Book;
import com.example.bookify.R;
import com.example.bookify.homenav.home.VerticalRecyclerViewAdapter;

import java.util.ArrayList;

public class LibraryRecyclerViewAdapter extends RecyclerView.Adapter<LibraryRecyclerViewAdapter.LibraryRVViewHolder> {

    Context context;
    ArrayList<Book> arrayList;

    public LibraryRecyclerViewAdapter(Context context, ArrayList<Book> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LibraryRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_view, parent, false);
        return new LibraryRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryRVViewHolder holder, int position) {
        final Book book = arrayList.get(position);
        holder.textLib.setText(book.getTitle());
        Glide.with(context).load(book.getCover_url()).into(holder.imageLib);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class LibraryRVViewHolder extends RecyclerView.ViewHolder{
        TextView textLib;
        ImageView imageLib;

        public LibraryRVViewHolder(View itemView){
            super(itemView);
            textLib = itemView.findViewById(R.id.libraryTitle);
            imageLib = itemView.findViewById(R.id.libImage);
        }
    }

}