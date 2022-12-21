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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.royalrosesgarden.DB.DBFirebase;
import com.example.royalrosesgarden.DB.DBHelper;
import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Services.ProductService;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Product_form extends AppCompatActivity {

    private ProductService productService;
    //private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private Button btnFormProduct, btnFormGet, btnFormDelete, btnFormUpdate, btnFormBack;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editIdFormProduct;
    private ImageView imgFormProduct;
    private TextView textLatitudeFormProduct, textLongitudeFormProduct;
    private MapView map;
    private MapController mapController;
    ActivityResultLauncher<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_form);


        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        btnFormBack = (Button)  findViewById(R.id.btnFormBack);
        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);
        textLatitudeFormProduct = (TextView)  findViewById(R.id.textLatitudeFormProduct);
        textLongitudeFormProduct = (TextView)  findViewById(R.id.textLongitudeFormProduct);

        // recibimos el valor de la variable edit
        Intent intentIN = getIntent();
        Boolean edit = intentIN.getBooleanExtra("edit", false);
        //

        // Maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map = (MapView) findViewById(R.id.mapFormProduct);
        map.setTileSource(TileSourceFactory.MAPNIK);

        //map.setBuiltInZoomControls(true);
        mapController = (MapController) map.getController();

        GeoPoint bogota = new GeoPoint(4.60971, -74.08175);

        mapController.setCenter(bogota);
        mapController.setZoom(11);
        map.setMultiTouchControls(true);
        // end maps

/*
        byte[] img = "".getBytes(StandardCharsets.UTF_8);
*/
        try {
            productService = new ProductService();
            dbFirebase = new DBFirebase(this);
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

        if(edit){
            btnFormProduct.setText("Update");

            editNameFormProduct.setText(intentIN.getStringExtra("name"));
            editDescriptionFormProduct.setText(intentIN.getStringExtra("description"));
            editPriceFormProduct.setText(String.valueOf(intentIN.getDoubleExtra("price", 0)));
            textLatitudeFormProduct.setText(String.valueOf(intentIN.getDoubleExtra("latitude", 0.0)));
            textLongitudeFormProduct.setText(String.valueOf(intentIN.getDoubleExtra("longitude", 0.0)));

            GeoPoint geoPoint = new GeoPoint(intentIN.getDoubleExtra("latitude", 0.0), intentIN.getDoubleExtra("longitude", 0.0));
            Marker marker = new Marker(map);
            marker.setPosition(geoPoint);
            map.getOverlays().add(marker);
        }

        // maps
        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                textLatitudeFormProduct.setText(String.valueOf(p.getLatitude()));
                textLongitudeFormProduct.setText(String.valueOf(p.getLongitude()));
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, mapEventsReceiver);
        map.getOverlays().add(mapEventsOverlay);
        // end maps

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
                    Product product = new Product(
                            editNameFormProduct.getText().toString(),
                            editDescriptionFormProduct.getText().toString(),
                            Double.valueOf(editPriceFormProduct.getText().toString()),
                            "",
                            Double.parseDouble(textLatitudeFormProduct.getText().toString().trim()),
                            Double.parseDouble(textLongitudeFormProduct.getText().toString().trim())
                    );

                    if(edit){
                        product.setId(intentIN.getStringExtra("id"));
                        dbFirebase.updateData(product, Products.class);
                    }else{
                        dbFirebase.insertData(product);
                        Intent intent = new Intent(getApplicationContext(), Products.class);
                        startActivity(intent);
                    }

                }catch (Exception e){
                    Log.e("Error Insert DB", e.toString());
                }


            }
        });
/*
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
                            "",
                            Double.parseDouble(textLatitudeFormProduct.getText().toString().trim()),
                            Double.parseDouble(textLongitudeFormProduct.getText().toString().trim())

                    );
                    Intent intent = new Intent(getApplicationContext(), Products.class);
                    startActivity(intent);
                    clean();
                }

            }
        });
*/
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