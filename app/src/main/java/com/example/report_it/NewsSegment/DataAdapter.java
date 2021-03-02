package com.example.report_it.NewsSegment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.report_it.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{
    Context context;
    ArrayList<Model> arrayList;

    public DataAdapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.news_item,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Model model=arrayList.get(position);
        Picasso.get().load(model.getImage()).into(holder.iNews);
        holder.tvTitle.setText(model.getTitle());
        holder.tvAuthor.setText(model.getAuthor());
        holder.tvPublishedAt.setText(model.getDate());
        holder.tvSource.setText(model.getSource());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iNews;
        private TextView tvPublishedAt,tvAuthor,tvTitle,tvSource;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            iNews=itemView.findViewById(R.id.imgNews);
            tvPublishedAt=itemView.findViewById(R.id.newsPublishedOn);
            tvAuthor=itemView.findViewById(R.id.newsAuthor);
            tvTitle=itemView.findViewById(R.id.newsTitle);
            tvSource=itemView.findViewById(R.id.newsSource);
        }
    }
}
