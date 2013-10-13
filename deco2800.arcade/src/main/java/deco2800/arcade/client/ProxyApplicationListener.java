package deco2800.arcade.client;

import com.badlogic.gdx.ApplicationListener;

public class ProxyApplicationListener implements ApplicationListener {

	private boolean created = false;
	private int width = 0, height = 0;
	private ApplicationListener target = null;
	
	public void setTarget(ApplicationListener target) {
		this.target = target;
		this.created = false;
		
		final ApplicationListener t = target;
		t.resize(width, height);
		t.create();
		created = true;
	}
	
	public ApplicationListener getTarget() {
		return target;
	}
	
	@Override
	public void create() {
		//So I wrote this a while ago and I can't remember how it works...
		target.resize(width, height);
		target.create();
		this.created = true;
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
		if (this.created) {
			target.render();
		}
	}

	@Override
	public void resize(int w, int h) {
		width = w;
		height = h;
		target.resize(w, h);
	}

	@Override
	public void resume() {
		target.resume();
	}

}
