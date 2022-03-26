package com.geektach.newsapp.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektach.newsapp.R;
import com.geektach.newsapp.databinding.ItemNewsBinding;
import com.geektach.newsapp.interfaces.OnItemClickListener;
import com.geektach.newsapp.models.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(Color.GRAY);
        } else {
            holder.binding.getRoot().setBackgroundColor(Color.DKGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(list.indexOf(news));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public News getItem(int position) {
        return list.get(position);
    }
    public void updateItem(News news, int position) {
        list.set(position, news);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;

        public ViewHolder(@NonNull ItemNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putString("key_text", binding.textTitle.getText().toString());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.inItemCLongClick(getAdapterPosition());
                    new AlertDialog.Builder(view.getContext()).setTitle("Удаление")
                            .setMessage("Вы точно хотите удалить?")
                            .setNegativeButton("Нет", null)
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(view.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                                    list.remove(getAdapterPosition());
                                    notifyDataSetChanged();
//                                    notifyItemRangeChanged(0, 0);
                                }
                            }).show();
                    return true;
                }
            });
        }

        public void bind(News news) {
            binding.textTitle.setText(news.getTitle());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss, dd MMM yyyy", Locale.ROOT);
            String date = String.valueOf(simpleDateFormat.format(news.getCreatedAt()));
            binding.tvTimes.setText(date);
        }
    }
}
