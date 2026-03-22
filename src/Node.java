import acm.graphics.GObject;

public class Node {
String type;
int[] accessibleNodes = {1, 2};

public Node(int i) {
	assignType(i);
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
	
	


}
