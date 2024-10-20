package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String,String> {
    Activity activity;

    List<String> price = new ArrayList<>();
    List<String> ingredients = new ArrayList<>();

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null ) {
            List<String> types = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("types");
                for (int i = 0; i < jsonArray.length(); i++) {
                    types.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                types= null;
            }
            if (!types.isEmpty()) {
                DataBaseHelper dbHelper = new DataBaseHelper(activity, "PIZZA_CUSTOMER", null, 1);
                init();

                Type type = new Type();
                for (String t : types) {
                    type.setTypeName(t);
                    type.setSize("Small-Medium-Large");
                    type.setPrice(price.get(0));
                    type.setIngredients(ingredients.get(0));
                    price.remove(0);
                    ingredients.remove(0);
                    switch (t) {
                        case "Margarita":
                            type.setImage(R.drawable.mar);
                            break;
                        case "Neapolitan":
                            type.setImage(R.drawable.nap);
                            break;
                        case "Hawaiian":
                            type.setImage(R.drawable.hw);
                            break;
                        case "Pepperoni":
                            type.setImage(R.drawable.pp);
                            break;
                        case "New York Style":
                            type.setImage(R.drawable.ne);
                            break;
                        case "Calzone":
                            type.setImage(R.drawable.c);
                            break;
                        case "Tandoori Chicken Pizza":
                            type.setImage(R.drawable.tan);
                            break;
                        case "BBQ Chicken Pizza":
                            type.setImage(R.drawable.bb);
                            break;
                        case "Seafood Pizza":
                            type.setImage(R.drawable.sea);
                            break;
                        case "Vegetarian Pizza":
                            type.setImage(R.drawable.v);
                            break;
                        case "Buffalo Chicken Pizza":
                            type.setImage(R.drawable.bf);
                            break;
                        case "Mushroom Truffle Pizza":
                            type.setImage(R.drawable.mash);
                            break;
                        case "Pesto Chicken Pizza":
                            type.setImage(R.drawable.pesto);
                            break;
                    }

                    try {
                        dbHelper.insertType(type);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                Intent intent = new Intent(activity, Registration.class);
                activity.startActivity(intent);
            } else {
                Toast.makeText(activity, "Failed to parse JSON.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Failed to get data from server.", Toast.LENGTH_SHORT).show();
        }
    }

    public void init() {

        // Margarita
        price.add("20,35,50");
        ingredients.add("Bubbly crust, Crushed San Marzano tomato sauce, Mozzarella, Basil");

        // Neapolitan
        price.add("10,30,50");
        ingredients.add("Tomato, Fresh mozzarella, Basil, Olive oil, Salt");

        // Hawaiian
        price.add("20,35,50");
        ingredients.add("Tomato sauce, Mozzarella, Ham, Pineapple");

        // Pepperoni
        price.add("20,35,50");
        ingredients.add("Tomato sauce, Mozzarella, Pepperoni");

        // New York Style
        price.add("20,35,50");
        ingredients.add("Tomato sauce, Mozzarella, Grated Parmesan, Olive oil, Dried oregano, Dried basil");

        // Calzone
        price.add("20,35,50");
        ingredients.add("Ricotta, Mozzarella, Parmesan, Ham, Salami, Tomato sauce (for dipping)");

        // Tandoori Chicken
        price.add("20,35,50");
        ingredients.add("Tandoori Chicken, Mozzarella, Red onion, Bell pepper, Yogurt sauce");

        // BBQ Chicken
        price.add("20,35,50");
        ingredients.add("BBQ sauce, Mozzarella, Grilled Chicken, Red onion, Cilantro");

        // Seafood
        price.add("20,35,50");
        ingredients.add("Tomato sauce, Mozzarella, Shrimp, Clams, Scallops, Garlic, Parsley");

        // Vegetarian
        price.add("20,35,50");
        ingredients.add("Tomato sauce, Mozzarella, Bell pepper, Onion, Mushrooms, Olives, Spinach");

        // Buffalo Chicken
        price.add("20,35,50");
        ingredients.add("Buffalo sauce, Mozzarella, Grilled Chicken, Red onion, Blue cheese");

        // Mushroom
        price.add("20,35,50");
        ingredients.add("Tomato sauce, Mozzarella, Mixed Mushrooms, Garlic, Thyme");

        // Pesto Chicken
        price.add("20,35,50");
        ingredients.add("Pesto sauce, Mozzarella, Grilled Chicken, Cherry tomatoes, Parmesan");
        for (int i = 0; i < price.size(); i++) {
            price.set(i, price.get(i).replace(",", "-"));
            // Replace commas with hyphens in ingredients list
            ingredients.set(i, ingredients.get(i).replace(",", "."));
        }
    }

}


