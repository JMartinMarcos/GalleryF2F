package com.practica.jmm.galleryf2f.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.practica.jmm.galleryf2f.R;
import com.practica.jmm.galleryf2f.VariablesGlobales;
import com.practica.jmm.galleryf2f.db.BaseDatos;
import com.practica.jmm.galleryf2f.fragments.RecyclerFragment;
import com.practica.jmm.galleryf2f.pojo.Carpetas;
import com.practica.jmm.galleryf2f.pojo.ConstructorCarpetas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sath on 1/05/17.
 */

public class NavAdapterRV extends RecyclerView.Adapter<NavAdapterRV.NavViewHolder>{
    ArrayList<Carpetas> gestorArchivos;
    Activity activity;


    private enum SwipedState {
        SHOWING_PRIMARY_CONTENT,
        SHOWING_SECONDARY_CONTENT
    }

    private List<SwipedState> mItemSwipedStates;


    public NavAdapterRV(ArrayList<Carpetas> gestorArchivos, Activity activity) {
        this.gestorArchivos = gestorArchivos;
        this.activity = activity;
        mItemSwipedStates = new ArrayList<>();
        for (int i = 0; i <= gestorArchivos.size(); i++) {
            mItemSwipedStates.add(i, SwipedState.SHOWING_PRIMARY_CONTENT);
        }
    }
/*** Añade una lista completa de items*/
    public void addAll(ArrayList<Carpetas> lista){
        gestorArchivos.addAll(lista);
        notifyDataSetChanged();
    }

    /***    Permite limpiar todos los elementos del recycler*/
    public void clear(){
        gestorArchivos.clear();
        notifyDataSetChanged();
    }

    @Override
    public NavAdapterRV.NavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         ViewPager v = (ViewPager) LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.cardview_navigator,parent,false);

        ViewPagerAdapterRecycler adapter = new ViewPagerAdapterRecycler();

        ((ViewPager) v.findViewById(R.id.viewPagerRecycler)).setAdapter(adapter);


        return new NavAdapterRV.NavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NavViewHolder holder, final int position) {


        ImageView imgImagen = (ImageView) holder.mView.findViewById(R.id.img_nav);
        TextView ruta= (TextView) holder.mView.findViewById(R.id.text_nav);
        LinearLayout linear= (LinearLayout) holder.mView.findViewById(R.id.cv_navigator);
        ImageButton botonBorrado = (ImageButton) holder.mView.findViewById(R.id.btn1);
        ImageButton backpager = (ImageButton) holder.mView.findViewById(R.id.btn_back);
        final ViewPager viewPager = (ViewPager) holder.mView.findViewById(R.id.viewPagerRecycler);


        final Carpetas carpeta = gestorArchivos.get(position);

        botonBorrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstructorCarpetas carpet = new ConstructorCarpetas(activity);
                BaseDatos db = new BaseDatos(activity);
                carpet.quitarCarpeta(db,Integer.parseInt(carpeta.getId()));
                gestorArchivos.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();

            }
        });
        backpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        Glide.with(activity)
                .load(carpeta.getIcono())
                .into(imgImagen);

        ruta.setText(carpeta.getNombre());
        viewPager.setCurrentItem(1);

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        int previousPagePosition = 0;
        int i=0;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
         if (position == previousPagePosition){
         /*    if(position==0){
                 if(i<=3){
                     i++;
                     return;
                 }
             }else{*/
                 return;
         //    }
             }

            switch (position) {
                case 0:

                   mItemSwipedStates.set(position, SwipedState.SHOWING_SECONDARY_CONTENT);
                  //  viewPager.setCurrentItem(1);

                 break;
                case 1:
                    mItemSwipedStates.set(position, SwipedState.SHOWING_PRIMARY_CONTENT);
                    //viewPager.setCurrentItem(2);

                  break;

            }
            previousPagePosition = position;
           // i=0;
      }

     @Override
     public void onPageSelected(int position) {

      }

       @Override
      public void onPageScrollStateChanged(int state) {

       }
    });

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariablesGlobales.PATH_GALL = carpeta.getPath();
                setFragment();
                DrawerLayout drawerLayout;
                drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
            }
        });

    }

    @Override
    public int getItemCount() {
        return gestorArchivos.size();
    }

    public static class NavViewHolder extends RecyclerView.ViewHolder{
        public View mView;


        public NavViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
    }

    public void setFragment() {
        android.app.FragmentManager fragmentManager;
        android.app.FragmentTransaction fragmentTransaction;
        fragmentManager = activity.getFragmentManager();
        //getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerFragment iRecycler = new RecyclerFragment();
        fragmentTransaction.replace(R.id.content_main, iRecycler);
        fragmentTransaction.commit();
    }

}
