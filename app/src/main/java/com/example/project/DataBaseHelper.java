package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.ui.Special_offers.Offer;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataBaseHelper extends SQLiteOpenHelper implements Serializable {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS USER(EMAIL TEXT PRIMARY KEY, PHONE TEXT, FNAME TEXT, LNAME TEXT, GENDER TEXT, PASSWORD TEXT, PROFILE_PICTURE BLOB,ISCUSTOMER INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS MENUPIZZA(NAME TEXT PRIMARY KEY, SIZE TEXT, PRICE TEXT, INGREDIENTS TEXT, IMAGE INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS FAVORITEPIZZA(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, FOREIGN KEY(NAME) REFERENCES MENUPIZZA(NAME), FOREIGN KEY(EMAIL) REFERENCES CUSTOMER(EMAIL))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ORDERPIZZA(IDORDER INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT,TIME LONG,SIZEQUNTITY TEXT,TOTALPRICE INTEGER, DATE TEXT,PICTURE BLOB,ISOFFER INTEGER, FOREIGN KEY(NAME) REFERENCES MENUPIZZA(NAME), FOREIGN KEY(EMAIL) REFERENCES CUSTOMER(EMAIL))");
        sqLiteDatabase.execSQL("INSERT INTO USER (EMAIL, PHONE, FNAME, LNAME, GENDER, PASSWORD, PROFILE_PICTURE, ISCUSTOMER) " + "VALUES ('admin@gmail.com', '0599999999', 'Admin', 'Admin', 'Male', 'admin123', NULL, 0)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS SPECIALOFFER (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TOTALPRICE INTEGER, STARTDATE TEXT, ENDDATE TEXT, PIZZADETAILS TEXT, IMAGE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertUser(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfPassword())) {
            throw new Exception("Passwords do not match");
        }

        String hashedPassword = hashPassword(user.getPassword());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("PHONE", user.getmPhone());
        contentValues.put("FNAME", user.getmFName());
        contentValues.put("LNAME", user.getmLName());
        contentValues.put("GENDER", user.getmGender());
        contentValues.put("PASSWORD",user.getPassword());
        contentValues.put("PROFILE_PICTURE", user.getProfilePicture());
        contentValues.put("ISCUSTOMER", user.getIsCustomer());
        sqLiteDatabase.insert("USER", null, contentValues);
    }
    public void addSpecialOffer(Offer offer) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", offer.getName());
        contentValues.put("TOTALPRICE", offer.getTotalPrice());
        contentValues.put("STARTDATE", offer.getStartDate());
        contentValues.put("ENDDATE", offer.getEndDate());
        contentValues.put("PIZZADETAILS", offer.getPizzaDetails());
        contentValues.put("IMAGE", offer.getImg());
        sqLiteDatabase.insert("SPECIALOFFER", null, contentValues);
    }

    public Cursor getAllEmail() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER", null);
    }
    public Cursor getCusOrAdmin(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM USER WHERE USER.EMAIL = ?";
        return sqLiteDatabase.rawQuery(query, new String[]{email});
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        byte[] bytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public void editCustomer(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfPassword())) {
            throw new Exception("Passwords do not match");
        }
        String hashedPassword = hashPassword(user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PHONE", user.getmPhone());
        values.put("FNAME", user.getmFName());
        values.put("LNAME", user.getmLName());
        values.put("GENDER", user.getmGender());
        values.put("PASSWORD", user.getPassword());
        values.put("PROFILE_PICTURE", user.getProfilePicture());
        values.put("ISCUSTOMER", user.getIsCustomer());

        db.update("USER", values, "EMAIL = ?", new String[]{user.getEmail()});
    }

    public void insertType(Type type) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", type.getTypeName());
        contentValues.put("SIZE", type.getSize());
        contentValues.put("PRICE", type.getPrice());
        contentValues.put("INGREDIENTS", type.getIngredients());
        contentValues.put("IMAGE", type.getImage());
        sqLiteDatabase.insert("MENUPIZZA", null, contentValues);
    }

    public Cursor getMenu() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM MENUPIZZA", null);
    }
    public Cursor getPizzaByCategory(String category) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM MENUPIZZA WHERE UPPER(MENUPIZZA.INGREDIENTS) LIKE '%' || UPPER(?) || '%'";
        return sqLiteDatabase.rawQuery(query, new String[]{category});
    }

    public Cursor getPizza(String category){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM MENUPIZZA WHERE MENUPIZZA.NAME = ?";
        return sqLiteDatabase.rawQuery(query, new String[]{category});
    }

    public Cursor SearchByPrice(String price){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM MENUPIZZA WHERE MENUPIZZA.PRICE  LIKE '%" + price + "%'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public void insertFavorite(String Email, String PizzaName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", Email);
        contentValues.put("NAME", PizzaName);
        sqLiteDatabase.insert("FAVORITEPIZZA", null, contentValues);
    }

    public Cursor getFavorite() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM FAVORITEPIZZA", null);
    }

    public void deleteFavorite(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "DELETE FROM FAVORITEPIZZA WHERE ID = ?";
        sqLiteDatabase.execSQL(query, new Object[]{id});
    }
    public void insertOrder(Order order) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", order.getCustomerEmail());
        contentValues.put("NAME", order.getPizzaName());
        contentValues.put("SIZEQUNTITY", String.valueOf(order.getSizeQuantity()));
        contentValues.put("TOTALPRICE", order.getTotalPrice());
        contentValues.put("TIME", order.getOrderTime());
        contentValues.put("DATE", order.getData());
        contentValues.put("PICTURE", order.getImage());
        contentValues.put("ISOFFER", order.getIsOffer());
        sqLiteDatabase.insert("ORDERPIZZA", null, contentValues);
    }

    public Cursor getOrder() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM ORDERPIZZA", null);
    }
    public String getCustomerName(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT FNAME, LNAME FROM USER WHERE EMAIL = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});
        String name = null;
        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(0);
            String lastName = cursor.getString(1);
            name = firstName + " " + lastName;
        }
        cursor.close();
        sqLiteDatabase.close();
        return name;
    }
    public Cursor getOffers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM SPECIALOFFER", null);
    }
    public int getTotalIncome() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT SUM(TOTALPRICE) FROM ORDERPIZZA";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int totalIncome = 0;
        if (cursor.moveToFirst()) {
            totalIncome = cursor.getInt(0);
        }
        cursor.close();
        sqLiteDatabase.close();
        return totalIncome;
    }

    public int [] getTypeCountPrice(String pizzaName) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT COUNT(*) FROM ORDERPIZZA WHERE NAME = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{pizzaName});
        String query2 = "SELECT SUM(TOTALPRICE) FROM ORDERPIZZA WHERE NAME = ?";
        Cursor cursor2 = sqLiteDatabase.rawQuery(query2, new String[]{pizzaName});

        int[] countPrice = new int[2];
        int count = 0;
        int price = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if (cursor2.moveToFirst()) {
            price = cursor2.getInt(0);
        }
        countPrice[0] = count;
        countPrice[1] = price;

        cursor.close();
        cursor2.close();
        sqLiteDatabase.close();
        return countPrice;
    }
}
