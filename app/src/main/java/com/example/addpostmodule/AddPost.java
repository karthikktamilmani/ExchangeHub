package com.example.addpostmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddPost extends Activity {
    EditText product,description,price;
    Spinner Category;
    Button btn;
    Bitmap image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        product = (EditText)findViewById(R.id.productTitle);
        description = (EditText)findViewById(R.id.descriptioneditText);
        price = (EditText)findViewById(R.id.cost);
        Category = (Spinner)findViewById(R.id.spinner);
        btn = (Button)findViewById(R.id.button);



        byte[] byteArrayImage = getIntent().getByteArrayExtra("image");
         image = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length);
        ImageView ib = (ImageView) findViewById(R.id.ProductImage);
        ib.setImageBitmap(image);
        Spinner staticSpinner = (Spinner) findViewById(R.id.spinner);


        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.categoryList,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData();
            }
        });


    }

    void retrieveData(){
        String productText,descriptionText,priceText,CategoryText;
        productText = product.getText().toString();
        descriptionText = description.getText().toString();
        priceText = price.getText().toString();
        CategoryText = Category.getSelectedItem().toString();

        if(productText.isEmpty() || descriptionText.isEmpty() || priceText.isEmpty() || CategoryText.isEmpty()){
            Toast.makeText(getApplicationContext(),"Check all fields",Toast.LENGTH_LONG).show();
            retrieveData();
        }

        requestService(productText,descriptionText,priceText,CategoryText);
    }

    private void requestService(final String productText, final String descriptionText, final String priceText,final String categoryText) {

        String ServiceURL = "http://f5653ad7.ngrok.io/AddPostModule";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {

                    //jsonObject = new JSONObject(response);
                    //NAME = jsonObject.getString("name");
                    //MAIL = jsonObject.getString("email");
                    //Toast.makeText(getApplicationContext(),MAIL,Toast.LENGTH_LONG).show();
                    //Log.d("STATE",name);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                //OBTAIN id from Database
                String userID = "1";
                //System.out.println(desc);
                params.put("userID",userID);
                params.put("Product",productText);
                params.put("Description",descriptionText);
                params.put("Price",priceText);
                params.put("Category",categoryText);
                params.put("image",imageToString(image));
                return params;
            }

        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }
    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }



}
