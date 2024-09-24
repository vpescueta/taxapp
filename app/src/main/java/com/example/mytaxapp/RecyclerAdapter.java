package com.example.mytaxapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytaxapp.databinding.UserItemBinding;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private List<User> users;
    private OnUserClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final UserItemBinding binding;

        public ViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User user, OnUserClickListener listener) {
            binding.tvHomeAdminRvName.setText(user.firstName + " " + user.lastName);
            binding.tvHomeAdminRvPhone.setText(user.getPhone());
            binding.tvHomeAdminRvCity.setText(user.getCity());
            binding.tvHomeAdminRvProcessStatus.setText(user.getProcessStatus());

            int cardColor;
            switch (user.processStatus) {
                case "awaited":
                    cardColor = itemView.getContext().getResources().getColor(R.color.awaited_color);
                    break;
                case "failedtoreach":
                    cardColor = itemView.getContext().getResources().getColor(R.color.failedtoreach_color);
                    break;
                case "onboarded":
                    cardColor = itemView.getContext().getResources().getColor(R.color.onboarded_color);
                    break;
                case "inprocess":
                    cardColor = itemView.getContext().getResources().getColor(R.color.inprocess_color);
                    break;
                case "completed":
                    cardColor = itemView.getContext().getResources().getColor(R.color.completed_color);
                    break;

                case "denied":
                    cardColor = itemView.getContext().getResources().getColor(R.color.denied_color);
                    break;
                default:
                    cardColor = itemView.getContext().getResources().getColor(R.color.denied_color);
                    break;
            }
            binding.cvHomeAdminRecycler.setCardBackgroundColor(cardColor);


            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUserClick(user);
                }
            });
        }
    }

    public RecyclerAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UserItemBinding binding = UserItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user, listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public void setOnUserClickListener(OnUserClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public User getUserAtPosition(int position) {
        return users.get(position);
    }

    public void removeUserAtPosition(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public void updateItem(int position, User user) {
        users.set(position, user);
        notifyItemChanged(position);
    }

}