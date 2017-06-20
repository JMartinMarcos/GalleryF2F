package com.practica.jmm.galleryf2f.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.practica.jmm.galleryf2f.R;

/**
 * Created by sath on 10/05/17.
 */

public class ViewPagerAdapterRecycler extends PagerAdapter{

        public Object instantiateItem(ViewGroup collection, int position) {

           // int resto = position % 2;

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.secondaryContentFrameLayout;
                    break;
                case 1:
                    resId = R.id.primaryContentCardView;
                    break;
            }
            return collection.findViewById(resId);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }

}
