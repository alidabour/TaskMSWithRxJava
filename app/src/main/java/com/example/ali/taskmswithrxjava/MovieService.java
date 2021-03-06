package com.example.ali.taskmswithrxjava;

import com.example.ali.taskmswithrxjava.model.MovieGsonResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Ali on 2/10/2017.
 */

public interface MovieService {
    @GET("movie/{type}")
    Observable<MovieGsonResponse> getMovieData(@Path("type")String sortType,
                                               @Query("api_key")String api);
}