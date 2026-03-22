import acm.graphics.GObject;

public class Node {
String type;
int[] accessibleNodes;
int index;

public Node(int i) {
	index = i;
	assignType(i);
	accessibleNodes = setAvailableNodes();
}

public String getRandomType() {
	
	int randomNum = Chance.range(0, 2);
	
	switch(randomNum) {
	case 0: return "Shop";
	case 1: return "CampFire";
	case 2: return "Loot";
	}
	return "n/a";
	
}

public void assignType(int i) {
	
	switch(i) {
	case 0: type = "Combat";
	case 1: type = getRandomType();
	case 2: type = getRandomType();
	case 3: type = "Combat";
	case 4: type = "Combat";
	case 5: type = "Combat";
	case 6: type = getRandomType();
	case 7: type = getRandomType();
	case 8: type = getRandomType();
	case 9: type = getRandomType();
	case 10: type = getRandomType();
	case 11: type = "Combat";
	case 12: type = "Combat";
	case 13: type = "Combat";
	}
	
	
}

public int[] setAvailableNodes() {
			
	switch(index){
	case 0: return new int[]{1,2};
	case 1: return new int[]{3,4};
	case 2: return new int[]{4,5};
	case 3: return new int[]{6,7};
	case 4: return new int[]{8};
	case 5: return new int[]{9,10};
	case 6: return new int[]{11};
	case 7: return new int[]{11};
	case 8: return new int[]{11,12};
	case 9: return new int[]{12};
	case 10: return new int[]{12};
	case 11: return new int[]{13};
	case 12: return new int[]{13};
	}
	return null;
}
	
	


}
