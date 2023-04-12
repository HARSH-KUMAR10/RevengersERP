package view;

import constant.Constant;
import dependency.SocketControllers;
import dependency.UserSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.Socket;

public class AccountView
{

    private String processRequest(JSONObject request)
    {

        try
        {

            SocketControllers socketControllers = new SocketControllers(new Socket(Constant.Dependencies.IP, Constant.Dependencies.PORT));

            request.put("clientAddress", socketControllers.getSocket().getLocalSocketAddress());

            socketControllers.getWriter().println(request);

            return socketControllers.getReader().readLine();

        }
        catch (Exception exception)
        {
            return "{}";
        }

    }

    public void signup(BufferedReader userInput)
    {
        try
        {

            System.out.println("Enter email: ");

            String email = userInput.readLine();

            System.out.println("Enter pin: ");

            String pin = userInput.readLine();

            System.out.println("Enter name: ");

            String name = userInput.readLine();

            System.out.println("1. Admin\t\t2. Employee\nEnter choice:");

            String type = userInput.readLine().equalsIgnoreCase("1") ? "admin" : "emp";

            System.out.println("1. HR\t\t2. DESIGN\t\t3. DEVELOPMENT\t\t4. TESTING\nEnter choice: ");

            String department;

            switch (userInput.readLine())
            {

                case "1" -> department = "HR";

                case "2" -> department = "DESIGN";

                case "3" -> department = "DEVELOPMENT";

                case "4" -> department = "TESTING";

                default -> department = "DEVELOPMENT";

            }

            System.out.println("1. LEAD\t\t2. SENIOR\t\t3. ASSOCIATE\t\t4. TRAINEE\nEnter choice: ");

            String designation;

            switch (userInput.readLine())
            {

                case "1" -> designation = "LEAD";

                case "2" -> designation = "SENIOR";

                case "3" -> designation = "ASSOCIATE";

                case "4" -> designation = "TRAINEE";

                default -> designation = "TRAINEE";

            }

            JSONObject request = new JSONObject();

            request.put("route", Constant.Route.ACCOUNT);

            request.put("operation", Constant.Operation.SIGNUP);

            request.put("email", email);

            request.put("pin", pin);

            request.put("name", name);

            request.put("type", type);

            request.put("department", department);

            request.put("designation", designation);

            JSONObject response = new JSONObject(processRequest(request));

            System.out.println(Constant.Message.DIVIDER);

            if (!response.isEmpty() && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {
                JSONObject data = response.getJSONObject("data");

                System.out.println("Client created: ");

                System.out.println("Email: " + data.getString("email"));

            }
            else
            {
                System.out.println("Client error: " +
                                   (response.isNull("message") ? "bad request" : response.getString("message")));
            }

            System.out.println(Constant.Message.DIVIDER);
        }
        catch (Exception exception)
        {
            System.out.println("Client error: please restart");
        }
    }

    public void showAllUser()
    {
        try
        {

            JSONObject request = new JSONObject();

            request.put("route", Constant.Route.ACCOUNT);

            request.put("operation", Constant.Operation.ALL_DETAIL);

            JSONObject response = new JSONObject(processRequest(request));

            System.out.println(Constant.Message.DIVIDER);

            if (!response.isEmpty() && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {

                JSONArray adminList = new JSONArray(response.getJSONArray("adminList"));

                System.out.println("EmpId\t\tName\t\t\tEmail\t\t\tDepartment\t\tDesignation\t\tAdminId");

                for (int iterator = 0; iterator < adminList.length(); iterator++)
                {
                    JSONObject admin = adminList.getJSONObject(iterator);

                    System.out.println(admin.getString("empId") + "\t\t\t" + admin.getString("name") + "\t\t\t"
                                       + admin.getString("email") + "\t\t\t" + admin.getString("department") + "\t\t\t\t"
                                       + admin.getString("designation") + "\t\t\t" + admin.getString("adminId"));
                }

                JSONArray employeeList = new JSONArray(response.getJSONArray("employeeList"));

                System.out.println(Constant.Message.DIVIDER);

                System.out.println("EmpId\t\tName\t\t\tEmail\t\t\tDepartment\t\tDesignation");

                for (int iterator = 0; iterator < employeeList.length(); iterator++)
                {
                    JSONObject employee = employeeList.getJSONObject(iterator);

                    System.out.println(employee.getString("empId") + "\t\t\t" + employee.getString("name") + "\t\t\t"
                                       + employee.getString("email") + "\t\t\t" + employee.getString("department") + "\t\t\t\t"
                                       + employee.getString("designation"));
                }

            }
            else
            {
                System.out.println("Client error: " + (response.isNull("message") ? "bad request" : response.getString("message")));
            }

            System.out.println(Constant.Message.DIVIDER);

        }
        catch (Exception exception)
        {
            System.out.println("Client error: please restart");
        }
    }

    public void login(BufferedReader userInput)
    {
        try
        {
            System.out.println("Enter email: ");

            String email = userInput.readLine();

            System.out.println("Enter pin: ");

            String pin = userInput.readLine();

            System.out.println("1. Admin\t\t2. Employee\nEnter choice:");

            String type = userInput.readLine().equalsIgnoreCase("1") ? "admin" : "emp";

            JSONObject request = new JSONObject();

            request.put("route", Constant.Route.ACCOUNT);

            request.put("operation", Constant.Operation.LOGIN);

            request.put("email", email);

            request.put("pin", pin);

            request.put("type", type);

            JSONObject response = new JSONObject(processRequest(request));


            if (!response.isEmpty()
                && !response.isNull(Constant.Keywords.STATUS_CODE)
                && !response.isNull(Constant.Keywords.MESSAGE)
                && (response.getInt(Constant.Keywords.STATUS_CODE) == Constant.StatusCode.SUCCESS))
            {

                System.out.println(Constant.Message.DIVIDER);

                System.out.println(response.getString(Constant.Keywords.MESSAGE));

                System.out.println(Constant.Message.DIVIDER);

                UserSession userSession;

                JSONObject data = response.getJSONObject("data");

                if (!data.isNull("adminId"))
                {

                    userSession = new UserSession(data.getString("empId"), data.getString("adminId"),
                            "admin", data.getString("email"));
                }
                else
                {
                    userSession = new UserSession(data.getString("empId"), "Ax",
                            "emp", data.getString("email"));
                }

                boolean loopFlag = true;

                while (loopFlag)
                {

                    System.out.println("0. Logout\n1. Attendance section\n2. Leave section");

                    if (userSession.getType().equalsIgnoreCase("admin"))
                    {
                        System.out.println("""
                                3. Create new user
                                4. Show all users
                                5. Check user attendance
                                6. Show all leaves
                                7. Update leave""");
                    }

                    System.out.println("Enter you choice: ");

                    if (userSession.getType().equalsIgnoreCase("admin"))
                    {

                        switch (userInput.readLine())
                        {

                            case "0" ->
                            {
                                loopFlag = false;
                                userSession = null;
                            }

                            case "1" ->
                            {
                                AttendanceView attendanceView = new AttendanceView();
                                attendanceView.attendanceMenu(userInput, userSession);
                            }

                            case "2" ->
                            {
                                LeaveView leaveView = new LeaveView();
                                leaveView.leaveView(userInput, userSession);
                            }

                            case "3" -> signup(userInput);

                            case "4" -> showAllUser();

                            case "5" ->
                            {
                                AttendanceView attendanceView = new AttendanceView();
                                attendanceView.showAttendance(userInput);
                            }
                            case "6" ->
                            {
                                LeaveView leaveView = new LeaveView();
                                leaveView.showLeaves(userInput);
                            }
                            case "7" ->
                            {
                                LeaveView leaveView = new LeaveView();
                                leaveView.updateLeave(userInput);
                            }
                        }
                    }
                    else
                    {
                        switch (userInput.readLine())
                        {

                            case "0" ->
                            {
                                loopFlag = false;
                                userSession = null;
                            }

                            case "1" ->
                            {
                                AttendanceView attendanceView = new AttendanceView();
                                attendanceView.attendanceMenu(userInput, userSession);
                            }

                            case "2" ->
                            {
                                LeaveView leaveView = new LeaveView();
                                leaveView.leaveView(userInput, userSession);
                            }
                        }
                    }

                }

            }
            else
            {
                System.out.println("Client error: "
                                   + (response.isNull("message") ? "bad request" : response.getString("message")));
            }


        }
        catch (Exception exception)
        {
            System.out.println("Client error: please restart");
        }
    }
}
