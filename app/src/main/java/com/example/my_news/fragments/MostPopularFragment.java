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
import com.example.my_news.adapter.RecyclerViewAdapterMostPopular;
import com.example.my_news.model.MostPopular;
import com.example.my_news.network.NewYorkTimesService;
import com.example.my_news.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularFragment extends Fragment {

    String apiKey = "pcRd7UBXGzyAG2aLj6raTyLe6yJJIZF9";
    String baseUrl = "https://api.nytimes.com/svc/";

    public static final String ITEM_POSITION = "web_view_position";
    public static final String KEY_POSITION = "position";
    public ArrayList<MostPopular.Result> mMostPopularArray;

    private Utils mUtils = new Utils();
    private MostPopular mMostPopular = new MostPopular();
    private NewYorkTimesService newYorkTimesService =
            NewYorkTimesService.retrofit.create(NewYorkTimesService.class);

    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerViewAdapterMostPopular mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private int position;

    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newMostPopularFragment(int position) {
        MostPopularFragment fragment = new MostPopularFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_most_popular,
                container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION, -1);
        }

        this.buildRecyclerView();
        executeHttpRequestWithRetrofit();
        mMostPopularArray = new ArrayList<>();
        updateUiMostPopular();
        //build SwipeRefreshLayout
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void buildRecyclerView() {
        this.mAdapter = new RecyclerViewAdapterMostPopular(mMostPopularArray, Glide.with(this));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        this.displayWebView();
    }

    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewAdapterMostPopular.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mUtils.openActivityInWebView(mMostPopularArray.get(position).getUrl(),
                        getContext(), WebViewActivity.class);
            }
        });
    }

    public void executeHttpRequestWithRetrofit() {
        Call<MostPopular> callMostPopular = newYorkTimesService.callMostPopularApi(baseUrl, apiKey);
        callMostPopular.enqueue(new Callback<MostPopular>() {
            @Override
            public void onResponse(Call<MostPopular> call, Response<MostPopular> response) {
                MostPopular mostPopular = response.body();
                mMostPopularArray.clear();
                if (mostPopular != null) {
                    mMostPopularArray.addAll(mostPopular.getResults());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MostPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Error loading response!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUiMostPopular() {
        this.mMostPopularArray.clear();
        this.mMostPopularArray.addAll(mMostPopular.getResults());
        this.mAdapter.notifyDataSetChanged();
    }
}
