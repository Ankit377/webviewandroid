package com.devparadigam.agrade.base;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.utils.PreferenceUtils;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BaseFragment extends Fragment {

    ProgressDialog progressDialog;
    public PreferenceUtils mPref;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPref = new PreferenceUtils();
    }


    public MultipartBody.Part getBodyFromImageFile(File f){
        if (f==null)
            return null;
        else {
            //File file=new File(uri.getPath());

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), f);
            Log.e("ImageFile size", ""+f.length());
            // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            //  RequestBody formBody = new FormBody.Builder().add("search", "Jurassic Park").build();
            MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", f.getName(), requestFile);
            return body;
        }
    }
    public void showToast(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public void showToast(int message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), getString(message), Toast.LENGTH_SHORT).show();
        }
    }

    public void enableLoadingBar(boolean enable) {
        if (enable) {
            loadProgressBar(null, getString(R.string.loading), false);
        } else {
            dismissProgressBar();
        }
    }

    public void loadProgressBar(String title, String message, boolean cancellable) {
        if (progressDialog == null && getActivity() != null){
            progressDialog = ProgressDialog.show(getActivity(), title, message, false, cancellable);
            progressDialog.setContentView(R.layout.custom_progress);
            progressDialog.getWindow().setBackgroundDrawable(new
                    ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    public void dismissProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }


    public void showSnackBar(View view, String msg){
        Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show();
    }

    public void replaceFragment(@IdRes final int container, final Fragment fragment) {
        final FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(container);
        frameLayout.removeAllViewsInLayout();

                getActivity().getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), fragment).addToBackStack(null).commitAllowingStateLoss();
                frameLayout.forceLayout();

    }
    public void replaceFragment1(@IdRes int container, Fragment fragment, Boolean addtoStack) {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        if (addtoStack) {
            ft.replace(container, fragment,fragment.getClass().getSimpleName());
            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        }else
        ft.replace(container, fragment).commit();
    }
}
