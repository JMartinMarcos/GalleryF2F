package com.practica.jmm.galleryf2f.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.practica.jmm.galleryf2f.fragments.FotoFragment;
import com.practica.jmm.galleryf2f.pojo.Foto;
import java.util.ArrayList;

/**
 * Created by sath on 4/05/17.
 */

public class MyPageAdapter extends FragmentPagerAdapter {

    ArrayList<Foto> fotos;

    public MyPageAdapter(FragmentManager fm, ArrayList<Foto> fotos) {
        super(fm);
        this.fotos = fotos;
    }

    @Override
    public Fragment getItem(int position) {
        return FotoFragment.newInstance(fotos.get(position),position);
    }

    @Override
    public int getCount() {
        return fotos.size();
    }
}
