import constant.Constant;
import dependency.SocketControllers;
import org.json.JSONObject;
import server.api.API;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{

    static int clientCount = 0;

    static ServerSocket bankServer;

    private static final String SERVER_STARTED = "Server started at ";

    private static final String SERVER_NOT_STARTED = "Unable to create server, please try again.";

    private static final String UNKNOWN_REQ = "Unknown request:";

    private static final String CLOSE_CONN = "Closing connection ...";

    private static final String READ_CLIENTS = "reading-clients";

    private static final ExecutorService executorService = Executors.newFixedThreadPool(16);

    private Server()
    {

        createServer();

        startConnections();

    }

    public void createServer()
    {
        try
        {
            bankServer = new ServerSocket(Constant.Dependencies.PORT);

            System.out.println(SERVER_STARTED + Constant.Dependencies.IP
                               + ":" + Constant.Dependencies.PORT);
        }
        catch (Exception exception)
        {
            System.out.println(SERVER_NOT_STARTED);
        }
    }

    public void startConnections()
    {
        try
        {
            new Thread(() -> {
                while (true)
                {
                    try
                    {
                        Socket socket = bankServer.accept();

                        System.out.println("__________________________________________________________________\n" +
                                           "New connection: " + socket.getRemoteSocketAddress());

                        clientCount++;

                        executorService.execute(() -> {
                            try
                            {
                                startReadingClient(new SocketControllers(socket));
                            }
                            catch (Exception exception)
                            {
                                System.out.println(Constant.Message.INTERNAL_SERVER_ERROR
                                                   + ": error in socket creation.");
                            }
                        });

                    }
                    catch (Exception exception)
                    {
                        System.out.println(Constant.Message.INTERNAL_SERVER_ERROR);

                        break;
                    }
                }
            }, READ_CLIENTS).start();
        }
        catch (Exception exception)
        {
            System.out.println(Constant.Message.INTERNAL_SERVER_ERROR);
        }
    }

    public void startReadingClient(SocketControllers socketControllers)
    {
        try
        {
            String request = socketControllers.getReader().readLine();

            if (request != null)
            {
                JSONObject requestObject = new JSONObject(request);

                if (!requestObject.isEmpty())
                {
                    if (requestObject.isNull(Constant.Keywords.ROUTE)
                        || requestObject.isNull(Constant.Keywords.OPERATION))
                    {
                        socketControllers.getWriter().println(Constant.Message.BAD_REQUEST);
                    }
                    else
                    {
                        System.out.println(requestObject.get(Constant.Keywords.CLIENT_ADD) + " => "
                                           + "/" + requestObject.get(Constant.Keywords.ROUTE) + "/"
                                           + requestObject.get(Constant.Keywords.OPERATION));

                        socketControllers.getWriter().println(API.getInstance().route(requestObject));
                    }

                }
                else
                {
                    System.out.println(UNKNOWN_REQ + request + "\n" + CLOSE_CONN);

                    socketControllers.getWriter()
                            .println(Constant.Message.BAD_REQUEST);

                }
            }
            else
            {
                System.out.println(CLOSE_CONN + socketControllers.getSocket().getRemoteSocketAddress());
                socketControllers.getWriter()
                        .println(Constant.Message.BAD_REQUEST);
            }
            System.out.println(CLOSE_CONN + socketControllers.getSocket().getRemoteSocketAddress());
        }
        catch (Exception exception)
        {

            System.out.println(Constant.Message.INTERNAL_SERVER_ERROR
                               + ": " + exception.getMessage());

            socketControllers.getWriter().println(Constant.Message.INTERNAL_SERVER_ERROR);
        }

    }

    public static void main(String[] args)
    {
        System.out.println("Welcome to Revenger's staff-book");
        new Server();
    }
}
