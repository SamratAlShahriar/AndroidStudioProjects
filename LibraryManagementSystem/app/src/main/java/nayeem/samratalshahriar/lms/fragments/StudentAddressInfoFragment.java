package nayeem.samratalshahriar.lms.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import nayeem.samratalshahriar.lms.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAddressInfoFragment extends Fragment {


    public StudentAddressInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_student_address_info, container, false);
        Spinner dropDownDistrict = customView.findViewById(R.id.spDistrict);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.district, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownDistrict.setAdapter(adapter);
        return customView;
    }

}
