package com.example.uman_android_project.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uman_android_project.MainActivity;
import com.example.uman_android_project.R;

import static com.example.uman_android_project.MainActivity.currentDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTreeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddTreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTreeFragment newInstance(String param1, String param2) {
        AddTreeFragment fragment = new AddTreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Spinner spinner_category;
    private Spinner spinner_size;
    private EditText nameInput, commentInput;
    private TextView dateInput;
    private Button chooseDate, addPhoto, submitForm;

    private String treeName, treeCategory, treeSize, treePosition, treePlantDate, treePhoto, treeComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_tree, container, false);

        spinner_category = view.findViewById(R.id.treeCategory);
        spinner_size = view.findViewById(R.id.treeSize);

        ArrayAdapter spinnerCategoryAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.tree_category, android.R.layout.simple_spinner_item);
        spinner_category.setAdapter(spinnerCategoryAdapter);
        ArrayAdapter spinnerSizeAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.tree_size, android.R.layout.simple_spinner_item);
        spinner_size.setAdapter(spinnerSizeAdapter);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                treeCategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                treeSize = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nameInput = view.findViewById(R.id.treeName);

        commentInput = view.findViewById(R.id.plantComment);


        //wait to update after finishing functions
        treePosition = "";
        treePhoto = "";

        dateInput = view.findViewById(R.id.plantDate);
        dateInput.setText(currentDate);
        chooseDate = view.findViewById(R.id.chooseDate);
        addPhoto = view.findViewById(R.id.addPhoto);
        submitForm = view.findViewById(R.id.submitForm);

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View viewDatePick = getLayoutInflater().inflate(R.layout.date_pick, null);
                DatePicker datePicker = viewDatePick.findViewById(R.id.date_picker);
                datePicker.setCalendarViewShown(false);
                builder.setView(viewDatePick);
                builder.setTitle("Please choose plant date");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth()+1;
                        int dayOfMonth = datePicker.getDayOfMonth();
                        dateInput.setText(dayOfMonth + "/" + month + "/" + year);
                        treePlantDate = dateInput.getText().toString();
                        chooseDate.setText("Modify");
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        treePlantDate = dateInput.getText().toString();

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treeName = nameInput.getText().toString();
                treeComment = commentInput.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View viewSubmit = getLayoutInflater().inflate(R.layout.form_confirm, null);
                TextView name_c, category_c, size_c, date_c;
                name_c = viewSubmit.findViewById(R.id.name);
                category_c = viewSubmit.findViewById(R.id.category);
                size_c = viewSubmit.findViewById(R.id.size);
                date_c = viewSubmit.findViewById(R.id.date);
                name_c.setText("Name: " + treeName);
                category_c.setText("Category: " + treeCategory);
                size_c.setText("Size: " + treeSize);
                date_c.setText("Date: " + treePlantDate);
                builder.setView(viewSubmit);
                builder.setTitle("Confirm your submit");
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //submit this form
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

}