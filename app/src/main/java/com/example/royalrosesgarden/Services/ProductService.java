package com.example.royalrosesgarden.Services;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProductService {
    public ArrayList<Product> cursorToArray(Cursor cursor ){
        ArrayList<Product> list = new ArrayList<>();
        if(cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Product product = new Product(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6)
                );
                list.add(product);
            }
        }
        return list;
    }

    /*
    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public Bitmap byteToBitmap(byte[] image){
        Bitmap bitmap  = BitmapFactory.decodeByteArray(image, 0, image.length );
        return bitmap;
    }
*/
}
