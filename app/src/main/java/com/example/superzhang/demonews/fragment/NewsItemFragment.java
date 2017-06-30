package com.example.superzhang.demonews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.superzhang.demonews.R;
import com.example.superzhang.demonews.adapter.NewsRecyclerViewAdapter;
import com.example.superzhang.demonews.data.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by superzhang on 2017/5/16.
 */

public class NewsItemFragment extends Fragment{

    private static final int MSG_GET_NEWS = 1001;
    private static final int REFRESH_FINISH = 1002;
    private static final int REFRESH_NOCHANGE = 1003;
    private int titleInt;

    private android.support.v7.widget.RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private Handler mHandler;

    private NewsRecyclerViewAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    final String labels[] = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();

        titleInt = getArguments().getInt("labels");

    }

    //刷新界面
    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //获取数据刷新列表
                if(msg.what == MSG_GET_NEWS){
                    newsAdapter.changeData(newsList);
                    return  true;
                }

                //下拉刷新成功
                if(msg.what == REFRESH_FINISH){
                    swipeRefreshLayout.setRefreshing(false);
                    //Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    newsAdapter.changeData(newsList);
                    return  true;
                }

                //无新内容刷新
                if(msg.what == REFRESH_NOCHANGE){
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),"没有新内容",Toast.LENGTH_SHORT).show();
                    return  true;
                }

                return false;
            }
        });
    }

    //使用OKHttp从聚合数据上取得JSON数据
    public List<News> getJSONDatasWithOkHttp(final String type) {
        List<News> dataList = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://v.juhe.cn/toutiao/index?type=" + type + "&key=2c65af6309309ae5e70a80d0d564bf7d").build();

            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            dataList = parseJSONWithJSONObject(responseData);

            //比较是否是新数据
            if(compareWithNewsList(dataList)){
                //旧数据
                mHandler.sendEmptyMessageDelayed(REFRESH_NOCHANGE,2000);
            }else {
                //新数据
                mHandler.sendEmptyMessageDelayed(REFRESH_FINISH,2000);
            }
            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
            return dataList;
        }
    }

    //解析JSON数据
    private List<News> parseJSONWithJSONObject(String jsonData) {
        List<News> dataList = new ArrayList<>();
        try {
            JSONObject oriDatas = new JSONObject(jsonData);
            JSONObject result = oriDatas.getJSONObject("result");
            JSONArray newsArray = result.getJSONArray("data");
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject newsObject = newsArray.getJSONObject(i);
                String title = newsObject.getString("title");//新闻标题
                String date = newsObject.getString("date");//时间
                String author_name = newsObject.getString("author_name");//来源
                String content = newsObject.getString("url");//内容
                String thumbnail_pic_s = newsObject.getString("thumbnail_pic_s");//图片

                News news = new News();
                news.setTitle(title);
                news.setContent(content);
                news.setThumbnail_pic_s(thumbnail_pic_s);
                news.setAuthor_name(author_name);
                news.setDate(date);
                dataList.add(news);
            }

            return  dataList;

        } catch (Exception e) {
            e.printStackTrace();
            return dataList;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_items,container,false);

        //做天气Fragment暂时不获取数据
        if (newsList.size()==0){
            getFragmentBundleDatas();
        }


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragmentSwipeRefesh);//1

        mRecyclerView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.mRecyclerView);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsRecyclerViewAdapter(newsList);
        mRecyclerView.setAdapter(newsAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //从网络获取数据
                getFragmentBundleDatas();
            }
        });

        return view;
    }

    //根据title获取数据
    public void getFragmentBundleDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<News> oldData = newsList;
                switch (titleInt){
                    case 0:newsList =  getJSONDatasWithOkHttp(labels[0]);break;
                    case 1:newsList =  getJSONDatasWithOkHttp(labels[1]);break;
                    case 2:newsList =  getJSONDatasWithOkHttp(labels[2]);break;
                    case 3:newsList =  getJSONDatasWithOkHttp(labels[3]);break;
                    case 4:newsList =  getJSONDatasWithOkHttp(labels[4]);break;
                    case 5:newsList =  getJSONDatasWithOkHttp(labels[5]);break;
                    case 6:newsList =  getJSONDatasWithOkHttp(labels[6]);break;
                    case 7:newsList =  getJSONDatasWithOkHttp(labels[7]);break;
                    case 8:newsList =  getJSONDatasWithOkHttp(labels[8]);break;
                    case 9:newsList =  getJSONDatasWithOkHttp(labels[9]);break;
                    default:break;
                }
            }
        }).start();
    }

    //比较传入数组是否与newsList不同，默认相同
    public boolean compareWithNewsList(List<News> oldData){
        if(oldData.size()==newsList.size()){
            for(int i=0;i<oldData.size();i++){
                if (oldData.get(i)!=newsList.get(i)){
                    return true;
                }
            }
        }
        return false;
    }
}
