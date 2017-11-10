package be.kul.gantry.domain;

<<<<<<< HEAD
public class Slot implements Comparable{
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Wim on 27/04/2015.
 */
public class Slot {
>>>>>>> fed119f9a6bab743b2ff5f9ae2ce4feb549d01a2

    private final int id;
    private final int centerX, centerY, z;
    private Item item;
    private final SlotType type;
    private Interval interval;
    private List<Slot> parents;
    private List<Slot> childeren;

    public Slot(int id, int centerX, int centerY, int xMin, int xMax, int yMin, int yMax, int z, SlotType type, Item item) {
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
        this.interval = new Interval(xMin, xMax, yMin, yMax);
        this.z = z;
        this.item = item;
        this.type = type;
        parents = new ArrayList<>();
        childeren = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getZ() {
        return z;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SlotType getType() {
        return type;
    }
    
   

    public static enum SlotType {
        INPUT,
        OUTPUT,
        STORAGE
    }

    public void addParent(Slot slot){
        parents.add(slot);
    }
    
    public void addChild(Slot slot){
        childeren.add(slot);
    }
    
    public boolean compare(Slot slot){
        return this.interval.compare(slot.interval);
    }
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Slot s=(Slot) o;
		return centerY-s.centerY;
	}

	@Override
	public String toString() {
		return "Slot [id=" + id + ", centerX=" + centerX + ", centerY=" + centerY + ", z=" + z + ", item=" + item + "]";
	}
	
	
    
    

    
    

}
