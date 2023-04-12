package model;

public class Leave
{
    private final String empId;

    private final String leaveId;

    private final String fromDate;

    private final String toDate;

    private final String reason;

    private LeaveStatus leaveStatus;

    private static int count = 1;

    public Leave(String empId, String fromDate, String toDate, String reason, LeaveStatus leaveStatus)
    {
        this.leaveId = "L" + count++;

        this.empId = empId;

        this.fromDate = fromDate;

        this.toDate = toDate;

        this.reason = reason;

        this.leaveStatus = leaveStatus;

    }

    public String getEmpId()
    {
        return empId;
    }

    public String getLeaveId()
    {
        return leaveId;
    }

    public String getFromDate()
    {
        return fromDate;
    }

    public String getToDate()
    {
        return toDate;
    }

    public String getReason()
    {
        return reason;
    }

    public LeaveStatus getLeaveStatus()
    {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus)
    {
        this.leaveStatus = leaveStatus;
    }
}
