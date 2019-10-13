package com.example.my_news.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.my_news.BuildConfig;
import com.example.my_news.R;
import com.example.my_news.adapters.RecyclerViewAdapterSearchArticle;
import com.example.my_news.model.DocsItem;
import com.example.my_news.model.SearchArticle;
import com.example.my_news.network.NewYorkTimesService;
import com.example.my_news.utils.Utils;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my_news.activities.SearchArticlesActivity.SEARCH_ARTICLE_VALUES;

//SearchArticleListActivity: activity is displayed when a user searches the NYT api
//database in the SearchArticlesActivity by providing a query term to filter responses
public class SearchArticleListActivity extends AppCompatActivity {

    public ArrayList<DocsItem> mDataValues = new ArrayList<>();

    private final String API_KEY = "pcRd7UBXGzyAG2aLj6raTyLe6yJJIZF9";

    //Views to be bound using Butterknife Api
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    //References to Utility class methods and the RecyclerView to be inflated
    private Utils mUtils = new Utils();
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private RecyclerViewAdapterSearchArticle mSearchAdapter;
    private NewYorkTimesService mNYTService =
            NewYorkTimesService.retrofit.create(NewYorkTimesService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article_list);
        ButterKnife.bind(this);

        // ^^ using Butterknife Api to bind views; builds recyclerView
        // and calls the configureSwipeRefreshLayout method when activity is created
        searchArticleHttpRequest();
        buildRecyclerView();
        configureSwipeRefreshLayout();
        configureToolbar();
    }

    //Instantiates instance of toolbar
    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //Building the recyclerView and attributing the necessary elements to it
    private void buildRecyclerView() {
        mSearchAdapter = new RecyclerViewAdapterSearchArticle(mDataValues, Glide.with(this));
        mRecyclerView.setAdapter(mSearchAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        displayWebView();
    }

    //Calling the openActivityInWebView method from Utils.java for a uri to
    //be passed and used to open the WebView class properly
    private void displayWebView() {
        this.mSearchAdapter.setOnItemClickListener(new RecyclerViewAdapterSearchArticle.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mUtils.openActivityInWebView(mDataValues.get(position).getWebUrl(),
                        SearchArticleListActivity.this, WebViewActivity.class);
            }
        });
    }

    //Passes the search query term via string array and uses that criteria to pull
    //up the relevant search results
    private void searchArticleHttpRequest() {
        ArrayList<String> mDataValues = getIntent().getStringArrayListExtra(SEARCH_ARTICLE_VALUES);
        String query = mDataValues.get(0);
        String sectionsString = mDataValues.get(1);
        String beginDate = mDataValues.get(2);
        String endDate = mDataValues.get(3);

        Call<SearchArticle> searchArticleCall = mNYTService.callArticleSearchApi(query, sectionsString, beginDate, endDate, API_KEY);
        searchArticleCall.enqueue(new Callback<SearchArticle>() {
            @Override
            public void onResponse(Call<SearchArticle> call, Response<SearchArticle> response) {
                if (response.body() != null) {
                    Log.d("SEARCH RESPONSE", "SEARCH RESPONSE:" + response.raw().toString());
                    SearchArticle searchArticle = response.body();
                    updateSearchArticleUI(searchArticle);
                } else {
                    Toast.makeText(SearchArticleListActivity.this, "Response null!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SearchArticle> call, Throwable t) {
                Toast.makeText(SearchArticleListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    //Functionality for swipeRefreshLayout to be enacted on the activity's RV
    private void configureSwipeRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchArticleHttpRequest();
            }
        });
    }

    //Refreshes search items and refills with any updates; notifies the current data set
    private void updateSearchArticleUI(SearchArticle searchArticle) {
        mDataValues.clear();
        mDataValues.addAll(searchArticle.getResponse().getDocs());
        mSearchAdapter.updateArticles(searchArticle.getResponse().getDocs());
    }
}
