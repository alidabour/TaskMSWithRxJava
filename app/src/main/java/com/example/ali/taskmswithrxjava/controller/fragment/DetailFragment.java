package com.example.ali.taskmswithrxjava.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.taskmswithrxjava.BuildConfig;
import com.example.ali.taskmswithrxjava.model.MovieResult;
import com.example.ali.taskmswithrxjava.R;
import com.example.ali.taskmswithrxjava.model.ReviewGsonResponse;
import com.example.ali.taskmswithrxjava.model.ReviewResult;
import com.example.ali.taskmswithrxjava.ReviewService;
import com.example.ali.taskmswithrxjava.model.VideoGsonResponse;
import com.example.ali.taskmswithrxjava.VideoService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.image)
    ImageView collapsingImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.poster_img)
    ImageView posterImg;
    @BindView(R.id.releaseDate)
    TextView releaseData;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.review)
    TextView review;
    @BindView(R.id.rating)
    TextView rating;

    String id;
    String text = " ";

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this,view);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            MovieResult movie =intent.getParcelableExtra("Movie");
            id = String.valueOf(movie.getId());
            collapsingToolbarLayout.setTitle(movie.getTitle());
            title.setText(movie.getTitle());
            Glide.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath()).into(posterImg);
            Glide.with(getContext()).load("http://image.tmdb.org/t/p/w500"+movie.getBackdropPath()).into(collapsingImage);
            releaseData.setText(movie.getReleaseDate());
            overview.setText(movie.getOverview());
            rating.setText(movie.getVoteAverage().toString());
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .build();
        ReviewService reviewService = retrofit.create(ReviewService.class);
        Observable<ReviewGsonResponse> movieData1 = reviewService.getReviewData(id, BuildConfig.API_KEY);

        movieData1.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieData -> {
                    for (ReviewResult x:movieData.getResults() ){
                        if(x.getAuthor()!=null){
                            text+=  "Author : "+x.getAuthor()+"\n";
                            text+= "Content :"+x.getContent()+"\n";
                        }
                    }
                    review.setText(text);
                    Log.v("Movie Data","Data title:"+ movieData.getResults().get(0).getAuthor()
                    );
                });
        Retrofit retrofitt = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .build();
        VideoService videoService = retrofit.create(VideoService.class);
        Observable<VideoGsonResponse> movieData11 = videoService.getVideoData(id,BuildConfig.API_KEY);

        movieData1.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieData -> {
                    Log.v("Movie Data","Data title:"+ movieData.getResults().get(0).getAuthor()
                    );
                });
    }


}
