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
import com.example.my_news.adapters.RecyclerViewAdapterMostPopular;
import com.example.my_news.model.MostPopular;
import com.example.my_news.network.NewYorkTimesService;
import com.example.my_news.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//MostPopularFragment: fragment class which will display the results of the NYT MostPopular api in
//the RecyclerViewAdapterMostPopular recyclerView
public class MostPopularFragment extends Fragment {

    //New York Times api key
    String apiKey = "pcRd7UBXGzyAG2aLj6raTyLe6yJJIZF9";

    //Variables for storing and identifying articles in the MostPopular results array
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

    //Creating new fragment and passing bundle arguments before being displayed
    public static MostPopularFragment newMostPopularFragment(int position) {
        MostPopularFragment fragment = new MostPopularFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_most_popular,
                container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION, -1);
        }

        mRecyclerView = rootView.findViewById(R.id.frag_recycler_view);
        mMostPopularArray = new ArrayList<>();
        buildRecyclerView();
        executeHttpRequestWithRetrofit();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //Creates RV and assembles all the components before displaying on screen and inflating
    private void buildRecyclerView() {
        this.mAdapter = new RecyclerViewAdapterMostPopular(mMostPopularArray, Glide.with(this));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        this.displayWebView();
    }

    //Launches intent to open WebView with a uri passed by the current activity
    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewAdapterMostPopular.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mUtils.openActivityInWebView(mMostPopularArray.get(position).getUrl(),
                        getContext(), WebViewActivity.class);
            }
        });
    }

    //Executes the retrofit api method to retrieve articles under the MostPopular section of the NYT api
    //Checks the response to see if its empty or if it has changed - if it has changed, the data set is notified
    public void executeHttpRequestWithRetrofit() {
        Call<MostPopular> callMostPopular = newYorkTimesService.callMostPopularApi(7, apiKey);
        callMostPopular.enqueue(new Callback<MostPopular>() {
            @Override
            public void onResponse(Call<MostPopular> call, Response<MostPopular> response) {
                MostPopular mostPopular = response.body();
                Log.d("Response", response.raw().toString());
                mMostPopularArray.clear();
                if (mostPopular != null) {
                    mMostPopularArray.addAll(mostPopular.getResults());
                    mAdapter.notifyDataSetChanged();
                }
            }

            //Return toast message when call fails
            @Override
            public void onFailure(Call<MostPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Error loading response!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
