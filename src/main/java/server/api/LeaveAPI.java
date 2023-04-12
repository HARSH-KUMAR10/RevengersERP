package server.api;

import constant.Constant;
import org.json.JSONObject;
import server.service.LeaveService;

public class LeaveAPI
{
    private static LeaveAPI leaveAPI = null;

    private LeaveAPI(){}

    public static LeaveAPI getInstance(){

        if(leaveAPI==null) leaveAPI = new LeaveAPI();

        return leaveAPI;

    }

    public JSONObject route(JSONObject request){

        LeaveService leaveService = new LeaveService();

        switch (request.getString("operation")){
            case Constant.Operation.APPLY_LEAVE ->
            {
                return leaveService.applyLeave(request);
            }
            case Constant.Operation.GET_LEAVE_STATUS -> {
                return leaveService.getLeaveStatus(request);
            }
            case Constant.Operation.UPDATE_LEAVE -> {
                return leaveService.updateLeave(request);
            }
            case Constant.Operation.SHOW_LEAVES -> {
                return leaveService.showLeaves(request);
            }
            default -> {
                JSONObject response = new JSONObject();

                response.put(Constant.Keywords.STATUS_CODE,Constant.StatusCode.INTERNAL_SERVER_ERROR);

                response.put(Constant.Keywords.MESSAGE,Constant.Message.INTERNAL_SERVER_ERROR);

                return response;
            }
        }

    }
}
