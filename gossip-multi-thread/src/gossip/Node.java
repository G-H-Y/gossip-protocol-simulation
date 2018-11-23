package gossip;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.net.InetAddress;

public class Node implements Runnable {
	private int node_id;
	private DatagramSocket socket;
	Receive receive;
	Send send;
	ArrayList<Integer> neighbor;
	BlockingQueue<Integer> mes = new ArrayBlockingQueue<Integer>(10);
	private int version;
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
		send.setVersion(version);
	}
	public Node(int node_id, DatagramSocket socket, ArrayList<Integer> neighbor,int version) {
		super();
		this.node_id = node_id;
		this.socket = socket;
		this.neighbor = neighbor;
		this.version = version;
	}
	public int getNode_id() {
		return node_id;
	}
	void init() {
		receive = new Receive(socket, mes);
		receive.init();
		new Thread(receive).start();
		send = new Send(socket, neighbor, version);
		new Thread(send).start();
	}
	@Override
	public void run() {
		while(true) {
			try {
				int v = mes.take();
				if(v > version) {
					version = v;
					send.setVersion(v);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}