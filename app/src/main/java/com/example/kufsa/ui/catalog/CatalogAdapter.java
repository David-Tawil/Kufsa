package com.example.kufsa.ui.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.databinding.ItemGameInCatalogBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Adapter for the catalog as a recyclerview.
 */
public class CatalogAdapter extends FirestoreRecyclerAdapter<BoardGame, CatalogAdapter.BoardGameHolder> {

    private OnItemClickListener listener;

    /**
     * We call settings for the recycler view's adapter.
     *
     * @param options the settings for this recycler view.
     */
    public CatalogAdapter(@NonNull FirestoreRecyclerOptions<BoardGame> options) {
        super(options);
    }

    /**
     * @param holder   holder of the recycler view.
     * @param position view's position.
     * @param model    board game object.
     */
    @Override
    protected void onBindViewHolder(@NonNull BoardGameHolder holder, int position, @NonNull BoardGame model) {
        holder.binding.textViewGameName.setText(model.getName());

        Glide.with(holder.itemView)
                .load(model.getImg())
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(holder.binding.gameImageView);
    }

    /**
     * @param parent   view that holds the recyclverview children views.
     * @param viewType id for view type.
     * @return board game holder object.
     */
    @NonNull
    @Override
    public BoardGameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_in_catalog,
                parent, false);
        return new BoardGameHolder(v);
    }

    /**
     * Set up a listener for clicking any item in the view
     *
     * @param listener The listener used when clicking on an item.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Activate the listener when item in the view is clicked
     */
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    /**
     * Define the boardgame holder.
     */
    class BoardGameHolder extends RecyclerView.ViewHolder {
        ItemGameInCatalogBinding binding;

        public BoardGameHolder(View itemView) {
            super(itemView);
            binding = ItemGameInCatalogBinding.bind(itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }

}

