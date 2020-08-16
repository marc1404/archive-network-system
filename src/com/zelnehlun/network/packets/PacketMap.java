package com.zelnehlun.network.packets;

import java.util.HashMap;
import java.util.Map;

public class PacketMap implements IPacketMap {

	private final Map<Integer, IPacket> packets = new HashMap<Integer, IPacket>();
	
	@Override
	public void registerPacket(IPacket packet){
		packets.put(packet.getId(), packet);
	}

	@Override
	public IPacket getPacketById(int id){
		synchronized(packets){
			return packets.get(id);
		}
	}

}
