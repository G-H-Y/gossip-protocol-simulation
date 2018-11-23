package gossip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

public class Send implements Runnable {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private InetAddress address;
	private int port;
	private ArrayList<Integer> neighbor;
	private int version;
	
	public Send( DatagramSocket socket,ArrayList<Integer> neighbor,int version) {
		super();
		this.socket = socket;
		this.neighbor = neighbor;
		this.version = version;
		try {
			this.address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run()
	{
		byte[] message;
		int i = 0;
		while(true) {
			message = InttoByte(version);
			packet = new DatagramPacket(message, message.length, address, port);
			int randtime = (int)(Math.random()*1000+500);
			Random r = new Random(i ++);
			int randNerb = RandomInteger.randInt(0, neighbor.size()-1);
			//System.out.println("Node: "+node_id+"  "+version);
			try {
				Thread.sleep(randtime);
				packet = new DatagramPacket(InttoByte(version),4,address,neighbor.get(randNerb));
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Node: "+node_id+" end at "+rand+"ms.");
		}
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public byte[] InttoByte(int num)
	{
		byte[] res = new byte[4];
		res[3] = (byte) (num & 0xff);
		res[2] = (byte) ((num >> 8) & 0xff);
		res[1] = (byte) ((num >> 16) & 0xff);
		res[0] = (byte) ((num >> 24) & 0xff);
		return res;
	}
}
