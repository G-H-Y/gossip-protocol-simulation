package gossip;

import java.awt.List;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/*************************
 * 5-1-2
 * |   |
 * 6-3-4
 *  \|  \
 *   7-8-9
 *   
 * total nodes: 9
 * total edges: 11
 ********************/

public class  Gossip{
	public static void main(String[] args) {		
		int[][] graph = {{1,4}, {0,3}, {3,5,6}, {1,2,8}, {0, 5}, {2, 4, 6}, {2,5,7}, {6,8}, {3,7}};
		int neiborNum[] = {2, 2, 3, 3, 2, 3, 3, 2, 2};
		Node[] nodes = new Node[9]; 
		Thread[] t = new Thread[9];
		Random random = new Random();
		int[] ports = new int[9];
		for(int i = 0;i < 9;i ++) {
			int p = RandomInteger.randInt(10000, 60000);
			while (!IfPortUsed.available(p) && isUsed(ports,p)) {
				p = RandomInteger.randInt(10000, 60000);	
			}
			ports[i] = p;
		}
		
		for(int i=0; i<9; i++)
		{
			ArrayList<Integer> neighbor = new ArrayList<>();
			for(int j = 0;j < neiborNum[i];j ++) {
				neighbor.add(ports[graph[i][j]]);
			}
			DatagramSocket socket;
			try {
				socket = new DatagramSocket(ports[i]);
				nodes[i] = new Node(i+1, socket,neighbor,0);
				t[i] = new Thread(nodes[i]);
				t[i].start();
				nodes[i].init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Seed d = new Seed(nodes);
		Thread td = new Thread(d);
		td.start();
		new Thread(new printInfo(nodes)).start();
	}
	private static boolean isUsed(int[]ports,int p) {
		for(int port : ports) {
			if(port == p) {
				return false;
			}
		}
		return true;
	}
}