package com.example.royalrosesgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Product extends AppCompatActivity {
    private Button btnProductBack;
    private TextView textProductTitle, textProductPrice;
    private ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        btnProductBack = (Button) findViewById(R.id.btnProductBack);
        textProductTitle = (TextView) findViewById(R.id.textProductTitle);
        textProductPrice = (TextView) findViewById(R.id.textProductPrice);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);

        Intent intentIn = getIntent();
        textProductTitle.setText(intentIn.getStringExtra("title"));
        textProductPrice.setText(intentIn.getStringExtra("price"));

        int codeImage = intentIn.getIntExtra("imageCode",0);
        imgProduct.setImageResource(codeImage);

        btnProductBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
            }
        });


    }
}