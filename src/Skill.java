/*
 * This class exists as a template for other skills
 */


public class Skill {
	private String name;
	private double manaCost;
	
	public Skill () {
		setName("DEFAULT_SKILL");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * Tests to check if the preconditions are met. OVERRIDE
	 */
	public boolean preconditionsMet () {
		return true;
	}
	
	/*
	 * Runs the skill's activation. OVERRIDE
	 */
	public void activationEffect (Entity self, Entity target) {
		
	}

	public double getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
}
