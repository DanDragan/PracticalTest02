package ro.pub.cs.systems.pdsd.practicaltest02.networkingthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ro.pub.cs.systems.pdsd.practicaltest02.general.Constants;
import ro.pub.cs.systems.pdsd.practicaltest02.general.Utilities;
import ro.pub.cs.systems.pdsd.practicaltest02.model.Alarm;
import android.util.Log;

public class CommunicationThread extends Thread {
	
	private ServerThread serverThread;
	private Socket       socket;
	
	public CommunicationThread(ServerThread serverThread, Socket socket) {
		this.serverThread = serverThread;
		this.socket       = socket;
	}
	
	@Override
	public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter    printWriter    = Utilities.getWriter(socket);
				if (bufferedReader != null && printWriter != null) {
					Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client!");
					Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
					String ip = socket.getInetAddress().toString();
					String command = bufferedReader.readLine();
					HashMap<String, Alarm> data = serverThread.getData();
					Alarm alarm = new Alarm();
					Log.i("command", command);
					if (command.equals("set")) {
						String hour            = bufferedReader.readLine();
						String minute 	= bufferedReader.readLine();
						alarm.setHour(hour);
						alarm.setMinute(minute);
						data.put(ip, alarm);
					} else if (command.equals("reset")) {
						data.remove(ip);
					} else if (command.equals("poll")) {
						HttpClient httpClient = new DefaultHttpClient();
						HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
						ResponseHandler<String> responseHandler = new BasicResponseHandler();
						String time = httpClient.execute(httpPost, responseHandler);
						time.substring(time.indexOf("T") + 1);
						String newHour = time.substring(0,  2);
						String newMinute = time.substring(4,6);
						String hour= null;
						String minute = null;
						if (data.containsKey(ip)) {
							Alarm alarma = data.get(ip);
							hour = alarma.getHour();
							minute = alarma.getMinute();
						}
						Log.e("time is ", newHour + " " + newMinute);
						if (hour == null || hour.isEmpty() || minute == null || minute.isEmpty()) {
							printWriter.println("none");
							printWriter.flush();
						}
						else if (Integer.parseInt(hour) < Integer.parseInt(hour)) {
							printWriter.println("inactive");
							printWriter.flush();
						} else if (Integer.parseInt(hour) > Integer.parseInt(hour)) {
							printWriter.println("active");
							printWriter.flush();
						}
					}
					//TODO parse request

					//TODO write response

				} else {
					Log.e(Constants.TAG, "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
				}
				socket.close();
			} catch (IOException ioException) {
				Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
				if (Constants.DEBUG) {
					ioException.printStackTrace();
				}
			} 
		} else {
			Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
		}
	}

}
