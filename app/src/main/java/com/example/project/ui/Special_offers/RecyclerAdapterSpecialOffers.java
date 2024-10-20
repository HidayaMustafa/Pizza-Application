package com.example.project.ui.Special_offers;

import static java.lang.String.valueOf;
import static java.security.AccessController.getContext;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.DataBaseHelper;
import com.example.project.Order;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class RecyclerAdapterSpecialOffers extends RecyclerView.Adapter<RecyclerAdapterSpecialOffers.MyViewHolder> {

    private FragmentManager fragmentManager;
    private DataBaseHelper dbHelper;
    private ArrayList<Offer> offersList =new ArrayList<>();
    String sizeQuantities ;
    String email;

    public RecyclerAdapterSpecialOffers(FragmentManager fragmentManager,DataBaseHelper dbHelper,String email) {
        this.fragmentManager = fragmentManager;
        this.dbHelper = dbHelper;
        this.email = email;
        fetchOffers();
    }

    private void fetchOffers() {
        Cursor cursor = null;
        try {
            cursor = dbHelper.getOffers();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Offer offer = new Offer();
                    offer.setName(cursor.getString(1));
                    offer.setTotalPrice(cursor.getInt(2));
                    offer.setEndDate(cursor.getString(4));
                    offer.setStartDate(cursor.getString(3));
                    offer.setPizzaDetails(cursor.getString(5));
                    offer.setImg(cursor.getBlob(6));
                    offersList.add(offer);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView pizzaOffer;
        private ImageView imageView;
        private Button Order;

        public MyViewHolder(final View view) {super(view);
            pizzaOffer = view.findViewById(R.id.pizza_offer);
            imageView = view.findViewById(R.id.imgpizza);
            Order = view.findViewById(R.id.button_order);

            pizzaOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Offer selectedOffer = offersList.get(position);
                        showOfferInfoDialog(selectedOffer);
                    }
                }
            });
            Order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Offer selectedOffer = offersList.get(position);
                        LocalTime currentTime = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            currentTime = LocalTime.now();
                        }
                        long timeInMillis = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            timeInMillis = (currentTime.getHour() * 3600 + currentTime.getMinute() * 60) * 1000;
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = sdf.format(new Date());
                        sizeQuantities=selectedOffer.getPizzaDetails();
                        Order newOrder = new Order(email, selectedOffer.getName(), timeInMillis ,sizeQuantities,selectedOffer.getTotalPrice(),currentDate,selectedOffer.getImg(),1);
                        dbHelper.insertOrder(newOrder);
                    }
                }
            });
        }
        private void showOfferInfoDialog(Offer offer) {
            OfferDetails dialog = OfferDetails.newInstance(offer.getName(), offer.getTotalPrice(), offer.getStartDate(), offer.getEndDate(), offer.getPizzaDetails());
            dialog.show(fragmentManager, "OfferDetails");
        }
    }

    @NonNull
    @Override
    public RecyclerAdapterSpecialOffers.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offfers, parent, false);
        return new RecyclerAdapterSpecialOffers.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSpecialOffers.MyViewHolder holder, int position) {
        Offer offer = offersList.get(position);
        holder.pizzaOffer.setText(offer.getName());
        if (offer.getImg()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(offer.getImg(), 0, offer.getImg().length);
            holder.imageView.setImageBitmap(bitmap);
        }else{
            holder.imageView.setImageResource(R.drawable.main);
        }
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }
}