import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CoordinateList<E> extends ArrayList<E> implements Comparable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//bevat lists van Nodes op hoogte z voor coördinaat x
	ArrayList<ArrayList<Node>> al;
	
	int coordinaat;
	int placeInUpperList;
	
	
	
	public int getPlaceInUpperList() {
		return placeInUpperList;
	}
	public void setPlaceInUpperList(int placeInUpperList) {
		this.placeInUpperList = placeInUpperList;
	}
	public int getCoordinaat() {
		return coordinaat;
	}
	public void setCoordinaat(int xCoordinaat) {
		this.coordinaat = coordinaat;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coordinaat;
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
		CoordinateList other = (CoordinateList) obj;
		if (coordinaat != other.coordinaat)
			return false;
		return true;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		
		CoordinateList l=(CoordinateList)o;
		return coordinaat-l.coordinaat;
	}
	
	
	
	

}
