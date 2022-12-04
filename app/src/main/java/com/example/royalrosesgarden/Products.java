package com.example.royalrosesgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Products extends AppCompatActivity {

    private Button btnProdsJungleNightBox, btnProdsRaspberryBox, btnProdsPinkBox, btnProdsBlueBox, btnBackProducts;
    private TextView textProdsJungleNightBox, textProdsRaspberryBox, textProdsPinkBox, textProdsBlueBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        btnProdsJungleNightBox = (Button) findViewById(R.id.btnProdsJungleNightBox);
        btnProdsRaspberryBox = (Button) findViewById(R.id.btnProdsRaspberryBox);
        btnProdsPinkBox = (Button) findViewById(R.id.btnProdsPinkBox);
        btnProdsBlueBox = (Button) findViewById(R.id.btnProdsBlueBox);
        btnBackProducts = (Button) findViewById(R.id.btnBackProducts);

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

        btnBackProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });
    }
}