package com.zelnehlun.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IPacket {

	public int getId();
	public void read(DataInputStream in) throws Exception;
	public void write(DataOutputStream out) throws Exception;
	
}
