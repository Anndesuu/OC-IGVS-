package com.example.instructorsgradeviewsystem;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class MasterListAdapter extends RecyclerView.Adapter<ImageViewHolder>{
    private Context context;
    private List<Model> imagelist;

    public MasterListAdapter(Context context, List<Model> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ml_recview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Model model = imagelist.get(position);
        String imageUrl = model.getImage();
        Glide.with(context).load(imagelist.get(position).getImage()).apply(requestOptions).into(holder.imageview);


    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }
}
class ImageViewHolder extends RecyclerView.ViewHolder{
    ImageView imageview;
    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageview = itemView.findViewById(R.id.image_view);
    }



}
