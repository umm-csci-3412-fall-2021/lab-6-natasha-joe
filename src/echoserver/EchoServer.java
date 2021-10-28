package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
  public static final int PORT_NUMBER = 6013;
  public static final int BUFFER_SIZE = 1024;

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

      while (true) {
        Socket socketToClient = serverSocket.accept();
        InputStream in = socketToClient.getInputStream();
        OutputStream out = socketToClient.getOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];

        // Read from the socket and echo any data back.
        // When the client closes their "output" half of the socket, end the
        // loop.
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
          System.out.println("Yay I got data!!");
		out.write(buffer, 0, bytesRead);
          	out.flush();
        }

        // Then, close our "output" half of the socket.
        socketToClient.shutdownOutput();
      }
    } catch (IOException ioe) {
      System.err.println("Unexpected exception:");
      ioe.printStackTrace();
    }
  }
}
//public class EchoServer {
	
	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
//	public static final int PORT_NUMBER = 0; 
//	public static void main(String[] args) throws IOException, InterruptedException {
//		EchoServer server = new EchoServer();
//		server.start();
//	}

//	private void start() throws IOException, InterruptedException {
//		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
//		while (true) {
//			Socket socket = serverSocket.accept();

			// Put your code here.
			// This should do very little, essentially:
			//   * Construct an instance of your runnable class
			//   * Construct a Thread with your runnable
			//      * Or use a thread pool
			//   * Start that thread
//		}
//	}
//}
