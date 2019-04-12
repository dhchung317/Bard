package com.hyunki.bard.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyunki.bard.Animations;
import com.hyunki.bard.R;
import com.hyunki.bard.controller.FragmentInteractionListener;

public class MainFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.mainFragment_header_textview)
    TextView mainTitle;
    @BindView(R.id.mainFragment_imageView)
    ImageView logo;
    @BindView(R.id.mainFragment_compose_button)
    Button compose;
    @BindView(R.id.main_songlist_button)
    Button library;
    private FragmentInteractionListener listener;

    public static MainFragment newInstance(){
        return new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        logo.setImageResource(R.drawable.bardlogo);
//        compose.setOnClickListener(v -> listener.displayComposer());
//        library.setOnClickListener(v -> listener.displayLibrary());
        Animations.setDropTitleAnimation(mainTitle);
        Animations.setPopUpAnimation(compose);
        Animations.setPopUpAnimation(library);
    }

    @OnClick({R.id.mainFragment_compose_button,
            R.id.main_songlist_button})
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.mainFragment_compose_button:
                listener.displayComposer();
                break;
            case R.id.main_songlist_button:
                listener.displayLibrary();
                break;
        }
    }
}
