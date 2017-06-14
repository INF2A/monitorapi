package main.java;

import org.json.JSONObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


@Path("/")
public class Monitor {
    String[] ip = {
            "192.168.1.1",
            "192.168.1.2",
            "192.168.1.3",
            "192.168.1.4",
            "192.168.1.5",
            "192.168.1.6",
            "192.168.1.7"
    };
    String nodeName;

    public boolean checkUp(String ip){
        boolean check = false;
        try{
            InetAddress addr = InetAddress.getByName(ip);
            int port = 22;
            SocketAddress sockaddr = new InetSocketAddress(addr, port);

            Socket sock = new Socket();

            int timeoutMs = 100;
            sock.connect(sockaddr, timeoutMs);

            if (sock.isConnected()){
                check =true;
            }
        }

        catch (UnknownHostException e){
            System.out.print(e.getStackTrace());
        }
        catch (IOException e){
            System.out.print(e.getStackTrace());
        }
        return check;
    }

    //LOCALHOST:8086/monitor
    @Path("/monitor")
    @GET
    @Produces("application/json")
    public Response monitor() throws Exception {
        JSONObject obj = new JSONObject();
        for(int i =0 ; i < ip.length; i++)
        {
            if(checkUp(ip[i]))
            {
                obj.put(ip[i], true);
            }
            else
            {
                obj.put(ip[i], false);
            }
        }
         return Response.status(200).entity(obj.toString()).build();
    }

    @Path("/monitor/{n}")
    @GET
    @Produces("application/json")
    public Response dockerPS(@PathParam("n") String s) {
        JSONObject obj = new JSONObject();  //Instantiate new JSONObject
        obj.put(s, dockerCommands(s).toString());
        //obj.put(s, ); //Put value in JSONObject
        return Response.ok(obj.toString()).build(); /* Return HTTP status code (2xx = OK,
                                                                                3xx = Redirection,
                                                                                4xx = client errors,
                                                                                5xx = server errors )
                                                                                with JSONObject to string as entity to be printed out. */
    }

    public List<String> dockerCommands(String command){
        List<String> commands = new ArrayList<>();
        commands.add("/script.sh");
        commands.add(command);
        System.out.println(commands);
        List<String> output = Shell.getInstance().runCommand(commands, false);
        System.out.println(output);
        return output;
    }
}
