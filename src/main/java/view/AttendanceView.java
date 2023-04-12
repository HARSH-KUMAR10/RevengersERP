package view;

import constant.Constant;
import dependency.SocketControllers;
import dependency.UserSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class AttendanceView
{

    private String getDateString()
    {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        return calendar.get(Calendar.DATE)
               + "/" + (calendar.get(Calendar.MONTH))
               + "/" + (calendar.get(Calendar.YEAR));
    }

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


    public void attendanceMenu(BufferedReader userInput, UserSession userSession)
    {
        try
        {

            boolean loopFlag = true;

            JSONObject request = new JSONObject();

            request.put("route", Constant.Route.ATTENDANCE);

            request.put("operation", Constant.Operation.CHECK_ATTENDANCE);

            request.put("eid", userSession.getEmpId());

            request.put("date", getDateString());

            JSONObject response = new JSONObject(processRequest(request));


            boolean checkedIn = false, checkedOut = false;

            System.out.println(Constant.Message.DIVIDER);
            if (!response.isEmpty() && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {
                JSONObject data = response.getJSONObject("data");
                if (data.getBoolean("found") && !data.isNull("checkOut"))
                {

                    System.out.println("Date: " + data.getString("date"));

                    System.out.println("Check in: " + data.getString("checkIn"));

                    System.out.println("Check out: " + data.getString("checkOut"));

                    System.out.println("Total hours: " + data.getString("totalHours"));

                    checkedIn = true;

                    checkedOut = true;

                }
                else if (data.getBoolean("found") && data.isNull("checkOut"))
                {

                    System.out.println("Date: " + data.getString("date"));

                    System.out.println("Check in: " + data.getString("checkIn"));

                    checkedIn = true;

                }
                else
                {
                    System.out.println("No attendance found for today");
                }
            }
            else
            {
                System.out.println("Client error: "
                                   + (response.isNull("message") ? "bad request" : response.getString("message")));
                return;
            }
            System.out.println(Constant.Message.DIVIDER);

            while (loopFlag)
            {
                if (checkedIn && checkedOut)
                {

                    System.out.println("Attendance for today is recorded");

                    break;

                }
                else if (checkedIn)
                {

                    System.out.println("1. Check-Out\n0. Exit\n Choose:");

                    switch (userInput.readLine())
                    {
                        case "1" ->
                        {
                            if (checkOut(userSession))
                            {

                                loopFlag = false;

                                attendanceMenu(userInput, userSession);

                            }

                        }

                        case "0" -> loopFlag = false;

                        default -> System.out.println("Wrong input");
                    }
                }
                else
                {

                    System.out.println("1. Check-In\n0. Exit\n Choose:");

                    switch (userInput.readLine())
                    {
                        case "1" ->
                        {
                            if (checkIn( userSession))
                            {

                                loopFlag = false;

                                attendanceMenu(userInput, userSession);

                            }

                        }

                        case "0" -> loopFlag = false;

                        default -> System.out.println("Wrong input");

                    }
                }
            }
        }
        catch (Exception exception)
        {
            System.out.println("Client error: please restart");
        }
    }

    public boolean checkIn( UserSession userSession)
    {
        try
        {

            JSONObject request = new JSONObject();

            request.put("route", Constant.Route.ATTENDANCE);

            request.put("operation", Constant.Operation.CHECKIN);

            request.put("eid", userSession.getEmpId());

            request.put("date", getDateString());

            JSONObject response = new JSONObject(processRequest(request));

            if (!response.isEmpty()
                && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {

                System.out.println(Constant.Message.DIVIDER);

                System.out.println(response.getString(Constant.Keywords.MESSAGE) + ": check-in");

                System.out.println(Constant.Message.DIVIDER);

                return true;
            }
            else
            {
                System.out.println("Client error: " + response.getString("message"));

                return false;
            }


        }
        catch (Exception exception)
        {

            System.out.println("Client error: please restart.");

            return false;
        }
    }

    public boolean checkOut( UserSession userSession)
    {
        try
        {
            JSONObject checkOutRequest = new JSONObject();

            checkOutRequest.put("route", Constant.Route.ATTENDANCE);

            checkOutRequest.put("operation", Constant.Operation.CHECKOUT);

            checkOutRequest.put("eid", userSession.getEmpId());

            checkOutRequest.put("date", getDateString());

            JSONObject checkOutResponse = new JSONObject(processRequest(checkOutRequest));

            if (!checkOutResponse.isEmpty() && !checkOutResponse.isNull("statusCode")
                && checkOutResponse.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {

                System.out.println(Constant.Message.DIVIDER);

                System.out.println(checkOutResponse.getString(Constant.Keywords.MESSAGE) + ": checkout");

                System.out.println(Constant.Message.DIVIDER);

                return true;
            }
            else
            {
                System.out.println("Client error: "
                                   + (checkOutResponse.isNull("message") ? "bad request" : checkOutResponse.getString("message")));
                return false;
            }
        }
        catch (Exception exception)
        {

            System.out.println("Client error: please restart.");
            return false;
        }
    }

    public void showAttendance(BufferedReader userInput)
    {
        try
        {

            System.out.println("Enter EmpId: ");

            String eid = userInput.readLine();

            JSONObject request = new JSONObject();

            request.put("eid", eid);

            request.put("route", Constant.Route.ATTENDANCE);

            request.put("operation", Constant.Operation.SHOW_ATTENDANCES);

            JSONObject response = new JSONObject(processRequest(request));

            System.out.println(Constant.Message.DIVIDER);

            if (!response.isEmpty() && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {

                JSONArray attendanceList = new JSONArray(response.getString(Constant.Keywords.DATA));

                System.out.println("Date\t\t\tCheckIn\t\tCheckOut\t\tTotalHours");

                for (int iterator = 0; iterator < attendanceList.length(); iterator++)
                {
                    JSONObject attendance = attendanceList.getJSONObject(iterator);

                    if (!attendance.isNull("date")) System.out.print(attendance.getString("date") + "\t\t");

                    if (!attendance.isNull("checkIn")) System.out.print(attendance.getString("checkIn") + "\t");

                    if (!attendance.isNull("checkOut")) System.out.print(attendance.getString("checkOut") + "\t\t");

                    if (!attendance.isNull("totalHours")) System.out.print(attendance.getString("totalHours") + "\t\t");

                    System.out.println();
                }

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
            System.out.println("Client error: please restart.");
        }
    }

}
