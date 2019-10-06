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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_news.R;
import com.example.my_news.adapters.ViewPagerAdapter;
import com.example.my_news.model.SearchArticle;
import com.example.my_news.model.TopStories;
import com.example.my_news.network.NewYorkTimesService;
import com.example.my_news.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my_news.fragments.TopStoriesFragment.ITEM_POSITION;

//MainActivity: the main page of the app opened when app is started - introduces layout
//and core functionality and displays unfiltered news articles for the user
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String apiKey = "pcRd7UBXGzyAG2aLj6raTyLe6yJJIZF9";

    private Toolbar toolbar;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;

    private View navHeader;
    private ImageView imgNavHeaderBg, imgNavHeader;
    private TextView txtTitle;

    //urls to load nav background and header image
    public static final String urlNavHeaderBg = "";
    public static final String urlNavHeaderImg = "";

    //Fixed string elements of the navigation bar search articles api call
    private NewYorkTimesService mNYTService =
            NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
    private ArrayList<SearchArticle.Docs> mSearchArticlesArray;
    private String TAG_SEARCH;
    private String FQ;
    private String BEGIN_DATE = "20190101";
    private String END_DATE = "20190101";

    private Utils mUtils;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mSearchArticlesArray = new ArrayList<>();
        mUtils = new Utils();

        //Setup primary UI elements and
        // drawer/functionality during onCreate
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

    //Corresponding functionality of menu items; launches intents or routes user to the searchArticles / Notifictions activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.param_notifications:
                launchNotificationsActivity();
                break;
            case R.id.menu_activity_main_search:
                launchSearchArticlesActivity();
                break;
            case R.id.param_about:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(ITEM_POSITION, "https://openclassrooms.com");
                startActivity(intent);
                break;
            case R.id.param_help:
                mUtils.openActivityInWebView("https://www.google.com",
                        this, WebViewActivity.class);
                break;
                default:
                    return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    //This method is called when a user selects the magnifying glass icon in the toolbar;
    // it launches an intent to open the SearchArticles activity
    private void launchSearchArticlesActivity() {
        Intent intent = new Intent(MainActivity.this,
                SearchArticlesActivity.class);
        this.startActivity(intent);
    }

    //This method is called when a user selects the Notifications item in the menu dropdown;
    // it launches an intent to open the Notifications activity
    private void launchNotificationsActivity() {
        Intent intent = new Intent(MainActivity.this,
                NotificationsActivity.class);
        this.startActivity(intent);
    }

    //This method sets the xml toolbar defined in the corresponding activity by initializing its corresponding ID
    private void setupToolbar() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //Initiates the ViewPager feature; creates and sets the adapter and fills it with the desired tabs
    private void setupViewPagerAndTabs() {
        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,
                getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Initiates the DrawerLayout displayed in the application and enables toggling of it to
    // be able to open and close seamlessly
    private void setupDrawerLayout() {
        this.drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    //This method detects which NavDrawer item has been selected using a switch statement and carries
    // out the respective intent or WebView launch
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_books:
                TAG_SEARCH = "books";
                launchDrawerItemIntent();
                break;
            case R.id.nav_health:
                TAG_SEARCH = "health";
                launchDrawerItemIntent();
                break;
            case R.id.nav_movies:
                TAG_SEARCH = "movies";
                launchDrawerItemIntent();
                break;
            case R.id.nav_science:
                TAG_SEARCH = "science";
                launchDrawerItemIntent();
                break;
            case R.id.nav_tech:
                TAG_SEARCH = "technology";
                launchDrawerItemIntent();
                break;
            case R.id.nav_travel:
                TAG_SEARCH = "travel";
                launchDrawerItemIntent();
                break;
            case R.id.nav_help:
                mUtils.openActivityInWebView("https://www.google.com",
                        this, WebViewActivity.class);
                break;
            case R.id.nav_about:
                mUtils.openActivityInWebView("https://openclassrooms.com",
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

    //Launches an intent to display articles related to the searched query
    public void launchDrawerItemIntent() {
        Call<SearchArticle> callSearchArticles = mNYTService.callArticleSearchApi(TAG_SEARCH, TAG_SEARCH, BEGIN_DATE, END_DATE,apiKey);
        callSearchArticles.enqueue(new Callback<SearchArticle>() {
            @Override
            public void onResponse(Call<SearchArticle> call, Response<SearchArticle> response) {
                SearchArticle searchArticle = response.body();
                Log.d("Search Article Response", "onResponse: " + response);
                mSearchArticlesArray.clear();
                if (searchArticle != null) {
                    mSearchArticlesArray.addAll(searchArticle.getResponse().getDocs());
                    Log.d("Search Article Response", searchArticle.getResponse().toString());

                    Intent intent = new Intent(getApplicationContext(), SearchArticleListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("NavDrawer Query", mSearchArticlesArray);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Empty call response!",
                            Toast.LENGTH_LONG).show();
                }
            }

            //Return toast message when call fails
            @Override
            public void onFailure(Call<SearchArticle> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error loading response!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //Initiates the NavigationView layout, checks that
    // it is not null and sets a listener for item clicks
    private void setupNavigationView() {
        this.navigationView = findViewById(R.id.nav_view);
        assert navigationView != null;
        this.navigationView.setNavigationItemSelectedListener(this);
    }
}
