package studio.papercube.moossage.ui.animators;

import java.awt.Window;

public class WindowLocationAcceleratedAnimator extends Animator{
	int lxf,lxt,lyf,lyt;
	boolean reverse;
	public WindowLocationAcceleratedAnimator(int locationxF,int locationxT,int locationyF,int locationyT,boolean reverse) {
		super(60);
		this.lxf = locationxF;
		this.lxt = locationxT;
		this.lyf = locationyF;
		this.lyt = locationyT;
		this.reverse = reverse;
	}

	public void start(Window w){
		super.tick(pct->{
			if(reverse)w.setLocation((int)(Math.pow(pct,2.0)*(lxt-lxf)+lxf),(int)(Math.pow(pct,2.0)*(lyt-lyf)+lyf));
			if(!reverse)w.setLocation((int)(Math.sqrt(pct)*(lxt-lxf)+lxf),(int)(Math.sqrt(pct)*(lyt-lyf)+lyf));
		});
	}
}
