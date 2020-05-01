package com.kashyapmedia.moviesdemo.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kashyapmedia.moviesdemo.databinding.FragmentProfileBinding;
import com.kashyapmedia.moviesdemo.db.entities.ProfileEntity;
import com.kashyapmedia.moviesdemo.repository.Resource;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private FragmentProfileBinding binding;
    private ProfileViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.saveProfileData(binding.inputName.getText().toString().trim(),binding.inputEmail.getText().toString().trim());
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        model.getProfileData().observe(this, new Observer<Resource<ProfileEntity>>() {
            @Override
            public void onChanged(Resource<ProfileEntity> profileEntityResource) {
                switch (profileEntityResource.getStatus()){
                    case SUCCESS:{
                        Log.d(TAG, "onChanged: success "+profileEntityResource.getData());
                        ProfileEntity profileEntity=profileEntityResource.getData();
                        if(profileEntity!=null){
                            String email=profileEntityResource.getData().email;
                            String name=profileEntityResource.getData().name;
                            binding.inputEmail.setText(email);
                            binding.inputName.setText(name);
                            if(email.length()>0){
                                binding.inputEmail.setSelection(email.length());
                            }
                            if(name.length()>0){
                                binding.inputName.setSelection(name.length());
                            }
                        }

                        break;
                    }
                    case ERROR:{
                        Log.d(TAG, "onChanged: error "+profileEntityResource.getMessage());

                        break;
                    }
                    case LOADING:{
                        Log.d(TAG, "onChanged: LOADING");
                        break;
                    }
                }
            }
        });

    }
    @Override
    public void onDestroyView() {
        binding=null;
        super.onDestroyView();
    }
}
