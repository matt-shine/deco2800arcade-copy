package deco2800.arcade.client;

import java.awt.EventQueue;

import com.badlogic.gdx.ApplicationListener;

public class ProxyApplicationListener implements ApplicationListener {

	private Object mon = null;
	private ApplicationListener target = null;
	
	
	public void setThreadMonitor(Object mon) {
		this.mon = mon;
	}
	
	public void setTarget(ApplicationListener target) {
		this.target = target;
		
		final ApplicationListener t = target;
		EventQueue.invokeLater(new Runnable() {
			public void run () {
				t.create();
			}
		});
	}
	
	public ApplicationListener getTarget() {
		return target;
	}
	
	@Override
	public void create() {
		if (mon != null) {
			target.create();
		}
		if (this.mon != null){
			synchronized (mon) {
				this.mon.notify();
				this.mon = null;
			}
		}
	}

	@Override
	public void dispose() {
		target.dispose();
	}

	@Override
	public void pause() {
		target.pause();
	}

	@Override
	public void render() {
		target.render();
	}

	@Override
	public void resize(int w, int h) {
		target.resize(w, h);
	}

	@Override
	public void resume() {
		target.resume();
	}

}
