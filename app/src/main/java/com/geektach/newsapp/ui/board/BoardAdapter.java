package com.geektach.newsapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geektach.newsapp.R;
import com.geektach.newsapp.databinding.ItemPagerBoardBinding;
import com.geektach.newsapp.models.Board;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ArrayList<Board> list;

    public BoardAdapter() {
        list = new ArrayList<>();
        list.add(new Board("Качок Доге и плачущий Чимс", "Мемом с собаками породы сиба-ину пользователи сравнивали настоящий момент и прошлое.", R.drawable.photo1));
        list.add(new Board("Танцующие носильщики гробов", "Танцующие с гробом темнокожие парни были популярны практически весь год.", R.drawable.photo2));
        list.add(new Board("Наташ, ты спишь?", "Мем «Наташ, ты спишь» стал абсолютным хитом в апреле:", R.drawable.photo3));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDisc;
        private Button btn_start;

        private ItemPagerBoardBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_start = itemView.findViewById(R.id.btn_start);
            textTitle = itemView.findViewById(R.id.textTitle);
            btn_start = itemView.findViewById(R.id.btn_start);
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).popBackStack();
                }
            });
        }

        public void bind(int position) {
            Board board = list.get(position);
            binding.imagePager.setImageResource(board.getImage());
            binding.textTitle.setText(board.getTitle());

//            textTitle.setText(board.getTitle());
//            textDisc.setText(board.getTitle());
            if (position == list.size() -1 ) {
                btn_start.setVisibility(View.VISIBLE);
            }else {
                btn_start.setVisibility(View.INVISIBLE);
            }
        }
    }
}