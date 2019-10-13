package com.example.my_news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_news.R;
import com.example.my_news.utils.SearchDate;
import com.example.my_news.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

//SearchArticlesActivity: displays checkboxes and an EditText to the user to search articles of a specific topic
//or identify categories of their liking with checkboxes to receive custom notifications
public class SearchArticlesActivity extends AppCompatActivity {

    public final String[] CHECKBOX_VALUES = {"Books", "Health", "Movies", "Science",
            "Technology", "Travel"};
    public static final String SEARCH_ARTICLE_VALUES = "SEARCH_ARTICLE_VALUES";

    //Defining views to be bound using Butterknife Api
    public String[] CHECKBOX_POSITION = new String[6];
    @BindView(R.id.query_text_input_layout)
    public TextInputLayout hintLabel;
    @BindView(R.id.search_query_term)
    EditText mSearchQuery;
    @BindView(R.id.end_date)
    EditText mEndDate;
    @BindView(R.id.begin_date)
    EditText mBeginDate;
    @BindView(R.id.search_activity_search_button)
    Button mSearchButton;
    @BindView(R.id.checkbox_1)
    CheckBox mCheckBox1;
    @BindView(R.id.checkbox_2)
    CheckBox mCheckBox2;
    @BindView(R.id.checkbox_3)
    CheckBox mCheckBox3;
    @BindView(R.id.checkbox_4)
    CheckBox mCheckBox4;
    @BindView(R.id.checkbox_5)
    CheckBox mCheckBox5;
    @BindView(R.id.checkbox_6)
    CheckBox mCheckBox6;

    private Utils mUtils = new Utils();
    private CheckBox[] mCheckboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);

        ButterKnife.bind(this);
        this.mCheckboxes = new CheckBox[]{mCheckBox1, mCheckBox2, mCheckBox3,
                mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6};

        //Configures core UI and functionality of the activity
        //during onCreate
        this.mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUtils.queryInputIsEmpty(mSearchQuery, hintLabel,
                        getResources().getString(R.string.query_error));
                //One box, at minimum must be checked
                if (mUtils.onUncheckedBoxes(mCheckboxes))
                    Toast.makeText(SearchArticlesActivity.this,
                            R.string.box_unchecked, Toast.LENGTH_LONG).show();
                if (!(mSearchQuery.getText().toString().isEmpty())
                        && !(mUtils.onUncheckedBoxes(mCheckboxes))) {
                    configureActivity();
                }
            }
        });
        this.configureToolbar();
        this.setSearchDate();
    }

    //Creates instance of the toolbar
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //Creates new values to be provided for both the begin / end dates of and article search
    private void setSearchDate() {
        SearchDate beginDate = new SearchDate(mBeginDate, this);
        SearchDate endDate = new SearchDate(mEndDate, this);
    }

    //Provides the values specified by the checkboxes along with a begin / end date to the appropriate
    //string array and passes them as search criteria when called
    private void configureActivity() {
        ArrayList<String> value = new ArrayList<>(Arrays.asList(mSearchQuery.getText().toString(),
                mUtils.getNewDesk(CHECKBOX_VALUES),
                mUtils.getBeginDate(mBeginDate.getText().toString()),
                mUtils.getEndDate(mEndDate.getText().toString())));

        Intent intent = new Intent(getBaseContext(), SearchArticleListActivity.class);
        intent.putStringArrayListExtra(SEARCH_ARTICLE_VALUES, value);
        startActivity(intent);
    }

    //Detects whether or not a checkbox is clicked when the user interacts with it and
    //then toggles the corresponding position in its string array
    public void onCheckboxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();
        CHECKBOX_POSITION[0] = CHECKBOX_VALUES[0];
        switch (view.getId()) {
            case R.id.checkbox_1:
                if (isChecked) {
                    CHECKBOX_POSITION[0] = CHECKBOX_VALUES[0];
                } else {
                    CHECKBOX_VALUES[0] = "";
                }
                break;
            case R.id.checkbox_2:
                if (isChecked) {
                    CHECKBOX_POSITION[1] = CHECKBOX_VALUES[1];
                } else {
                    CHECKBOX_VALUES[1] = "";
                }
                break;
            case R.id.checkbox_3:
                if (isChecked) {
                    CHECKBOX_POSITION[2] = CHECKBOX_VALUES[2];
                } else {
                    CHECKBOX_VALUES[2] = "";
                }
                break;
            case R.id.checkbox_4:
                if (isChecked) {
                    CHECKBOX_POSITION[3] = CHECKBOX_VALUES[3];
                } else {
                    CHECKBOX_VALUES[3] = "";
                }
                break;
            case R.id.checkbox_5:
                if (isChecked) {
                    CHECKBOX_POSITION[4] = CHECKBOX_VALUES[4];
                } else {
                    CHECKBOX_VALUES[4] = "";
                }
                break;
            case R.id.checkbox_6:
                if (isChecked) {
                    CHECKBOX_POSITION[5] = CHECKBOX_VALUES[5];
                } else {
                    CHECKBOX_VALUES[5] = "";
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
