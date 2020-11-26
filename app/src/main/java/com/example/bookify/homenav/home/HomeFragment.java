package com.example.bookify.homenav.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookify.R;
import com.example.bookify.homenav.home.models.HorizontalModel;
import com.example.bookify.homenav.home.models.VerticalModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView verticalRecyclerView;
    VerticalRecyclerViewAdapter adapter;
    ArrayList<VerticalModel> arrayListVertical;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //
        //homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //   @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});

        arrayListVertical = new ArrayList<>();

        verticalRecyclerView = root.findViewById(R.id.recycleviewhome);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new VerticalRecyclerViewAdapter(root.getContext(), arrayListVertical);
        verticalRecyclerView.setAdapter(adapter);

        setData();
        Toast.makeText(root.getContext(), "Help", Toast.LENGTH_SHORT).show();

        // vertical adapter for recyclerview



        return root;
    }

    private void setData() {
        for (int i = 0; i <= 5; i++) {
            VerticalModel verticalModel = new VerticalModel();
            verticalModel.setTitle("Title: "+i);
            ArrayList<HorizontalModel> arrayListHorizontal = new ArrayList<>();

            for(int j=0;j<=5;j++){
                HorizontalModel horizontalModel = new HorizontalModel();
                horizontalModel.setDescription("Description: " +j);
                horizontalModel.setName(i+"Name: "+j);
                arrayListHorizontal.add(horizontalModel);

            }

            verticalModel.setArrayList(arrayListHorizontal);
            arrayListVertical.add(verticalModel);
        }
        adapter.notifyDataSetChanged();
    }
}