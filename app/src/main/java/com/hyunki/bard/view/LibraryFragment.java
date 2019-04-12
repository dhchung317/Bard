package com.hyunki.bard.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyunki.bard.R;
import com.hyunki.bard.viewmodel.ViewModel;
import com.hyunki.bard.controller.LibraryAdapter;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {
    private ViewModel viewModel;
    private LibraryAdapter adapter;
    @BindView(R.id.libraryFragment_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.libraryFragment_exit_button)
    Button exitLibraryButton;

    public LibraryFragment() {}

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
//        recyclerView = rootview.findViewById(R.id.recyclerview);
//        exitLibraryButton = rootview.findViewById(R.id.exit_library_button);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new LibraryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter();
        exitLibraryButton.setOnClickListener(v -> getActivity().onBackPressed());
    }

    public void setAdapter(){
        if(viewModel.getAllSongs() != null) {
            viewModel.getAllSongs().observe(getViewLifecycleOwner(), songs -> adapter.setSongList(songs));
        }
    }
}
