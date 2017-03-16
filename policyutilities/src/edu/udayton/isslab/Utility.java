package edu.udayton.isslab;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Utility 
{
	private static String server = null;
	private static String username = null;
	private static String password = null;
	private static MqttClient client = null;
	private static boolean connect = false;
	
	public static void init(String serv, String user, String pass){
		if(server==null || username ==null || password==null){
			server = serv;
			username = user;
			password = pass;
			connect = false;
		}
	}
	
	static boolean connect(){
		try{
			client = new MqttClient(server, "errorReport");
	        MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setCleanSession(true);
	        connOpts.setUserName(username);
	        connOpts.setPassword(password.toCharArray());
	        client.connect(connOpts);
	        connect=true;
		}catch(MqttException e){
	    	return false;
	    }
		return true;
	}
	
    public static void send(String error){
    	if(!connect){
    		connect = connect();
    	}
    	if(connect){
    		System.out.println("Message Created");
    		MqttMessage message = new MqttMessage(error.getBytes());
            try {
				client.publish("error", message);
			} catch (MqttPersistenceException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			}
    	}
    	
    }
}
