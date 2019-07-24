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
import com.example.my_news.adapter.RecyclerViewAdapterSearchArticle;
import com.example.my_news.model.SearchArticle;
import com.example.my_news.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.my_news.activities.SearchArticlesActivity.SEARCH_ARTICLE_VALUES;

public class SearchArticleListActivity extends AppCompatActivity {

    public ArrayList<SearchArticle.Docs> mDocsArrayList = new ArrayList<>();

    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Utils mUtils = new Utils();
    private RecyclerView.LayoutManager mLayoutManager =
            new LinearLayoutManager(this);
    private RecyclerViewAdapterSearchArticle mSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article_list);
        ButterKnife.bind(this);

        this.buildRecyclerView();
        this.configureSwipeRefreshLayout();
        configureToolbar();
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void buildRecyclerView() {
        this.mSearchAdapter = new RecyclerViewAdapterSearchArticle(
                mDocsArrayList, Glide.with(this));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mSearchAdapter);
        this.mRecyclerView.setLayoutManager(mLayoutManager);

        this.displayWebView();
    }

    private void displayWebView() {
        this.mSearchAdapter.setOnItemClickListener(new RecyclerViewAdapterSearchArticle.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mUtils.openActivityInWebView(mDocsArrayList.get(position).getWebUrl(), SearchArticleListActivity.this, WebViewActivity.class);
            }
        });
    }

    private void SearchArticleHttpRequest() {
        String [] mDataValues = getIntent().getStringArrayExtra(SEARCH_ARTICLE_VALUES);
    }

    private void configureSwipeRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                execute SearchArticleHttpRequest();
            }
        });
    }

    private void updateSearchArticleUI(SearchArticle searchArticle) {
        this.mDocsArrayList.clear();
        this.mDocsArrayList.addAll(searchArticle.getResponse().getDocs());
        this.mSearchAdapter.notifyDataSetChanged();
    }
}
