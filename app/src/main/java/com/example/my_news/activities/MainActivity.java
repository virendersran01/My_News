package com.example.my_news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_news.R;
import com.example.my_news.adapter.ViewPagerAdapter;
import com.example.my_news.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    private DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.param_notifications:
                launchNotificationsActivity();
                return true;
            case R.id.menu_activity_main_search:
                launchSearchArticlesActivity();
                return true;
            case R.id.param_about:
                mUtils.openActivityInWebView("https://openclassrooms.com",
                        this, WebViewActivity.class);
                return true;
            case R.id.param_help:
                mUtils.openActivityInWebView("https://www.google.com",
                        this, WebViewActivity.class);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void launchSearchArticlesActivity() {
        Intent intent = new Intent(MainActivity.this,
                SearchArticlesActivity.class);
        this.startActivity(intent);
    }

    private void launchNotificationsActivity() {
        Intent intent = new Intent(MainActivity.this,
                NotificationsActivity.class);
        this.startActivity(intent);
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
                mUtils.openActivityInWebView("https://www.google.com",
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
