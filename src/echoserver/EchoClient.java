package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

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
		//Create the socket and open the streams
      		socket = new Socket(serverName, PORT_NUMBER);
     		socketIn = socket.getInputStream();
      		socketOut = socket.getOutputStream();

		//Create the thread that reads from the standard input and writes to the server
		Thread firstThread = new Thread(EchoClient::readFromUser);
		firstThread.start();

		//Create the thread that reads from the server and writes to the standard output
		Thread secondThread = new Thread(EchoClient::readFromServer);
		secondThread.start();

		//Ensure that the threads are not being closed before they are done communicating
     	 	firstThread.join();
		secondThread.join();

		//Close the socket
      		socket.close();
    	} catch (IOException ioe) {
      		System.err.println("Unexpected exception when talking to server:");
      		ioe.printStackTrace();
    		}
	}


	public static void readFromUser(){
		int byteRead;
		try{
			//Read from the standard input until it reaches the last byte
			while((byteRead = System.in.read()) != -1){
				socketOut.write(byteRead);
				//Ensure that all of the data has transferred
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
			//Read from the server until it reaches the last byte
			while((byteRead = socketIn.read()) != -1){
				System.out.write(byteRead);
				//Ensure that all the data has transferred
				System.out.flush();
			} 	
		} catch(IOException ioe){
			System.err.println("Unexpected exception when reading from server:");
			ioe.printStackTrace();
		}
	}
}
