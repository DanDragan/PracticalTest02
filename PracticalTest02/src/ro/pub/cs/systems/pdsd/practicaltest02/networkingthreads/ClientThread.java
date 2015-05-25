package ro.pub.cs.systems.pdsd.practicaltest02.networkingthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.pdsd.practicaltest02.general.Constants;
import ro.pub.cs.systems.pdsd.practicaltest02.general.Utilities;
import android.util.Log;
import android.widget.TextView;

public class ClientThread extends Thread {
	
	private String   address;
	private int      port;
	private String hours;
	private String minute;
	private String command;
	
	private Socket   socket;
	
	public ClientThread(
			String address,
			int port, String hours, String minute, String command
			) {
		this.address                 = address;
		this.port                    = port;
		this.hours = hours;
		this.minute = minute;
		this.command = command;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(address, port);
			if (socket == null) {
				Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
			}
			
			BufferedReader bufferedReader = Utilities.getReader(socket);
			PrintWriter    printWriter    = Utilities.getWriter(socket);
			if (bufferedReader != null && printWriter != null) {
				//TODO write request
				printWriter.println(command);
				printWriter.flush();
				printWriter.println(hours);
				printWriter.flush();
				printWriter.println(minute);
				printWriter.flush();
				
				//TODO read response
				String req;
				while ((req = bufferedReader.readLine()) != null) {
					Log.e("req is ", req);
				}
			} else {
				Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
			}
			socket.close();
		} catch (IOException ioException) {
			Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
			if (Constants.DEBUG) {
				ioException.printStackTrace();
			}
		}
	}

}
