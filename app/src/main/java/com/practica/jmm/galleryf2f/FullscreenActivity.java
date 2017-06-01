package com.practica.jmm.galleryf2f;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import com.practica.jmm.galleryf2f.adapter.MyPageAdapter;
import com.practica.jmm.galleryf2f.pojo.Foto;
import java.io.File;
import java.util.ArrayList;
import butterknife.Bind;
import android.app.AlertDialog;
import android.widget.Toast;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends BaseActivity {

    @Bind(R.id.viewppager) ViewPager viewPager;

    ArrayList<Foto> fotos;
    int position = 0;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(500);
            getWindow().setEnterTransition(slide);
        }

        Bundle bundle = getIntent().getExtras();

        fotos = bundle.getParcelableArrayList("fotos");
        position = bundle.getInt("position");

        adapterViewPager = new MyPageAdapter(getSupportFragmentManager(),fotos);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(position);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fullscreen;
    }

    public void compartirImagen(View view){

        int imagePos = viewPager.getCurrentItem();

/***** COMPARTIR IMAGEN *****/
        try {
            File file = new File(fotos.get(imagePos).getImagen());

            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrarImagen(final View view) {
        final View viewf = view;
        final int imagePos = viewPager.getCurrentItem();
        final File file = new File(fotos.get(imagePos).getImagen());

        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(FullscreenActivity.this);
        alertDialog1.setTitle("Borrar:");
        alertDialog1.setMessage("De verdad quieres borrar la imagen?");
        alertDialog1.setCancelable(true);
        alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which){

                try {
                    if(file.exists()) {

                        if (file.delete()) {

                            //     viewPager.removeViewAt(imagePos);
                            fotos.remove(imagePos);
                            int indx = imagePos - 1;
                            Intent intent = new Intent(FullscreenActivity.this, FullscreenActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("fotos", fotos);
                            bundle.putInt("position", indx);
                            intent.putExtras(bundle);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "No se pudo borrar: " + file.getName(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Fichero no existe: " + file.getName(),
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which){

                Toast.makeText(getApplicationContext(), getString(R.string.cancelar) + file.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
        alertDialog1.show();

    }

    public void editarImagen(View view){
        int imagePos = viewPager.getCurrentItem();
        File file = new File(fotos.get(imagePos).getImagen());

        Intent editIntent = new Intent(Intent.ACTION_EDIT);
        editIntent.setDataAndType( Uri.fromFile(file), "image/*");
        editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(Intent.createChooser(editIntent, null));
    }
}
