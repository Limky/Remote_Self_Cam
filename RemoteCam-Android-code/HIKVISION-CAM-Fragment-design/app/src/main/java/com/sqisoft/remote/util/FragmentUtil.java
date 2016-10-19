package com.sqisoft.remote.util;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sqisoft.remote.fragment.FragmentBase;
import com.sqisoft.remote.fragment.FragmentMain;
import com.sqisoft.remote.view.TitleView;

public class FragmentUtil {
    private static final String TAG = "FragmentUtil";
 
    private static int defaultContainer = 0;
    private static FragmentManager defaultFragmentManager = null;
    private static TitleView defaultTitleView = null;

    public static void init(int containerResId, FragmentManager manager, TitleView titleView) {
        defaultContainer = containerResId;
        defaultFragmentManager = manager;
        defaultTitleView = titleView;
    }

    public static void addFragment(FragmentBase fragmentType) {
        addFragment(defaultFragmentManager, fragmentType, null);
    }

    public static void addFragment(FragmentBase fragmentType, Bundle args) {
        addFragment(defaultFragmentManager, fragmentType, args);
    }

    public static void addFragment(FragmentManager manager, FragmentBase fragmentType) {
        addFragment(manager, fragmentType, null);
    }
    
    public static FragmentManager getFragmentManager() {
        return defaultFragmentManager;
    }

    public static void addFragment(FragmentManager manager, FragmentBase fragment, Bundle args) {
            if(args != null) {
                fragment.setArguments(args);
            }
            FragmentTransaction transaction = manager.beginTransaction();
//                if(fragmentType.playAnimation())
//                    transaction.setCustomAnimations(R.anim.rtoc, R.anim.ctol, R.anim.ltoc, R.anim.ctor);
            if(defaultTitleView != null) {
            }
            transaction.replace(defaultContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

    }

    /**
     * 메인화면으로 이동한다.
     */
    public static void goMain(FragmentManager manager) {
    	if (manager.getFragments() != null) {
    		// DialogFragment 가 있으면 닫아 주자.
    		for (Fragment f : manager.getFragments()) {
    			if(f instanceof DialogFragment) {
    				((DialogFragment) f).dismiss();
    			}
    		}
    	}

        if(manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            try{
            	manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }catch (Exception e){
            	Log.e(TAG, e.getMessage());
            }
        }
    }

    public static void addRootFragment(FragmentBase fragment) {
        goMain(defaultFragmentManager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(defaultFragmentManager, fragment, null);
    }

    public static void addRootFragment(FragmentBase fragment, Bundle args) {
        goMain(defaultFragmentManager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(defaultFragmentManager, fragment, args);
    }

    public static void addRootFragment(FragmentManager manager, FragmentBase fragment) {
        goMain(manager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(manager, fragment, null);
    }

    public static void addRootFragment(FragmentManager manager, FragmentBase fragment, Bundle args) {
        goMain(manager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(manager, fragment, args);
    }

    public static void reflash() {
        reflash(defaultFragmentManager);
    }

    /**
     * 화면을 갱신한다.
     */
    public static void reflash(FragmentManager manager) {

        Fragment currentFragment = manager.getFragments().get(manager.getBackStackEntryCount());
        FragmentTransaction fragTransaction = manager.beginTransaction();
        fragTransaction.detach(currentFragment);
        fragTransaction.attach(currentFragment);
        fragTransaction.commit();
    }

    public static void goBack() {
        goBack(defaultFragmentManager);
    }

    /**
     * 이전화면으로 넘어 간다.
     */
    public static void goBack(FragmentManager manager) {
        if(manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate();
        }
        // if (fm.getBackStackEntryCount() == 0)
        // changeLayoutView(true);
    }

    public static void trace() {
        Log.d("FragmentUtil", "Count:" + defaultFragmentManager.getBackStackEntryCount() + ", "
                + defaultFragmentManager.getFragments().size());
        for (int i = 0; i < defaultFragmentManager.getFragments().size(); i++) {
            if(defaultFragmentManager.getFragments().get(i) != null)
                Log.d("FragmentUtil", defaultFragmentManager.getFragments().get(i).toString() + " "
                        + defaultFragmentManager.getFragments().get(i).getTag());
            else
                Log.d("FragmentUtil", "null");
        }
    }
}
