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
    //String from ip adressen for the Raspberry Pi's
    String[] ip = {
            "192.168.1.1",
            "192.168.1.2",
            "192.168.1.3",
            "192.168.1.4",
            "192.168.1.5",
            "192.168.1.6",
            "192.168.1.7"
    };

    /***
     *
     * @param ip It use the ip addres from the Raspberry Pi's
     * @return boolean true if ssh port(22) is open otherwise false.
     */
    public boolean checkUp(String ip){
        boolean check = false;
        try{
            //looks at the ip addres (ip)
            InetAddress addr = InetAddress.getByName(ip);
            //checks on port 22(ssh
            int port = 22;
            //merge ip and port
            SocketAddress sockaddr = new InetSocketAddress(addr, port);

            Socket sock = new Socket();
            //it checks 1second if up of down
            int timeoutMs = 100;
            sock.connect(sockaddr, timeoutMs);

            //If connection is OK then give its boolean treu
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

    /***
     *
     * @return its build a json file and shows it in webbrowser
     *         Make use of the checkup methode and add treu of false
     *         LOCALHOST:8086/monitor
     * @throws Exception
     */
    @Path("/monitor")
    @GET
    @Produces("application/json")
    public Response monitor() throws Exception {
        JSONObject obj = new JSONObject();
        //Looks at the lenghte of the array and add true of false(checkup())
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
        //build the json file.
         return Response.status(200).entity(obj.toString()).build();/* Return HTTP status code (2xx = OK,
                                                                                3xx = Redirection,
                                                                                4xx = client errors,
                                                                                5xx = server errors )
                                                                                with JSONObject to string as entity to be printed out. */
    }

    /**
     *
     * @param s its runs a script with parameter from s
     * @return Will schow the running api's of the Raspberry Pi
     */
    //LOCALHOST:8086/monitor/amsterdam
    @Path("/monitor/{n}")
    @GET
    @Produces("application/json")
    public Response dockerPS(@PathParam("n") String s) {
        JSONObject obj = new JSONObject();  //Instantiate new JSONObject
        //Runs the dockerCommands() inside convertOutput(). And convertOuput() change the info.
        String[] output = convertOutput(dockerCommands(s).toString());
        //add the output of convertOutput()
        obj.put(s, output);
        //obj.put(s, ); //Put value in JSONObject
        //build the json file.
        return Response.ok(obj.toString()).build(); /* Return HTTP status code (2xx = OK,
                                                                                3xx = Redirection,
                                                                                4xx = client errors,
                                                                                5xx = server errors )
                                                                                with JSONObject to string as entity to be printed out. */
    }
    /**
     *
     * @param command is form the methode dockerPS(). The command wil be build in this methode.
     * @return output of the script
     */
    public List<String> dockerCommands(String command){
        //make a arraylist of all the commands
        List<String> commands = new ArrayList<>();
        //add script.sh. script is standing in the root of the container
        commands.add("./script.sh");
        //add the command given from the webbrowser. example amsterdam
        commands.add(command);
        System.out.println(commands);
        //Runs the script from class Shell.class
        List<String> output = Shell.getInstance().runCommand(commands, false);
        System.out.println(output);
        //returns the output of the command
        return output;
    }
    /**
     *
     * @param input from the methode dockercommands();
     * @return Makes the information valid JSON.
     */
    public String[] convertOutput(String input){
        String[] output;
        String temp;
        //remove [ ] () {} : of the string.
        temp = input.replaceAll("[\\[\\](){}:]", "");
        //split the string in to a array. white space;)
        output = temp.split(" ");
        //returns the right information
        return output;
    }
}
