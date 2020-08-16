package com.zelnehlun.network;

import java.io.DataInputStream;
import java.net.Socket;

import com.zelnehlun.network.packets.IPacket;
import com.zelnehlun.network.packets.IPacketMap;

public class InputManager {

	private final INetworkManager networkManager;
	private final IPacketMap packetMap;
	private final Socket socket;
	private final DataInputStream in;
	private final Thread thread;
	private boolean running = true;
	
	public InputManager(INetworkManager networkManager, Socket socket) throws Exception {
		this.networkManager = networkManager;
		this.packetMap = networkManager.getPacketMap();
		this.socket = socket;
		this.in = new DataInputStream(socket.getInputStream());
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
	
	public void stop(){
		this.running = false;
	}
	
	private void onRun() throws Exception {
		int id = in.readInt();
		IPacket packet = packetMap.getPacketById(id);
		
		if(packet != null){
			packet.read(in);
		}
	}
	
	private void onException(){
		if(socket.isInputShutdown() || socket.isClosed()){
			stop();
			networkManager.closeConnection();
		}
	}
	
}
