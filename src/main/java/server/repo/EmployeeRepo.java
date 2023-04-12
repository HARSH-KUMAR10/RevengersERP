package server.repo;

import model.Department;
import model.Designation;
import model.Employee;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

public class EmployeeRepo implements RepoOperation<JSONObject, Employee>
{
    private static EmployeeRepo employeeRepo = null;

    private final ConcurrentHashMap<String, Employee> employees = new ConcurrentHashMap<>();

    private EmployeeRepo()
    {

        Employee employee =
                new Employee("John", Department.DEVELOPMENT, Designation.TRAINEE, "john@mail.in", "1111");
        employees.put("john@mail.in", employee);

        Employee employee1 =
                new Employee("Jack", Department.DESIGN, Designation.SENIOR, "jack@mail.in", "2222");
        employees.put("jack@mail.in", employee1);

    }

    public static EmployeeRepo getInstance()
    {
        if (employeeRepo == null) employeeRepo = new EmployeeRepo();
        return employeeRepo;
    }

    @Override
    public Employee post(JSONObject request)
    {
        String name = request.getString("name");

        Department department = Department.valueOf(request.getString("department"));

        Designation designation = Designation.valueOf(request.getString("designation"));

        String email = request.getString("email");

        String pin = request.getString("pin");

        Employee employee = new Employee(name, department, designation, email, pin);

        return employees.putIfAbsent(email, employee);

    }

    @Override
    public Employee get(JSONObject request)
    {
        return employees.get(request.getString("email"));
    }

    @Override
    public Employee put(JSONObject request)
    {
        return null;
    }

    @Override
    public Employee delete(JSONObject request)
    {
        return null;
    }

    public ConcurrentHashMap<String, Employee> get()
    {
        return employees;
    }
}
