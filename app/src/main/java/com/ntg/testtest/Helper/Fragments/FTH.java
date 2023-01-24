package com.ntg.testtest.Helper.Fragments;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ntg.testtest.R;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class FTH {


    private static final String TAG = "FTH";

    static FragmentManager fm;
    static FragmentTransaction ft;

    public static void addFadeFragmentUpFragment(@IdRes int container, FragmentActivity fa,
                                                 Fragment fragment, String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.FADE);
        ft.add(container, fragment, name);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addFadeFragmentUpFragment(@IdRes int container, FragmentActivity fa,
                                                 @NotNull Class<? extends Fragment> fragment,
                                                 @Nullable Bundle bundle,
                                                 String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.FADE);
        ft.add(container, fragment, bundle, name);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addSlideFragmentUpFragment(@IdRes int container, FragmentActivity fa,
                                                  Fragment fragment, String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.SLIDE);
        ft.add(container, fragment, name);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addSlideLRFragmentUpFragment(@IdRes int container, FragmentActivity fa,
                                                    Fragment fragment, String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.LTR);
        ft.add(container, fragment);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addSlidedFragmentUpFragment(@IdRes int container, FragmentActivity fa,
                                                   Fragment fragment, String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.SLIDE);
        ft.add(container, fragment);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addSlidedFragmentUpFragment(@IdRes int container,
                                                   FragmentActivity fa,
                                                   @NotNull Class<? extends Fragment> fragment,
                                                   @Nullable Bundle bundle,
                                                   String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.SLIDE);
        ft.add(container, fragment, bundle, name);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addToStakeFragment(@IdRes int container, FragmentActivity fa,
                                          Fragment fragment, String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.FADE);
        ft.replace(container, fragment, name);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addToStakeFragment(@IdRes int container,
                                          @NotNull FragmentActivity fa,
                                          @NotNull Class<? extends Fragment> fragment,
                                          @Nullable Bundle bundle,
                                          @Nullable String name) {

        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.FADE);
        ft.replace(container, fragment, bundle, name);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void addToStakeSlideFragment(@IdRes int container, FragmentActivity fa,
                                               Fragment fragment, String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.SLIDE);
        ft.add(container, fragment, name);
        ft.addToBackStack(name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static int getCount(FragmentActivity fa) {
        return fa.getSupportFragmentManager().getBackStackEntryCount();
    }


    public static Fragment getFragmentByTag(FragmentActivity fa, String name) {
        return fa.getSupportFragmentManager().findFragmentByTag(name);
    }

    /**
     * get latest fragment on back Stack
     *
     * @param fragmentActivity FragmentActivity
     * @return fragment with type {@link Fragment}
     */
    public static Fragment getCurrentFragment(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    /**
     * get name of latest fragment on back Stack
     *
     * @param fragmentActivity FragmentActivity
     * @return name fragment on top stack
     */
    public static String getCurrentFragmentName(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        String name = "";
        try {
            name = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        } catch (Exception ignore) {
        }

        return name;
    }

    private static void initAnimation(FragmentTransaction ft, @AnimationFlag int flag) {

        switch (flag) {
            case AnimationFlag.SLIDE: {
                ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
                break;
            }
            case AnimationFlag.FADE:
                ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                break;
            case AnimationFlag.LTR:
                ft.setCustomAnimations(R.anim.slide_left, R.anim.slide_right, R.anim.slide_left, R.anim.slide_right);
                break;
            case AnimationFlag.RTL:
                ft.setCustomAnimations(R.anim.slide_right, R.anim.slide_left, R.anim.slide_right, R.anim.slide_left);
                break;
        }


    }

    private static FragmentTransaction initFM(FragmentActivity fa) {
        return fa.getSupportFragmentManager().beginTransaction();
    }

    public static void popTopStack(FragmentActivity fragmentActivity) {
        fragmentActivity.getSupportFragmentManager().popBackStack();
    }

    public static void popAllStack(FragmentActivity fragmentActivity) {
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }


    public static void replaceFadeFragment(@IdRes int container, FragmentActivity fa,
                                           @NonNull Class<? extends Fragment> fragment,
                                           @Nullable Bundle bundle,
                                           String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.FADE);
        ft.replace(container, fragment, bundle, name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void replaceFadeFragment(@IdRes int container, FragmentActivity fa,
                                           Fragment fragment, String name) {
        ft = initFM(fa);
        initAnimation(ft, AnimationFlag.FADE);
        ft.replace(container, fragment, name);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public static void popo(FragmentActivity fa, String... names) {
        fm = fa.getSupportFragmentManager();
        for (String name : names)
            fm.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void replaceSlideFragment(@IdRes int container, FragmentActivity fa, Fragment fragment) {
        ft = initFM(fa);
        ft.replace(container, fragment);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public interface OnChildDestroy extends Serializable {
        void onChildDestroy(boolean param1Boolean);
    }

    public interface OnChildDestroyData extends Serializable {
        void onChildDestroy(@NotNull Bundle bundle);


    }

    @IntDef({AnimationFlag.FADE,
            AnimationFlag.LTR,
            AnimationFlag.RTL,
            AnimationFlag.SLIDE,})
    private @interface AnimationFlag {
        int FADE = 1;

        int LTR = 2;

        int RTL = 3;

        int SLIDE = 0;
    }

}
