package com.example.ali.taskmswithrxjava;

import com.example.ali.taskmswithrxjava.model.VideoGsonResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Ali on 2/10/2017.
 */

public interface VideoService {
    @GET("{id}/videos?")
    Observable<VideoGsonResponse> getVideoData(@Path("id")String id, @Query("api_key")String api);
}
