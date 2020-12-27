package com.example.bookify.homenav.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.bookify.BookView;
import com.example.bookify.R;
import com.example.bookify.Book;


import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

        MultiTransformation<Bitmap> multi = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)
        );

        Glide.with(context).load(book.getCover_url())
                .transform(multi).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.imageViewThumb);

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
