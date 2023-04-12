package server.service;

import constant.Constant;
import model.Admin;
import model.Employee;
import org.json.JSONArray;
import org.json.JSONObject;
import server.repo.AdminRepo;
import server.repo.EmployeeRepo;

import java.util.concurrent.ConcurrentHashMap;

public class AccountsService
{
    private JSONObject response;

    public JSONObject createAccount(JSONObject request)
    {
        try
        {
            response = new JSONObject();

            if (!request.isEmpty()
                && !request.isNull("type") && !request.isNull("name")
                && !request.isNull("department") && !request.isNull("designation")
                && !request.isNull("email") && !request.isNull("pin"))
            {

                if (request.getString("type").equalsIgnoreCase("admin"))
                {
                    Admin admin = AdminRepo.getInstance().post(request);

                    if (!(admin == null))
                    {

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.FAILED);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.FAILED+": account already exits.");

                    }
                    else
                    {

                        JSONObject data = new JSONObject();

                        data.put("email", request.getString("email"));

                        response.put(Constant.Keywords.DATA, data);

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);
                    }

                }
                else
                {
                    Employee employee = EmployeeRepo.getInstance().post(request);

                    if(!(employee==null)){

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.FAILED);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.FAILED+": account already exits.");

                    }else
                    {

                        JSONObject data = new JSONObject();

                        data.put("email", request.getString("email"));

                        response.put(Constant.Keywords.DATA, data);

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);
                    }

                }
            }
            else
            {
                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.BAD_REQUEST);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.BAD_REQUEST);

            }

            return response;
        }
        catch (Exception exception)
        {

            response = new JSONObject();

            response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.INTERNAL_SERVER_ERROR);

            response.put(Constant.Keywords.MESSAGE, Constant.Message.INTERNAL_SERVER_ERROR);

            return response;

        }
    }

    public JSONObject loginAccount(JSONObject request)
    {
        try
        {

            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("type")
                && !request.isNull("email") && !request.isNull("pin"))
            {
                if (request.getString("type").equalsIgnoreCase("admin"))
                {

                    Admin admin = AdminRepo.getInstance().get(request);

                    if (admin != null && admin.getPin().equals(request.getString("pin")))
                    {

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

                        JSONObject data = new JSONObject();

                        data.put("email", admin.getEmail());

                        data.put("empId", admin.getEmpId());

                        data.put("adminId", admin.getAdminId());

                        response.put(Constant.Keywords.DATA, data);

                    }
                    else
                    {

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.FAILED);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.FAILED + " to login.");

                    }

                }
                else
                {
                    Employee employee = EmployeeRepo.getInstance().get(request);

                    if (employee != null && employee.getPin().equals(request.getString("pin")))
                    {

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

                        JSONObject data = new JSONObject();

                        data.put("email", employee.getEmail());

                        data.put("empId", employee.getEmpId());

                        response.put(Constant.Keywords.DATA, data);

                    }
                    else
                    {

                        response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.FAILED);

                        response.put(Constant.Keywords.MESSAGE, Constant.Message.FAILED + " to login.");

                    }
                }
            }
            else
            {

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.BAD_REQUEST);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.BAD_REQUEST);

            }
            return response;

        }
        catch (Exception exception)
        {


            response = new JSONObject();

            response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.INTERNAL_SERVER_ERROR);

            response.put(Constant.Keywords.MESSAGE, Constant.Message.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public JSONObject getAllEmployees()
    {
        try
        {

            response = new JSONObject();

            ConcurrentHashMap<String, Admin> admins = AdminRepo.getInstance().get();

            JSONArray adminList = new JSONArray();

            admins.forEach((email, admin) -> {

                JSONObject adminDetail = new JSONObject();

                adminDetail.put("email", admin.getEmail());

                adminDetail.put("adminId", admin.getAdminId());

                adminDetail.put("empId", admin.getEmpId());

                adminDetail.put("name", admin.getName());

                adminDetail.put("department", admin.getDepartment());

                adminDetail.put("designation", admin.getDesignation());

                adminList.put(adminDetail);

            });

            response.put("adminList", adminList);


            ConcurrentHashMap<String, Employee> employees = EmployeeRepo.getInstance().get();

            JSONArray employeeList = new JSONArray();

            employees.forEach((email, employee) -> {

                JSONObject employeeDetail = new JSONObject();

                employeeDetail.put("email", employee.getEmail());

                employeeDetail.put("empId", employee.getEmpId());

                employeeDetail.put("name", employee.getName());

                employeeDetail.put("department", employee.getDepartment());

                employeeDetail.put("designation", employee.getDesignation());

                employeeList.put(employeeDetail);

            });

            response.put("employeeList", employeeList);

            response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

            response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

            return response;

        }
        catch (Exception exception)
        {


            response = new JSONObject();

            response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.INTERNAL_SERVER_ERROR);

            response.put(Constant.Keywords.MESSAGE, Constant.Message.INTERNAL_SERVER_ERROR);

            return response;
        }
    }
}
