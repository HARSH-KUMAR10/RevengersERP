package model;

public class Admin extends Employee
{
    private static int count=1;
    private final String adminId;

    public Admin(String name, Department department, Designation designation, String email, String pin)
    {
        super( name, department, designation, email, pin);

        this.adminId = "A"+count++;
    }

    public String getAdminId()
    {
        return adminId;
    }
}
