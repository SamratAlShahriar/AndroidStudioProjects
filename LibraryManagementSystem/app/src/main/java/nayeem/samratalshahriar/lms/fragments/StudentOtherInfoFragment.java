package nayeem.samratalshahriar.lms.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nayeem.samratalshahriar.lms.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentOtherInfoFragment extends Fragment {


    public StudentOtherInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_other_info, container, false);
    }

}
