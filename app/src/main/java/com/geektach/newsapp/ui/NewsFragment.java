package com.geektach.newsapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.geektach.newsapp.Prefs;
import com.geektach.newsapp.R;
import com.geektach.newsapp.databinding.FragmentHomeBinding;
import com.geektach.newsapp.databinding.FragmentNewsBinding;
import com.geektach.newsapp.models.News;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private News news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
  }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news = (News) requireArguments().getSerializable("hjhv");
        if (news != null) binding.editText.setText(news.getTitle());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        Bundle bundle = new Bundle();
        String text = binding.editText.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(requireContext(), "Введите текст", Toast.LENGTH_SHORT).show();
            return;
        }if (news == null) {
            news = new News(text, System.currentTimeMillis());
        } else {
            news.setTitle(text);
        }
        bundle.putSerializable("news", news);
        getParentFragmentManager().setFragmentResult("rk_news", bundle);
        close();
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}