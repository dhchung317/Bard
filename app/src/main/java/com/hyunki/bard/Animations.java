package com.hyunki.bard;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animations {
    private static Animation dropImage;
    private static Animation dropTitle;
    private static Animation popUp;

    public static Animation getDropImageAnimation(View v){
        if(dropImage == null){
            dropImage = AnimationUtils.loadAnimation(v.getContext(),R.anim.drop_image);
        }
        return dropImage;
    }

    private static Animation getDropTitleAnimation(View v){
        if(dropTitle == null){
            dropTitle = AnimationUtils.loadAnimation(v.getContext(),R.anim.drop_title);
        }
        return dropTitle;
    }

    private static Animation getPopUpAnimation(View v){
        if(popUp == null){
            popUp = AnimationUtils.loadAnimation(v.getContext(),R.anim.popup);
        }
        return popUp;
    }

    public static void setDropTitleAnimation(View v){
        v.startAnimation(getDropTitleAnimation(v));
    }

    public static void setPopUpAnimation(View v){
        v.startAnimation(getPopUpAnimation(v));
    }
}