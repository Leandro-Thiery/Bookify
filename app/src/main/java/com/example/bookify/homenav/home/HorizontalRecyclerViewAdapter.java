package com.example.bookify.homenav.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookify.BookView;
import com.example.bookify.MainActivity;
import com.example.bookify.R;
import com.example.bookify.Book;
import com.example.bookify.Register;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.HorizontalRVViewHolder> {

    Context context;
    ArrayList<Book> arrayList;

    public HorizontalRecyclerViewAdapter(Context context, ArrayList<Book> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HorizontalRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_horizontal,parent,false);
        return new HorizontalRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalRVViewHolder holder, int position) {
        final Book book = arrayList.get(position);
        holder.textViewTitle.setText(book.getTitle());
        holder.progressBar.setVisibility(View.VISIBLE);


        Picasso.get().load(book.getCover_url()).error(R.mipmap.ic_launcher).into(holder.imageViewThumb, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, book.getDescription(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BookView.class);
                intent.putExtra("Book", book);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HorizontalRVViewHolder extends  RecyclerView.ViewHolder{
        TextView textViewTitle;
        ImageView imageViewThumb;
        ProgressBar progressBar;

        public HorizontalRVViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_title_horizontal);
            imageViewThumb = itemView.findViewById(R.id.ivThumb);
            progressBar = itemView.findViewById(R.id.progressBar4);
        }
    }
}
