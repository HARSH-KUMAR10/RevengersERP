package server.service;

import constant.Constant;
import model.Leave;
import org.json.JSONArray;
import org.json.JSONObject;
import server.repo.LeaveRepo;

import java.util.HashMap;

public class LeaveService
{
    JSONObject response;

    public JSONObject applyLeave(JSONObject request)
    {
        try
        {
            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("fromDate")
                && !request.isNull("toDate") && !request.isNull("reason")
                && !request.isNull("empId"))
            {
                Leave leave = LeaveRepo.getInstance().post(request);

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

                JSONObject data = new JSONObject();

                data.put("lid", leave.getLeaveId());

                data.put("eid", leave.getEmpId());

                data.put("from", leave.getFromDate());

                data.put("to", leave.getToDate());

                data.put("reason", leave.getReason());

                data.put("status", leave.getLeaveStatus().toString());

                response.put("data", data);

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

    public JSONObject getLeaveStatus(JSONObject request)
    {
        try
        {
            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("eid") && !request.isNull("lid"))
            {
                Leave leave = LeaveRepo.getInstance().get(request);

                JSONObject data = new JSONObject();

                data.put("fromDate", leave.getFromDate());

                data.put("toDate", leave.getToDate());

                data.put("reason", leave.getReason());

                data.put("status", leave.getLeaveStatus().toString());

                response.put("data", data);

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

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

    public JSONObject updateLeave(JSONObject request)
    {
        try
        {

            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("eid") && !request.isNull("lid"))
            {
                Leave leave = LeaveRepo.getInstance().put(request);

                if(leave!=null)
                {

                    JSONObject data = new JSONObject();

                    data.put("fromDate", leave.getFromDate());

                    data.put("toDate", leave.getToDate());

                    data.put("reason", leave.getReason());

                    data.put("status", leave.getLeaveStatus().toString());

                    response.put("data", data);

                    response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                    response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);
                }else{

                    response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.FAILED);

                    response.put(Constant.Keywords.MESSAGE, Constant.Message.FAILED+" : unable to find leave.");
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

    public JSONObject showLeaves(JSONObject request)
    {
        try
        {

            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("eid"))
            {

                HashMap<String, Leave> leaves = LeaveRepo.getInstance().get(request.getString("eid"));

                JSONArray leavesList = new JSONArray();

                if (leaves != null)
                {

                    leaves.forEach((lid, leave) -> {

                        JSONObject leaveObj = new JSONObject();

                        leaveObj.put("lid", lid);

                        leaveObj.put("fromDate", leave.getFromDate());

                        leaveObj.put("toDate", leave.getToDate());

                        leaveObj.put("reason", leave.getReason());

                        leaveObj.put("leaveStatus", leave.getLeaveStatus());

                        leavesList.put(leaveObj);

                    });
                }

                response.put("data", (leavesList.toString()));

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

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

}
