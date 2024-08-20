package server;

public class WebServerRunner {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("java -jar (jar name) PORT_NUMBER SERVER_NAME");
        }
        int currentServerPort = Integer.parseInt(args[0]);
        String serverName = args[1];
        var server = new WebServer(currentServerPort, serverName);
        server.startServer();
    }

}
