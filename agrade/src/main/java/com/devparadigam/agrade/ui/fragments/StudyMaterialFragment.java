package com.devparadigam.agrade.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseFragment;


import com.devparadigam.agrade.databinding.StudyMaterialBinding;

import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.StudyMetarialModel;
import com.devparadigam.agrade.ui.activiries.WebUrlActivity;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudyMaterialFragment extends BaseFragment {

    StudyMaterialBinding binding;
    UserViewModel userViewModel;
    GenericListAdapter<StudyMetarialModel> listAdapter;
    ArrayList<StudyMetarialModel> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding =StudyMaterialBinding.inflate(inflater,container,false);
        String token = StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(getActivity()).getToken();
        setUpViewModel();
        observerStudyMaterial(token);




        return binding.getRoot();
    }

    private void setUpViewModel() {
        UserFactory factory = new UserFactory(new UserRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void observerStudyMaterial(String token) {
        userViewModel.getStudyMaterial(token).observe(this, new Observer<Resource<List<StudyMetarialModel>>>() {
            @Override
            public void onChanged(Resource<List<StudyMetarialModel>> studyMaterialResource) {
                switch (studyMaterialResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (studyMaterialResource.message != null)
                            showSnackBar(binding.getRoot(), studyMaterialResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (studyMaterialResource.status.equals("success"))
                            showToast(studyMaterialResource.message);
                        arrayList = (ArrayList<StudyMetarialModel>) studyMaterialResource.data;

                        listAdapter = new GenericListAdapter<StudyMetarialModel>(arrayList, R.layout.studymetarial_layoutdesign, new GenericListAdapter.OnListItemClickListener<StudyMetarialModel>() {
                            @Override
                            public void onListItemClicked(StudyMetarialModel selItem, @org.jetbrains.annotations.Nullable Object extra, int position) {
                                if (extra != null && extra instanceof View) {
                                    switch (((View) extra).getId()) {
                                        case R.id.card_test_history: {
                                            enableLoadingBar(true);
                                            Intent intent = new Intent(getActivity(), WebUrlActivity.class);
                                            intent.putExtra("pdf",selItem.getFile());
                                            startActivity(intent);
                                            enableLoadingBar(false);

                                        }
                                        break;
                                    }
                                }
                            }
                        });
                        binding.studyRecycler.setAdapter(listAdapter);



                    }
                }
            }
        });

    }


}
