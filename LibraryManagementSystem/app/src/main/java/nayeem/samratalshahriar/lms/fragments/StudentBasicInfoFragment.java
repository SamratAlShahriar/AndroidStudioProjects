package nayeem.samratalshahriar.lms.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import nayeem.samratalshahriar.lms.R;
import nayeem.samratalshahriar.lms.StudentInformationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentBasicInfoFragment extends Fragment {
    Spinner spinner;


    public StudentBasicInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_student_basic_info, container, false);
        Spinner dropDownSession = customView.findViewById(R.id.spSession);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.session, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownSession.setAdapter(adapter);
        return customView;
    }
}
