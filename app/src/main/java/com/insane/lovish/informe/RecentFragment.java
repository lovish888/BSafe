package com.insane.lovish.informe;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<CrimeDetailsVariables> crimeDetails = new ArrayList<>();

    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < 3; i++) {
            CrimeDetailsVariables variables = new CrimeDetailsVariables();
            if (i == 0) {
                variables.setCrimeImage(BitmapFactory.decodeResource(getResources(), R.drawable.story1));
                variables.setTitle("Gold chain snatched");
                variables.setAuthor("Express new service");
                variables.setTimePassed("8h");

            } else if (i == 1) {
                variables.setCrimeImage(BitmapFactory.decodeResource(getResources(), R.drawable.story2));
                variables.setTitle("Truck rans into tourist bus");
                variables.setAuthor("Express new service");
                variables.setTimePassed("10h");

            } else if (i == 2) {
                variables.setCrimeImage(BitmapFactory.decodeResource(getResources(), R.drawable.story3));
                variables.setTitle("Businessman robbed, then killed");
                variables.setAuthor("Express new service");
                variables.setTimePassed("12h");
            }
            crimeDetails.add(variables);
        }
        adapter = new RecyclerViewAdapter(getActivity(), crimeDetails);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
