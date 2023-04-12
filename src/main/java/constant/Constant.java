package constant;

public class Constant
{
    public static class Keywords{

        public final static String STATUS_CODE = "statusCode";

        public final static String MESSAGE = "message";

        public final static String DATA = "data";

        public final static String ROUTE = "route";

        public final static String OPERATION = "operation";

        public final static String CLIENT_ADD = "clientAddress";

    }

    public static class StatusCode{

        public final static int SUCCESS = 200;

        public final static int FAILED = 300;

        public final static int BAD_REQUEST = 400;

        public final static int INTERNAL_SERVER_ERROR = 500;
    }

    public static class Message{

        public final static String SUCCESS = "Success";

        public final static String FAILED = "Failed";

        public final static String INTERNAL_SERVER_ERROR = "Internal server error";

        public final static String BAD_REQUEST = "Bad Request";

    }

    public static class Route{

        public final static String ACCOUNT = "account";

        public final static String ATTENDANCE = "attendance";

        public final static String LEAVE = "leave";

    }

    public static class Operation{

        public final static String SIGNUP = "signup";

        public final static String LOGIN = "login";

        public final static String ALL_DETAIL = "allDetails";

        public final static String CHECK_IN = "checkIn";

        public final static String CHECKOUT = "checkOut";

        public final static String CHECK_ATTENDANCE = "checkAttendance";

        public final static String SHOW_ATTENDANCES = "showAttendances";

        public final static String APPLY_LEAVE = "applyLeave";

        public final static String GET_LEAVE_STATUS = "getLeaveStatus";

        public final static String UPDATE_LEAVE = "updateLeave";

        public final static String SHOW_LEAVES = "showLeaves";

    }

    public static class Dependencies{

        public static final String IP = "10.20.40.194";

        public static final int PORT = 8080;

    }

}
