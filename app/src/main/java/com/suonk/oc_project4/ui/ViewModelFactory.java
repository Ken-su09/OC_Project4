package com.suonk.oc_project4.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.suonk.oc_project4.data.meetings.MeetingRepository;
import com.suonk.oc_project4.ui.create.CreateMeetingViewModel;
import com.suonk.oc_project4.ui.details.MeetingDetailsViewModel;
import com.suonk.oc_project4.ui.list.MeetingsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    @NonNull
    private final MeetingRepository repository;

    public ViewModelFactory(@NonNull MeetingRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingsViewModel.class)) {
            return (T) new MeetingsViewModel(repository);
        } else if (modelClass.isAssignableFrom(CreateMeetingViewModel.class)) {
            return (T) new CreateMeetingViewModel(repository);
        } else {
            return (T) new MeetingDetailsViewModel(repository);
        }
    }

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                factory = new ViewModelFactory(new MeetingRepository());
            }
        }

        return factory;
    }
}
