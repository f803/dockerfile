/*

Simple Web Server example.

Returns the server's IP address and port for Load-Balancing tests.
Also looks for  $PORT0 and starts in that port if it exists, which
is useful for deployment in Marathon.

*/

///A Simple Web Server (simpleweb.java)

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class simpleweb {

  /**
   * simpleweb constructor.
   */
  protected void start() {
    ServerSocket s;

    //Detect if the variable $PORT0 is defined. Start the server in that port if so
    String port0 = System.getenv("PORT0");
    if ((port0 == null) || port0.isEmpty()){
      port0 = "80";
    }

    System.out.println("Webserver starting up on port " + port0);
    System.out.println("(press ctrl-c to exit)");
    try {
      // create the main server socket
      s = new ServerSocket(Integer.parseInt(port0));
    } catch (Exception e) {
      System.out.println("Error: " + e);
      return;
    }

    // Detect my IP address so that I can return it as part of the response
    // print to stdout all system's IP addresses, keep the last value as "ip"
    String ip="";

    try {
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      while (interfaces.hasMoreElements()) {
          NetworkInterface iface = interfaces.nextElement();
          // filters out 127.0.0.1 and inactive interfaces
          if (iface.isLoopback() || !iface.isUp())
            continue;

          Enumeration<InetAddress> addresses = iface.getInetAddresses();
          while(addresses.hasMoreElements()) {
            InetAddress addr = addresses.nextElement();
            ip = addr.getHostAddress();
            System.out.println(iface.getDisplayName() + " " + ip);
          }
      }
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Waiting for connection");
    for (;;) {
      try {
        // wait for a connection
        Socket remote = s.accept();
        // remote is now the connected socket
        System.out.println("Connection, sending data.");
        BufferedReader in = new BufferedReader(new InputStreamReader(
            remote.getInputStream()));
        PrintWriter out = new PrintWriter(remote.getOutputStream());

        // read the data sent. We basically ignore it,
        // stop reading once a blank line is hit. This
        // blank line signals the end of the client HTTP
        // headers.
        String str = ".";
        while (!str.equals(""))
          str = in.readLine();

        // Send the response
        // Send the headers
        out.println("HTTP/1.0 200 OK");
        out.println("Server: Bot");
        out.println("Content-Type: text/html");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<!DOCTYPE html> <html> <body>");
        out.println("<H1>a simple java web server.</H1>");
        // Send the IP address
        out.println("whose higher interface's IP address is:");
        out.println("<font color=\"red\">" + ip + "</font>");
        out.println("<br />"); //newline
        out.println("and who listens on port: ");
        out.println("<font color=\"red\">" + port0 + "</font>");
        out.println("</body> </html>");
        out.flush();
        remote.close();
      } catch (Exception e) {
        System.out.println("Error: " + e);
      }
    }
  }

  /**
   * Start the application.
   *
   * @param args
   *            Command line parameters are not used.
   */
  public static void main(String args[]) {
    simpleweb ws = new simpleweb();
    ws.start();
  }
}
