package com.example.addingstarratingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //Init var
    RecyclerView recyclerView;
    FloatingActionButton fb_add;

    JSONArray jsonArray = new JSONArray();
    MainAdapter mainAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        fb_add = findViewById(R.id.fb_add);

        //Set layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //Init adapter
        mainAdapter = new MainAdapter(this, jsonArray);
        //Set adapter
        recyclerView.setAdapter( mainAdapter);
        fb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Init
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_main);
                dialog.show();


                RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);
                TextView tv_rating = dialog.findViewById(R.id.tv_rating);
                Button btSubmit = dialog.findViewById(R.id.bt_submit);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        tv_rating.setText(String.format("(%s)", v));
                    }
                });
                //Set listener onsubmit button
                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Get rating from rating bar
                        String sRating = String.valueOf(ratingBar.getRating());
                        try {
                            jsonArray.put(new JSONObject().put("rating", sRating));
                            //Set adapter
                            recyclerView.setAdapter(mainAdapter);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}