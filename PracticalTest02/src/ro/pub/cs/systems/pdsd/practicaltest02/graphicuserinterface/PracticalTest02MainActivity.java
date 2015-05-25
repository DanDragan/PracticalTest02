package ro.pub.cs.systems.pdsd.practicaltest02.graphicuserinterface;

import ro.pub.cs.systems.pdsd.practicaltest02.R;
import ro.pub.cs.systems.pdsd.practicaltest02.general.Constants;
import ro.pub.cs.systems.pdsd.practicaltest02.networkingthreads.ClientThread;
import ro.pub.cs.systems.pdsd.practicaltest02.networkingthreads.ServerThread;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends Activity {
	
	// TODO Server widgets
	
	// TODO Client widgets
	
	private ServerThread serverThread             = null;
	private ClientThread clientThread             = null;
	private EditText     serverPortEditText 	= null;
	private EditText     clientAddressEditText    = null;
	private EditText     clientPortEditText       = null;
	private EditText     hours             = null;
	private EditText     minute            = null;
	private Button       connectButton            = null;
	private Button       alarmButton = null;
	private Button       pollButton = null;
	private Button 		resetButton = null;
	
	private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
	private class ConnectButtonClickListener implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			String serverPort = serverPortEditText.getText().toString();
			if (serverPort == null || serverPort.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Server port should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			serverThread = new ServerThread(Integer.parseInt(serverPort));
			if (serverThread.getServerSocket() != null) {
				serverThread.start();
			} else {
				Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not creat server thread!");
			}
			
		}
	}
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	private class ButtonClickListener implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			String clientAddress = clientAddressEditText.getText().toString();
			String clientPort    = clientPortEditText.getText().toString();
			if (clientAddress == null || clientAddress.isEmpty() ||
				clientPort == null || clientPort.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Client connection parameters should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			if (serverThread == null || !serverThread.isAlive()) {
				Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
				return;
			}
			
			//TODO get request info from UI
			
			clientThread = new ClientThread(
					clientAddress,
					Integer.parseInt(clientPort), hours.getText().toString(), minute.getText().toString(), "set");
			clientThread.start();
		}
	}
	
	private ButtonClickListener buttonClickListener2 = new ButtonClickListener();
	private class ButtonClickListener2 implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			String clientAddress = clientAddressEditText.getText().toString();
			String clientPort    = clientPortEditText.getText().toString();
			if (clientAddress == null || clientAddress.isEmpty() ||
				clientPort == null || clientPort.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Client connection parameters should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			if (serverThread == null || !serverThread.isAlive()) {
				Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
				return;
			}
			
			//TODO get request info from UI
			
			clientThread = new ClientThread(
					clientAddress,
					Integer.parseInt(clientPort), hours.getText().toString(), minute.getText().toString(), "poll");
			clientThread.start();
		}
	}
	
	private ButtonClickListener buttonClickListener3 = new ButtonClickListener();
	private class ButtonClickListener3 implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			String clientAddress = clientAddressEditText.getText().toString();
			String clientPort    = clientPortEditText.getText().toString();
			if (clientAddress == null || clientAddress.isEmpty() ||
				clientPort == null || clientPort.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Client connection parameters should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			if (serverThread == null || !serverThread.isAlive()) {
				Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
				return;
			}
			
			//TODO get request info from UI
			
			clientThread = new ClientThread(
					clientAddress,
					Integer.parseInt(clientPort), hours.getText().toString(), minute.getText().toString(), "reset");
			clientThread.start();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_main);
		
		//TODO init widgets
	}
	
	@Override
	protected void onDestroy() {
		if (serverThread != null) {
			serverThread.stopThread();
		}
		super.onDestroy();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test02_main, menu);
		serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
		connectButton = (Button)findViewById(R.id.connect_button);
		connectButton.setOnClickListener(connectButtonClickListener);
		
		clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
		clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
		hours = (EditText)findViewById(R.id.editText1);
		minute = (EditText)findViewById(R.id.editText2);
		alarmButton = (Button)findViewById(R.id.button1);
		alarmButton.setOnClickListener(buttonClickListener);
		pollButton = (Button)findViewById(R.id.button3);
		pollButton.setOnClickListener(buttonClickListener2);
		resetButton = (Button)findViewById(R.id.button2);
		resetButton.setOnClickListener(buttonClickListener3);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
