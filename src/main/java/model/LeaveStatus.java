package model;

public enum LeaveStatus
{
    APPROVED, REFUSED;

    public LeaveStatus negate()
    {
        if (this == LeaveStatus.REFUSED)
        {
            return APPROVED;
        }
        return REFUSED;
    }
}
