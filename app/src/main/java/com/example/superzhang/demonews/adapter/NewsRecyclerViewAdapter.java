package com.example.superzhang.demonews.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.superzhang.demonews.data.News;
import com.example.superzhang.demonews.R;
import com.example.superzhang.demonews.activity.ContentActivity;

import java.util.List;

/**
 * Created by superzhang on 2017/5/16.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.myViewHolder>{

    private List<News> newsList;

    static class myViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        private TextView tvTitle,tv_source,tv_date;
        private ImageView imgHead;
        private View itemView;

        public myViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            imgHead = (ImageView) itemView.findViewById(R.id.imgHead);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public NewsRecyclerViewAdapter(List<News> newsList){
        this.newsList = newsList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_news_items,parent,false);
        final myViewHolder holder = new myViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(v.getContext(),ContentActivity.class);
                News news = newsList.get(position);
                String title = news.getTitle();
                String date = news.getDate();
                String author_name = news.getAuthor_name();
                String url = news.getContent();
                intent.putExtra("title",title);
                intent.putExtra("date",date);
                intent.putExtra("author_name",author_name);
                intent.putExtra("url",url);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tv_source.setText(news.getAuthor_name());
        holder.tv_date.setText(news.getDate());

        String imgUrl = newsList.get(position).getThumbnail_pic_s();

        if (holder.itemView != null) {
            Glide.with(holder.itemView.getContext()).load(imgUrl).into(holder.imgHead);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void changeData(List<News> newsList){
        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
