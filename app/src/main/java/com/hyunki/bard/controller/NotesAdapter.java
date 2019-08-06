package com.hyunki.bard.controller;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.hyunki.bard.R;
import com.hyunki.bard.model.ClickableNote;
import com.hyunki.bard.model.Note;
import com.hyunki.bard.model.Song;
import com.hyunki.bard.view.ComposeFragment;
import com.hyunki.bard.viewmodel.ViewModel;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<ClickableNote> notesList;
    private ClickableNoteListener listener;
    private int selected_position = 0;
    public NotesAdapter(List<ClickableNote> noteList) {
        this.notesList = noteList;
    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View child = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_itemview, viewGroup, false);
        Context context = viewGroup.getContext();
        if (context instanceof ClickableNoteListener) {
            listener = (ClickableNoteListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + context.getString(R.string.fragment_exception_message));
        }

        return new NotesViewHolder(child);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {
        notesViewHolder.onBind(notesList.get(i), listener);
        notesViewHolder.itemView.setBackgroundColor(selected_position == i ? Color.LTGRAY:Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setNotesList(List<ClickableNote> notesList) {
        this.notesList = notesList;
        notifyDataSetChanged();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView noteName;
        ImageView noteImage;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName_textview);
            noteImage = itemView.findViewById(R.id.note_imageView);
        }

        public void onBind(final ClickableNote note, final ClickableNoteListener listener) {
            noteName.setText(note.getNote());
            noteImage.setImageResource(note.getImgSrc());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);

                    listener.setCurrentNote(note);

                    MediaPlayer mp = MediaPlayer.create(itemView.getContext(),note.getRawNote());
                    mp.seekTo(600);
                    mp.start();
                }
            });
        }
    }
}
