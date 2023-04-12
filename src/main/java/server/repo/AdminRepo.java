package server.repo;

import model.Admin;
import model.Department;
import model.Designation;
import org.json.JSONObject;
import java.util.concurrent.ConcurrentHashMap;

public class AdminRepo implements RepoOperation<JSONObject, Admin>
{
    private static AdminRepo adminRepo = null;

    private final ConcurrentHashMap<String, Admin> admins = new ConcurrentHashMap<>();

    private AdminRepo()
    {

        Admin admin = new Admin("admin", Department.HR, Designation.LEAD, "admin", "1234");

        admins.put("admin", admin);

        Admin admin1 = new Admin("Mia", Department.TESTING, Designation.ASSOCIATE, "mia@mail.in", "3333");

        admins.put("mia@mail.in", admin1);

    }

    public static AdminRepo getInstance()
    {
        if (adminRepo == null) adminRepo = new AdminRepo();
        return adminRepo;
    }

    @Override
    public Admin post(JSONObject request)
    {
        String name = request.getString("name");

        Department department = Department.valueOf(request.getString("department"));

        Designation designation = Designation.valueOf(request.getString("designation"));

        String email = request.getString("email");

        String pin = request.getString("pin");

        Admin admin = new Admin(name, department, designation, email, pin);

        return admins.putIfAbsent(email, admin);

    }

    @Override
    public Admin get(JSONObject request)
    {
        return admins.get(request.getString("email"));
    }

    @Override
    public Admin put(JSONObject request)
    {
        return null;
    }

    @Override
    public Admin delete(JSONObject request)
    {
        return null;
    }

    public ConcurrentHashMap<String, Admin> get()
    {
        return admins;
    }
}
