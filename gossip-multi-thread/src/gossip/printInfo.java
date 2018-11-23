package gossip;

import javax.net.ssl.SSLException;

public class printInfo implements Runnable{
	Node[] nodes;

	public printInfo(Node[] nodes) {
		super();
		this.nodes = nodes;
	}
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				for(Node node:nodes) {
					System.out.println("Version of Node "+ node.getNode_id() +": "+node.getVersion());
				}
				System.out.println();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
