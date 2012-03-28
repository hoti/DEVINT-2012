package slick;

public enum Direction {
	X("X"),
	Y("Y");
	
	private final String direction;
	
	Direction(String direction){
		this.direction=direction;
	}

	public String toString(){
		return direction;
	}
}
