package com.example.my_news.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.my_news.R;
import com.example.my_news.activities.WebViewActivity;
import com.example.my_news.adapters.RecyclerViewAdapterTopStories;
import com.example.my_news.model.TopStories;
import com.example.my_news.network.NewYorkTimesService;
import com.example.my_news.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TopStoriesFragment: fragment class which will display the results of the NYT TopStories api in
//the RecyclerViewAdapterTopStories recyclerView
public class TopStoriesFragment extends Fragment {

    //New York Times api key
    String apiKey = "pcRd7UBXGzyAG2aLj6raTyLe6yJJIZF9";
    String baseUrl = "https://api.nytimes.com/svc/";

    //Variables for storing and identifying articles in the MostPopular results array
    public static final String ITEM_POSITION = "web_view_position";
    public static final String KEY_POSITION = "position";
    public ArrayList<TopStories.Result> mTopStoriesArray;

    private Utils mUtils = new Utils();
    private TopStories mTopStories = new TopStories();
    private NewYorkTimesService newYorkTimesService =
            NewYorkTimesService.retrofit.create(NewYorkTimesService.class);

    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;

    private RecyclerViewAdapterTopStories mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int position;

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    //Creating new fragment and passing bundle arguments before being displayed
    public static TopStoriesFragment newTopStoriesFragment(int position) {
        TopStoriesFragment fragment = new TopStoriesFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    //onCreate
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Creates view - binds views using Butterknife and receives the arguments from the fragment
    //Initiates functionality methods defined below at the opening of the activity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_stories,
                container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION, -1);
        }

        mRecyclerView = rootView.findViewById(R.id.frag_recycler_view);
        mTopStoriesArray = new ArrayList<>();
        buildRecyclerView();
        executeHttpRequestWithRetrofit();
        //build SwipeRefreshLayout
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //Creates RV and assembles all the components before displaying on screen and inflating
    private void buildRecyclerView() {
        this.mAdapter = new RecyclerViewAdapterTopStories(mTopStoriesArray, Glide.with(this));
        this.mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
//        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);

        this.displayWebView();
    }

    //Launches intent to open WebView with a uri passed by the current activity
    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewAdapterTopStories.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mUtils.openActivityInWebView(mTopStoriesArray.get(position).getUrl(),
                        getContext(), WebViewActivity.class);
            }
        });
    }

    //Executes the retrofit api method to retrieve articles under the TopStories section of the NYT api
    //Checks the response to see if its empty or if it has changed - if it has changed, the data set is notified
    public void executeHttpRequestWithRetrofit() {
        Call<TopStories> callTopStories = newYorkTimesService.callTopStoriesApi("home", apiKey);
        callTopStories.enqueue(new Callback<TopStories>() {
            @Override
            public void onResponse(Call<TopStories> call, Response<TopStories> response) {
                TopStories topStories = response.body();
                mTopStoriesArray.clear();
                if (topStories != null) {
                    mTopStoriesArray.addAll(topStories.getResults());
                    mAdapter.notifyDataSetChanged();
                    Log.d("HA", topStories.getResults().toString());
                }
            }

            //Return toast message when call fails
            @Override
            public void onFailure(Call<TopStories> call, Throwable t) {
                Toast.makeText(getContext(), "Error loading reponse!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void updateUITopStories() {
//        this.mTopStoriesArray.clear();
//        this.mTopStoriesArray.addAll(mTopStories.getResults());
//        this.mAdapter.notifyDataSetChanged();
//    }
}