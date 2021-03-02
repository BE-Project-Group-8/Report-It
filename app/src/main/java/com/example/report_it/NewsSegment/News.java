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
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<Model>arrayList;
    private DataAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        arrayList=new ArrayList<>();
        progressBar=findViewById(R.id.nProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        getJsonData();
    }

    private void getJsonData() {
        String url="http://newsapi.org/v2/everything?q=tesla&from=2021-02-01&sortBy=publishedAt&apiKey=MyKey";

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                Toast.makeText(News.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}
