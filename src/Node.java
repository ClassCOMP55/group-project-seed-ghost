import acm.graphics.GObject;

public class Node {
String type;
int[] accessibleNodes = {1, 2};

public Node(int i) {
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
	
	


}
