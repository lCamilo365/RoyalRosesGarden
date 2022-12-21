package com.example.royalrosesgarden.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.royalrosesgarden.DB.DBFirebase;
import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Maps;
import com.example.royalrosesgarden.Product_Info;
import com.example.royalrosesgarden.Product_form;
import com.example.royalrosesgarden.Products;
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

  /*
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
        Button btnEditTemplate = (Button)  view.findViewById(R.id.btnEditTemplate);
        Button btnDeleteTemplate = (Button)  view.findViewById(R.id.btnDeleteTemplate);


        //imgProductTemplate.setImageResource(product.getImage());
        //byte[] image = product.getImage();
        //Bitmap bitmap  = BitmapFactory.decodeByteArray(image, 0, image.length );
        //imgProductTemplate.setImageBitmap(bitmap);

        textNameTemplate.setText(product.getName());
        textDescriptionTemplate.setText(product.getDescription());
        textPriceTemplate.setText("USD $" + String.valueOf(product.getPrice()));

        btnEditTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), Product_form.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", product.getId());
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getImage());
                intent.putExtra("latitude", product.getLatitude());
                intent.putExtra("longitude", product.getLongitude());

                context.startActivity(intent);
            }
        });
*/
  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
      LayoutInflater layoutInflater = LayoutInflater.from(this.context);
      view = layoutInflater.inflate(R.layout.product_template, null);

      ImageView imgProductTemplate = (ImageView) view.findViewById(R.id.imgProductTemplate);
      TextView textNameTemplate = (TextView) view.findViewById(R.id.textNameTemplate);
      TextView textDescriptionTemplate = (TextView) view.findViewById(R.id.textDescriptionTemplate);
      TextView textPriceTemplate = (TextView) view.findViewById(R.id.textPriceTemplate);
      Button btnEditTemplate = (Button) view.findViewById(R.id.btnEditTemplate);
      Button btnDeleteTemplate = (Button) view.findViewById(R.id.btnDeleteTemplate);

      Product product = arrayProducts.get(i);

      textNameTemplate.setText(product.getName());
      textDescriptionTemplate.setText(product.getDescription());
      textPriceTemplate.setText("USD $" + String.valueOf(product.getPrice()));
      /*
      imgProduct.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(context.getApplicationContext(), MainActivity3.class);
              intent.putExtra("id", String.valueOf(product.getId()));
              intent.putExtra("name", String.valueOf(product.getName()));
              intent.putExtra("description", String.valueOf(product.getDescription()));
              intent.putExtra("price", String.valueOf(product.getPrice()));
              intent.putExtra("image", String.valueOf(product.getImage()));
              context.startActivity(intent);
          }
      }
      );*/

      btnEditTemplate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(context.getApplicationContext(), Product_form.class);
              intent.putExtra("edit", true);
              intent.putExtra("id", product.getId());
              intent.putExtra("name", product.getName());
              intent.putExtra("description", product.getDescription());
              intent.putExtra("price", product.getPrice());
              intent.putExtra("image", product.getImage());
              intent.putExtra("latitude", product.getLatitude());
              intent.putExtra("longitude", product.getLongitude());

              context.startActivity(intent);
          }
      });

      btnDeleteTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Delete the Product?")
                        .setTitle("Confirmation")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBFirebase dbFirebase = new DBFirebase(context);
                                dbFirebase.deleteData(product.getId());
                                Intent intent = new Intent(context.getApplicationContext(), Products.class);
                                context.startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

/*
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
*/
        return view;

    }
}
