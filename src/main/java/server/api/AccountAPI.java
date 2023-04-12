package server.api;

import constant.Constant;
import org.json.JSONObject;
import server.service.AccountsService;

public class AccountAPI
{
    private static AccountAPI accountAPI = null;

    private AccountAPI()
    {
    }

    public static AccountAPI getInstance()
    {
        if (accountAPI == null) accountAPI = new AccountAPI();

        return accountAPI;
    }

    public JSONObject route(JSONObject request)
    {

        AccountsService accountsService = new AccountsService();

        switch (request.getString("operation"))
        {
            case Constant.Operation.SIGNUP ->
            {
                return accountsService.createAccount(request);
            }
            case Constant.Operation.LOGIN ->
            {
                return accountsService.loginAccount(request);
            }
            case Constant.Operation.ALL_DETAIL ->
            {
                return accountsService.getAllEmployees();
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
