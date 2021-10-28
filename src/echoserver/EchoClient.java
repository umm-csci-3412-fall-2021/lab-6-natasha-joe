package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;
	public static final int BUFFER_SIZE = 1024;

	static InputStream socketIn;
	static OutputStream socketOut;
	static Socket socket;
	public static void main(String[] args) throws IOException, InterruptedException {
		String serverName;

   	 // Use localhost unless we're given a server name on the command line.
   	 if (args.length == 0) {
     		 serverName = "127.0.0.1";
   	 } else {
     		 serverName = args[0];
    	}

    	try {
      		socket = new Socket(serverName, PORT_NUMBER);
     		socketIn = socket.getInputStream();
      		socketOut = socket.getOutputStream();
     		byte[] buffer = new byte[BUFFER_SIZE];
     		int bytesRead;

		Thread firstThread = new Thread(EchoClient::readFromUser);
		firstThread.start();

		Thread secondThread = new Thread(EchoClient::readFromServer);
		secondThread.start();

     	 	firstThread.join();
		secondThread.join();

      		socket.close();
    	} catch (IOException ioe) {
      		System.err.println("Unexpected exception when talking to server:");
      		ioe.printStackTrace();
    		}
	}


	public static void readFromUser(){
		int byteRead;
		try{
			while((byteRead = System.in.read()) != -1){
			socketOut.write(byteRead);
			socketOut.flush();
			}
			socket.shutdownOutput();
		} catch(IOException ioe){
		System.err.println("Unexpected exception when talking to server:");
		ioe.printStackTrace();
		}
	}
	public static void readFromServer(){
		int byteRead;
		try{
			while((byteRead = socketIn.read()) != -1){
			System.out.write(byteRead);
			System.out.flush();
			} 	
		} catch(IOException ioe){
		System.err.println("Unexpected exception when reading from server:");
		ioe.printStackTrace();
		}
	}
}
