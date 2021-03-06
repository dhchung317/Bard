package com.hyunki.bard.controller;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyunki.bard.R;
import com.hyunki.bard.model.Song;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private List<Song> songList;
    private FragmentInteractionListener listener;

    public LibraryAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View child = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_itemview, viewGroup, false);
        Context context = viewGroup.getContext();
        if (context instanceof FragmentInteractionListener) {
            listener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + context.getString(R.string.fragment_exception_message));
        }
        return new LibraryViewHolder(child);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder libraryViewHolder, int i) {
        libraryViewHolder.onBind(songList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.library_songTitle_textview);
        }

        public void onBind(final Song song, final FragmentInteractionListener listener) {
            songTitle.setText(song.getSongTitle());
            songTitle.setOnClickListener(v -> listener.displaySong(song));
        }
    }
}
