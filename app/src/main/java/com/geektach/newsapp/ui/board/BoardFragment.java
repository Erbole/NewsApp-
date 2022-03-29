package com.geektach.newsapp.ui.board;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektach.newsapp.MainActivity;
import com.geektach.newsapp.Prefs;
import com.geektach.newsapp.R;
import com.geektach.newsapp.databinding.FragmentBoardBinding;
import com.geektach.newsapp.databinding.FragmentProfileBinding;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class BoardFragment extends Fragment {
    private FragmentBoardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BoardAdapter adapter = new BoardAdapter();
        binding.viewPager.setAdapter(adapter);
        binding.dotsIndicator.setViewPager2(binding.viewPager);
        BoardAdapter boardAdapter = new BoardAdapter();
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2){
                    binding.tvSkip.setVisibility(View.INVISIBLE);
                }else
                {
                    binding.tvSkip.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateUp();
            }
        });
    }

    public void navigateUp() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardState();
        NavController navController = Navigation.findNavController((Activity) requireContext(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}