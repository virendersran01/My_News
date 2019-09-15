package com.example.my_news.network;

import com.example.my_news.model.MostPopular;
import com.example.my_news.model.SearchArticle;
import com.example.my_news.model.TopStories;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//NewYorkTimesService: this class lays out the Retrofit interface and the structure of the calls to be made
//by it to the NYT website/api
public interface NewYorkTimesService {

    String baseUrl = "https://api.nytimes.com/svc/";
    String SearchArticleFl =
            "fl=web_url,snippet,pub_date,news_desk,multimedia,document_type,type_of_material";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //Top Stories Fragment
    @GET("topstories/v2/{section}.json")
    Call<TopStories> callTopStoriesApi(@Path("section") String SECTION, @Query("api-key") String API_KEY);

    //Most Popular Fragment
    @GET("mostpopular/v2/viewed/{period}.json")
    Call<MostPopular> callMostPopularApi(@Path("period") int PERIOD, @Query("api-key") String API_KEY);

    @GET("search/vs/articlesearch.json?" + SearchArticleFl + "&page=1&sort=newest")
    Call<SearchArticle> callArticleSearchApi(@Query("q") String QUERY,
                                             @Query("fq") String FILTER,
                                             @Query("begin_date") String BEGIN_DATE,
                                             @Query("end_date") String END_DATE,
                                             @Query("api-key") String API_KEY);
}
