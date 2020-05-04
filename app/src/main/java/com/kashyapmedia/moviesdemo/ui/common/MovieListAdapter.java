package com.kashyapmedia.moviesdemo.ui.common;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

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

public class MovieListAdapter extends ListAdapter<MovieEntity, ViewBindViewHolder<RowMovieBinding>> {
    private static final String TAG = "MovieListAdapter";
    private MovieCallback movieCallback;

    // TODO: 01-05-2020 inject with di as a singleton
    private RequestOptions requestOptions;
    private RequestManager requestManager;

    public MovieListAdapter(MovieCallback movieCallback, Context context) {
        super(new DiffUtil.ItemCallback<MovieEntity>() {

            @Override
            public boolean areItemsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                Log.d(TAG, "areItemsTheSame: id:"+oldItem.id);
                return oldItem.id==newItem.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                Log.d(TAG, "--line--");
                Log.d(TAG, "areContentsTheSame: id:"+oldItem.id+": newid:"+newItem.id);
                Log.d(TAG, "areContentsTheSame: data: "+oldItem.toString());
                Log.d(TAG, "areContentsTheSame: data: "+newItem.toString());
                boolean val= oldItem.toString().equals(newItem.toString());// TODO: 01-05-2020
                Log.d(TAG, "areContentsTheSame: same:"+val);
                return val;
            }
        });

        this.movieCallback=movieCallback;
        requestOptions= new RequestOptions().override(ScreenUtils.convertDpToPixels(200,context));
        requestManager=Glide.with(context)
                .applyDefaultRequestOptions(requestOptions);
    }

    @Override
    public void submitList(@Nullable List<MovieEntity> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
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
                movieCallback.onClick(getItem(position),position);
            }
        });

        MovieEntity movieEntity=getItem(position);
        Log.d(TAG, "onBindViewHolder: id:"+movieEntity.id+" pos:"+position);
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
                movieCallback.onLikeClick(movieEntity,position);
            }
        });

    }


    public interface MovieCallback{
        void onClick(MovieEntity movieEntity,int pos);
        void onLikeClick(MovieEntity movieEntity,int pos);
    }

}
