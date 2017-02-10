package com.example.ali.taskmswithrxjava;


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

import butterknife.BindView;
import butterknife.ButterKnife;


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
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    private Unbinder unbinder;


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
//        unbinder =ButterKnife.bind(this,view);
        ButterKnife.bind(this,view);
        Bundle args = this.getArguments();
        if (args != null){
            MovieResult movie =args.getParcelable("Movie");
            Log.v("Test","DF Movie : "+movie.getTitle());
//            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
            collapsingToolbarLayout.setTitle(movie.getTitle());
            title.setText(movie.getTitle());
            Glide.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath()).into(posterImg);
            Glide.with(getContext()).load("http://image.tmdb.org/t/p/w500"+movie.getBackdropPath()).into(collapsingImage);
            releaseData.setText(movie.getReleaseDate());
            overview.setText(movie.getOverview());
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //        Retrofit retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://api.themoviedb.org/3/movie/")
//                .build();
//        ReviewService reviewService = retrofit.create(ReviewService.class);
//        Observable<ReviewGsonResponse> movieData1 = reviewService.getReviewData("346672","144eefdfe75e0f8cb5d9f9b68d178670");
//
//        movieData1.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(movieData -> {
//                    Log.v("Movie Data","Data title:"+ movieData.getResults().get(0).getAuthor()
//                    );
//                });
//        Retrofit retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://api.themoviedb.org/3/movie/")
//                .build();
//        VideoService videoService = retrofit.create(VideoService.class);
//        Observable<VideoGsonResponse> movieData1 = videoService.getVideoData("346672","144eefdfe75e0f8cb5d9f9b68d178670");
//
//        movieData1.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(movieData -> {
//                    Log.v("Movie Data","Data title:"+ movieData.getResults().get(0).getName()
//                    );
//                });
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        unbinder.unbind();
//    }
}
