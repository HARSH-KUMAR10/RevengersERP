package server.api;

import constant.Constant;
import org.json.JSONObject;

public class API
{
    private static API api = null;

    private API()
    {
    }

    public static API getInstance()
    {
        if (api == null) api = new API();

        return api;
    }

    public JSONObject route(JSONObject request)
    {
        switch (request.getString("route"))
        {
            case Constant.Route.ACCOUNT ->
            {
                return AccountAPI.getInstance().route(request);
            }
            case Constant.Route.ATTENDANCE ->
            {
                return AttendanceAPI.getInstance().route(request);
            }
            case Constant.Route.LEAVE ->
            {
                return LeaveAPI.getInstance().route(request);
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
