package com.example.report_it.NewsSegment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.report_it.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class News extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<Model>arrayList;
    DataAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        progressBar=findViewById(R.id.nProgressBar);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        arrayList=new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        getJsonData();
    }

    private void getJsonData() {
        String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=f629cf3c29be4555a7bad45a4b29469b";

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(News.this,response,Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray array=jsonObject.getJSONArray("articles");
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                JSONObject source=object.getJSONObject("source");


                                String sourceName=source.getString("name");
                                String image=object.getString("urlToImage");
                                String author=object.getString("author");
                                String date=object.getString("publishedAt");
                                String title=object.getString("title");
                                arrayList.add(new Model(image,title,date,author,sourceName));
                            }
                            dataAdapter=new DataAdapter(News.this,arrayList);
                            recyclerView.setAdapter(dataAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(News.this,"Here! Lies Mistake",Toast.LENGTH_SHORT).show();
                Toast.makeText(News.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}