package com.example.my_news.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.my_news.R;
import com.example.my_news.activities.WebViewActivity;
import com.example.my_news.adapter.RecyclerViewAdapterTopStories;
import com.example.my_news.model.TopStories;
import com.example.my_news.network.NewYorkTimesService;
import com.example.my_news.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopStoriesFragment extends Fragment {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mRefreshLayout;

    String apiKey = "pcRd7UBXGzyAG2aLj6raTyLe6yJJIZF9";
    String baseUrl = "https://api.nytimes.com/svc/";

    public static final String ITEM_POSITION = "web_view_position";
    public static final String KEY_POSITION = "position";
    public ArrayList<TopStories.Result> mTopStoriesArray;

    private Utils mUtils = new Utils();
    private TopStories mTopStories = new TopStories();
    private NewYorkTimesService newYorkTimesService =
            NewYorkTimesService.retrofit.create(NewYorkTimesService.class);

    private RecyclerViewAdapterTopStories mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int position;

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    public static TopStoriesFragment newTopStoriesFragment(int position) {
        TopStoriesFragment fragment = new TopStoriesFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_stories,
                container, false);
        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION, -1);
        }

        mRecyclerView = rootView.findViewById(R.id.frag_recycler_view);
        this.buildRecyclerView();
        executeHttpRequestWithRetrofit();
        mTopStoriesArray = new ArrayList<>();
        updateUITopStories();
        //build SwipeRefreshLayout
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void buildRecyclerView() {
        this.mAdapter = new RecyclerViewAdapterTopStories(mTopStoriesArray, Glide.with(this));
        this.mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);

        this.displayWebView();
    }

    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewAdapterTopStories.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mUtils.openActivityInWebView(mTopStoriesArray.get(position).getUrl(),
                        getContext(), WebViewActivity.class);
            }
        });
    }

    public void executeHttpRequestWithRetrofit() {
        Call<TopStories> callTopStories = newYorkTimesService.callTopStoriesApi(baseUrl, apiKey);
        callTopStories.enqueue(new Callback<TopStories>() {
            @Override
            public void onResponse(Call<TopStories> call, Response<TopStories> response) {
                TopStories topStories = response.body();
                mTopStoriesArray.clear();
                if (topStories != null) {
                    mTopStoriesArray.addAll(topStories.getResults());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TopStories> call, Throwable t) {
                Toast.makeText(getContext(), "Error loading reponse!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUITopStories() {
        this.mTopStoriesArray.clear();
        this.mTopStoriesArray.addAll(mTopStories.getResults());
        this.mAdapter.notifyDataSetChanged();
    }
}