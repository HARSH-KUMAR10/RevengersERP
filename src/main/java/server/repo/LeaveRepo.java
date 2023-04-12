package server.repo;

import model.Leave;
import model.LeaveStatus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class LeaveRepo implements RepoOperation<JSONObject, Leave>
{
    private static LeaveRepo leaveRepo = null;

    private final ConcurrentHashMap<String, HashMap<String, Leave>> leaves = new ConcurrentHashMap<>();

    private LeaveRepo()
    {
    }

    public static LeaveRepo getInstance()
    {
        if (leaveRepo == null) leaveRepo = new LeaveRepo();

        return leaveRepo;
    }

    @Override
    public Leave post(JSONObject request)
    {
        String empId = request.getString("empId");

        String fromDate = request.getString("fromDate");

        String toDate = request.getString("toDate");

        String reason = request.getString("reason");

        LeaveStatus leaveStatus = LeaveStatus.REFUSED;

        Leave leave = new Leave(empId, fromDate, toDate, reason, leaveStatus);

        HashMap<String, Leave> empLeaves = leaves.get(empId);

        if (empLeaves == null)
        {
            empLeaves = new HashMap<>();
        }

        empLeaves.put(leave.getLeaveId(), leave);

        leaves.put(empId, empLeaves);

        return leave;
    }

    @Override
    public Leave get(JSONObject request)
    {
        return leaves.get(request.getString("eid")).get(request.getString("lid"));
    }

    @Override
    public Leave put(JSONObject request)
    {

        Leave empLeave = get(request);

        if (empLeave != null)
        {
            empLeave.setLeaveStatus(empLeave.getLeaveStatus().negate());
        }

        return empLeave;
    }

    @Override
    public Leave delete(JSONObject params)
    {
        return null;
    }

    public HashMap<String, Leave> get(String eid)
    {
        return leaves.get(eid);
    }
}
