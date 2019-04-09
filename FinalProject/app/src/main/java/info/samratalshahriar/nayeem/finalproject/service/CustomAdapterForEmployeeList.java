package info.samratalshahriar.nayeem.finalproject.service;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.samratalshahriar.nayeem.finalproject.R;
import info.samratalshahriar.nayeem.finalproject.domain.EmployeeInfo;

public class CustomAdapterForEmployeeList extends BaseAdapter{
    TextView name, designation, department, email, phone, salary;
    ArrayList<EmployeeInfo> empArrayList;
    Context context;

    public CustomAdapterForEmployeeList(ArrayList<EmployeeInfo> empArrayList, Context context) {
        this.empArrayList = empArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return empArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.custom_list_for_emp, null);

        name = customView.findViewById(R.id.listShowEmpName);
        designation = customView.findViewById(R.id.listShowEmpDesignation);
        department =customView.findViewById(R.id.listShowEmpDepartment);
        email =customView.findViewById(R.id.listShowEmpEmail);
        phone =customView.findViewById(R.id.listShowEmpPhone);
        salary =customView.findViewById(R.id.listShowEmpSalary);

        name.setText(empArrayList.get(position).getName());
        designation.setText(empArrayList.get(position).getDesignation());
        department.setText(empArrayList.get(position).getDepartment());
        email.setText(empArrayList.get(position).getEmail());
        phone.setText(empArrayList.get(position).getPhone());
        salary.setText(empArrayList.get(position).getSalary());

        customView.setBackgroundColor(position % 2 == 0 ? Color.parseColor("#92e7cf") : Color.parseColor("#82d5e7"));

        return customView;
    }

}
