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
import com.example.royalrosesgarden.DB.DBHelper;
import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Services.ProductService;

public class Products extends AppCompatActivity {
    private DBHelper dbHelper;
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
        dbHelper = new DBHelper(this);
        productService = new ProductService();



        try{
            dbHelper = new DBHelper(this);
            // Insertas datos manualmente. La base de datos está vacia
/*
                byte[] img = "".getBytes();
                dbHelper.insertData("Blue",  "Blue Box",  49.8, img);
                dbHelper.insertData("Night",  "Jungle Night Box",  64.5, img);
                dbHelper.insertData("Pink",  "Pink Box",  51.75, img);
                dbHelper.insertData("Raspberry",  "Raspberry Box",  51.0, img);
                Toast.makeText(this,"INSERTAR DATOS",Toast.LENGTH_LONG).show();
*/
            // fin insertar datos
            productService = new ProductService();
            Cursor cursor = dbHelper.getData();
            arrayProducts = productService.cursorToArray(cursor);
            //Toast.makeText(this,"TRAER DATOS",Toast.LENGTH_LONG).show();
            //Log.d("mostrar ",String.valueOf(arrayProducts.size()));
        }catch (Exception e){
            Log.e("Database", e.toString());
            //Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}