package com.example.bookify;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.bookify.homenav.library.LibraryRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DeleteRecyclerViewAdapter extends RecyclerView.Adapter<DeleteRecyclerViewAdapter.DeleteRVViewHolder> {
    private Context context;
    private ArrayList<Book> arrayList;

    public DeleteRecyclerViewAdapter(Context context, ArrayList<Book> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DeleteRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_view, parent, false);
        return new DeleteRecyclerViewAdapter.DeleteRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteRVViewHolder holder, final int position) {
        final Book book = arrayList.get(position);
        holder.text.setText(book.getTitle());
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
        }).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class DeleteRVViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView image;
        public DeleteRVViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.libraryTitle);
            image= itemView.findViewById(R.id.libImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Book book = arrayList.get(getAdapterPosition());
                    Intent intent = new Intent(context, BookView.class);
                    intent.putExtra("Book", book);
                    context.startActivity(intent);
                }
            });

        }
    }

}
