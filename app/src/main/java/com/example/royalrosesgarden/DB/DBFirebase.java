package com.example.royalrosesgarden.DB;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.royalrosesgarden.Adapters.ProductAdapter;
import com.example.royalrosesgarden.Entities.Product;
import com.example.royalrosesgarden.Product_form;
import com.example.royalrosesgarden.Products;
import com.example.royalrosesgarden.Services.ProductService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private Context context;
    private FirebaseFirestore dbFireBaseFs;
    private ProductService productService;

    public DBFirebase(Context context) {
        this.dbFireBaseFs = FirebaseFirestore.getInstance();
        this.productService = new ProductService();
        this.context = context;
    }

    public void insertData(Product prod){
        Map<String, Object> product = new HashMap<>();
        product.put("id",prod.getId() );
        product.put("name", prod.getName());
        product.put("description", prod.getDescription());
        product.put("price", prod.getPrice());
        product.put("image", prod.getImage());
        product.put("latitude", prod.getLatitude());
        product.put("longitude", prod.getLongitude());

        // Add a new document with a generated ID
        dbFireBaseFs.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID : " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document ", e);
                    }
                });
        //db.terminate();
    }

    public void getData(ProductAdapter productAdapter, ArrayList<Product> list){
        dbFireBaseFs.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "XXXXXXX " + document.getId() + " => " + document.getData());
                                Product product = null;
                                //if(!Boolean.valueOf(document.getData().get("deleted").toString())){
                                    product = new Product(
                                            document.getData().get("id").toString(),
                                            document.getData().get("name").toString(),
                                            document.getData().get("description").toString(),
                                            Double.parseDouble(document.getData().get("price").toString()),
                                            document.getData().get("image").toString(),
                                            Double.parseDouble(document.getData().get("latitude").toString()),
                                            Double.parseDouble(document.getData().get("longitude").toString())
                                    );
                                    list.add(product);
                                //}
                            }
                            productAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void deleteData(String id){
        dbFireBaseFs.collection("products").whereEqualTo("id",id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                     if(task.isSuccessful()){
                                                         for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                             documentSnapshot.getReference().delete();
                                                         }
                                                     }
                                                 }
                                             }
                );
    }

    public void updateData(Product prod, Class classFinal){
        dbFireBaseFs.collection("products").whereEqualTo("id", prod.getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().update(
                                        "name", prod.getName(),
                                        "description", prod.getDescription(),
                                        "price", prod.getPrice(),
                                        "image", prod.getImage(),
                                        "latitude", prod.getLatitude(),
                                        "longitude", prod.getLongitude()
                                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(context, classFinal);
                                            context.startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }

}
