package com.geektach.newsapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektach.newsapp.R;
import com.geektach.newsapp.databinding.FragmentHomeBinding;
import com.geektach.newsapp.interfaces.OnItemClickListener;
import com.geektach.newsapp.models.News;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NewsAdapter adapter;
    private int position;
    private boolean isChanged = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChanged = false;
                open(null);
            }
        });
        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("news");
                Log.e("Home", "text" + news.getTitle());
                if (isChanged) {
                    adapter.updateItem(news, position);
                }
                else {
                    adapter.addItem(news);
                }
            }
        });
        binding.recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                isChanged = true;
                News news = adapter.getItem(position);
                Toast.makeText(requireContext(), news.getTitle(), Toast.LENGTH_SHORT).show();
                open(news);
                HomeFragment.this.position = position;
            }

            @Override
            public void inItemCLongClick(int position) {
                deleteNewsDialog(position);
            }
        });

    }

    private void deleteNewsDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Вы уверены что хотите удалить данную запись?");
        builder.setMessage(adapter.getItem(position).getTitle());
        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.removeItem(position);
            }
        });
        builder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void open(News news) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("hjhv", news);
        navController.navigate(R.id.newsFragment, bundle);
    }
}