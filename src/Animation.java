import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import acm.graphics.GObject;
import acm.graphics.GImage;

public class Animation {

	private GImage animation;
	
	public void animate(String type, GImage target,GImage user, MainApplication mainScreen, ArrayList<GObject> contents) {
		animation = new GImage("spr_SHIELD_guard.gif");
		double animTime = 0.25;
		
		 switch(type) {
		 case "DefenseOrUtility":
			 animation.setImage("spr_SHIELD_guard.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animTime = 0.5;
			 break;
		 case "NonMagicAttack":
			 animation.setImage("spr_ATTACK_slash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 break;
		 case "MagicAttack":
			 animation.setImage("spr_ATTACK_blast.gif");
			 animation.setLocation(target.getX(), target.getY());
			 break;
		 case "LightningAttack":
			 animation.setImage("spr_ATTACK_bolt.gif");
			 animation.setLocation(target.getX(), target.getY());
			 break;
		 case "FireAttack":
			 animation.setImage("spr_ATTACK_fire.gif");
			 animation.setLocation(target.getX(), target.getY());
			 break;
		 case "TriSlash":
			 animation.setImage("spr_ATTACK_trislash.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animTime = 1;
			 break;
		 case "SelfSac":
			 animation.setImage("spr_ATTACK_judge.gif");
			 animation.setLocation(target.getX(), target.getY());
			 animTime = 1;
		 }
         contents.add(animation);
         mainScreen.add(animation);
		 
		 Timer timer = new Timer((int)(animTime * 1000), new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	 contents.remove(animation);
		             mainScreen.remove(animation);
		            return;
		            
		        }
		    });
		    timer.setRepeats(false);
		    timer.start();
		    
	}
	
	public GImage getAnimation(){
		return animation;
	}
}
