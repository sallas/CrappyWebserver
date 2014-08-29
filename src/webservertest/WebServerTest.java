package webservertest;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WebServerTest {

    static int port = 8080;
    static String ip = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        if (args.length >= 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        InetSocketAddress i = new InetSocketAddress("127.0.0.1", 8080);
        HttpServer server = HttpServer.create(i, 0);
        server.createContext("/welcome", new WelcomeHandler());
        server.createContext("/headers", new HeaderHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Started the server, listening on: " + port);
        System.out.println(ip);

    }

    static class HeaderHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response;
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            //table stuff
            Map<String, List<String>> m = he.getRequestHeaders();
            sb.append("<table border='1'>");
            for (Entry<String, List<String>> entry : m.entrySet()) {
                String s = entry.getKey();
                List<String> list = entry.getValue();
                sb.append("<tr>");
                sb.append("<td>");
                sb.append(s);
                sb.append("</td>");
                sb.append("<td>");
                for (String string : list) {
                    sb.append(string);
                    //sb.append(" , "); 
                }
                sb.append("</td>");
                //sb.replace(sb.length() - 3, sb.length() - 1, "");
                sb.append("</tr>");
                
                
            }
            sb.append("</table>");
                    
            sb.append("");
            sb.append("</body>\n");
            sb.append("</html>\n");

            
            
            response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response);
            }
        }

    }

    static class WelcomeHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response;
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("<h2>Welcome to my very first home made Web Server :-)</h2>\n");
            sb.append("</body>\n");
            sb.append("</html>\n");

            response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response);
            }

        }

    }

}
