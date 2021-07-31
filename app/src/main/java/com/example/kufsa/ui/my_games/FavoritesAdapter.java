package com.example.kufsa.ui.my_games;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.ItemGameInFavoriteListBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class FavoritesAdapter extends FirestoreRecyclerAdapter<BoardGame, FavoritesAdapter.BoardGameHolder> {

    private OnItemClickListener listener;

    public FavoritesAdapter(@NonNull FirestoreRecyclerOptions<BoardGame> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BoardGameHolder holder, int position, @NonNull BoardGame model) {
        holder.binding.textViewGameName.setText(model.getName());

        Glide.with(holder.itemView)
                .load(model.getImg())
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(holder.binding.gameImageView);
    }

    @NonNull
    @Override
    public BoardGameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_in_favorite_list,
                parent, false);
        return new BoardGameHolder(v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class BoardGameHolder extends RecyclerView.ViewHolder {
        ItemGameInFavoriteListBinding binding;

        public BoardGameHolder(View itemView) {
            super(itemView);
            binding = ItemGameInFavoriteListBinding.bind(itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }
}



