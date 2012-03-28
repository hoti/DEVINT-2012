package slick;

public enum LastMouvement {
	
	GAUCHE("Gauche"),
	DROITE("Droite"),
	SAUT("Saut"),
	CHUTE("Chute");
	
	private final String mvt;
	
	LastMouvement(String mvt){
		this.mvt=mvt;
	}

	public String toString(){
		return mvt;
	}
	
	
}