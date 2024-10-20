package com.example.project.ui.Your_orders;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.DataBaseHelper;
import com.example.project.Order;
import com.example.project.R;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterOrder extends RecyclerView.Adapter<RecyclerAdapterOrder.MyViewHolder> {
    private ArrayList<Order> orderList;
    private FragmentManager fragmentManager;
    private String Email;
    private DataBaseHelper dbHelper;

    public RecyclerAdapterOrder(FragmentManager fragmentManager, String Email, DataBaseHelper dbHelper, ArrayList<Order> orderList ) {
        this.fragmentManager = fragmentManager;
        this.Email = Email;
        this.dbHelper = dbHelper;
        this.orderList = orderList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView pizzaOrder;
        private ImageView imageView;

        public MyViewHolder(final View view) {
            super(view);
            pizzaOrder = view.findViewById(R.id.pizza_order);
            imageView = view.findViewById(R.id.imgpizza);

            pizzaOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Order selectedOrder = orderList.get(position);
                        showOrderInfoDialog(selectedOrder);
                    }
                }
            });
        }
        private void showOrderInfoDialog(Order order) {
            OrderDetails dialog = OrderDetails.newInstance(order.getIsOffer(),order.getPizzaName(), order.getTotalPrice(), order.getData(), order.getOrderTime(),order.getSizeQuantity());
            dialog.show(fragmentManager, "OrderDetails");
        }
    }

    @NonNull
    @Override
    public RecyclerAdapterOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOrder.MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.pizzaOrder.setText(String.format("%d - %s", position+1, order.getPizzaName()));

        if(order.getIsOffer()==0){
        Cursor cursor = dbHelper.getMenu();
        while (cursor.moveToNext()) {
            if (order.getPizzaName().equals(cursor.getString(0))) {
                holder.imageView.setImageResource(cursor.getInt(4));
            }
        }
            cursor.close();
        }else{
            if(order.getImage()!=null) {
                byte[] imageBytes = order.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
