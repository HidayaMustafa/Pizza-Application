package com.example.project.ui.Special_offers;

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

public class OfferDetails extends DialogFragment {
    public static OfferDetails newInstance(String Name, int price, String sdate, String edate, String details) {
        OfferDetails dialog = new OfferDetails();
        Bundle args = new Bundle();
        args.putString("name", Name);
        args.putInt("totalPrice", price);
        args.putString("sdate", sdate);
        args.putString("edate", edate);
        args.putString("details", details);
        dialog.setArguments(args);
        return dialog;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offer_info, container, false);

        TextView name = view.findViewById(R.id.pizza_name);
        TextView totalPrice = view.findViewById(R.id.price);
        TextView sdate = view.findViewById(R.id.date);
        TextView edate = view.findViewById(R.id.time);
        TextView detail = view.findViewById(R.id.size_quantity);

        Bundle args = getArguments();
        if (args != null) {
            String Name = args.getString("name");
            int Price = args.getInt("totalPrice");
            String SDate = args.getString("sdate");
            String EDate = args.getString("edate");
            String details = args.getString("details");

            name.setText(Name);
            totalPrice.setText(valueOf(Price));
            sdate.setText(SDate);
            edate.setText(EDate);
            detail.setText(details);
        }
        return view;
    }
}
