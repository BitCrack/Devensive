package game;

import java.awt.*;
import java.util.Random;

public class WavesHandler{
	private static int wave = 1;
	private static boolean waveState = false;

	private static Random randomizer = new Random();

	private static int waveEnemies;

	public static int currentWave(){
		return wave;
	}

	public static int currentEnemies(){
		return waveEnemies;
	}

	public static void update(game.entities.Enemy[] enemiest){
		if(enemiest != null && !waveState){
			System.out.println("enemiest isn't null, waveState " + waveState);
			for(int i = 0; i < enemiest.length; i++){
				if(enemiest[i] != null){
					waveEnemies++;
					System.out.println("wavesEnemies increased! " + waveEnemies);
				}
			}
		}

		if(waveEnemies == 0 && !waveState){
			for(int i = 0; i < wave; i++){
				waveEnemies++;
				Game.createEnemy(50 + randomizer.nextInt(500), -50 + -randomizer.nextInt(100) + (-waveEnemies * 150), wave);
				System.out.println(-50 + -randomizer.nextInt(100) + (-waveEnemies * 50));
				waveState = true;
			}
		}
	}

	public static void enemyDestroyed(){
		System.out.println("Enemy destroyed");
		waveEnemies--;
		System.out.println("waveEnemies: " + waveEnemies);
		if(waveEnemies == 0 && waveState){
			wave++;
			waveState = false;
			System.out.println("waveState: " + waveState + " wave: " + wave);
		}
	}
}
