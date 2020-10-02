package com.devparadigam.agrade.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.utils.DemoBase;
import com.devparadigam.agrade.utils.LanguageModel;
import com.devparadigam.agrade.utils.PreferenceUtils;
import com.devparadigam.agrade.utils.StaticData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.snackbar.Snackbar;
import com.konstant.locationmodule.LocationDialog;
import com.konstant.locationmodule.UserLocationModel;

import org.jetbrains.annotations.NotNull;

public class BaseActivity extends DemoBase implements com.konstant.locationmodule.MyLocationListener {

    private ProgressDialog progressDialog;
    GoogleApiClient googleApiClient;
    public PreferenceUtils mPref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = new PreferenceUtils();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageModel.setLocale(base));
    }

    public void checkForLocationPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //  openSetting(2);
                    requestPermissions(StaticData.PERMISSIONS_LOCATION, StaticData.PERMISSIONS_REQUEST_LOCATION);

                } else {
                    requestPermissions(StaticData.PERMISSIONS_LOCATION, StaticData.PERMISSIONS_REQUEST_LOCATION);
                }

            } else {
//                actionCamera();
                //staartLocationTracking();
                requestLocationDialog();
            }
        } else {
//            actionCamera();
            requestLocationDialog();
            // staartLocationTracking();
        }
    }


    GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };
    GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    };

    com.konstant.locationmodule.MyLocationListener loclistner;

    public void setLocListner(com.konstant.locationmodule.MyLocationListener listner) {
        loclistner = listner;
    }


    public void requestLocationDialog() {
        if (googleApiClient == null)
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(connectionFailedListener).build();

        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        LocationDialog locationDialog = new LocationDialog();
                        locationDialog.locationCallback(BaseActivity.this, BaseActivity.this);

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    BaseActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                            Log.e("play loc error ", e.toString());
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        showToast("Please install Google Play Services");
                        break;
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == StaticData.PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length >= 2) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    ;
                //  staartLocationTracking();
                requestLocationDialog();
            } else {
                showToast("Permission required for Location");
            }
        }
    }

    @Override
    protected void saveToGallery() {

    }

   /* void sendLocation(Location location){
        Geocoder geocoder =new Geocoder(BaseActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

            add = add + "," + obj.getCountryName();
            add = add + "," + obj.getCountryCode();
            add = add + "," + obj.getAdminArea();
            add = add + "," + obj.getPostalCode();
            add = add + "," + obj.getSubAdminArea();
            add = add + "," + obj.getLocality();
            add = add + "," + obj.getSubThoroughfare();

            Log.e("IGA location", "Address : "+add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();
            UserLocationModel userLocationModel =new UserLocationModel(obj.getCountryName(), add,location.getLatitude(),location.getLongitude());
            // TennisAppActivity.showDialog(add);
            BaseActivity.this.MyLocation(userLocationModel);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }
    }
*/

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show();
    }

    public void enableLoadingBar(boolean enable) {
        if (enable) {
            loadProgressBar(null, getString(R.string.loading), false);
        } else {
            dismissProgressBar();
        }
    }

    public void showSnackBar(View root, String msg) {
        if (root != null)
            Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show();
    }


    public void loadProgressBar(String title, String message, boolean cancellable) {
        if (progressDialog == null && !this.isFinishing()) {
            progressDialog = ProgressDialog.show(this, title, message, false, cancellable);
            progressDialog.setContentView(R.layout.custom_progress);
            progressDialog.getWindow().setBackgroundDrawable(new
                    ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void dismissProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    public void replaceFragment(@IdRes int container, Fragment fragment, Bundle arguments, String tag) {
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        getSupportFragmentManager().beginTransaction().replace(container, fragment, tag).commitAllowingStateLoss();
    }

    public void replaceFragment1(@IdRes int container, Fragment fragment, Boolean addtoStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addtoStack) {
            ft.replace(container, fragment, fragment.getClass().getSimpleName());
            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        } else
            ft.replace(container, fragment).commit();
    }

    @Override
    public void MyLocation(@NotNull Location location) {

    }

    @Override
    public void MyLocation(@NotNull UserLocationModel userLocationModel) {

    }
}
