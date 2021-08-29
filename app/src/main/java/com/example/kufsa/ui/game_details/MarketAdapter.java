/*
  @authors Aaron David Tawil & Eldar Weiss
*/

package com.example.kufsa.ui.game_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kufsa.R;
import com.example.kufsa.data.MarketAd;
import com.example.kufsa.databinding.ItemListingInMarketBinding;
import com.example.kufsa.utils.Tools;
import com.example.kufsa.utils.ViewAnimation;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * This class is an adapter for the market itself.
 */
public class MarketAdapter extends FirestoreRecyclerAdapter<MarketAd, MarketAdapter.AdHolder> {

    public MarketAdapter(@NonNull FirestoreRecyclerOptions<MarketAd> options) {
        super(options);
    }

    /**
     * This class defines the toggle arrow foe the view.
     *
     * @param show This is the boolean where the arrow shown
     * @param view the view being used
     * @return true if arrow is shown, false otherwise
     */
    public static boolean toggleArrow(boolean show, View view) {
        return toggleArrow(show, view, true);
    }

    /**
     * This class defines the toggle arrow foe the view.
     *
     * @param show This is the boolean where the arrow shown
     * @param view the view being used
     * @return true if arrow is shown, false otherwise
     */
    public static boolean toggleArrow(boolean show, View view, boolean delay) {
        if (show) {
            view.animate().setDuration(delay ? 200 : 0).rotation(180);
            return true;
        } else {
            view.animate().setDuration(delay ? 200 : 0).rotation(0);
            return false;
        }
    }

    /**
     * @param holder   holder of the recycler view
     * @param position view's position
     * @param model    board game object
     */
    @Override
    protected void onBindViewHolder(@NonNull MarketAdapter.AdHolder holder, int position, @NonNull MarketAd model) {

        ItemListingInMarketBinding binding = holder.binding;
        binding.userNameText.setText(model.getUserName());
        binding.publishDateText.setText(new SimpleDateFormat("dd/MM/yy", holder.itemView.getResources().getConfiguration().locale).format(model.getPublishDate()));
        binding.cityText.setText(model.getCity());
        binding.conditionText.setText(model.getCondition().toString());
        binding.tradeTypeText.setText(model.getTradeType().toString());
        if (model.getNotes().isEmpty())
            binding.notesLabel.setVisibility(View.GONE);
        else
            binding.notesText.setText(model.getNotes());

        if (model.isPhoneContact()) {
            binding.phoneIcon.setVisibility(View.VISIBLE);
            binding.phoneIcon.setOnClickListener(view -> Tools.callFromDailer(holder.itemView.getContext(), model.getPhone()));
        }
        if (model.isEmailContact()) {
            binding.emailIcon.setVisibility(View.VISIBLE);
            binding.emailIcon.setOnClickListener(view -> Tools.composeEmail(holder.itemView.getContext(), new String[]{model.getEmail()}, "Market add in Kufsa app"));
        }
        if (model.isWhatsappContact()) {
            binding.whatsappIcon.setVisibility(View.VISIBLE);
            binding.whatsappIcon.setOnClickListener(view -> Tools.sendWhatsAppMessage(holder.itemView.getContext(), model.getPhone(), "Hi, regarding your ad in Kufsa app"));
        }
        if (model.isCash())
            binding.cashIcon.setVisibility(View.VISIBLE);
        if (model.isCreditCard())
            binding.creditCardIcon.setVisibility(View.VISIBLE);
        if (model.isBitcoin())
            binding.bitcoinIcon.setVisibility(View.VISIBLE);
        if (model.getTradeType() == MarketAd.TradeType.SELL) {
            String textPrice = "$" + new DecimalFormat("#").format(model.getSellPrice());
            binding.priceText.setText(textPrice);
        }
        if (model.getTradeType() == MarketAd.TradeType.RENT) {
            String textPrice = "$" + new DecimalFormat("#").format(model.getRentalFee()) + " " + model.getRentalPeriod();
            binding.priceText.setText(textPrice);
        }
        if (model.getTradeType() == MarketAd.TradeType.EXCHANGE) {
            binding.priceText.setVisibility(View.GONE);
        }
        Glide.with(holder.itemView)
                .load(model.getUserPhotoUrl())
                .error(R.drawable.outline_account_circle_24)
                .into(holder.binding.profileImgView);


        binding.btExpand.setOnClickListener(v -> holder.expanded = toggleLayoutExpand(!holder.expanded, v, binding.layoutExpand));

        // void recycling view
        if (holder.expanded) {
            binding.layoutExpand.setVisibility(View.VISIBLE);
        } else {
            binding.layoutExpand.setVisibility(View.GONE);
        }
        toggleArrow(holder.expanded, binding.btExpand, false);
    }

    /**
     * @param parent   parent view group for the review holder
     * @param viewType which type of view it is
     * @return the holder object
     */
    @NonNull
    @Override
    public MarketAdapter.AdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing_in_market,
                parent, false);
        return new AdHolder(v);
    }

    /**
     * This method expands the layout when clicked.
     *
     * @param show       parameter whther to expand layout and use the animation
     * @param view       the view being used
     * @param lyt_expand the view that we will expand to
     * @return the view expanded
     */
    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }

    /**
     * This Class defines a recyclerview object that is a ad holder for ads.
     */
    static class AdHolder extends RecyclerView.ViewHolder {
        ItemListingInMarketBinding binding;
        boolean expanded = false;

        /**
         * This method is a constructor for an adholder.
         *
         * @param itemView the view of the item being used here
         */
        public AdHolder(View itemView) {
            super(itemView);
            binding = ItemListingInMarketBinding.bind(itemView);
        }
    }

}