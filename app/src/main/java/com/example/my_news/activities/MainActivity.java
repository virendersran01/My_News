package com.example.my_news.activities;

import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_news.R;
import com.example.my_news.adapter.ViewPagerAdapter;
import com.example.my_news.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String NYT_API_KEY =
            "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-pcRd7UBXGzyAG2aLj6raTyLe6yJJIZF9";

    private Toolbar toolbar;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private View navHeader;
    private ImageView imgNavHeaderBg, imgNavHeader;
    private TextView txtTitle;

    //urls to load nav background and header image
    public static final String urlNavHeaderBg = "";
    public static final String urlNavHeaderImg = "";

    //tags used to attach the needed fragments
    public static final String TAG_HOME = "home";
    public static final String TAG_MOST_POPLAR = "most popular";
    public static final String TAG_TOP_STORIES = "top stories";

    //toolbar titles respective to selected nav item
    private String[] activityTitles;

    //flag to load home fragment when user presses back key
    private boolean loadHomeFragOnBackPressed = true;
    private Handler mHandler;

    private Utils mUtils = new Utils();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup primary UI elements and drawer/functionality
        setupToolbar();
        setupViewPagerAndTabs();
        setupDrawerLayout();
        setupNavigationView();
    }

    private void setupToolbar() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewPagerAndTabs() {
        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,
                getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupDrawerLayout() {
        this.drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_books:

                break;
            case R.id.nav_health:

                break;
            case R.id.nav_movies:

                break;
            case R.id.nav_science:

                break;
            case R.id.nav_tech:

                break;
            case R.id.nav_travel:

                break;
            case R.id.nav_help:
                mUtils.openActivityInWebView("https://openclassrooms.com",
                        this, WebViewActivity.class);
                break;
            case R.id.nav_about:
                mUtils.openActivityInWebView("https://www.google.fr",
                        this, WebViewActivity.class);
                break;
            default:
                //Do nothing
                break;
        }
        //Regardless of the action taken by the user, the drawer is closed
        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupNavigationView() {
        this.navigationView = findViewById(R.id.nav_view);
        assert navigationView != null;
        this.navigationView.setNavigationItemSelectedListener(this);
    }
}
