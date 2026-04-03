/*
 * This class exists as a template for other skills
 */


public class Skill {
	private String name, description, vTarget;
	private double manaCost;
	
	public Skill () {
		setName("DEFAULT_SKILL");
		setDescription("A dummy skill that does nothing. If you're seeing this, something went wrong.");
		manaCost = 0;
		vTarget = "NA";
	}
	
	public Skill (double mana_cost) {
		setName("DEFAULT_SKILL");
		setDescription("A dummy skill that does nothing. If you're seeing this, something went wrong.");
		manaCost = mana_cost;
		vTarget = "NA";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public boolean preconditionsMet (Entity self, Entity target) {
		return self.getMana() >= getManaCost();
	}
	
	/*
	 * Runs the skill's activation. OVERRIDE
	 */
	public void activationEffect (Entity self, Entity target) {
		
	}
	
	/*
	 * Important for Guard and other temporary effects.
	 */
	public void nextTurnEffect (Entity self, Entity target) {
		
	}

	public double getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public String getvTarget() {
		return vTarget;
	}

	public void setvTarget(String vTarget) {
		this.vTarget = vTarget;
	}
}
