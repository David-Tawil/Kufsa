package com.example.kufsa.ui.my_games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kufsa.R;
import com.example.kufsa.data.BoardGame;
import com.example.kufsa.data.MarketAd;
import com.example.kufsa.databinding.ItemListingInUserAdsBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * This class is an adapter for the user ads in market
 */
public class MyAdsAdapter extends FirestoreRecyclerAdapter<MarketAd, MyAdsAdapter.AdHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options - Firestore query option for this recycler
     */
    public MyAdsAdapter(@NonNull FirestoreRecyclerOptions<MarketAd> options) {
        super(options);
    }

    /**
     * called immediately after item view is created
     *
     * @param holder   - item holder
     * @param position - holder position in
     * @param model    - model class
     */
    @Override
    protected void onBindViewHolder(@NonNull MyAdsAdapter.AdHolder holder, int position, @NonNull MarketAd model) {

        String date = new SimpleDateFormat("dd/MM/yy", holder.itemView.getResources().getConfiguration().locale).format(model.getPublishDate());
        String tradeTyp = model.getTradeType().toString();
        String price = "";

        if (model.getTradeType() == MarketAd.TradeType.SELL) {
            price = " - $" + new DecimalFormat("#").format(model.getSellPrice());
        }
        if (model.getTradeType() == MarketAd.TradeType.RENT) {
            price = " - $" + new DecimalFormat("#").format(model.getRentalFee()) + " " + model.getRentalPeriod();
        }
        String infoText = date + " - " + tradeTyp + price;
        holder.binding.adInfoText.setText(infoText);
        holder.binding.deleteButton.setOnClickListener(view -> showDeleteConfirmDialog(holder.itemView.getContext(), model));

        //retrieve game image and name
        FirebaseFirestore.getInstance().collection("games").document(model.getGameID()).get().addOnSuccessListener(documentSnapshot -> {
            BoardGame game = documentSnapshot.toObject(BoardGame.class);
            if (game != null) {
                Glide.with(holder.itemView)
                        .load(game.getImg())
                        .error(R.drawable.ic_error)
                        .into(holder.binding.gameImg);
                holder.binding.gmaeNameText.setText(game.getName());
            }
        });


    }

    /**
     * shows alert dialog to confirm the user wants to delete an ad
     *
     * @param context context
     * @param model   ad data class
     */
    private void showDeleteConfirmDialog(Context context, MarketAd model) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Delete this ad?")
                .setMessage("This can't be undone")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (dialogInterface, i) -> {
                    //delete ad from game collection
                    FirebaseFirestore.getInstance().collection("games").document(model.getGameID()).collection("listing").document(model.getId()).delete();
                    //delete ad from user collection
                    FirebaseFirestore.getInstance().collection("users").document(model.getUserID()).collection("userListing").document(model.getId()).delete();
                    Toast.makeText(context, "Ad deleted", Toast.LENGTH_LONG).show();
                })
                .show();
    }

    /**
     * Returns a holder for a user ad
     *
     * @param parent   The parent view
     * @param viewType the int of type of view
     * @return a board game holder
     */
    @NonNull
    @Override
    public MyAdsAdapter.AdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing_in_user_ads,
                parent, false);
        return new AdHolder(v);
    }

    /*
    This Class defines a recyclerview object that is a ad holder for view ad
    */
    static class AdHolder extends RecyclerView.ViewHolder {
        ItemListingInUserAdsBinding binding;

        /*
         * constructor for an ad holder
         * @param itemView the view of the item being used here
         */
        public AdHolder(View itemView) {
            super(itemView);
            binding = ItemListingInUserAdsBinding.bind(itemView);
        }
    }
}
