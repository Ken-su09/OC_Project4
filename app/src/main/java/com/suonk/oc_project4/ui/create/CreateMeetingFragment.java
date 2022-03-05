package com.suonk.oc_project4.ui.create;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.suonk.oc_project4.R;
import com.suonk.oc_project4.databinding.FragmentCreateMeetingBinding;
import com.suonk.oc_project4.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateMeetingFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CreateMeetingViewModel viewModel;
    private FragmentCreateMeetingBinding binding;

    private String spinnerText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateMeetingBinding.inflate(inflater, container, false);
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
        setupSpinner();
        setupViewModel();
        emailEditTextOnEditorAction();
        editTextTimePicker();
    }

    private void onCreateMeetingClick() {
        viewModel.createMeeting(binding.subjectEditText.getEditText().getText().toString(),
                spinnerText,
                viewModel.convertTime(binding.startTimeEditText.getText().toString(), binding.endTimeEditText.getText().toString()),
                viewModel.convertChipGroupToListOfString(binding.chipGroup));
    }

    private void setupViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance();
        viewModel = new ViewModelProvider(this, factory).get(CreateMeetingViewModel.class);
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
        spinnerText = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //endregion

    //region ============================================= Chip =============================================

    public void addChipFromEmailEditText() {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        Chip chip = (Chip) inflater.inflate(R.layout.layout_chip_entry, binding.chipGroup, false);
        Editable text = binding.listEmailsEditText.getEditText().getText();
        chip.setText(text.toString());
        binding.chipGroup.addView(chip);
        binding.listEmailsEditText.getEditText().getText().clear();

        chip.setOnCloseIconClickListener(view1 -> {
            binding.chipGroup.removeView(chip);
        });
    }

    public void emailEditTextOnEditorAction() {
        binding.listEmailsEditText.getEditText().setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (viewModel.checkIfEmailValid(binding.listEmailsEditText.getEditText().getText().toString())) {
                    List<String> list = viewModel.convertChipGroupToList(binding.chipGroup);

                    if (list.contains(binding.listEmailsEditText.getEditText().getText().toString())) {
                        Toast.makeText(getContext(), "The email is already entered", Toast.LENGTH_SHORT).show();
                    } else {
                        addChipFromEmailEditText();
                    }
                } else {
                    Toast.makeText(getContext(), "The email is not valid", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });
    }

    //endregion

    //region ============================================= Date =============================================

    private void editTextTimePicker() {
        binding.startTimeEditText.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (timePicker, hourOfDay, minutes) -> {
                binding.startTimeEditText.setText(hourOfDay + "h" + minutes);

            }, 0, 0, true);

            timePickerDialog.show();
        });
        binding.endTimeEditText.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (timePicker, hourOfDay, minutes) -> {
                binding.endTimeEditText.setText(hourOfDay + "h" + minutes);

            }, 0, 0, true);

            timePickerDialog.show();
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.create_meeting_toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.create_new_meeting) {
            String subject = binding.subjectEditText.getEditText().getText().toString();
            String startTime = binding.startTimeEditText.getText().toString();
            String endTime = binding.endTimeEditText.getText().toString();

            if (viewModel.checkIfFieldsNotEmpty(subject, spinnerText, startTime, endTime) &&
                    !viewModel.convertChipGroupToListOfString(binding.chipGroup).isEmpty()) {
                if (viewModel.checkIfTimeToSuperiorThanTimeFrom(startTime, endTime)) {
                    onCreateMeetingClick();
                    popBackStack();
                } else {
                    Toast.makeText(getContext(), "Time Started can't be higher than Time Ended", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "No field should be empty", Toast.LENGTH_LONG).show();
            }
            return true;
        } else if (item.getItemId() == android.R.id.home) {
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