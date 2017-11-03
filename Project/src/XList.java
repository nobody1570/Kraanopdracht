import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class XList {
	
	//bevat lists van Nodes op hoogte z voor coördinaat x
	ArrayList<ArrayList<Node>> al;
	
	int xCoordinaat;
	
	
	
	public XList(int xCoordinaat) {
		
		this.al = new ArrayList<ArrayList<Node>>();
		this.xCoordinaat = xCoordinaat;
	}
	
	public int getxCoordinaat() {
		return xCoordinaat;
	}
	public void setxCoordinaat(int xCoordinaat) {
		this.xCoordinaat = xCoordinaat;
	}
	public ArrayList<ArrayList<Node>> getAl() {
		return al;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xCoordinaat;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XList other = (XList) obj;
		if (xCoordinaat != other.xCoordinaat)
			return false;
		return true;
	}
	
	

}
