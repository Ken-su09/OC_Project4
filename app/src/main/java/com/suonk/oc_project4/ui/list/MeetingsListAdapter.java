package com.suonk.oc_project4.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.suonk.oc_project4.databinding.ItemMeetingBinding;
import com.suonk.oc_project4.ui.OnMeetingEventListener;

public class MeetingsListAdapter extends ListAdapter<MeetingsViewState, MeetingsListAdapter.MeetingViewHolder> {

    private final OnMeetingEventListener callBack;

    public MeetingsListAdapter(OnMeetingEventListener callBack) {
        super(new MeetingsItemCallBack());
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMeetingBinding binding = ItemMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MeetingViewHolder(binding, callBack);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private final ItemMeetingBinding binding;
        private final OnMeetingEventListener callBack;

        public MeetingViewHolder(@NonNull ItemMeetingBinding binding, @NonNull OnMeetingEventListener callBack) {
            super(binding.getRoot());
            this.binding = binding;
            this.callBack = callBack;
        }

        public void onBind(MeetingsViewState meeting) {
            // Put on the ViewModel
            binding.meetingSubject.setText(meeting.getSubject() + " " + meeting.getTime() + " " + meeting.getPlace());
            binding.meetingListOfMails.setText(meeting.getListOfMails().toString());

            binding.getRoot().setOnClickListener(view -> {
                callBack.onMeetingClick(view, meeting.getId());
            });

            binding.deleteButton.setOnClickListener(view -> {
                callBack.onMeetingDelete(meeting.getId());
            });
        }
    }

    public static class MeetingsItemCallBack extends DiffUtil.ItemCallback<MeetingsViewState> {
        @Override
        public boolean areItemsTheSame(@NonNull MeetingsViewState oldUser, @NonNull MeetingsViewState newUser) {
            return oldUser.getId() == newUser.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingsViewState oldUser, @NonNull MeetingsViewState newUser) {
            return oldUser.equals(newUser);
        }
    }
}
