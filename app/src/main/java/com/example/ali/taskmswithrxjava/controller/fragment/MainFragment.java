package com.example.ali.taskmswithrxjava.controller.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.taskmswithrxjava.BuildConfig;
import com.example.ali.taskmswithrxjava.model.MovieGsonResponse;
import com.example.ali.taskmswithrxjava.adapter.MovieRecycleAdapter;
import com.example.ali.taskmswithrxjava.model.MovieResult;
import com.example.ali.taskmswithrxjava.MovieService;
import com.example.ali.taskmswithrxjava.R;
import com.example.ali.taskmswithrxjava.controller.activity.DetailActivity;

import java.security.GuardedObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class MainFragment extends Fragment implements MovieRecycleAdapter.OnClickHandler {

    MovieRecycleAdapter movieRecycleAdapter;
    RecyclerView recyclerView;
    TextView error;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.mainRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        error = (TextView)view.findViewById(R.id.error);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortType = sharedPreferences.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popular)
        );
        String sort_by="";
        if(sortType.equals(getString(R.string.pref_sort_popular))){
            sort_by=getString(R.string.pref_sort_popular);

        }else if (sortType.equals(getString(R.string.pref_sort_highRated))){
            sort_by=getString(R.string.pref_sort_highRated);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.themoviedb.org/3/")
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Observable<MovieGsonResponse> movieData1 = movieService.getMovieData(sort_by, BuildConfig.API_KEY);
            movieData1.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movieData -> {
                        movieRecycleAdapter = new MovieRecycleAdapter(getContext(),
                                movieData.getResults(), this);
                        recyclerView.setAdapter(movieRecycleAdapter);
                        error.setVisibility(View.GONE);

                    }, throwable -> {
                        throwable.printStackTrace();
                        error.setVisibility(View.VISIBLE);

                    });




    }

    @Override
    public void onClick(MovieResult movieResult) {
        Log.v("Test","id : "+movieResult.getTitle());
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("Movie",movieResult);
        startActivity(intent);
    }
}
