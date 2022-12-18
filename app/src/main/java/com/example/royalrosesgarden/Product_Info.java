package com.example.royalrosesgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.royalrosesgarden.DB.DBHelper;
import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Services.ProductService;

import java.util.ArrayList;

public class Product_Info extends AppCompatActivity {
    private DBHelper dbHelper;
    private ProductService productService;
    private Button btnProductBack;
    private TextView textProductTitle, textProductDescription, textProductPrice;
    private ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info);

        btnProductBack = (Button) findViewById(R.id.btnProductBack);
        textProductTitle = (TextView) findViewById(R.id.textProductTitle);
        textProductDescription = (TextView) findViewById(R.id.textProductDescription);
        textProductPrice = (TextView) findViewById(R.id.textProductPrice);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);
        dbHelper = new DBHelper(this);
        productService = new ProductService();

        Intent intentIn = getIntent();
        String id = intentIn.getStringExtra("id");
        ArrayList<Product> list = productService.cursorToArray(dbHelper.getDataById(id));
        Product product = list.get(0);

        textProductTitle.setText(product.getName());
        textProductDescription.setText(product.getDescription());
        textProductPrice.setText(String.valueOf(product.getPrice()));
        imgProduct.setImageBitmap(productService.byteToBitmap(product.getImage()));

        //textProductTitle.setText(intentIn.getStringExtra("name"));
        //textProductDescription.setText(intentIn.getStringExtra("description"));
        //textProductPrice.setText(intentIn.getStringExtra("price"));

        //int codeImage = intentIn.getIntExtra("imageCode",0);
        //imgProduct.setImageResource(codeImage);

        btnProductBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
            }
        });


    }
}