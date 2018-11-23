package gossip;

public class Seed implements Runnable{
	int version;
	Node nodes[];
	public Seed(Node nodes[]) {
		this.version = 1;
		this.nodes = nodes;
	}
	
	@Override
	public void run() {
		while(true)
		{
			int rand = (int)(Math.random()*9);
			nodes[rand].setVersion(version);
			System.out.println("Node "+(rand+1)+"'s version is update to "+version+".");
			version++;
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}