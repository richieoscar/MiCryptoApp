package com.example.micrypto.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.micrypto.R;
import com.example.micrypto.model.CoinEntity;
import com.example.micrypto.model.FavoriteList;
import com.example.micrypto.ui.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteListViewHolder> {
    private static final String TAG = "CoinAdapter";
    List<FavoriteList> coins = new ArrayList<>();

    Context context;
    final String BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/";

    public FavoriteAdapter(List<FavoriteList> coins, Context ctx) {
        this.coins = coins;
        context = ctx;
    }


    @NonNull
    @Override
    public FavoriteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_coin, parent, false);

        return new FavoriteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListViewHolder holder, int position) {

        FavoriteList currentCoins = coins.get(position);

       int coinId = currentCoins.getId();
       String imgType = ".png";
       String imgSize = "128x128/";
       String imgUrl = BASE_URL +imgSize +coinId +imgType;

        double value = currentCoins.getQuote().getUSD().getPrice();
        double value2 = currentCoins.getQuote().getUSD().getPercentChangePerDay();
        double value3 = currentCoins.getQuote().getUSD().getPercentChangeInSevenDays();

        String price = String.format("%,.2f", value);
        String price24 = String.format("%.2f", value2);
        String price7 = String.format("%.2f", value3);

        holder.name.setText(currentCoins.getName());
        holder.price.setText("$" + price);
        holder.priceIn24Hours.setText(price24 + "%" + " 1d");
        holder.priceIn7days.setText(price7 + "%" + " 7d");
        holder.coinSymbol.setText(currentCoins.getSymbol());
        Log.d(TAG, "onBindViewHolder: bind image");
        Glide.with(context).load(imgUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public void setCoins(List<FavoriteList> cryptoCoin) {
        coins.clear();
        coins.addAll(cryptoCoin);
        notifyDataSetChanged();
    }

    public class FavoriteListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView price;
        TextView priceIn24Hours;
        TextView priceIn7days;
        TextView coinSymbol;
        ImageView imageView;

        public FavoriteListViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView_name);
            price = itemView.findViewById(R.id.textView_price);
            priceIn24Hours = itemView.findViewById(R.id.textView_24hour);
            priceIn7days = itemView.findViewById(R.id.textView_7days);
            imageView = itemView.findViewById(R.id.imageView_icon);
            coinSymbol = itemView.findViewById(R.id.textView_symbol);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            FavoriteList selectedCoin = coins.get(position);
//            int id = coins.get(position).getId();
//
//            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
//            intent.putExtra("favCoin", selectedCoin);
//            intent.putExtra("coinId", id);
//            v.getContext().startActivity(intent);


        }
    }
}
