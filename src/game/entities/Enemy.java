package game.entities;

import game.WavesHandler;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class Enemy{
	private int posX, posY, health;
	private Random randomizer = new Random();

	public Enemy(int posX, int posY, int health){
		this.posX = posX;
		this.posY = posY;
		this.health = health + randomizer.nextInt(WavesHandler.currentWave());
	}

	public int getPosX(){
		return posX;
	}

	public int getPosY(){
		return posY;
	}

	public int getHealth(){
		return health;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public void update(){
		this.posY = posY + 2;
	}

	public void render(){
		glBegin(GL_TRIANGLES);
			glVertex2i(50 + posX, posY);
			glVertex2i(25 + posX, posY + 50);
			glVertex2i(posX, posY);
		glEnd();
	}
}
