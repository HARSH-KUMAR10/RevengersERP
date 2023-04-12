package server.repo;

import model.Attendance;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AttendanceRepo implements RepoOperation<JSONObject, Attendance>
{

    private static AttendanceRepo attendanceRepo = null;

    private final ConcurrentHashMap<String, HashMap<String, Attendance>> attendances = new ConcurrentHashMap<>();

    private AttendanceRepo(){}

    public static AttendanceRepo getInstance(){
        if(attendanceRepo==null) attendanceRepo = new AttendanceRepo();
        return  attendanceRepo;
    }

    @Override
    public Attendance post(JSONObject params)
    {
        String eid = params.getString("eid");

        String date = params.getString("date");

        String checkIn = getCurrentTime();

        Attendance attendance = new Attendance(eid, date, checkIn);

        HashMap<String, Attendance> attendanceHashMap = attendances.get(eid);

        if (attendanceHashMap == null)
        {
            attendanceHashMap = new HashMap<>();
        }

        attendanceHashMap.put(date, attendance);

        attendances.put(eid, attendanceHashMap);

        return attendance;
    }

    @Override
    public Attendance get(JSONObject params)
    {

        String eid = params.getString("eid");

        String date = params.getString("date");

        HashMap<String, Attendance> empAttendances = attendances.get(eid);

        if(empAttendances!=null){
            return empAttendances.get(date);
        }else{
            return null;
        }

    }

    @Override
    public Attendance put(JSONObject params)
    {
        Attendance attendance = get(params);

        String checkOut = getCurrentTime();

        String totalHours = getTimeDifference(checkOut, attendance.getCheckIn());

        attendance.setCheckOut(checkOut);

        attendance.setTotalHours(totalHours);

        return attendance;
    }

    @Override
    public Attendance delete(JSONObject params)
    {
        return null;
    }

    public HashMap<String,Attendance> get(String eid){
        return attendances.get(eid);
    }

    private String getCurrentTime()
    {

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

    }

    private String getTimeDifference(String checkOut, String checkIn)
    {

        String[] checkOutSplit = checkOut.split(":");

        String[] checkInSplit = checkIn.split(":");

        return (Integer.parseInt(checkOutSplit[1])) > Integer.parseInt(checkInSplit[1]) ?

                (Integer.parseInt(checkOutSplit[0]) - Integer.parseInt(checkInSplit[0])) + ":"
                + (Integer.parseInt(checkOutSplit[1]) - Integer.parseInt(checkInSplit[1])) :

                ((Integer.parseInt(checkOutSplit[0]) - 1) - Integer.parseInt(checkInSplit[0])) + ":"
                + ((Integer.parseInt(checkOutSplit[1]) + 60) - Integer.parseInt(checkInSplit[1]));

    }
}
