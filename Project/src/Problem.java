
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Problem {

	private final int minX, maxX, minY, maxY;
	private final int maxLevels;
	private final List<Item> items;
	private final List<Job> inputJobSequence;
	private final List<Job> outputJobSequence;

	private final List<Gantry> gantries;
	private final List<Slot> slots;
	private final int safetyDistance;
	private final int pickupPlaceDuration;
	private static HashMap<Integer, Slot> map = new HashMap<>(20000);

	public Problem(int minX, int maxX, int minY, int maxY, int maxLevels, List<Item> items, List<Gantry> gantries,
			List<Slot> slots, List<Job> inputJobSequence, List<Job> outputJobSequence, int gantrySafetyDist,
			int pickupPlaceDuration, HashMap<Integer, Slot> map) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.maxLevels = maxLevels;
		this.items = new ArrayList<>(items);
		this.gantries = new ArrayList<>(gantries);
		this.slots = new ArrayList<>(slots);
		this.inputJobSequence = new ArrayList<>(inputJobSequence);
		this.outputJobSequence = new ArrayList<>(outputJobSequence);
		this.safetyDistance = gantrySafetyDist;
		this.pickupPlaceDuration = pickupPlaceDuration;
		this.map = map;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMaxLevels() {
		return maxLevels;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Job> getInputJobSequence() {
		return inputJobSequence;
	}

	public List<Job> getOutputJobSequence() {
		return outputJobSequence;
	}

	public List<Gantry> getGantries() {
		return gantries;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public int getSafetyDistance() {
		return safetyDistance;
	}

	public int getPickupPlaceDuration() {
		return pickupPlaceDuration;
	}

	public void writeJsonFile(File file) throws IOException {
		JSONObject root = new JSONObject();

		JSONObject parameters = new JSONObject();
		root.put("parameters", parameters);

		parameters.put("gantrySafetyDistance", safetyDistance);
		parameters.put("maxLevels", maxLevels);
		parameters.put("pickupPlaceDuration", pickupPlaceDuration);

		JSONArray items = new JSONArray();
		root.put("items", items);

		for (Item item : this.items) {
			JSONObject jo = new JSONObject();
			jo.put("id", item.getId());

			items.add(jo);
		}

		JSONArray slots = new JSONArray();
		root.put("slots", slots);
		for (Slot slot : this.slots) {
			JSONObject jo = new JSONObject();
			jo.put("id", slot.getId());
			jo.put("cx", slot.getCenterX());
			jo.put("cy", slot.getCenterY());
			// jo.put("minX",slot.getXMin());
			// jo.put("maxX",slot.getXMax());
			// jo.put("minY",slot.getYMin());
			// jo.put("maxY",slot.getYMax());
			jo.put("z", slot.getZ());
			jo.put("type", slot.getType().name());
			jo.put("itemId", slot.getItem() == null ? null : slot.getItem().getId());

			slots.add(jo);
		}

		JSONArray gantries = new JSONArray();
		root.put("gantries", gantries);
		for (Gantry gantry : this.gantries) {
			JSONObject jo = new JSONObject();

			jo.put("id", gantry.getId());
			jo.put("xMin", gantry.getXMin());
			jo.put("xMax", gantry.getXMax());
			jo.put("startX", gantry.getStartX());
			jo.put("startY", gantry.getStartY());
			jo.put("xSpeed", gantry.getXSpeed());
			jo.put("ySpeed", gantry.getYSpeed());

			gantries.add(jo);
		}

		JSONArray inputSequence = new JSONArray();
		root.put("inputSequence", inputSequence);

		for (Job inputJ : this.inputJobSequence) {
			JSONObject jo = new JSONObject();
			jo.put("itemId", inputJ.getItem().getId());
			jo.put("fromId", inputJ.getPickup().getSlot().getId());

			inputSequence.add(jo);
		}

		JSONArray outputSequence = new JSONArray();
		root.put("outputSequence", outputSequence);

		for (Job outputJ : this.outputJobSequence) {
			JSONObject jo = new JSONObject();
			jo.put("itemId", outputJ.getItem().getId());
			jo.put("toId", outputJ.getPlace().getSlot().getId());

			outputSequence.add(jo);
		}

		try (FileWriter fw = new FileWriter(file)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			fw.write(gson.toJson(root));
		}

	}

	public static Problem fromJson(File file) throws IOException, ParseException {

		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader(file)) {
			JSONObject root = (JSONObject) parser.parse(reader);

			List<Item> itemList = new ArrayList<>();
			List<Slot> slotList = new ArrayList<>();
			List<Gantry> gantryList = new ArrayList<>();
			List<Job> inputJobList = new ArrayList<>();
			List<Job> outputJobList = new ArrayList<>();

			JSONObject parameters = (JSONObject) root.get("parameters");
			int safetyDist = ((Long) parameters.get("gantrySafetyDistance")).intValue();
			int maxLevels = ((Long) parameters.get("maxLevels")).intValue();
			int pickupPlaceDuration = ((Long) parameters.get("pickupPlaceDuration")).intValue();

			JSONArray items = (JSONArray) root.get("items");
			for (Object o : items) {
				int id = ((Long) ((JSONObject) o).get("id")).intValue();

				Item c = new Item(id);
				itemList.add(c);
			}

			int overallMinX = Integer.MAX_VALUE, overallMaxX = Integer.MIN_VALUE;
			int overallMinY = Integer.MAX_VALUE, overallMaxY = Integer.MIN_VALUE;

			JSONArray slots = (JSONArray) root.get("slots");
			for (Object o : slots) {
				JSONObject slot = (JSONObject) o;

				int id = ((Long) slot.get("id")).intValue();
				int cx = ((Long) slot.get("cx")).intValue();
				int cy = ((Long) slot.get("cy")).intValue();
				int minX = ((Long) slot.get("minX")).intValue();
				int minY = ((Long) slot.get("minY")).intValue();
				int maxX = ((Long) slot.get("maxX")).intValue();
				int maxY = ((Long) slot.get("maxY")).intValue();
				int z = ((Long) slot.get("z")).intValue();

				overallMinX = Math.min(overallMinX, minX);
				overallMaxX = Math.max(overallMaxX, maxX);
				overallMinY = Math.min(overallMinY, minY);
				overallMaxY = Math.max(overallMaxY, maxY);

				Slot.SlotType type = Slot.SlotType.valueOf((String) slot.get("type"));
				Integer itemId = slot.get("itemId") == null ? null : ((Long) slot.get("itemId")).intValue();
				Item c = itemId == null ? null : itemList.get(itemId);

				Slot s = new Slot(id, cx, cy, minX, maxX, minY, maxY, z, type, c);
				if (itemId == null) {
				} else {
					map.put(itemId, s);
				}
				slotList.add(s);
			}

			JSONArray gantries = (JSONArray) root.get("gantries");
			for (Object o : gantries) {
				JSONObject gantry = (JSONObject) o;

				int id = ((Long) gantry.get("id")).intValue();
				int xMin = ((Long) gantry.get("xMin")).intValue();
				int xMax = ((Long) gantry.get("xMax")).intValue();
				int startX = ((Long) gantry.get("startX")).intValue();
				int startY = ((Long) gantry.get("startY")).intValue();
				double xSpeed = ((Double) gantry.get("xSpeed")).doubleValue();
				double ySpeed = ((Double) gantry.get("ySpeed")).doubleValue();

				Gantry g = new Gantry(id, xMin, xMax, startX, startY, xSpeed, ySpeed);
				gantryList.add(g);
			}

			JSONArray inputJobs = (JSONArray) root.get("inputSequence");
			int jid = 0;
			for (Object o : inputJobs) {
				JSONObject inputJob = (JSONObject) o;

				int iid = ((Long) inputJob.get("itemId")).intValue();
				int sid = ((Long) inputJob.get("fromId")).intValue();

				Job job = new Job(jid++, itemList.get(iid), slotList.get(sid), null);
				inputJobList.add(job);
			}

			JSONArray outputJobs = (JSONArray) root.get("outputSequence");
			for (Object o : outputJobs) {
				JSONObject outputJob = (JSONObject) o;

				int iid = ((Long) outputJob.get("itemId")).intValue();
				int sid = ((Long) outputJob.get("toId")).intValue();

				Job job = new Job(jid++, itemList.get(iid), null, slotList.get(sid));
				outputJobList.add(job);
			}

			return new Problem(overallMinX, overallMaxX, overallMinY, overallMaxY, maxLevels, itemList, gantryList,
					slotList, inputJobList, outputJobList, safetyDist, pickupPlaceDuration, map);

		}

	}

	/*
	 * methode om de verbanden tussen de verschillende slots te leggen. we kunnen
	 * hiervoor een
	 */
	public void makeTree() {
		for (Slot slot : slots) {
			for (Slot temp : slots) {
				if (slot.getZ() == temp.getZ() + 1) {
					slot.compare(temp);
				}
			}
		}
	}

	// voor effectief op te lossen hieronder.

	// houdt ruimtelijke positie slots bij
	// x,y,z
	List<CoordinateList<CoordinateList<Slot>>> field;

	// houdt voor alle items bij in welke slots ze zitten (behalve voor input en
	// output)
	HashMap<Item, Slot> itemPositions = new HashMap<Item, Slot>();

	Slot input;
	Slot output;

	public void prepareSolve() {

		field = new ArrayList<CoordinateList<CoordinateList<Slot>>>();

		for (Slot slot : slots) {
			if (slot.getType().equals(Slot.SlotType.STORAGE)) {

				// slot opslaan
				// als er een item in zit.

				if (slot.getItem() != null) {

					itemPositions.put(slot.getItem(), slot);

				}

				// slot plaatsen

				int x = slot.getCenterX();
				int y = slot.getCenterY();

				boolean foundx = false;
				boolean foundy = false;

				for (CoordinateList<CoordinateList<Slot>> xList : field) {

					if (xList.getCoordinaat() == x) {
						foundx = true;
						for (CoordinateList<Slot> yList : xList) {

							if (yList.getCoordinaat() == y) {

								foundy = true;
								yList.add(slot);
								yList.sort(null);

							}

						}

						if (foundx && !foundy) {

							CoordinateList<Slot> listY = new CoordinateList<Slot>();
							listY.add(slot);
							listY.setCoordinaat(y);
							xList.add(listY);
							xList.sort(null);

							for (int i = 0; i < xList.size(); i++) {

								xList.get(i).setPlaceInUpperList(i);
							}

						}

					}
				}

				if (!foundx) {

					CoordinateList<CoordinateList<Slot>> list = new CoordinateList<CoordinateList<Slot>>();
					CoordinateList<Slot> yList = new CoordinateList<Slot>();
					list.setCoordinaat(x);
					yList.setCoordinaat(y);
					yList.add(slot);
					list.add(yList);
					field.add(list);
					field.sort(null);

					for (int i = 0; i < field.size(); i++) {

						field.get(i).setPlaceInUpperList(i);
					}

				}

			} else

			if (slot.getType().equals(Slot.SlotType.INPUT)) {

				input = slot;

			} else

			if (slot.getType().equals(Slot.SlotType.OUTPUT)) {

				output = slot;

			}

		}

		// System.out.println(field);
		// System.out.println(itemPositions);

	}

	
	int inputJob=0,outputJob=0;
	double time=0;
	public void solve() {

		
		
		/*
		 * 
		 * code gebruikt om hasItemsAbove uit te testen-->werkt!
		for(Item i:itemPositions.keySet()) {
			
			
			System.out.println(i.getId()+" "+hasItemsAbove(i));
			
			
		}
		*/
		
		//eigenlijke code-->deze houdt rekening met de volgorde v/d input/output
		
		//alle outputjobs in volgorde uitvoeren
		Job j;
		while(outputJob<outputJobSequence.size()) {
			j=outputJobSequence.get(outputJob);
			Item i=j.getItem();
			//kijken of het in field zit.
			if(itemPositions.containsKey(i)) {
				
				if(!hasItemsAbove(i)) {
					
					//move i to output
					moveToOutput(i);
					
					outputJob++;
					
				}else {
					
					//place items above i somewhere else
					
					moveItemsAbove(i);
					
					//move i to output
					moveToOutput(i);
					
					
					outputJob++;
					
				}
				
				
			}else {
				//een nieuw item binnenbrengen
				
				//als volgende input gelijk is aan nodige output
				if(inputJobSequence.get(inputJob).getItem().getId()==i.getId()) {
					//move i from input to output
					moveFromInputToOutput(i);
					
					inputJob++;
					outputJob++;
					
				}else {
					//het item binnenbrengen
					
					putInFieldFromInput(inputJobSequence.get(inputJob).getItem());
					
					inputJob++;
				}
				
				
			}
			
			
		}
		
		//eventueele overblijvende inputjobs uitvoeren
		while(inputJob<inputJobSequence.size()) {
			//een item binnenbrengen
			
			putInFieldFromInput(inputJobSequence.get(inputJob).getItem());
			inputJob++;
			
			
			
		}
		
		
	}
	
	
	private void moveItemsAbove(Item i) {
		// TODO Auto-generated method stub
		
	}

	private void putInFieldFromInput(Item item) {
		// TODO Auto-generated method stub
		
	}

	private void moveFromInputToOutput(Item i) {
		// TODO Auto-generated method stub
		
	}

	private void moveToOutput(Item i) {
		// TODO Auto-generated method stub
		
	}
	
	private void printMovement(int gantryID,int x,int y, Item item) {
		
		
	}

	//hasItemsAbove returns true if the item has an item above it.
	//--> if false item can be taken
	boolean hasItemsAbove(Item item) {
		boolean above=false;
		
		Slot s= itemPositions.get(item);
		
		int x=s.getCenterX();
		int y=s.getCenterY();
		int z=s.getZ();
		
		CoordinateList <CoordinateList<Slot>> yRow = null;
		CoordinateList<Slot> zRow = null;
		for(CoordinateList <CoordinateList<Slot>> row:field) {
			
			if(row.getCoordinaat()==x) {
				
				yRow=row;
				break;
			}
			
		}
		
		for(CoordinateList<Slot> row:yRow) {
			
			if(row.getCoordinaat()==y) {
				
				zRow=row;
				break;
			}
			
		}
		
		System.out.println(zRow);
		int maxZposition=zRow.size()-1;
		int maxZ=zRow.get(maxZposition).getZ();
		
		
		if(z == maxZ)above = false;
		else {
			
			int position=0;
			for(int i=0;i<maxZ;i++) {
				
				if(zRow.get(i).getId()==s.getId()) {
					position=i;
					break;
				}
			}
			
			
			above=zRow.get(position+1).getItem()!=null;
			
		}
		
		
		return above;
	}
	
	

}
