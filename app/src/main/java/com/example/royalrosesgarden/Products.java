package com.example.royalrosesgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.royalrosesgarden.Adapters.ProductAdapter;

import java.util.ArrayList;
import com.example.royalrosesgarden.Entities.Product;

public class Products extends AppCompatActivity {

    private ListView listViewProduct;
    private ArrayList<Product> arrayProducts;
    private ProductAdapter productAdapter;
    private Button btnBackProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        btnBackProducts = (Button) findViewById(R.id.btnBackProducts);
        arrayProducts = new ArrayList<>();


        Product product1 = new Product(R.drawable.boxes_bluebox,  "Blue",  "Blue Box",  49.8);
        Product product2 = new Product(R.drawable.boxes_junglenightbox,  "Night",  "Jungle Night Box",  64.5);
        Product product3 = new Product(R.drawable.boxes_pinkbox,  "Pink",  "Pink Box",  51.75);
        Product product4 = new Product(R.drawable.boxes_raspberrybox,  "Raspberry",  "Raspberry Box",  51);

        arrayProducts.add(product1);
        arrayProducts.add(product2);
        arrayProducts.add(product3);
        arrayProducts.add(product4);


        productAdapter = new ProductAdapter(this, arrayProducts);

        listViewProduct = (ListView) findViewById(R.id.listViewProducts);
        listViewProduct.setAdapter(productAdapter);

        btnBackProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });

/*
        btnProdsJungleNightBox = (Button) findViewById(R.id.btnProdsJungleNightBox);
        btnProdsRaspberryBox = (Button) findViewById(R.id.btnProdsRaspberryBox);
        btnProdsPinkBox = (Button) findViewById(R.id.btnProdsPinkBox);
        btnProdsBlueBox = (Button) findViewById(R.id.btnProdsBlueBox);

        textProdsJungleNightBox = (TextView) findViewById(R.id.textProdsJungleNightBox);
        textProdsRaspberryBox = (TextView) findViewById(R.id.textProdsRaspberryBox);
        textProdsPinkBox = (TextView) findViewById(R.id.textProdsPinkBox);
        textProdsBlueBox = (TextView) findViewById(R.id.textProdsBlueBox);


        btnProdsJungleNightBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Product.class);
                intent.putExtra("title", textProdsJungleNightBox.getText().toString());
                intent.putExtra("price", "USD $64.8");
                intent.putExtra("imageCode", R.drawable.boxes_junglenightbox);

                startActivity(intent);
            }
        });

        btnProdsRaspberryBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Product.class);
                intent.putExtra("title", textProdsRaspberryBox.getText().toString());
                intent.putExtra("price", "USD $50.0");
                intent.putExtra("imageCode", R.drawable.boxes_raspberrybox);

                startActivity(intent);
            }
        });

        btnProdsPinkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Product.class);
                intent.putExtra("title", textProdsPinkBox.getText().toString());
                intent.putExtra("price", "USD $51.25");
                intent.putExtra("imageCode", R.drawable.boxes_pinkbox);

                startActivity(intent);
            }
        });

        btnProdsBlueBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Product.class);
                intent.putExtra("title", textProdsBlueBox.getText().toString());
                intent.putExtra("price", "USD $49.80");
                intent.putExtra("imageCode", R.drawable.boxes_bluebox);

                startActivity(intent);
            }
        });
*/

    }
}