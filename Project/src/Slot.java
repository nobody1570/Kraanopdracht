
public class Slot {

    private final int id;
    private final int centerX, centerY, z;
    private Item item;
    private final SlotType type;
    private Interval interval;

    public Slot(int id, int centerX, int centerY, int xMin, int xMax, int yMin, int yMax, int z, SlotType type, Item item) {
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
        this.interval = new Interval(xMin, xMax, yMin, yMax);
        this.z = z;
        this.item = item;
        this.type = type;
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

    public int getXMin() {
        return interval.xMin;
    }

    public int getXMax() {
        return interval.xMax;
    }

    public int getYMin() {
        return interval.yMin;
    }

    public int getYMax() {
        return interval.yMax;
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
}
