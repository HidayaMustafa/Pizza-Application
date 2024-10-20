package com.example.project.ui.Your_orders;

import static java.lang.String.valueOf;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.project.R;


public class OrderDetails extends DialogFragment {
    public static OrderDetails newInstance(int isOffer,String pizzaName, int price, String date, long time, String sizeQuantity) {
        OrderDetails dialog = new OrderDetails();
        Bundle args = new Bundle();
        args.putString("pizza_name", pizzaName);
        args.putInt("totalPrice", price);
        args.putString("date", date);
        args.putLong("time", time);
        args.putString("sizeQuantity", sizeQuantity);
        args.putInt("isOffer", isOffer);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_info, container, false);

        TextView name = view.findViewById(R.id.pizza_name);
        TextView totalPrice = view.findViewById(R.id.price);
        TextView date = view.findViewById(R.id.date);
        TextView time = view.findViewById(R.id.time);
        TextView sizeQuantity = view.findViewById(R.id.size_quantity);

        Bundle args = getArguments();
        if (args != null) {
            String Name = args.getString("pizza_name");
            int Price = args.getInt("totalPrice");
            String Date = args.getString("date");
            long Time = args.getLong("time");
            String SizeQuantity = args.getString("sizeQuantity");
            int isOffer = args.getInt("isOffer");

            name.setText(Name);
            totalPrice.setText(valueOf(Price));
            date.setText(Date);
            time.setText(millisecondsToTime(Time));

            if(isOffer==0){
            String output =SizeQuantity.replaceAll("[{}]", "");
            String[] prices = output.split(",");

            for (int i = 0; i < prices.length; i++) {
                String[] price = prices[i].split("=");
                if (price.length == 2) {
                    String size = price[0].trim();
                    String quantity = price[1].trim();
                    sizeQuantity.append("- "+size+"    ->    "+ quantity+"\n");
                }
            }}else{
                sizeQuantity.setText(SizeQuantity);
            }
        }
        return view;
    }


    public static String millisecondsToTime(long milliseconds) {
        // Convert milliseconds to hours
        long hours = milliseconds / (1000 * 60 * 60);
        long remainingMilliseconds = milliseconds % (1000 * 60 * 60);
        long minutes = remainingMilliseconds / (1000 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

}