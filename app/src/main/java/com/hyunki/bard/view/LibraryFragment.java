package com.hyunki.bard.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyunki.bard.R;
import com.hyunki.bard.viewmodel.ViewModel;
import com.hyunki.bard.controller.LibraryAdapter;
import com.hyunki.bard.model.Song;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
    private ViewModel viewModel;
    private RecyclerView recyclerView;
    private LibraryAdapter adapter;

    public LibraryFragment() {
    }

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_library, container, false);
        recyclerView = rootview.findViewById(R.id.recyclerview);
        adapter = new LibraryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter();
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setAdapter(){
        if(viewModel.getAllSongs() != null) {
            viewModel.getAllSongs().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
                @Override
                public void onChanged(List<Song> songs) {
                    adapter.setSongList(songs);
                }
            });
        }
    }
}