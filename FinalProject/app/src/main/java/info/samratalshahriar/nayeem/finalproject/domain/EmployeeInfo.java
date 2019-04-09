package info.samratalshahriar.nayeem.finalproject.domain;

public class EmployeeInfo {
    private String name;
    private String department;
    private String designation;
    private String email;
    private String phone;
    private String salary;

    public EmployeeInfo() {
    }

    public EmployeeInfo(String name, String department, String designation, String email, String phone, String salary) {
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEmpData(){
        return "\nName :\t\t"+ getName() + "\n\n" +
                "Designation :\t\t"+ getDesignation() + "\n\n" +
                "Department :\t\t"+ getDepartment() + "\n\n" +
                "Salary :\t\t"+ getSalary() + "\n\n" +
                "Email :\t\t"+ getEmail() + "\n\n" +
                "Phone :\t\t"+ getPhone();
    }
}
