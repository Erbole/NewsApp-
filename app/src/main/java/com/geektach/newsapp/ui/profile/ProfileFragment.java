package com.geektach.newsapp.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.geektach.newsapp.Prefs;
import com.geektach.newsapp.R;
import com.geektach.newsapp.databinding.FragmentProfileBinding;
import com.google.android.gms.cast.framework.media.ImagePicker;

import java.io.File;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private Prefs prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageView.setOnClickListener(view1 -> mGetContent.launch("image/*"));
        Prefs prefs = new Prefs(requireContext());

        binding.etName.setText(prefs.isEditText());
        if (prefs.isImageView() != null) {
            Glide.with(binding.imageView)
                    .load(prefs.isImageView())
                    .circleCrop()
                    .into(binding.imageView);
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    Prefs prefs = new Prefs(requireContext());
                    if (uri != null) {
                        Glide.with(binding.imageView)
                                .load(uri)
                                .circleCrop()
                                .into(binding.imageView);
                        prefs.saveImageView(String.valueOf(uri));
                    }
                }
            });

    @Override
    public void onDestroy() {
        prefs.saveEditText(binding.etName.getText().toString());
        super.onDestroy();
    }
}