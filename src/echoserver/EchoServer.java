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
	new Thread(new ConnectionHandler(socketToClient)).start();
      }
    } catch (IOException ioe) {
      System.err.println("Unexpected exception:");
      ioe.printStackTrace();
    }
  }
	public static class ConnectionHandler implements Runnable{ 
  		private Socket socket;

		public ConnectionHandler(Socket socket){
			this.socket = socket;
		}

		@Override
		public void run(){
			try{
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				int byteRead;
				while((byteRead = in.read()) != -1){
					out.write(byteRead);
					out.flush();
				}
				socket.shutdownOutput();
			} catch(IOException e){
				System.err.println("Unexpected exception:");
				e.printStackTrace();
			}
		}
	}	
}
