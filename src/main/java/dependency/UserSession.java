package dependency;

public class UserSession
{

    private final String empId;

    private final String adminId;

    private final String type;

    private final String email;

    public UserSession(String empId, String adminId, String type, String email)
    {
        this.empId = empId;

        this.adminId = adminId;

        this.type = type;

        this.email = email;
    }

    public String getEmpId()
    {
        return empId;
    }

    public String getAdminId()
    {
        return adminId;
    }

    public String getType()
    {
        return type;
    }

    public String getEmail()
    {
        return email;
    }

}
