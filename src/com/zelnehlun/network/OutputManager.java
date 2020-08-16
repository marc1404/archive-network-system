package com.zelnehlun.network;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import com.zelnehlun.network.packets.IPacket;

public class OutputManager {

	private final Queue<IPacket> packetQueue = new LinkedList<IPacket>();
	private final INetworkManager networkManager;
	private final Socket socket;
	private final DataOutputStream out;
	private final Thread thread;
	private boolean running = true;
	
	public OutputManager(INetworkManager networkManager, Socket socket) throws Exception {
		this.networkManager = networkManager;
		this.socket = socket;
		this.out = new DataOutputStream(socket.getOutputStream());
		this.thread = new Thread(){
			@Override
			public void run(){
				while(running){
					try{
						onRun();
					}catch(Exception ex){
						ex.printStackTrace();
						onException();
					}
				}
			}
		};
		
		thread.start();
	}
	
	public void queuePacket(IPacket packet){
		packetQueue.add(packet);
	}
	
	public void stop(){
		this.running = false;
	}
	
	private void onRun() throws Exception {
		IPacket packet;
		
		while((packet = packetQueue.poll()) != null){
			packet.write(out);
		}
	}
	
	private void onException(){
		if(socket.isOutputShutdown() || socket.isClosed()){
			stop();
			networkManager.closeConnection();
		}
	}
	
}
