import java.util.HashMap;
import java.util.Map;

public class GameSounds {

	// Basic sounds
	public static final String MAP_NODE_ATTACH = "SFX_map_node.wav";
	public static final String CAMPFIRE_LOOP = "CampFirecrackling.wav";
	public static final String GAME_OVER = "SFX_game_over.wav";
	public static final String ENEMY_ACTION = "SFX_enemy_action.wav";
	public static final String CONSUMABLE_USE = "SFX_consumable.wav";
	public static final String VICTORY_STINGER = "SuccessSoundEffect.wav";

	// Each class/profession gets its own sound
	private static Map<String, String> professionSound = new HashMap<String, String>();

	// Optional: skill specific sounds (uses Skill.getName())
	private static Map<String, String> skillSound = new HashMap<String, String>();

	static {
		// profession sounds (you already have these files in Audio/)
		professionSound.put("knight", "SFX_char_knight.wav");
		professionSound.put("samurai", "SFX_char_samurai.wav");
		professionSound.put("thief", "SFX_char_thief.wav");
		professionSound.put("viking", "SFX_char_viking.wav");
		professionSound.put("cleric", "SFX_char_cleric.wav");
		professionSound.put("sorcerer", "SFX_char_sorcerer.wav");
		professionSound.put("paladin", "SFX_char_paladin.wav");
		professionSound.put("ranger", "SFX_char_ranger.wav");
		professionSound.put("marksman", "SFX_char_marksman.wav");
		professionSound.put("none", "SFX_char_none.wav");

		// skill sounds (mapped to files you already have)
		skillSound.put("Strike", "AttackBlock.wav");
		skillSound.put("Heavy Blow", "AttackBlock.wav");
		skillSound.put("Iron Wave", "MedievalFanFire.wav");
		skillSound.put("Taunt", "Pop.wav");
		skillSound.put("Guard Self", "AttackBlock.wav");

		skillSound.put("Fireball", "FireSpell.wav");
		skillSound.put("Lightning Bolt", "SlavicMagic.wav");
		skillSound.put("Drain", "SlavicMagic.wav");
		skillSound.put("Weaken Foe", "SlavicMagic.wav");

		skillSound.put("Prayer of Healing", "SuccessSoundEffect.wav");
		skillSound.put("Recovery", "SuccessSoundEffect.wav");
		skillSound.put("Shield of Faith", "SuccessSoundEffect.wav");

		skillSound.put("Perfect Precision", "ArrowShoot.wav");
		skillSound.put("Reposition", "ArrowShoot.wav");
		skillSound.put(".50 Caliber", "ArrowShoot.wav");
		skillSound.put("Assassinate", "ArrowShoot.wav");

		skillSound.put("Sleight of Greed", "GoldCoinPick.wav");
		skillSound.put("Determination", "MedievalFanFire.wav");
		skillSound.put("Self Sacrifice", "CartoonDrowning.wav");
		skillSound.put("Buff Allies", "Pop.wav");
		skillSound.put("Buff Self", "Pop.wav");
	}

	public static void playMapNodeAttach() {
		AudioManager.playSfxOnce(MAP_NODE_ATTACH);
	}

	public static void startCampfireAmbience() {
		AudioManager.playAmbientSfxLoop(CAMPFIRE_LOOP);
	}

	public static void stopCampfireAmbience() {
		AudioManager.stopAmbientSfx();
	}

	public static void playGameOver() {
		AudioManager.playSfxOnce(GAME_OVER);
	}

	public static void playEnemyAction() {
		AudioManager.playSfxOnce(ENEMY_ACTION);
	}

	public static void playConsumableUse() {
		AudioManager.playSfxOnce(CONSUMABLE_USE);
	}

	public static void playVictory() {
		AudioManager.playSfxOnce(VICTORY_STINGER);
	}

	// fallback if you only pass character
	public static void playCharacterAction(Character c) {
		playCharacterAction(c, null);
	}

	// prefers skill sound, otherwise uses profession sound
	public static void playCharacterAction(Character c, Skill s) {
		if (c == null) return;

		if (s != null && s.getName() != null) {
			String skillFile = skillSound.get(s.getName());
			if (skillFile != null) {
				AudioManager.playSfxOnce(skillFile);
				return;
			}
		}

		String prof = c.getProfession();
		if (prof == null) prof = "none";
		prof = prof.toLowerCase().trim();

		String profFile = professionSound.get(prof);
		if (profFile == null) {
			profFile = professionSound.get("none");
		}

		AudioManager.playSfxOnce(profFile);
	}

	// preload all known game sounds
	public static void preloadAll() {
		AudioManager.preloadSfx(
			MAP_NODE_ATTACH,
			CAMPFIRE_LOOP,
			GAME_OVER,
			ENEMY_ACTION,
			CONSUMABLE_USE,
			VICTORY_STINGER,

			"SFX_char_knight.wav",
			"SFX_char_samurai.wav",
			"SFX_char_thief.wav",
			"SFX_char_viking.wav",
			"SFX_char_cleric.wav",
			"SFX_char_sorcerer.wav",
			"SFX_char_paladin.wav",
			"SFX_char_ranger.wav",
			"SFX_char_marksman.wav",
			"SFX_char_none.wav",

			"AttackBlock.wav",
			"MedievalFanFire.wav",
			"Pop.wav",
			"CartoonDrowning.wav",
			"SuccessSoundEffect.wav",
			"FireSpell.wav",
			"GoldCoinPick.wav",
			"ArrowShoot.wav",
			"SlavicMagic.wav"
		);
	}
}