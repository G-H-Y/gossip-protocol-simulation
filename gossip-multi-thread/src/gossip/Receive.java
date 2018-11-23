package gossip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class Receive implements Runnable{
	private DatagramSocket socket;
	private DatagramPacket packet;
	BlockingQueue<Integer> mes;
	public Receive(DatagramSocket socket, BlockingQueue<Integer> mes) {
		super();
		this.socket = socket;
		this.mes = mes;
	}
	byte[] rcv;
	void init() {
		rcv = new byte[4];
		packet = new DatagramPacket(rcv,rcv.length);
	}
	@Override
	public void run() {
		while(true){
			try {
				socket.receive(packet);
				mes.add(getSequenceNumber(rcv));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public int getSequenceNumber(byte[] bytes) {
		return bytes[3] & 0xFF | (bytes[2] & 0xFF) << 8 |
				(bytes[1] & 0xFF) << 16 | (bytes[0] & 0xFF) << 24; 
	}
	
}
