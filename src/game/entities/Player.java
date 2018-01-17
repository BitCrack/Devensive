package game.entities;

import static org.lwjgl.opengl.GL11.*;

public class Player{
	private int movement = 0;
	private int posX;
	public Player(int posX){
		this.posX = posX;
	}

	public void addXPos(int amount){
		this.posX = posX + amount;
	}

	public int getX(){
		return posX + 50;
	}

	public void render(){
		glBegin(GL_TRIANGLES);
			glVertex2i(50 + posX, 900);
			glVertex2i(25 + posX, 850);
			glVertex2i(posX, 900);
		glEnd();
	}
}
