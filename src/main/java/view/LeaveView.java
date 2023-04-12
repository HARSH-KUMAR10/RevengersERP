package view;

import constant.Constant;
import dependency.SocketControllers;
import dependency.UserSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.Socket;
import java.util.Date;

public class LeaveView
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

    private String getDateString()
    {
        Date date = new Date();
        return date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900);
    }

    private String setDateString(BufferedReader userInput)
    {
        try
        {
            System.out.println("(dd/mm/yyyy)");
            String date = userInput.readLine();
            return date;
        }
        catch (Exception exception)
        {
            System.out.println("Client error: please restart");
            return getDateString();
        }
    }


    public void leaveView(BufferedReader userInput, UserSession userSession)
    {
        try
        {

            boolean loopFlag = true;

            JSONObject request = new JSONObject();

            request.put("route", Constant.Route.LEAVE);

            request.put("operation", Constant.Operation.SHOW_LEAVES);

            request.put("eid", userSession.getEmpId());

            JSONObject response = new JSONObject(processRequest(request));

            System.out.println(Constant.Message.DIVIDER);

            if (!response.isEmpty() && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS
            )
            {
                if (!response.getString("data").equalsIgnoreCase("[]"))
                {
                    JSONArray data = new JSONArray(response.getString("data"));

                    System.out.println("leaveId\t\tFrom\t\t\tTo\t\t\tStatus\t\tReason");

                    for (int iterator = 0; iterator < data.length(); iterator++)
                    {
                        JSONObject leave = data.getJSONObject(iterator);

                        System.out.println(leave.getString("lid") + "\t\t" + leave.getString("fromDate") + "\t\t"
                                           + leave.getString("toDate") + "\t\t" + leave.getString("leaveStatus") + "\t\t"
                                           + leave.getString("reason"));
                    }
                }
                else
                {
                    System.out.println("No leaves found");
                }
            }
            else
            {
                System.out.println("Client error: " + response.getString("message"));
                return;
            }
            System.out.println(Constant.Message.DIVIDER);

            while (loopFlag)
            {

                System.out.println("1. Apply leave\n0. Exit\n Choose:");
                switch (userInput.readLine())
                {
                    case "1" ->
                    {
                        JSONObject checkOutRequest = new JSONObject();

                        checkOutRequest.put("route", Constant.Route.LEAVE);

                        checkOutRequest.put("operation", Constant.Operation.APPLY_LEAVE);

                        checkOutRequest.put("empId", userSession.getEmpId());

                        System.out.println("Enter start date: ");

                        checkOutRequest.put("fromDate", setDateString(userInput));

                        System.out.println("Enter end date: ");

                        checkOutRequest.put("toDate", setDateString(userInput));

                        System.out.println("Enter reason: ");

                        checkOutRequest.put("reason", userInput.readLine());

                        JSONObject checkInResponse = new JSONObject(processRequest(checkOutRequest));

                        if (!checkInResponse.isEmpty() && !checkInResponse.isNull("statusCode")
                            && checkInResponse.getInt("statusCode") == Constant.StatusCode.SUCCESS)
                        {
                            loopFlag = false;
                            leaveView(userInput, userSession);
                        }
                        else
                        {
                            System.out.println("Client error: "
                                               + (response.getString("message") == null ?
                                    "bad request" : response.getString("message")));
                        }

                    }

                    case "0" -> loopFlag = false;

                    default -> System.out.println("Wrong input");
                }
            }
        }
        catch (Exception exception)
        {
            System.out.println("Client error: please restart");
        }
    }

    public void showLeaves(BufferedReader userInput)
    {
        try
        {

            JSONObject request = new JSONObject();

            System.out.println("Enter EmpId: ");

            request.put("eid", userInput.readLine());

            request.put("route", Constant.Route.LEAVE);

            request.put("operation", Constant.Operation.SHOW_LEAVES);

            JSONObject response = new JSONObject(processRequest(request));


            if (!response.isEmpty() && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {

                JSONArray leavesList = new JSONArray(response.getString("data"));

                System.out.println(Constant.Message.DIVIDER);

                System.out.println("LeaveId\t\t\tfrom\t\t\tto\t\t\tstatus\t\tReason");

                for (int iterator = 0; iterator < leavesList.length(); iterator++)
                {
                    JSONObject leave = leavesList.getJSONObject(iterator);

                    if (!leave.isNull("lid")) System.out.print(leave.getString("lid") + "\t\t\t");

                    if (!leave.isNull("fromDate")) System.out.print(leave.getString("fromDate") + "\t\t");

                    if (!leave.isNull("toDate")) System.out.print(leave.getString("toDate") + "\t\t");

                    if (!leave.isNull("leaveStatus")) System.out.print(leave.getString("leaveStatus") + "\t\t");

                    if (!leave.isNull("reason")) System.out.print(leave.getString("reason") + "\t\t");

                    System.out.println();
                }

                System.out.println(Constant.Message.DIVIDER);

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

    public void updateLeave(BufferedReader reader)
    {
        try
        {

            JSONObject request = new JSONObject();

            System.out.println("Enter EmpId");

            request.put("eid", reader.readLine());

            System.out.println("Enter LeaveId: ");

            request.put("lid", reader.readLine());

            request.put("route", Constant.Route.LEAVE);

            request.put("operation", Constant.Operation.UPDATE_LEAVE);

            JSONObject response = new JSONObject(processRequest(request));


            System.out.println(Constant.Message.DIVIDER);

            if (!response.isEmpty() && !response.isNull("statusCode")
                && response.getInt("statusCode") == Constant.StatusCode.SUCCESS)
            {

                JSONObject data = response.getJSONObject("data");

                System.out.println("Leave updated: ");

                if (!data.isNull("fromDate")) System.out.println("From: " + data.getString("fromDate"));

                if (!data.isNull("toDate")) System.out.println("To: " + data.getString("toDate"));

                if (!data.isNull("reason")) System.out.println("Reason: " + data.getString("reason"));

                if (!data.isNull("status")) System.out.println("Status: " + data.getString("status"));

            }
            else
            {
                System.out.println("Client error: "
                                   + (response.isNull("message") ? "bad request" : response.getString("message")));
            }

            System.out.println(Constant.Message.DIVIDER);

        }
        catch (Exception exception)
        {
            System.out.println("Client error: please restart");
        }
    }

}
