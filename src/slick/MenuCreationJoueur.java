package slick;

import java.awt.event.KeyEvent;

import org.newdawn.slick.SlickException;

import devintAPI.MenuAbstrait;

public class MenuCreationJoueur extends MenuAbstrait {

	
	private static final long serialVersionUID = -4671023183687953337L;
	
	
	
	public MenuCreationJoueur() {
		super("Nouveau Joueur");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String[] nomOptions() {
		String[] noms = {"Saisis ton nom", "Start", "Retour"};
		return noms;
	}

	@Override
	protected void lancerOption(int i) {
		
		switch (i){  
		case 1 : this.setVisible(false); 				
		
		try {
			new Game().launch();
		} catch (SlickException e) {} ;
		break;
		case 2 : this.setVisible(false);new MenuJoueur(); break;
		default: System.err.println("action non définie");
		}
		
	}
	
	
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		String text = boutonOption[0].getText();

		if(optionCourante==0){
			if((e.getKeyCode() <= KeyEvent.VK_Z && e.getKeyCode() >= KeyEvent.VK_A )|| (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) ){
				if(text.equals("Saisis ton nom")){
					boutonOption[0].setText(" ");
				}
				
				text+=e.getKeyChar();
			}
			if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
				text=text.substring(0,text.length()-1);
			}
			boutonOption[0].setText(text);

		}
		
	}


	@Override
	protected String wavAccueil() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String wavRegleJeu() {
		// TODO Auto-generated method stub
		return null;
	}

}
