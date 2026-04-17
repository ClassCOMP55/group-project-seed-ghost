import acm.graphics.GImage;
import acm.graphics.GObject;

public class Node {
private String type,combatAffinity;
int[] accessibleNodes;
int index,difficulty;
boolean cleared, isBoss;
Enemy boss;
String[] pool;
GImage sprite;


public Node(int i) {
	index = i;
	isBoss = false;
	assignType(i);
	accessibleNodes = setAvailableNodes();
	cleared = false;
	loadSprite();
	if (combatAffinity!=null) pool = getPool();
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
	if (i==13) isBoss=true;
}

public void assignCombatAffinity(int i) {
	combatAffinity = Chance.choose(new String[] {"combatFire","combatHoly","combatLightning","combatMagic",});
}

public void assignDifficulty(int i) {
	switch(i) {
	case 0: difficulty =1;break;
	case 3: difficulty =2;break;
	case 4: difficulty =2;break;
	case 5: difficulty =2;break;
	case 11: difficulty =3;break;
	case 12: difficulty =3;break;
	case 13: difficulty =10;break;
	
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
	case 13: return new int[]{0};
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

private String[] getPool() {
	String[][] temp = null;
	
	if (index>10 && index !=13) {
		switch(combatAffinity) {
		case "combatHoly":
			temp = Enemy.getHolyEnemiesHard();
			break;
		case "combatFire":
			temp = Enemy.getFireEnemiesHard();
			break;
		case "combatMagic":
			temp = Enemy.getMageEnemiesHard();
			break;
		case "combatLightning":
			temp = Enemy.getElecEnemiesHard();
			break;
		
		}
	}
	else if (index<10) {
		switch(combatAffinity) {
		case "combatHoly":
			temp = Enemy.getHolyEnemiesEasy();
			break;
		case "combatFire":
			temp = Enemy.getFireEnemiesEasy();
			break;
		case "combatMagic":
			temp = Enemy.getMageEnemiesEasy();
			break;
		case "combatLightning":
			temp = Enemy.getElecEnemiesEasy();
			break;
		}
	}
	else {
		temp = Enemy.getBossEnemies();
		int hornet = Chance.range(0, 5);
		
		if (hornet == 1) {
			boss = new Enemy ("boss_drip",10);
			sprite = new GImage("spr_BOSS_Hornet.png");
			sprite.setSize(70, 70);
			return temp[1];
		}
		
		switch(combatAffinity) {
		case "combatHoly":
			boss = new Enemy ("boss_seraphim",10);
			sprite = new GImage("spr_BOSS_Seraphim.png");
			sprite.setSize(70, 70);
			return temp[0];
		case "combatFire":
			boss = new Enemy ("boss_deathknight",10);
			sprite = new GImage("spr_BOSS_DeathKnight.gif");
			sprite.setSize(70, 70);
			return temp[3];
		case "combatMagic":
			boss = new Enemy ("boss_mage",10);
			sprite = new GImage("spr_BOSS_GreatMage.png");
			sprite.setSize(70, 70);
			return temp[2];
		case "combatLightning":
			boss = new Enemy ("boss_spiritofstorms",10);
			sprite = new GImage("spr_BOSS_SpiritofStorms.jpg");
			sprite.setSize(50, 50);
			return temp[4];
		}
	}
	
	int tier = Chance.range(0, temp.length-1);
	return temp[tier];
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
