package com.example.royalrosesgarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;

import com.example.royalrosesgarden.Adapters.ProductAdapter;
import com.example.royalrosesgarden.DB.DBFirebase;
import com.example.royalrosesgarden.DB.DBHelper;
import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Services.ProductService;

public class Products extends AppCompatActivity {
 //   private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductService productService;
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
        //dbHelper = new DBHelper(this);
        productService = new ProductService();



        try{
            //dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase(this);
            productService = new ProductService();
            //Log.d("mostrar ",String.valueOf(arrayProducts.size()));
        }catch (Exception e){
            Log.e("Database", e.toString());
            //Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

        listViewProduct = (ListView) findViewById(R.id.listViewProducts);


        productAdapter = new ProductAdapter(this, arrayProducts);

        listViewProduct.setAdapter(productAdapter);

        dbFirebase.getData(productAdapter, arrayProducts);

        btnBackProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAddProduct:
                Intent intent = new Intent(getApplicationContext(), Product_form.class);
                startActivity(intent);
                return true;
            case R.id.actionMapProduct:
                intent = new Intent(getApplicationContext(), Maps.class);
                ArrayList<String> latitudes = new ArrayList<>();
                ArrayList<String> longitudes = new ArrayList<>();

                for(int i=0; i<arrayProducts.size(); i++){
                    latitudes.add(String.valueOf(arrayProducts.get(i).getLatitude()));
                    longitudes.add(String.valueOf(arrayProducts.get(i).getLongitude()));
                }
                intent.putStringArrayListExtra("latitudes", latitudes);
                intent.putStringArrayListExtra("longitudes", longitudes);

                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}