package com.example.bookify.homenav.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookify.BookView;
import com.example.bookify.CategoryView;
import com.example.bookify.R;
import com.example.bookify.Book;
import com.example.bookify.homenav.home.models.VerticalModel;

import java.util.ArrayList;

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalRVViewHolder> {

    Context context;
    ArrayList<VerticalModel> arrayList;
    public VerticalRecyclerViewAdapter(Context context, ArrayList<VerticalModel> arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public VerticalRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new VerticalRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalRVViewHolder holder, int position) {
        final VerticalModel verticalModel = arrayList.get(position);
        String title = verticalModel.getTitle();
        ArrayList<Book> singleitem = verticalModel.getArrayList();

        holder.title.setText(title);
        HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(context,singleitem);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setAdapter(horizontalRecyclerViewAdapter);

        holder.buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = verticalModel.getTitle();
                Intent intent = new Intent(context, CategoryView.class);
                intent.putExtra("Category", category);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VerticalRVViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView title;
        Button buttonMore;
        public VerticalRVViewHolder(View itemView){
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycleviewhomehorizontal);
            title = itemView.findViewById(R.id.text_title);
            buttonMore = itemView.findViewById(R.id.buttonMore);


        }
    }


}
