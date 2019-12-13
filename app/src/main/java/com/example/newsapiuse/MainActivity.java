package com.example.newsapiuse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    LottieAnimationView lottieAnimationView;
    ArrayList<Model> arrayList=new ArrayList<>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lottieAnimationView=(LottieAnimationView) findViewById(R.id.anim);
        lottieAnimationView.setVisibility(View.VISIBLE);


        textView=(TextView) findViewById(R.id.text);
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        String url="http://newsapi.org/v2/top-headlines?country=us&apiKey=ddb94057509a4a2998c2126e43d54a95";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    JSONArray jsonArray=response.getJSONArray("articles");
                    Log.d("Myactivity", "onResponse: ");

                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject hit= jsonArray.getJSONObject(i);
                        String title=hit.getString("title");
                        String description=hit.getString("description");
                        String image=hit.getString("urlToImage");


                        Model model=new Model(title, description,image);
                        arrayList.add(model);
                        adapter=new Adapter(arrayList, MainActivity.this);
                        recyclerView.setAdapter(adapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();


            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
}
