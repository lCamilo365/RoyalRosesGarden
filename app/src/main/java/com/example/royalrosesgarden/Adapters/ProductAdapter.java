package com.example.royalrosesgarden.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Product_Info;
import com.example.royalrosesgarden.R;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> arrayProducts;

    public ProductAdapter(Context context, ArrayList<Product> arrayProducts) {
        this.context = context;
        this.arrayProducts = arrayProducts;
    }

    @Override
    public int getCount() {
        return this.arrayProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return this.arrayProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_template, null);
        Product product = arrayProducts.get(position);


        ImageView imgProductTemplate = (ImageView) view.findViewById(R.id.imgProductTemplate);
        TextView textNameTemplate = (TextView) view.findViewById(R.id.textNameTemplate);
        TextView textDescriptionTemplate = (TextView) view.findViewById(R.id.textDescriptionTemplate);
        TextView textPriceTemplate = (TextView) view.findViewById(R.id.textPriceTemplate);
        Button btnProductTemplate = (Button)  view.findViewById(R.id.btnProductTemplate);

        //imgProductTemplate.setImageResource(product.getImage());
        byte[] image = product.getImage();
        Bitmap bitmap  = BitmapFactory.decodeByteArray(image, 0, image.length );
        imgProductTemplate.setImageBitmap(bitmap);

        textNameTemplate.setText(product.getName());
        textDescriptionTemplate.setText(product.getDescription());
        textPriceTemplate.setText("USD $" + String.valueOf(product.getPrice()));



        btnProductTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), Product_Info.class);
                //Toast.makeText(context.getApplicationContext(), "Press " + product.getName(), Toast.LENGTH_SHORT).show();
                intent.putExtra("id", String.valueOf(product.getId()));
                //intent.putExtra("name", product.getName());
                //intent.putExtra("description", product.getDescription());
                //intent.putExtra("price", "USD $" + String.valueOf(product.getPrice()));
                //intent.putExtra("imageCode", product.getImage());
                context.startActivity(intent);

            }
        });

        return view;

    }
}
