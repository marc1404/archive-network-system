package com.zelnehlun.network;

import com.zelnehlun.network.listener.INetworkListener;
import com.zelnehlun.network.packets.IPacketMap;

public interface INetworkManager {

	public boolean connect(String ip, int port);
	public boolean listen(int port);
	public boolean listen(int port, boolean local);
	public IPacketMap getPacketMap();
	public void registerNetworkListener(INetworkListener networkListener);
	public void closeConnection();
	public void stop();
	
}
