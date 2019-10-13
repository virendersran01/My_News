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

    public ArrayList<String> checkboxValues = new ArrayList<>();
    public static final String SEARCH_ARTICLE_VALUES = "SEARCH_ARTICLE_VALUES";

    //Defining views to be bound using Butterknife Api
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
        String[] checkedSections = new String[checkboxValues.size()];
        for (int j = 0; j < checkboxValues.size(); j++) {
            checkedSections[j] = checkboxValues.get(j);
        }

        ArrayList<String> value = new ArrayList<>(Arrays.asList(mSearchQuery.getText().toString(),
                mUtils.getNewDesk(checkedSections),
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
        switch (view.getId()) {
            case R.id.checkbox_1:
                if (isChecked) {
                    checkboxValues.add("Books");
                } else {
                    checkboxValues.remove("Books");
                }
                break;
            case R.id.checkbox_2:
                if (isChecked) {
                    checkboxValues.add("Health");
                } else {
                    checkboxValues.remove("Health");
                }
                break;
            case R.id.checkbox_3:
                if (isChecked) {
                    checkboxValues.add("Movies");
                } else {
                    checkboxValues.remove("Movies");
                }
                break;
            case R.id.checkbox_4:
                if (isChecked) {
                    checkboxValues.add("Science");
                } else {
                    checkboxValues.remove("Science");
                }
                break;
            case R.id.checkbox_5:
                if (isChecked) {
                    checkboxValues.add("Technology");
                } else {
                    checkboxValues.remove("Technology");
                }
                break;
            case R.id.checkbox_6:
                if (isChecked) {
                    checkboxValues.add("Travel");
                } else {
                    checkboxValues.remove("Travel");
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
