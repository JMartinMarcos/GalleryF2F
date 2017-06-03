package com.practica.jmm.galleryf2f.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.practica.jmm.galleryf2f.FullscreenActivity;
import com.practica.jmm.galleryf2f.R;
import com.practica.jmm.galleryf2f.pojo.Carpetas;
import com.practica.jmm.galleryf2f.pojo.Foto;
import java.util.ArrayList;

/**
 * Created by sath on 17/03/17.
 */

public class GallAdapter extends RecyclerView.Adapter<GallAdapter.GallViewHolder> {

    ArrayList<Foto> fotos;
    Activity activity;


    public GallAdapter(ArrayList<Foto> fotos, Activity activity) {
        this.fotos = fotos;
        this.activity = activity;
    }

    @Override
    public GallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new GallViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GallViewHolder holder, int position) {
        final Foto foto = fotos.get(position);
        final int indexFoto = position;
        String ruta = foto.getImagen();
        Glide.with(activity)
                .load(ruta)
                .into(holder.imgImagen);
        holder.imgImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(activity, FullscreenActivity.class);
                Intent intent = new Intent(activity, FullscreenActivity.class);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("fotos", fotos);
                bundle.putInt("position",indexFoto);
                intent.putExtras(bundle);


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,"").toBundle());

                }else {
                    activity.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    public static class GallViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgImagen;

        public GallViewHolder(View itemView) {
            super(itemView);
            imgImagen = (ImageView) itemView.findViewById(R.id.imageCard);
        }
    }

    public void updateData(ArrayList<Foto> lista){
        fotos.clear();
        fotos.addAll(lista);
        notifyDataSetChanged();
    }

}
