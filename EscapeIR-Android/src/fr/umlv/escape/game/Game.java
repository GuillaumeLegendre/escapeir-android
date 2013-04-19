package fr.umlv.escape.game;

import java.io.IOException;
import java.util.Iterator;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.gesture.Gesture;
import fr.umlv.escape.move.PlayerMove;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.ship.ShipFactory;
import fr.umlv.escape.weapon.ShootPlayer;
import fr.umlv.escape.world.Bodys;
import fr.umlv.escape.world.CollisionMonitor;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Main class of the Escape game. It initialize and manage all the progress of the game
 */
public class Game {
	private Level currentLevel;
	private int currentNbLv;
	private final int nbLevel;
	private static Game TheGame;
	final Player player1;
	private final CollisionMonitor collisionMonitor;
	private State currentState;
	private Object lock=new Object();
	private boolean isValideLevelState;
	
	private Game(){
		this.nbLevel=3; //TODO chercher dans fichier
		Ship playerShip=ShipFactory.getTheShipFactory().createShip("DefaultShipPlayer", height/3, width/2, 99, "StraightLine");
		this.player1=new Player("Marc",playerShip,3);
		this.collisionMonitor=new CollisionMonitor();
		this.currentNbLv=1;
	}

	/**
	 * Initialize all that is needed to launch the screen and start playing.
	 * @throws IOException
	 * @throws IllegalFormatContentFile
	 */
	public void initializeGame() throws IOException, IllegalFormatContentFile{	
		currentLevel=LevelFactory.getTheLevelFactory().createLevel("level1");
		
		//Initialize the player
		filter.categoryBits=2;
		filter.maskBits=53;
		this.player1.getShip().setMoveBehaviour(new PlayerMove(gesture));
		this.player1.getShip().setShootBehaviour(new ShootPlayer(gesture));
		this.player1.getShip().getBody().setActive(true);
		this.player1.getShip().getBody().getFixtureList().setSensor(false);
		this.player1.getShip().getBody().getFixtureList().setFilterData(filter);
		this.player1.getShip().getBody().setLinearDamping(3);
		isValideLevelState=true;
	}


	/**
	 * Start the game. When this method is called, the simulation of the world begin.
	 * 
	 * @throws IOException
	 * @throws IllegalFormatContentFile
	 */
	public void startGame() throws IOException, IllegalFormatContentFile{
		long begin;
		long elapsedWave;
		long elapsedStep;
		boolean isWaveFinished;
		long lastDeath=0;
		
		//Process all levels
		LoadLevel:while(currentNbLv<=nbLevel){
			currentLevel=LevelFactory.getTheLevelFactory().createLevel("level"+currentNbLv);
			while(currentLevel.launchNextWave()){
				Iterator<Ship> iterShip;
				begin=System.currentTimeMillis();
				elapsedWave=0;
				
				isWaveFinished=false;
				while(!isWaveFinished){
					//world step every 15ms
					elapsedStep=System.currentTimeMillis();
					EscapeWorld.getTheWorld().step();
					collisionMonitor.performPostStepCollision(); // Process post collision treatment
	
					iterShip=currentLevel.getWaveList().get(currentLevel.getCurrentWave()).getShipList().iterator();
					while(iterShip.hasNext()){
						Ship ship=iterShip.next();
						ship.move();			// Move all ship of the wave
						if(ship.shoot(ship.getPosXCenter(),ship.getPosYCenter())){
							ship.fire();		// Make all ship shooting
						}
						if(ship.isAlive()){
							isWaveFinished=false; // If their still are ship the wave is not finished
						}
					}
					
					try{
						elapsedStep=System.currentTimeMillis()-elapsedStep;
						if(elapsedStep>15){
							elapsedStep=15;
						}
						Thread.sleep(15-elapsedStep);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				currentLevel.changeWave();
			}
			setCurrentNbLv(currentNbLv+1);
		}
		setCurrentState(State.Ended);
	}
	
	/**
	 * Get the current level of the game.
	 * @return The current level of the game.
	 */
	public Level getCurrentLevel(){
		return this.currentLevel;
	}

	/** Get the unique instance of {@link Game}.
	 * @return The unique instance of {@link Game}
	 */
	public static Game getTheGame(){
		if(Game.TheGame==null){
			Game.TheGame = new Game();
		}
		return Game.TheGame;
	}

	/**
	 * Get the current state of the game.
	 * @return The current state of the game.
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * Set the current state of the game.
	 * @param currentState The new state of the game.
	 */
	public void setCurrentState(State currentState) {
		synchronized(lock){
			this.currentState = currentState;
			if(currentState==State.Playing){
				lock.notify();
			}
		}
	}
	
	/**Set the current level number. If this method is called while a level is running, the current level will
	 * be cleared and the new one will begin.
	 * @param currentNbLv the new level number.
	 */
	public void setCurrentNbLv(int currentNbLv) {
		this.currentNbLv = currentNbLv;
		DisplayableMonitor.deleteAllBullet();
		DisplayableMonitor.deleteAllShipEnemy();
		DisplayableMonitor.deleteAllBonus();
		if(currentNbLv>3){
			isValideLevelState=false;
			return;
		}
		frontApplication.setBackGround("level"+currentNbLv);
		this.isValideLevelState=true;
		this.setCurrentState(Game.State.Playing);
	}
}
