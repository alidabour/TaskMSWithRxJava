package com.example.ali.taskmswithrxjava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MovieRecycleAdapter.OnClickHandler {

    MovieRecycleAdapter movieRecycleAdapter;
    RecyclerView recyclerView;
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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.themoviedb.org/3/discover/")
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Observable<MovieGsonResponse> movieData1 = movieService.getMovieData("popularity.desc","144eefdfe75e0f8cb5d9f9b68d178670");

        movieData1.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieData -> {
                    movieRecycleAdapter = new MovieRecycleAdapter(getContext(),movieData.getResults(),this);
                    recyclerView.setAdapter(movieRecycleAdapter);
                    Log.v("Movie Data","Data title:"+ movieData.getResults().get(0).getTitle()
                    );
                });

    }

    @Override
    public void onClick(MovieResult movieResult) {
        Log.v("Test","id : "+movieResult.getTitle());
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("Movie",movieResult);
        detailFragment.setArguments(args);
        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction()
//                        .detach(MainFragment.this)
//                        .hide(MainFragment.this)
//                        .remove(MainFragment.this)
                .replace(R.id.fragment,detailFragment)
                .commit();
    }
}
