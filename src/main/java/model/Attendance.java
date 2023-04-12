package model;

public class Attendance
{

    private final String empId;

    private final String date;

    private final String checkIn;

    private String checkOut;

    private String totalHours;

    public Attendance(String empId, String date, String checkIn)
    {

        this.empId = empId;

        this.date = date;

        this.checkIn = checkIn;

    }


    public String getEmpId()
    {
        return empId;
    }

    public String getDate()
    {
        return date;
    }

    public String getCheckIn()
    {
        return checkIn;
    }

    public String getCheckOut()
    {
        return checkOut;
    }

    public String getTotalHours()
    {
        return totalHours;
    }

    public void setCheckOut(String checkOut)
    {
        this.checkOut = checkOut;
    }

    public void setTotalHours(String totalHours)
    {
        this.totalHours = totalHours;
    }

}
