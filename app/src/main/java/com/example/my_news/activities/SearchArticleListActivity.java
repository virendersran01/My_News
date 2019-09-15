package com.example.my_news.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.my_news.R;
import com.example.my_news.adapters.RecyclerViewAdapterSearchArticle;
import com.example.my_news.model.SearchArticle;
import com.example.my_news.utils.Utils;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.my_news.activities.SearchArticlesActivity.SEARCH_ARTICLE_VALUES;

//SearchArticleListActivity: activity is displayed when a user searches the NYT api
//database in the SearchArticlesActivity by providing a query term to filter responses
public class SearchArticleListActivity extends AppCompatActivity {

    public ArrayList<SearchArticle.Docs> mDocsArrayList = new ArrayList<>();

    //Views to be bound using Butterknife Api
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    //References to Utility class methods and the RecyclerView to be inflated
    private Utils mUtils = new Utils();
    private RecyclerView.LayoutManager mLayoutManager =
            new LinearLayoutManager(this);
    private RecyclerViewAdapterSearchArticle mSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article_list);
        ButterKnife.bind(this);

        // ^^ using Butterknife Api to bind views; builds recyclerView
        // and calls the configureSwipeRefreshLayout method when activity is created
        this.buildRecyclerView();
        this.configureSwipeRefreshLayout();
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
        this.mSearchAdapter = new RecyclerViewAdapterSearchArticle(
                mDocsArrayList, Glide.with(this));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mSearchAdapter);
        this.mRecyclerView.setLayoutManager(mLayoutManager);

        this.displayWebView();
    }

    //Calling the openActivityInWebView method from Utils.java for a uri to
    //be passed and used to open the WebView class properly
    private void displayWebView() {
        this.mSearchAdapter.setOnItemClickListener(new RecyclerViewAdapterSearchArticle.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mUtils.openActivityInWebView(mDocsArrayList.get(position).getWebUrl(),
                        SearchArticleListActivity.this, WebViewActivity.class);
            }
        });
    }

    //Passes the search query term via  string arry and uses that criteria to pull
    //up the relevant search results
    private void searchArticleHttpRequest() {
        String [] mDataValues = getIntent().getStringArrayExtra(SEARCH_ARTICLE_VALUES);
    }

    //Functionality for swipeRefreshLayout to be enacted on the activity's RV
    private void configureSwipeRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                execute SearchArticleHttpRequest();
            }
        });
    }

    //Refreshes search items and refills with any updates; notifies the current data set
    private void updateSearchArticleUI(SearchArticle searchArticle) {
        this.mDocsArrayList.clear();
        this.mDocsArrayList.addAll(searchArticle.getResponse().getDocs());
        this.mSearchAdapter.notifyDataSetChanged();
    }
}
