package com.andyshon.cryptocoins.utils.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.IntDef;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andyshon.cryptocoins.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public interface Router {

    int TRANSACTION_ADD = 0;
    int TRANSACTION_REPLACE = 1;
    int TRANSACTION_SWITCH = 2;

    int ANIM_NONE = -1;
    int ANIM_ALPHA = 0;
    int ANIM_SLIDE = 1;
    int ANIM_MODAL = 2;
    int ANIM_MENU = 3;
    int ANIM_MENU_CROPPED = 4;

    @IntDef({TRANSACTION_ADD, TRANSACTION_REPLACE, TRANSACTION_SWITCH})
    @Retention(RetentionPolicy.SOURCE)
    @interface Transaction {
    }

    @IntDef({ANIM_NONE, ANIM_ALPHA, ANIM_MODAL, ANIM_SLIDE, ANIM_MENU,ANIM_MENU_CROPPED})
    @Retention(RetentionPolicy.SOURCE)
    @interface Animation {
    }

    class ActivityRouteBuilder<T extends Activity> {

        private final Intent intent;

        private Class<T> activityClass;

        public ActivityRouteBuilder(Intent intent) {
            this.intent = intent;
        }

        public ActivityRouteBuilder(Class<T> clazz) {
            this.intent = new Intent();
            this.activityClass = clazz;
        }

        public ActivityRouteBuilder<T> setFlags(int flags) {
            intent.setFlags(flags);
            return this;
        }

        public ActivityRouteBuilder<T> addFlags(int flags) {
            intent.addFlags(flags);
            return this;
        }

        public ActivityRouteBuilder<T> clearBackStack() {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            return this;
        }

        public ActivityRouteBuilder<T> setActivityClass(Class<T> clazz) {
            this.activityClass = clazz;
            return this;
        }

        public ActivityRouteBuilder<T> newTaskAsSingleTop() {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return this;
        }

        public ActivityRouteBuilder<T> putExtra(String key, Object value) {
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            } else if (value instanceof String) {
                intent.putExtra(key, (String) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            } else if (value instanceof Parcelable) {
                intent.putExtra(key, (Parcelable) value);
            } else if (value instanceof ArrayList) {
                intent.putParcelableArrayListExtra(key, (ArrayList<? extends Parcelable>) value);
            }
            return this;
        }

        public ActivityRouteBuilder<T> putStringArrayListExtra(String key, ArrayList<String> values) {
            intent.putStringArrayListExtra(key, values);
            return this;
        }

        public Intent getIntent() {
            return intent;
        }

        public void startForResultFrom(Activity activity, int requestCode) {
            intent.setClass(activity, activityClass);
            activity.startActivityForResult(intent, requestCode);
        }

        public void startForResultFrom(Fragment fragment, int requestCode) {
            if (fragment != null && fragment.getActivity() != null) {
                intent.setClass(fragment.getActivity(), activityClass);
                fragment.startActivityForResult(intent, requestCode);
            }
        }

        public void startFrom(Context context) {
            intent.setClass(context, activityClass);
            context.startActivity(intent);
        }

        public void startFrom(Fragment fragment) {
            if (fragment != null && fragment.getActivity() != null) {
                intent.setClass(fragment.getActivity(), activityClass);
                fragment.startActivity(intent);
            }
        }
    }

    class FragmentTransactionBuilder {

        private final Fragment fragment;

        @Transaction
        private int transactionType = TRANSACTION_REPLACE;

        @Animation
        private int animation = ANIM_NONE;

        private boolean addToBackStack;

        private int containerRes = R.id.container;

        private boolean clearBackStack = false;

        private Bundle args = new Bundle();

        public FragmentTransactionBuilder(Fragment fragment) {
            this.fragment = fragment;
        }

        public FragmentTransactionBuilder setTransactionType(@Transaction int transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public FragmentTransactionBuilder setAnimation(@Animation int animation) {
            this.animation = animation;
            return this;
        }

        public FragmentTransactionBuilder addToBackStack(boolean addToBackStack) {
            this.addToBackStack = addToBackStack;
            return this;
        }

        public FragmentTransactionBuilder setContainerRes(int containerRes) {
            this.containerRes = containerRes;
            return this;
        }

        public FragmentTransactionBuilder clearBackstack() {
            this.clearBackStack = true;
            return this;
        }

        public FragmentTransactionBuilder putParam(String key, Boolean value) {
            args.putBoolean(key, value);
            return this;
        }

        public FragmentTransactionBuilder putParam(String key, int value) {
            args.putInt(key, value);
            return this;
        }

        public FragmentTransactionBuilder putParam(String key, String value) {
            args.putString(key, value);
            return this;
        }

        public FragmentTransactionBuilder putParam(String key, ArrayList value) {
            args.putParcelableArrayList(key, value);
            return this;
        }

        public boolean applyTransaction(FragmentManager manager) {
            if (fragment == null) {
                throw new IllegalStateException("Fragment must be set");
            }

            final FragmentTransaction transaction = manager.beginTransaction();
            final String tag = fragment.getClass().getName();

            fragment.setArguments(args);

            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }

            switch (transactionType) {
                case Router.TRANSACTION_ADD:
                    transaction.add(containerRes, fragment, tag);
                    break;

                case Router.TRANSACTION_REPLACE:
                    transaction.replace(containerRes, fragment, tag);
                    break;

                case Router.TRANSACTION_SWITCH:
                    manager.popBackStack();
                    transaction.replace(containerRes, fragment, tag);
                    break;
            }

            if (clearBackStack) {
                int count = manager.getBackStackEntryCount();
                for (int i = 0; i < count; ++i) {
                    manager.popBackStackImmediate();
                }
            }

            transaction.setReorderingAllowed(true);
            transaction.commitAllowingStateLoss();

            return true;
        }
    }

}