package main;

import game.Game;

import static org.lwjgl.glfw.GLFW.glfwGetVersionString;
import static org.lwjgl.glfw.GLFW.glfwInit;

public class Main{
	public static void main(String[] args){
		if(!glfwInit()){
			throw new IllegalStateException("[Main.java:11] Cannot initialize GLFW " + glfwGetVersionString());
		}

		System.out.println("[Main.java:13] GLFW initialized sucessfuly! " + glfwGetVersionString());
		Game.startGameThread();
	}
}
