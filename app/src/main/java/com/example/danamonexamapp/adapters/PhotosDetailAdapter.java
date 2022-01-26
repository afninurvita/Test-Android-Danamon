package com.example.danamonexamapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.danamonexamapp.R;
import com.example.danamonexamapp.models.Photos;

import java.util.List;

public class PhotosDetailAdapter extends RecyclerView.Adapter<PhotosDetailAdapter.PhotosDetailHolder> {
    private Context mContext;
    private List<Photos> photosList;

    public PhotosDetailAdapter(Context mContext, List<Photos> photosList) {
        this.mContext = mContext;
        this.photosList = photosList;
    }

    @NonNull
    @Override
    public PhotosDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_photos, parent, false);

        return new PhotosDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosDetailHolder holder, int position) {
        Photos photos = photosList.get(position);

        holder.tvId.setText(photos.getId() + ".");
        holder.tvTitle.setText(photos.getTitle());
        holder.tvAlbumId.setText("Album ID: " + photos.getAlbumId());
        holder.tvUrl.setText(photos.getUrl());

//        Glide.with(mContext)
//                .load(photos.getUrl())
//                .error(R.mipmap.ic_launcher)
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.ivUrl);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(mContext).load(photos.getThumbnailUrl()).apply(options).into(holder.ivUrl);
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class PhotosDetailHolder extends RecyclerView.ViewHolder {
        public TextView tvId, tvTitle, tvAlbumId, tvUrl;
        public ImageView ivUrl;

        public PhotosDetailHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAlbumId = itemView.findViewById(R.id.tvAlbumId);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            ivUrl = itemView.findViewById(R.id.ivUrl);
        }
    }
}
