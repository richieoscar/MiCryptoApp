package com.example.micrypto.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.micrypto.R;
import com.example.micrypto.model.CoinEntity;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    final String BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/";
    private TextView name;
    private TextView price;
    private ImageView image;
    private CoinEntity coin;
    private TextView symbol;
    private TextView percent_Hour;
    private TextView percent_perDay;
    private TextView percent_week;
    private TextView nairaValue;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bindViews();
        displayDetails();
        covertToNaira();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_price_alert:
                priceAlert();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindViews(){
        name = findViewById(R.id.name_text);
        price = findViewById(R.id.price_text);
        image = findViewById(R.id.imageView);
        symbol = findViewById(R.id.textView_detail_symbol);
        percent_Hour = findViewById(R.id.textView_detail_hour);
        percent_perDay = findViewById(R.id.textView_detail_perday);
        percent_week = findViewById(R.id.textView_detail_week);
        nairaValue = findViewById(R.id.textView_naira_value);
    }

    private void displayDetails(){
        coin = getIntent().getParcelableExtra("CoinEntity");

        actionBar = getSupportActionBar();
        actionBar.setTitle(coin.getName());
        actionBar.isHideOnContentScrollEnabled();




        name.setText(coin.getName());
       double priceN = coin.getQuote().getUSD().getPrice();
       double percent24 = coin.getQuote().getUSD().getPercentChangePerDay();
       double percentHour = coin.getQuote().getUSD().getPercentChangePerHour();
       double percentWeek = coin.getQuote().getUSD().getPercentChangeInSevenDays();

      String priceFormat = String.format("%,.2f", priceN);
      String p24Format = String.format("%,.2f", percent24);
      String pHourFormat = String.format("%,.2f",percentHour );
      String pWeekFormat = String.format("%,.2f", percentWeek);

        price.setText("$"+priceFormat);
        symbol.setText(coin.getSymbol());
        percent_Hour.setText(pHourFormat);
        percent_perDay.setText(p24Format);
        percent_week.setText(pWeekFormat);


        int coinId = coin.getID();
        String imgType = ".png";
        String imgSize = "128x128/";
        String imgUrl = BASE_URL +imgSize +coinId +imgType;

        Glide.with(this).load(imgUrl).into(image);
    }

    public void covertToNaira(){
        int Id = coin.getID();
       switch (Id){
           case  1:
             double val =  convert(coin.getQuote().getUSD().getPrice());
             String valF = String.format("%,.2f", val);
             nairaValue.setText(getString(R.string.one)+coin.getSymbol() +getString(R.string.divider)+getString(R.string.naira)+valF );
             break;

           case 1027:
               double val2 =  convert(coin.getQuote().getUSD().getPrice());
               String valEthF = String.format("%,.2f", val2);
               nairaValue.setText(getString(R.string.one) +coin.getSymbol() +getString(R.string.divider)+getString(R.string.naira) +valEthF);
               break;

           case 825:
               double valTether = convert(coin.getQuote().getUSD().getPrice());
               String valTetF = String.format("%,.2f", valTether);
               nairaValue.setText(getString(R.string.one) +coin.getSymbol() +getString(R.string.divider)+getString(R.string.naira) +valTetF);
               break;
           case 52:
               double valXRP = convert(coin.getQuote().getUSD().getPrice());
               String valXRPF = String.format("%,.2f", valXRP);
               nairaValue.setText(getString(R.string.one)+coin.getSymbol() +getString(R.string.divider) +getString(R.string.naira)+valXRPF );
               break;
           case 1831:
               double valBCH = convert(coin.getQuote().getUSD().getPrice());
               String valBCHF = String.format("%,.2f", valBCH);
               nairaValue.setText(getString(R.string.one) +coin.getSymbol() +getString(R.string.naira) +valBCHF);
               break;
           case 1975:
               double valCHLINK = convert(coin.getQuote().getUSD().getPrice());
               String valCHLINKF = String.format("%,.2f", valCHLINK);
               nairaValue.setText("1"+coin.getSymbol()+" "+getString(R.string.naira) +valCHLINKF);
               break;
           case 1839:
               double bNB = convert(coin.getQuote().getUSD().getPrice());
               String bNBF = String.format("%,.2f", bNB);
               nairaValue.setText("1" +coin.getSymbol()+" | " +getString(R.string.naira) +bNBF);
               break;
           case 2:
               double lTE = convert(coin.getQuote().getUSD().getPrice());
               String lTEF = String.format("%,.2f", lTE);
               nairaValue.setText(getString(R.string.one) +coin.getSymbol()+getString(R.string.divider) +getString(R.string.naira) +lTEF);
               break;
           case 6636:
               double dOT = convert(coin.getQuote().getUSD().getPrice());
               nairaValue.setText(getString(R.string.one) +coin.getSymbol() +getString(R.string.divider) +getString(R.string.naira)+String.format("%,.2f", dOT));
               break;

           case 2010:
               double aDA = convert(coin.getQuote().getUSD().getPrice());
               nairaValue.setText(getString(R.string.one) +coin.getSymbol()+getString(R.string.divider) +getString(R.string.naira)+String.format("%,.2f", aDA));
               break;
           default:
               nairaValue.setText("");
       }

    }

    public double convert(double val){
        return val * 460;
    }

    public  void convertToNairaValue(){
        ArrayList<Integer> Id = new ArrayList<>();

        while(coin.getID()> 0){
            Id.add(coin.getID());
        }

        Toast.makeText(this, "array size" +Id.size(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "item 1 is" + Id.get(0), Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "item 2 is" + Id.get(1), Toast.LENGTH_SHORT).show();

    }

    private void priceAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.price_alert,null);
        builder.setView(R.layout.price_alert);

        Button set = view.findViewById(R.id.button_set);
        set.setOnClickListener(v->{
            EditText price = view.findViewById(R.id.editText_price);
            Toast.makeText(this, "Notification set", Toast.LENGTH_SHORT).show();
            finish();
        });
        builder.show();

    }
}