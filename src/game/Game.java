package game;

import game.entities.Enemy;
import game.entities.Missile;
import game.entities.Player;
import io.Timer;
import io.Window;
import org.lwjgl.opengl.GL;

import java.lang.reflect.Array;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;

public class Game{

	private static Enemy[] enemies;
	private static Missile[] missiles;

	public static void startGameThread(){
		System.out.println("[Game.java:12] Started game thread!");

		Window window = new Window();
		window.setSize(600, 900);
		window.createWindow("Devensive");

		if(window == null){
			glfwTerminate();
			throw new RuntimeException("[Game.java:20] Window is null!");
		}

		glfwMakeContextCurrent(window.getWindow());

		GL.createCapabilities();

		System.out.println("[Game.java:25] Successfully created capabilities!");

		Player ply = new Player(275);

		enemies = new Enemy[255];

		missiles = new Missile[255];

		double frame_cap = 1.0 / 60.0;

		double frame_time = 0;
		int frames = 0;

		double time = Timer.getTime();
		double unprocessed = 0;

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 600, 900, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		while(!window.shouldClose()){
			boolean can_render = false;

			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;

			time = time_2;

			while(unprocessed >= frame_cap){
				unprocessed -= frame_cap;
				can_render = true;

				if(window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)){
					glfwSetWindowShouldClose(window.getWindow(), true);
					System.out.println("[Game.java:46] Successfully closed game!");
				}

				if(window.getInput().isKeyDown(GLFW_KEY_D) && ply.getX() < 600) {
					ply.addXPos(2);
				}

				if(window.getInput().isKeyDown(GLFW_KEY_A) && ply.getX() > 50) {
					ply.addXPos(-2);
				}

				if(window.getInput().isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT)) {
					int index = -1;
					for(int current = 0; current < missiles.length; current++){
						if(missiles[current] == null){
							index = current;
							break;
						}
					}

					missiles[index] = new Missile(20, ply.getX() - 25, 825, index, 28);
				}

				for (int i = 0; i < missiles.length; i++){
					Missile missile = missiles[i];
					if(missile != null){

						if(missile.getState()){
							missile.setVerticalMovement(missile.getPosY() + -5);
							missile.setHorizontalMovement(missile.getPosX());
						}else{
							missile.setHorizontalMovement(ply.getX());
							missile.setVerticalMovement(750);
						}

						if(missile.getPosY() < 0 - missile.getLength()){
							missiles[i] = null;
						}

						for(int j = 0; j < enemies.length; j++){
							Enemy enemy = enemies[j];
							if(enemy != null && missile != null && enemy.getPosY() + 50 > missile.getPosY() + missile.getLength() && enemy.getPosY() < missile.getPosY() && enemy.getPosX() + 50 > missile.getPosX() && enemy.getPosX() < missile.getPosX()){
								if(enemy.getHealth() <= 0){
									enemies[j] = null;
									WavesHandler.enemyDestroyed();
								} else{
									enemy.setHealth(enemy.getHealth() - 1);
								}
								missile.setState(false);
							}
							enemy = null;
						}
					}
					missile = null;
				}

				for(int i = 0; i < enemies.length; i++){
					Enemy enemy = enemies[i];
					if(enemy != null){
						enemy.update();
					}
					enemy = null;
				}

				window.update();

				WavesHandler.update(enemies);

				if(frame_time >= 1.0){
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}

				glViewport(0,0, window.getWidth(), window.getHeight());

				window.setWindowTitle("Devensive | Wave: " + WavesHandler.currentWave() + " | Enemies left: " + WavesHandler.currentEnemies() + "/" + WavesHandler.currentWave() + " |");

				if(can_render){

					glClear(GL_COLOR_BUFFER_BIT);

					glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

					for(int i = 0; i < enemies.length; i++){
						Enemy enemy = enemies[i];
						if(enemy != null){
							enemy.render();
						}
						enemy = null;
					}

					for(int i = 0; i < missiles.length; i++){
						Missile missile = missiles[i];
						if(missile != null){
							missile.render();
							if(missile.getState() == false){
								missiles[i] = null;
							}
						}
						missile = null;
					}

					ply.render();

					glDisable(GL_BLEND);

					window.SwapBuffers();

					frames++;
				}
			}
		}

		glfwTerminate();
	}

	public static void createEnemy(int posX, int posY, int health){
		System.out.println("x: " + posX + " y: " +  posY + " health: " + health);
		int index = -1;
		for(int current = 0; current < enemies.length; current++){
			if(enemies[current] == null){
				index = current;
				break;
			}
		}
		enemies[index] = new Enemy(posX, posY, health); //275, 0, 1
	}

	public static game.entities.Missile[] getMissiles(){
		return missiles;
	}
}
