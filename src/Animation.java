import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class Animation {

	private GImage animation;
	private ArrayList<GImage> animations = new ArrayList<>();
	
	public void animate(String type, GImage target,GImage user, MainApplication mainScreen, ArrayList<GObject> contents, GImage[] aliveEnemeies, GImage[] aliveCharacters) {
		animation = new GImage("spr_SHIELD_guard.gif");
		double animTime = 0.25;
		
		 switch(type) {
		 case "DefenseOrUtility":
			 animation.setImage("spr_SHIELD_guard.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 animTime = 0.5;
			 break;
		 case "NonMagicAttack":
			 animation.setImage("spr_ATTACK_slash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
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
			 break;
		 case "FireAttack":
			 animation.setImage("spr_ATTACK_fire.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 break;
		 case "TriSlash":
			 animation.setImage("spr_ATTACK_trislash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 animTime = 1;
			 break;
		 case "SelfSac":
			 animation.setImage("spr_ATTACK_judge.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 animTime = 1;
			 break;
		 case "Explode":
			 animation.setImage("spr_ATTACK_blast.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 animTime = 1;
			 break;
		 case "Buff":
			 animation.setImage("spr_SKILL_buff.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 animTime = 0.5;
			 break;
		 case "Debuff":
			 animation.setImage("spr_SKILL_debuff.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animations.add(animation);
			 animTime = 0.5;
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
