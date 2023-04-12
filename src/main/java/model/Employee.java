package model;

public class Employee
{
    private static int count = 1;

    private final String empId;

    private final String name;

    private final Department department;

    private final Designation designation;

    private final String email;

    private final String pin;

    public Employee(String name, Department department, Designation designation, String email, String pin)
    {

        this.empId = "E" + (count++);

        this.name = name;

        this.department = department;

        this.designation = designation;

        this.email = email;

        this.pin = pin;

    }

    public String getEmpId()
    {
        return empId;
    }

    public String getName()
    {
        return name;
    }

    public Department getDepartment()
    {
        return department;
    }

    public Designation getDesignation()
    {
        return designation;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPin()
    {
        return pin;
    }
}
