package com.zelnehlun.network.packets;

public interface IPacketMap {

	public void registerPacket(IPacket packet);
	public IPacket getPacketById(int id);
	
}
