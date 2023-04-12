package server.service;

import constant.Constant;
import model.Attendance;
import org.json.JSONArray;
import org.json.JSONObject;
import server.repo.AttendanceRepo;

import java.util.HashMap;

public class AttendanceService
{

    private JSONObject response;

    public JSONObject checkIn(JSONObject request)
    {
        try
        {

            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("eid") && !request.isNull("date"))
            {

                Attendance attendance = AttendanceRepo.getInstance().post(request);

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

                JSONObject data = new JSONObject();

                data.put("date", attendance.getDate());

                data.put("checkIn", attendance.getCheckIn());

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

    public JSONObject checkOut(JSONObject request)
    {
        try
        {
            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("eid") && !request.isNull("date"))
            {

                Attendance attendance = AttendanceRepo.getInstance().put(request);

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

                JSONObject data = new JSONObject();

                data.put("date", attendance.getDate());

                data.put("checkIn", attendance.getCheckIn());

                data.put("checkOut", attendance.getCheckOut());

                data.put("totalHours", attendance.getTotalHours());

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

    public JSONObject checkAttendance(JSONObject request)
    {
        try
        {
            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("eid") && !request.isNull("date"))
            {
                Attendance attendance = AttendanceRepo.getInstance().get(request);

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

                JSONObject data = new JSONObject();

                if (attendance != null)
                {
                    data.put("found", true);

                    data.put("date", attendance.getDate());

                    data.put("checkIn", attendance.getCheckIn());

                    data.put("checkOut", attendance.getCheckOut());

                    data.put("totalHours", attendance.getTotalHours());

                    response.put("data", data);

                }
                else
                {

                    data.put("found", false);

                    response.put("data", data);

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

            exception.printStackTrace();

            response = new JSONObject();

            response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.INTERNAL_SERVER_ERROR);

            response.put(Constant.Keywords.MESSAGE, Constant.Message.INTERNAL_SERVER_ERROR);

            return response;

        }
    }

    public JSONObject showAttendances(JSONObject request)
    {
        try
        {
            response = new JSONObject();

            if (!request.isEmpty() && !request.isNull("eid"))
            {

                HashMap<String, Attendance> attendanceMap = AttendanceRepo.getInstance().get(request.getString("eid"));

                JSONArray attendanceList = new JSONArray();

                if(attendanceMap!=null)
                {

                    attendanceMap.forEach((date, attendance) -> {

                        JSONObject attendanceObj = new JSONObject();

                        attendanceObj.put("date", attendance.getDate());

                        attendanceObj.put("checkIn", attendance.getCheckIn());

                        attendanceObj.put("checkOut", attendance.getCheckOut());

                        attendanceObj.put("totalHours", attendance.getTotalHours());

                        attendanceList.put(attendanceObj);

                    });

                }
                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.SUCCESS);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.SUCCESS);

                response.put(Constant.Keywords.DATA, attendanceList.toString());

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
            exception.printStackTrace();

            response = new JSONObject();

            response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.INTERNAL_SERVER_ERROR);

            response.put(Constant.Keywords.MESSAGE, Constant.Message.INTERNAL_SERVER_ERROR);

            return response;

        }
    }

}
