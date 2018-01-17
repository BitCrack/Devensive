package io;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;

public class Window{
     private long window;

     private int width, height;
     private boolean fullscreen;

     private static Input input;

     public static void setCallbacks(){
          glfwSetErrorCallback(new GLFWErrorCallback() {
               @Override
               public void invoke(int err, long desc) {
                    throw new IllegalStateException(GLFWErrorCallback.getDescription(desc));
               }
          });
     }

     public Window(){
     }

     public void createWindow(String title){
          window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

          if (window == 0) {
               throw new IllegalStateException("Failed to create window!");
          }

          if(!fullscreen) {
               GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());

               glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);

               glfwShowWindow(window);
          }

          glfwMakeContextCurrent(window);

          input = new Input(window);
     }

     public boolean shouldClose(){
          return glfwWindowShouldClose(window);
     }

     public void SwapBuffers(){
          glfwSwapBuffers(window);
     }


     public void setSize(int width, int height){
          this.width = width;
          this.height = height;
     }

     public void setFullscreen(boolean fullscreen){
          this.fullscreen = fullscreen;
     }

     public void setWindowTitle(String title){ glfwSetWindowTitle(window, title); }

     public void update(){
          input.update();
          glfwPollEvents();
     }

     public int getWidth(){ return width; }
     public int getHeight(){ return height; }
     public boolean getFullscreen(){ return fullscreen; }
     public long getWindow(){ return window; }
     public Input getInput(){ return input; }
}
