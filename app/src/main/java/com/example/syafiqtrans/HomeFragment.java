package com.example.syafiqtrans;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;


public class HomeFragment extends Fragment  {
    private SliderLayout mDemoSlider;
    private Activity activity;
    PreferenceHelper mPrefHelper;
    private Intent context;
    ImageView img_brosur;
    private AlertDialog.Builder mBuilder;
    ImageView img_brosur1;


    @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.home_fragment, container, false);
            return view;

        }

        @Override
        public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mPrefHelper = new PreferenceHelper(getContext());
            mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
            img_brosur = view.findViewById(R.id.brosur);
            context = context;
            final ImageView image = new ImageView(getActivity());
            image.setImageResource(R.drawable.brosur);
            img_brosur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                    // set title dialog
                    alertDialogBuilder.setTitle("Brosur PO.SyafiqTrans");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setIcon(R.mipmap.ic_launcher)
                            .setView(image)
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {
                                    // jika tombol diklik, maka akan menutup activity ini
                                    dialog.dismiss();
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
                }
            });


            HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
            file_maps.put("Bus Zagan Syafiq Trans",R.drawable.zagan);
            file_maps.put("Bus Gumara Syafiq Transy",R.drawable.gumara);
            file_maps.put("Bus Kramat Syafiq Trans",R.drawable.kramat);
            for(String name : file_maps.keySet()){
                TextSliderView textSliderView = new TextSliderView(getContext());
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener((BaseSliderView.OnSliderClickListener) context);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra",name);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) context);
            }};




