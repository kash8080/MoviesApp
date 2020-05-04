package com.kashyapmedia.moviesdemo.ui.common;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kashyapmedia.moviesdemo.R;
import com.kashyapmedia.moviesdemo.api.ApiClient;
import com.kashyapmedia.moviesdemo.databinding.RowMovieBinding;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class SimpleMovieListAdapter extends RecyclerView.Adapter< ViewBindViewHolder<RowMovieBinding>> {
    private static final String TAG = "MovieListAdapter";
    private MovieCallback movieCallback;

    public static final int likeAnimDuration=150;

    // TODO: 01-05-2020 inject with di as a singleton
    private RequestOptions requestOptions;
    private RequestManager requestManager;
    private List<MovieEntity> list;
    private Handler handler;

    private TimeInterpolator likeInterpolator=new OvershootInterpolator(20);

    public SimpleMovieListAdapter(MovieCallback movieCallback, Context context) {

        this.movieCallback=movieCallback;
        handler=new Handler();
        list=new ArrayList<>();
        // TODO: 04-05-2020 add error and placeholder images 
        requestOptions= new RequestOptions().override(ScreenUtils.convertDpToPixels(200,context));
        requestManager=Glide.with(context)
                .applyDefaultRequestOptions(requestOptions);
    }

    @NonNull
    @Override
    public ViewBindViewHolder<RowMovieBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowMovieBinding binding = RowMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewBindViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBindViewHolder<RowMovieBinding> holder, int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieCallback.onClick(list.get(position),holder.getAdapterPosition());
            }
        });

        MovieEntity movieEntity=list.get(position);
        if(movieEntity.poster_path!=null&& movieEntity.poster_path.length()>0){
            requestManager.load(ApiClient.IMAGEBASEURL+ApiClient.IMAGESIZEMEDIUM+ movieEntity.poster_path)
                    .into(holder.binding.image);
        }else{
            holder.binding.image.setImageDrawable(null);// TODO: 01-05-2020
        }

        if(movieEntity.isFavourite){
            holder.binding.iconLike.setImageResource(R.drawable.ic_favourite_on);
        }else{
            holder.binding.iconLike.setImageResource(R.drawable.ic_favourite_off);
        }
        holder.binding.iconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateLikeClick(holder.binding.iconLike);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieCallback.onLikeClick(movieEntity,position);
                    }
                },likeAnimDuration);

            }
        });

    }
    private void animateLikeClick(ImageView like){
        if(like.animate()!=null){
            like.animate().cancel();
        }
        like.setScaleX(0.95f);
        like.setScaleY(0.95f);
        like.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(likeInterpolator)
                .setDuration(likeAnimDuration)
                ;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<MovieEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public interface MovieCallback{
        void onClick(MovieEntity movieEntity, int pos);
        void onLikeClick(MovieEntity movieEntity, int pos);
    }

}
