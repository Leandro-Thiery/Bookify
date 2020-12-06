package com.example.bookify.homenav.notifications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bookify.Book;
import com.example.bookify.BookView;
import com.example.bookify.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

        MultiTransformation<Bitmap> multi = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)
        );

        Glide.with(context).load(book.getCover_url())
                .transform(multi).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).placeholder(R.drawable.ic_launcher_foreground).into(holder.imageLib);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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