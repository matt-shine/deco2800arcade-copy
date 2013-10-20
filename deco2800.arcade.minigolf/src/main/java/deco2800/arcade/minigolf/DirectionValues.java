package deco2800.arcade.minigolf;

/* base values to be modified upon DirectionLogic update */
public class DirectionValues {
	
	private float power = 0f; 
	private float angle = 0f;
	
	public float getPower(){
		return power;
	}
	public void setPower(float value){
		this.power = value;
	}
	
	public float getAngle(){
		return angle;
	}
	public void setAngle(float value){
		this.angle = value;
	}
}

