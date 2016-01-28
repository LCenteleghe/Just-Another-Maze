package br.edu.unisinos.pgr.lcenteleghe.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import br.edu.unisinos.pgr.lcenteleghe.assync.Scheduler;
import br.edu.unisinos.pgr.lcenteleghe.dataservices.GameSettings;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Renderer;
import br.edu.unisinos.pgr.lcenteleghe.state.AbstractGameState;
import br.edu.unisinos.pgr.lcenteleghe.state.ExceptionState;
import br.edu.unisinos.pgr.lcenteleghe.state.IStateController;
import br.edu.unisinos.pgr.lcenteleghe.state.InGameState;
import br.edu.unisinos.pgr.lcenteleghe.state.MenuGameState;

/***************************************/

public class Main implements IStateController {
	private static final int width = GameSettings.getInstance()
			.getScreenWidth();
	private static final int height = GameSettings.getInstance()
			.getScreenHeight();

	private AbstractGameState gameState;

	public void run() {
		init();

		while (!Display.isCloseRequested()) {
			updateFPS();
			try {
				render();
			} catch (Exception e) {
				gameState = new ExceptionState(e);
			}
		}
		Display.destroy();
	}

	// ----------------------------------------------INIT------------------------------------------------//

	private void init() {
		try {
			openGLInit();
			fieldsInit();
			assyncPollInputInit();
			assyncStateUpdateInit();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void assyncPollInputInit() {
		Scheduler.repeate(1, "pollInput", this);

	}

	private void assyncStateUpdateInit() {
		try {
			Scheduler.repeate(2, "stateUpdate", this);
		} catch (Exception e) {
			gameState = new ExceptionState(e);
		}
	}

	private void fieldsInit() {
		try {
			gameState =  InGameState.getInstance();
		} catch (Exception e) {
			gameState = new ExceptionState(e);
		}
	}

	private void openGLInit() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.create();
		Display.setVSyncEnabled(true);
		glOrtho(-width/2, width/2, height, 0, -1.0F, 1.0F);
	}

	// ----------------------------------------------RENDER
	// STATES----------------------------------------------//

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		Renderer.renderImage(gameState.getImage());
		Display.update();
		Display.sync(60);
	}

	// ----------------------------------------------INPUT
	// ACTION----------------------------------------------//

	
	public void pollInput() {
		try {
			if (Mouse.isCreated())
				while (Mouse.next()) {
					gameState.handleMouseInput(Mouse.getX(), Mouse.getY(),
							Mouse.getEventButtonState());
				}

			if (Keyboard.isCreated())
				while (Keyboard.next()) {
					gameState.handleKeyboardInput(Keyboard.getEventKey(),
							Keyboard.getEventCharacter(),
							Keyboard.getEventKeyState());
				}
		} catch (Exception e) {
			gameState = new ExceptionState(e);
		}
	}

	// ---------------------------------------------- RUN =)
	// ------------------------------------------------//

	@Override
	public void stateUpdate() {
		try {
			gameState = gameState.getNextState();
			gameState.stateUpdate();
		} catch (Exception e) {
			gameState = new ExceptionState(e);
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

	/** position of quad */
	float x = 400, y = 300;
	/** angle of quad rotation */
	float rotation = 0;

	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS = getTime();;

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
}
