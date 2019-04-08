package com.hyunki.bard.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hyunki.bard.R;
import com.hyunki.bard.viewmodel.ViewModel;
import com.hyunki.bard.controller.FragmentInteractionListener;

public class MainFragment extends Fragment {
    private ViewModel viewModel;
    private ImageView logo;
    private Button compose;
    private Button library;
    private FragmentInteractionListener listener;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener){
            listener = (FragmentInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main,container,false);
        logo = rootview.findViewById(R.id.main_imageView);
        compose = rootview.findViewById(R.id.main_compose_button);
        library = rootview.findViewById(R.id.main_songlist_button);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logo.setImageResource(R.drawable.bardlogo);
        compose.setOnClickListener(v -> listener.displayComposer());
        library.setOnClickListener(v -> listener.displayLibrary());
    }
}
