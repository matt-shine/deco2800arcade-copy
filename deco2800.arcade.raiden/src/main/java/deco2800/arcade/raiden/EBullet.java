package deco2800.arcade.raiden;

public class EBullet extends Bullet{

	
	
    public boolean isUsed = false;     
	

	public EBullet(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		// TODO Auto-generated constructor stub
	}

	public void bulletMove() {
		// TODO Auto-generated method stub
		y+=10;
	}


	public boolean isEBulletHitPPlane(){
		
//		int x = Controller.pplane.x; 
//		int y = Controller.pplane.y;
//		int w = Controller.pplane.w;
//		int h = Controller.pplane.h;
//		Rectangle recEbullet = new Rectangle(this.x, this.y, width, heigth);
//		Rectangle recPplane = new Rectangle(x, y, w, h);
//		return recEbullet.intersects(recPplane) && !isUsed;
		return true;
	}
}
