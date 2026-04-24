import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class Animation {

	private GImage animation;
	private ArrayList<GImage> animations = new ArrayList<>();
	
	public void animate(String type, GImage target,GImage user, MainApplication mainScreen, ArrayList<GObject> contents, GImage[] targets, GImage[] allies) {
		animation = new GImage("spr_SHIELD_guard.gif");
		double animTime = 0.25;
		
		 switch(type) {
		 case "DefenseOrUtility":
			 animation.setImage("spr_SHIELD_guard.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("Shield Counter B.wav");

			 animTime = 0.5;
			 break;
		 case "DefenseOrUtilitySelf":
			 animation.setImage("spr_SHIELD_guard.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("Shield Counter B.wav");
			 animTime = 0.5;
			 break;
		 case "NonMagicAttack":
			 animation.setImage("spr_ATTACK_slash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("AttackBlock.wav");
			 break;
		 case "MagicAttack":
			 animation.setImage("spr_ATTACK_blast.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 break;
		 case "LightningAttack":
			 animation.setImage("spr_ATTACK_bolt.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("Electric Whip D.wav");
			 break;
		 case "FireAttack":
			 for (GImage image: targets){
				 animation = new GImage("spr_ATTACK_fire.gif");
				 animation.setLocation(image.getX(), image.getY());
				 animations.add(animation);
				 AudioManager.playSfxOnce("Fire Shuriken.wav");
			 }
			 break;
		 case "TriSlash":
			 animation.setImage("spr_ATTACK_trislash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("multi_attack.wav");
			 animTime = 1;
			 break;
		 case "SelfSac":
			 animation.setImage("spr_ATTACK_judge.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("Cause Fear.wav");
			 animTime = 1;
			 break;
		 case "Explode":
			 for (GImage image: targets){
				 animation = new GImage("spr_ATTACK_blast.gif");
				 animation.setLocation(image.getX(), image.getY());
				 animations.add(animation);
				 AudioManager.playSfxOnce(""); //Explode audio goes here
			 };
			 animTime = 1;
			 break;
		 case "Buff":
			 animation.setImage("spr_SKILL_buff.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("Buff.wav");
			 animTime = 0.5;
			 break;
		 case "BuffAllies":
			 for (GImage image: allies){
				 animation = new GImage("spr_SKILL_buff.gif");
				 animation.setLocation(image.getX(), image.getY());
				 animations.add(animation);
			 }
			 AudioManager.playSfxOnce("Buff.wav");
			 animTime = 0.5;
			 break;
		 case "BuffAndAttackAll":
			 animation.setImage("spr_SKILL_buff.gif");
			 animation.setLocation(user.getX(), user.getY());
			 animations.add(animation);
			 for (GImage image: targets){
				 animation = new GImage("spr_ATTACK_slash.gif");
				 animation.setLocation(image.getX(), image.getY());
				 animations.add(animation);
				 AudioManager.playSfxOnce("AttackBlock.wav");
			 }
			 animTime = 0.5;
			 break;
		 case "Debuff":
			 animation.setImage("spr_SKILL_debuff.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("Poison Hit A.wav");
			 animTime = 0.5;
			 break;
		 case "DebuffAll":
			 for (GImage image: targets){
				 animation = new GImage("spr_SKILL_debuff.gif");
				 animation.setLocation(image.getX(), image.getY());
				 animations.add(animation);
				 AudioManager.playSfxOnce("Poison Hit A.wav");
			 }
			 animTime = 0.5;
			 break;
		 case "50 Cal":
			 animation.setImage("spr_ATTACK_blast.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("5.56 M4 Rifle - Gunshot B 004.wav");
			 break;
		 case "Perfect percision":
			 animation.setImage("spr_ATTACK_blast.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("30-30 Lever Action Rifle - Gunshot A 003.wav");
			 break;
		 case "Prone shot":
			 animation.setImage("spr_ATTACK_slash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("30-30 Lever Action Rifle - Gunshot A 003.wav");
			 break;
		 case "Greed":
			 animation.setImage("spr_ATTACK_slash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce("");//Greed audio file goes below here
			 break;
		 case "Taunt":
			 for (GImage image: targets){
				 animation = new GImage("spr_SKILL_debuff.gif");
				 animation.setLocation(image.getX(), image.getY());
				 animations.add(animation);
				 AudioManager.playSfxOnce("Taunt.wav"); //Taunt audio file goes here
			 }
			 animTime = 0.5;
			 break;
		 case "IronWave":
			 animation.setImage("spr_ATTACK_slash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 AudioManager.playSfxOnce(""); // Iron wave goes here
			 break;
		 }
		 
		 for (GImage image: animations) {
			 contents.add(image);
	         mainScreen.add(image);
		 }
		 
		 Timer timer = new Timer((int)(animTime * 1000), new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	for (GImage image: animations) {
		        		contents.remove(image);
			            mainScreen.remove(image);
		   		 }
		        	animations.clear();
		             return;
		            
		        }
		    });
		    timer.setRepeats(false);
		    timer.start();
	}
	
	public GImage getAnimation(){
		return animation;
	}
	
	public ArrayList<GImage> getAnimations(){
		return animations;
	}
}
