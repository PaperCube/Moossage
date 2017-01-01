package studio.papercube.moossage.ui.animators;

import java.awt.Window;

public class WindowTransparencyAnimator extends Animator {
	float opf,opt;
	public WindowTransparencyAnimator(float opacityFrom,float opacityTo) {
		super(30);
		opf=opacityFrom;
		opt=opacityTo;
	}
	
	
	public void start(Window w){
		super.tick(pctg -> {
			if(pctg>=0.0 && pctg <= 1.0)w.setOpacity((float)((opt-opf)*pctg + opf));
		});
	}
}
