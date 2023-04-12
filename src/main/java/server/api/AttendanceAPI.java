package server.api;

import constant.Constant;
import org.json.JSONObject;
import server.service.AttendanceService;

public class AttendanceAPI
{

    private static AttendanceAPI attendanceAPI = null;

    private AttendanceAPI(){}

    public static AttendanceAPI getInstance(){
        if(attendanceAPI==null) attendanceAPI = new AttendanceAPI();
        return attendanceAPI;
    }

    public JSONObject route(JSONObject request)
    {

        AttendanceService attendanceService = new AttendanceService();

        switch (request.getString("operation"))
        {
            case Constant.Operation.CHECK_IN ->
            {
                return attendanceService.checkIn(request);
            }
            case Constant.Operation.CHECKOUT ->
            {
                return attendanceService.checkOut(request);
            }
            case Constant.Operation.CHECK_ATTENDANCE ->
            {
                return attendanceService.checkAttendance(request);
            }
            case Constant.Operation.SHOW_ATTENDANCES ->
            {
                return attendanceService.showAttendances(request);
            }
            default ->
            {
                JSONObject response = new JSONObject();

                response.put(Constant.Keywords.STATUS_CODE, Constant.StatusCode.INTERNAL_SERVER_ERROR);

                response.put(Constant.Keywords.MESSAGE, Constant.Message.INTERNAL_SERVER_ERROR);

                return response;
            }
        }

    }
}
