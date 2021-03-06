package com.suonk.oc_project4.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.suonk.oc_project4.R;
import com.suonk.oc_project4.databinding.FragmentMeetingsListBinding;
import com.suonk.oc_project4.ui.OnMeetingEventListener;
import com.suonk.oc_project4.ui.ViewModelFactory;

public class MeetingsListFragment extends Fragment implements OnMeetingEventListener {

    private MeetingsViewModel viewModel;
    private MeetingsListAdapter listAdapter;
    private FragmentMeetingsListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = ViewModelFactory.getInstance();
        viewModel = new ViewModelProvider(this, factory).get(MeetingsViewModel.class);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeetingsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupActionBar();
        onFabClickListener();
        getMeetingsListFromViewModel();
    }

    private void setupActionBar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bluePrimary));
        binding.toolbar.setTitleTextColor(AppCompatResources.getColorStateList(requireActivity(), R.color.white));
        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Ma r??u");
        }
    }

    public void getMeetingsListFromViewModel() {
        listAdapter = new MeetingsListAdapter(this);

        viewModel.getAllMeetings().observe(getViewLifecycleOwner(), meetings -> {
            listAdapter.submitList(meetings);
        });
        binding.meetingsRv.setAdapter(listAdapter);
        binding.meetingsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.meetingsRv.setHasFixedSize(true);
    }

    private void onFabClickListener() {
        binding.addMeeting.setOnClickListener(view -> {
            navigationToAnotherFragment(view, MeetingsListFragmentDirections.actionMeetingsListFragmentToCreateMeetingFragment());
        });
    }

    private void navigationToAnotherFragment(View view, NavDirections action) {
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onMeetingClick(View view, long id) {
        navigationToAnotherFragment(view, (NavDirections) MeetingsListFragmentDirections.actionMeetingsListFragmentToMeetingDetailsFragment(id));
    }

    @Override
    public void onMeetingDelete(long id) {
        viewModel.onMeetingDelete(id);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_meetings_toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() != R.id.filter_by_place && item.getItemId() != R.id.filter_by_date) {
            checkItemId(item.getItemId());
            item.setChecked(true);
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkItemId(Integer itemId) {
        if (itemId == R.id.place_peach) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.place_mario) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.place_luigi) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.place_bowser) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.place_toad) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.place_yoshi) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.place_daisy) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.place_donkey_kong) {
            viewModel.setFilterPlaceLiveData(itemId);
        } else if (itemId == R.id.no_filter_place) {
            viewModel.setFilterPlaceLiveData(itemId);
        }

        if (itemId == R.id.date_under_8) {
            Log.i("filterDate", "1 : under 8");
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_8_under_10) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_10_under_12) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_12_under_14) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_14_under_16) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_16_under_18) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_18_under_20) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_20_under_22) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.date_over_22_under_00) {
            viewModel.setFilterDateLiveData(itemId);
        } else if (itemId == R.id.no_filter_date) {
            viewModel.setFilterDateLiveData(itemId);
        }
    }
}