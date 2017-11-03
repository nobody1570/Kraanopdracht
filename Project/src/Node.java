
public class Node {

	Node boven;
	Node onderLinks;
	//nog niet gebruikt.
	//Node onderRechts;
	
	
	Item item;
	int x,y;
	
	public Node(int x, int y, Node onder) {
		
		this.x = x;
		this.y = y;
		
		if(onder!=null) {
		this.onderLinks = onder;
		onder.setBoven(this);
		}else {
		 this.onderLinks=null;
		}
	}

	public Node getBoven() {
		return boven;
	}

	public void setBoven(Node boven) {
		this.boven = boven;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	boolean hasItem() {
		
		return item!=null;
	}
	
	
}
