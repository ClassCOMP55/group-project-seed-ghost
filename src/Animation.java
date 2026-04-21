import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.GImage;

public class Animation {

	private GImage animation;
	
	public void animate(String type, Entity target, GImage image, MainApplication mainScreen) {
		animation = new GImage("spr_SHIELD_guard.gif");
		double animTime = 0.25;
		
		 switch(type) {
		 case "DefenseOrUtility":
			 animation.setImage("spr_SHIELD_guard.gif");
			 animTime = 0.5;
			 break;
		 case "NonMagicAttack":
			 animation.setImage("spr_ATTACK_slash.gif");
			 break;
		 case "MagicAttack":
			 animation.setImage("spr_ATTACK_blast.gif");
			 break;
		 case "LightningAttack":
			 animation.setImage("spr_ATTACK_bolt.gif");
			 break;
		 case "FireAttack":
			 animation.setImage("spr_ATTACK_fire.gif");
			 break;
		 case "TriSlash":
			 animation.setImage("spr_ATTACK_trislash.gif");
			 animTime = 1.09;
			 break;
		 case "SelfSac":
			 animation.setImage("spr_ATTACK_judge.gif");
			 animTime = 1;
		 }
		 animation.setLocation(image.getX(), image.getY());
         mainScreen.add(animation);
         mainScreen.add(animation);
		 
		 Timer timer = new Timer((int)(animTime * 1000), new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	 mainScreen.remove(animation);
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
