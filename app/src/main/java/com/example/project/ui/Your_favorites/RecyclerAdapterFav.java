package com.example.project.ui.Your_favorites;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.DataBaseHelper;
import com.example.project.R;
import com.example.project.Type;
import com.example.project.ui.Pizza_menu.OrderDialog;
import com.example.project.ui.Pizza_menu.pizzaDialog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecyclerAdapterFav extends RecyclerView.Adapter<RecyclerAdapterFav.MyViewHolder> {
    private ArrayList<Type> typeList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private String Email;
    private DataBaseHelper dbHelper;

    public RecyclerAdapterFav(FragmentManager fragmentManager, String Email, DataBaseHelper dbHelper) {
        this.fragmentManager = fragmentManager;
        this.Email = Email;
        this.dbHelper = dbHelper;
        fetchFav();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView pizzaName;
        private Button orderButton;
        private ImageButton favButton;

        public MyViewHolder(final View view) {
            super(view);
            imageView = view.findViewById(R.id.imgpizza);
            pizzaName = view.findViewById(R.id.pizzaType);
            orderButton = view.findViewById(R.id.button_order);
            favButton = view.findViewById(R.id.imgFav);

            pizzaName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Type selectedType = typeList.get(position);
                        showPizzaDialog(selectedType);
                    }
                }
            });

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Type selectedType = typeList.get(position);
                        showOrderDialog(selectedType);
                    }
                }
            });

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Type selectedType = typeList.get(position);
                        toggleFavorite(selectedType, favButton);
                    }
                }
            });
        }

        private void showPizzaDialog(Type type) {
            pizzaDialog dialog = pizzaDialog.newInstance(type.getTypeName(), type.getSize(), type.getPrice(), type.getIngredients());
            dialog.show(fragmentManager, "PizzaDialog");
        }

        private void showOrderDialog(Type type) {
            String[] prices = type.getPrice().split("-");
            OrderDialog oDialog = OrderDialog.newInstance(null,type.getTypeName(), prices[0], prices[1], prices[2],dbHelper,Email);
            oDialog.show(fragmentManager, "OrderDialog");
        }

        private void toggleFavorite(Type selectedType, ImageButton favButton) {
                int isFavorite = isFavorite(selectedType);
                dbHelper.deleteFavorite(isFavorite);
                favButton.setImageResource(R.drawable.baseline_favorite_border_24);
                removeFavoriteFromList(selectedType);

        }

        private int isFavorite(Type selectedType) {
            Cursor allFavCursor = dbHelper.getFavorite();
            while (allFavCursor.moveToNext()) {
                if (allFavCursor.getString(2).equals(Email) && allFavCursor.getString(1).equals(selectedType.getTypeName())) {
                    return allFavCursor.getInt(0);
                }
            }
            allFavCursor.close();
            return -1;
        }
    }

    private void removeFavoriteFromList(Type selectedType) {
        int position = typeList.indexOf(selectedType);
        if (position != -1) {
            typeList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, typeList.size());
        }
    }

    private void fetchFav() {
        typeList = new ArrayList<>();
        Cursor cursor = null;
        Cursor cursorFav = null;

        try {
            cursorFav = dbHelper.getFavorite();
            Set<String> favoriteItems = new HashSet<>();

            while (cursorFav.moveToNext()) {
                if (cursorFav.getString(2).equals(Email)) {
                    favoriteItems.add(cursorFav.getString(1));
                }
            }

            cursor = dbHelper.getMenu();

            while (cursor.moveToNext()) {
                if (favoriteItems.contains(cursor.getString(0))) {
                    Type type = new Type();
                    type.setTypeName(cursor.getString(0));
                    type.setSize(cursor.getString(1));
                    type.setPrice(cursor.getString(2));
                    type.setIngredients(cursor.getString(3));
                    type.setImage(cursor.getInt(4));
                    typeList.add(type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursorFav != null) {
                cursorFav.close();
            }
        }
    }

    @NonNull
    @Override
    public RecyclerAdapterFav.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterFav.MyViewHolder holder, int position) {
        Type type = typeList.get(position);
        holder.pizzaName.setText(type.getTypeName());
        holder.imageView.setImageResource(type.getImage());

        int isFavorite = holder.isFavorite(type);
        holder.favButton.setImageResource(isFavorite != -1 ? R.drawable.fillfav : R.drawable.baseline_favorite_border_24);
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}
