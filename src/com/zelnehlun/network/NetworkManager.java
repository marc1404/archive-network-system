package com.zelnehlun.network;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import com.zelnehlun.network.listener.INetworkListener;
import com.zelnehlun.network.packets.IPacketMap;
import com.zelnehlun.network.packets.PacketMap;

public class NetworkManager implements INetworkManager {

	private final Collection<INetworkListener> networkListeners = new ArrayList<INetworkListener>();
	private final IPacketMap packetMap = new PacketMap();
	private Socket socket;
	private ServerSocket server;
	private InputManager inputManager;
	private OutputManager outputManager;
	
	@Override
	public boolean connect(String ip, int port){
		try{
			this.socket = new Socket(ip, port);
			this.inputManager = new InputManager(this, socket);
			this.outputManager = new OutputManager(this, socket);
		}catch(Exception ex){
			ex.printStackTrace();
			
			return false;
		}
		
		return true;
	}

	@Override
	public boolean listen(int port){
		return listen(port, false);
	}
	
	@Override
	public boolean listen(int port, boolean local){
		try{
			if(local){
				this.server = new ServerSocket(port, 0, InetAddress.getByName("127.0.0.1"));
			}else{
				this.server = new ServerSocket(port);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return false;
		}
		
		return true;
	}

	@Override
	public IPacketMap getPacketMap(){
		return packetMap;
	}

	@Override
	public void registerNetworkListener(INetworkListener networkListener){
		networkListeners.add(networkListener);
	}
	
	@Override
	public void closeConnection(){
		if(socket != null){
			try{
				socket.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		for(INetworkListener networkListener:networkListeners){
			networkListener.onConnectionClose();
		}
	}
	
	@Override
	public void stop(){
		if(inputManager != null){
			inputManager.stop();
		}
		
		if(outputManager != null){
			outputManager.stop();
		}
		
		closeConnection();
		
		if(server != null){
			try{
				server.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

}
