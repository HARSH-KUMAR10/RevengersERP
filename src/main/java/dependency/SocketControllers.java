package dependency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketControllers
{
    private final Socket socket;

    private final BufferedReader reader;

    private final PrintWriter writer;

    public SocketControllers(Socket socket) throws IOException
    {
        this.socket = socket;

        // Maximum timeout for socket controller to handle request
        this.socket.setSoTimeout(2000);

        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.writer = new PrintWriter(socket.getOutputStream(), true);

    }

    public Socket getSocket()
    {
        return socket;
    }

    public BufferedReader getReader()
    {
        return reader;
    }

    public PrintWriter getWriter()
    {
        return writer;
    }
}
