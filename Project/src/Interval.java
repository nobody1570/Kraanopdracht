

public class Interval {

	int xMax, xMin, yMax, yMin;
	
	public Interval (int xMin, int xMax, int yMin, int yMax) {
		this.xMin = xMin;
                this.xMax = xMax;
                this.yMin = yMin;
                this.yMax = yMax;
	}        
        
	
	/*
	 * returns true when this is greater then int1
	 */
	private boolean compareX(Interval inter) {
            if (this.xMin < inter.xMax && this.xMax > inter.xMin) return true;
            if (this.xMin < inter.xMin && this.xMax > inter.xMin) return true;
            if (this.xMin == this.xMin && this.xMax == inter.xMax) return true;
            
            return false;
	}
        
        private boolean compareY(Interval inter){
            if (this.yMin < inter.yMax && this.yMax > inter.yMin) return true;
            if (this.yMin < inter.yMin && this.yMax > inter.yMin) return true;
            if (this.yMin == this.yMin && this.yMax == inter.yMax) return true;
            
            return false;
        }
        
        public boolean compare(Interval inter){
            if (this.compareX(inter) && this.compareY(inter)) return true; 
            else return false;
        }

}
