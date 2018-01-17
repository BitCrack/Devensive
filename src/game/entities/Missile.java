package game.entities;

import game.Game;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class Missile{
	private int posX, posY, length, id, accuracy;
	private boolean flying;
	private Random randomizer;

	public Missile(int length, int posX, int posY, int id, int accuracy){
		this.posX = posX;
		this.posY = posY;
		this.length = length;
		this.flying = true;
		this.id = id;
		this.randomizer = new Random();
		this.accuracy = accuracy;
	}

	public void setHorizontalMovement(int amount){
		this.posX = amount + (randomizer.nextInt(40 - this.accuracy) - (randomizer.nextInt(40 - this.accuracy)));
	}

	public void setVerticalMovement(int amount){
		this.posY = amount;
	}

	public void setState(boolean state){
		this.flying = state;
	}

	public boolean getState(){
		return this.flying;
	}

	public int getPosX(){
		return this.posX;
	}

	public int getPosY(){
		return this.posY;
	}

	public int getLength(){
		return this.length;
	}

	public void render(){
		glBegin(GL_LINES);
			glColor3f(1,0,0);
				glVertex2i(posX, posY);
				glVertex2i(posX, posY + length);
			glColor3f(1,1,1);
		glEnd();

		if(!flying){
			glBegin(GL_POLYGON);
				glColor3f(1,0.5f,0);
				glVertex2i(posX - 10, posY + length + 5);
				glVertex2i(posX + 10, posY + length + 5);

				glVertex2i(posX + 15, posY + 10);

				glVertex2i(posX + 10, posY - 5);
				glVertex2i(posX - 10, posY - 5);

				glVertex2i(posX - 15, posY + 10);
				glColor3f(1,1,1);
			glEnd();
		}
	}
}
