package com.example.jsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import org.json.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.squareup.picasso.Picasso;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private TextView myHeroName;
    private Button myButtonParse;
    private ImageView heroImage;
    private RequestQueue myQueue;

    private ProgressBar intelligence_pb, strength_pb, speed_pb, durability_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHeroName = findViewById(R.id.textView);
        myButtonParse = findViewById(R.id.button);
        heroImage = findViewById(R.id.imageView);

        intelligence_pb = findViewById(R.id.intelligencePb);
        strength_pb = findViewById(R.id.strengthPb);
        speed_pb = findViewById(R.id.speedPb);
        durability_pb = findViewById(R.id.durabilityPb);

        myQueue = Volley.newRequestQueue(this);



    }

    public void searchButton(View v){
        getRequest();
    }


    private void getRequest(){
        final Random random = new Random();
        int id = random.nextInt(563);
        String url = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/id/"+id+".json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String myPokemon = response.getString("name");
                            JSONObject myPokeSprite = response.getJSONObject("images");
                            String sprite_url = myPokeSprite.getString("md");
                            JSONObject powerStats = response.getJSONObject("powerstats");
                            //power stats
                            int count_intel = powerStats.getInt("intelligence");
                            int count_strength = powerStats.getInt("strength");
                            int count_speed = powerStats.getInt("speed");
                            int count_durability = powerStats.getInt("durability");

                            myHeroName.setText(myPokemon);
                            intelligence_pb.setProgress(count_intel);
                            strength_pb.setProgress(count_strength);
                            speed_pb.setProgress(count_speed);
                            durability_pb.setProgress(count_durability);
                            Picasso.with(MainActivity.this).load(sprite_url).into(heroImage);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myQueue.add(request);

    }


}