package com.example.royalrosesgarden;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.royalrosesgarden.DB.DBHelper;
import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Services.ProductService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Product_form extends AppCompatActivity {

    private ProductService productService;
    private DBHelper dbHelper;
    private Button btnFormProduct, btnFormGet, btnFormDelete, btnFormUpdate, btnFormBack;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editIdFormProduct;
    private ImageView imgFormProduct;
    ActivityResultLauncher<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_form);

        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        btnFormGet = (Button) findViewById(R.id.btnFormGet);
        btnFormDelete = (Button) findViewById(R.id.btnFormDelete);
        btnFormUpdate = (Button) findViewById(R.id.btnFormUpdate);
        btnFormBack = (Button)  findViewById(R.id.btnFormBack);
        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        editIdFormProduct = (EditText) findViewById(R.id.editIdFormProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);

        byte[] img = "".getBytes(StandardCharsets.UTF_8);
        try {
            productService = new ProductService();
            dbHelper = new DBHelper(this);
        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        content = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(result);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imgFormProduct.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


        imgFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });

        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    dbHelper.insertData(
                            editNameFormProduct.getText().toString(),
                            editDescriptionFormProduct.getText().toString(),
                            Double.valueOf(editPriceFormProduct.getText().toString()),
                            //img   // IMAGEN FIJA
                            productService.imageViewToByte(imgFormProduct)
                    );
                }catch (Exception e){
                    Log.e("DB Insert", e.toString());
                }

                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
            }
        });

        btnFormGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") != 0){
                    ArrayList<Product> list = productService.cursorToArray(dbHelper.getDataById(id));
                    if(list.size() != 0){
                        Product product = list.get(0);
                        imgFormProduct.setImageBitmap(productService.byteToBitmap(product.getImage()));
                        editNameFormProduct.setText(product.getName());
                        editDescriptionFormProduct.setText(product.getDescription());
                        editPriceFormProduct.setText(String.valueOf(product.getPrice()));
                    }else{
                        Toast.makeText(getApplicationContext(),"No Found Id ",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Enter Id ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFormDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") != 0){
                    //Log.d("DB",id);
                    dbHelper.deleteDataById(id);
                    clean();
                    Intent intent = new Intent(getApplicationContext(), Products.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Enter Id to Delete",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFormUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") != 0){
                    dbHelper.updateDataById(
                            id,
                            editNameFormProduct.getText().toString(),
                            editDescriptionFormProduct.getText().toString(),
                            Double.valueOf(editPriceFormProduct.getText().toString()),
                            productService.imageViewToByte(imgFormProduct)

                    );
                    Intent intent = new Intent(getApplicationContext(), Products.class);
                    startActivity(intent);
                    clean();
                }

            }
        });

        btnFormBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
            }
        });



    }

    public void clean(){
        editNameFormProduct.setText("");
        editDescriptionFormProduct.setText("");
        editPriceFormProduct.setText("");
        imgFormProduct.setImageResource(R.drawable.empty);
    }

}