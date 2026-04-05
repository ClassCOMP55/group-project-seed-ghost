import acm.graphics.GImage;
import acm.graphics.GObject;

public class Node {
String type,combatAffinity;
int[] accessibleNodes;
int index,difficulty;
boolean cleared;
GImage sprite;


public Node(int i) {
	index = i;
	assignType(i);
	accessibleNodes = setAvailableNodes();
	cleared = false;
	loadSprite();
}

public boolean isCleared() {
	return cleared;
}

public void setCleared(boolean cleared) {
	this.cleared = cleared;
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
	case 0: type = "Combat";assignCombatAffinity(i);assignDifficulty(i);break;
	case 1: type = getRandomType();break;
	case 2: type = getRandomType();break;
	case 3: type = "Combat"; assignCombatAffinity(i);assignDifficulty(i);break;
	case 4: type = "Combat"; assignCombatAffinity(i);assignDifficulty(i);break;
	case 5: type = "Combat"; assignCombatAffinity(i);assignDifficulty(i);break;
	case 6: type = getRandomType(); break;
	case 7: type = getRandomType(); break;
	case 8: type = getRandomType(); break;
	case 9: type = getRandomType(); break;
	case 10: type = getRandomType() ;break;
	default: type = "Combat";assignCombatAffinity(i);assignDifficulty(i);
	}		
}

public void assignCombatAffinity(int i) {
	combatAffinity = Chance.choose(new String[] {"combatFire","combatHoly","combatLightning","combatMagic",});
}

public void assignDifficulty(int i) {
	switch(i) {
	case 0: difficulty =1;break;
	case 3: difficulty =5;break;
	case 4: difficulty =5;break;
	case 5: difficulty =5;break;
	case 11: difficulty =15;break;
	case 12: difficulty =15;break;
	case 13: difficulty =30;break;
	
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

public boolean hasAccess(int i) {
	for (int n:accessibleNodes) {
		if (n==i)return true;
	}
	return false;
}

public void loadSprite() {
	switch(type) {
	case "Shop": sprite = new GImage("ShopNode.png"); break;
	case "Loot": sprite = new GImage("LootNode.png"); break;
	case "CampFire": sprite = new GImage("CampFireNodeUpdated.png"); break;
	}
	
	if (type =="Combat") {
		switch (combatAffinity) {
		case "combatMagic": sprite = new GImage("spr_CombatNode_MAGIC.png"); break;
		case "combatFire": sprite = new GImage("spr_CombatNode_FIRE.png"); break;
		case "combatHoly": sprite = new GImage("spr_CombatNode_HOLY.png"); break;
		case "combatLightning": sprite = new GImage("spr_CombatNode_LIGHTNING.png"); break;
		}
	}
	sprite.setSize(50, 50);
}


public String getCombatAffinity() {
	return combatAffinity;
}

public void setCombatAffinity(String combatAffinity) {
	this.combatAffinity = combatAffinity;
}

public int[] getAccessibleNodes() {
	return accessibleNodes;
}

public void setAccessibleNodes(int[] accessibleNodes) {
	this.accessibleNodes = accessibleNodes;
}

public int getIndex() {
	return index;
}

public void setIndex(int index) {
	this.index = index;
}

public int getDifficulty() {
	return difficulty;
}

public void setDifficulty(int difficulty) {
	this.difficulty = difficulty;
}

public void setType(String type) {
	this.type = type;
}

public String getType() {
	return type;
}

public void cleared() {
	cleared =true;
}

public GImage getSprite() {
	return sprite;
}
	


}
