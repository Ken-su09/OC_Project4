package com.suonk.oc_project4.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.suonk.oc_project4.R;
import com.suonk.oc_project4.databinding.FragmentMeetingDetailsBinding;
import com.suonk.oc_project4.ui.ViewModelFactory;

public class MeetingDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private MeetingDetailsViewModel viewModel;
    private FragmentMeetingDetailsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeetingDetailsBinding.inflate(inflater, container, false);
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

        setupViewModel();
        getMeetingFromViewModel(MeetingDetailsFragmentArgs.fromBundle(getArguments()).getId());
        setupActionBar();
        setupSpinner();
        emailEditTextOnEditorAction();
    }

    private void setupViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance();
        viewModel = new ViewModelProvider(this, factory).get(MeetingDetailsViewModel.class);
    }

    private void getMeetingFromViewModel(long id) {
        viewModel.getMeetingDetailsLiveData(id).observe(getViewLifecycleOwner(), meetingDetailsViewState -> {
            binding.subjectInputEditText.setText(meetingDetailsViewState.getSubject());
            binding.spinnerPlace.setSelection(meetingDetailsViewState.getPlace());
            binding.startTimeEditText.setText(meetingDetailsViewState.getStartTime());
            binding.endTimeEditText.setText(meetingDetailsViewState.getEndTime());
            initChip(meetingDetailsViewState);
        });
    }

    //region ============================================ Spinner ===========================================

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.places_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPlace.setAdapter(adapter);
        binding.spinnerPlace.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //endregion

    //region ============================================= Chip =============================================

    public void initChip(MeetingDetailsViewState meeting) {
        String[] emailArray = meeting.getListOfMails().split(",");
        for (String email : emailArray) {
            LayoutInflater inflater = LayoutInflater.from(requireContext());
            Chip chip = (Chip) inflater.inflate(R.layout.layout_chip_entry, binding.chipGroup, false);
            chip.setText(email);
            binding.chipGroup.addView(chip);
        }
    }

    public void emailEditTextOnEditorAction() {
        binding.listEmailsEditText.getEditText().setOnEditorActionListener((textView, actionId, keyEvent) -> {
            return true;
        });
    }

    //endregion

    //region ============================================ Toolbar ===========================================

    private void setupActionBar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bluePrimary));

        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    public void popBackStack() {
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }
}