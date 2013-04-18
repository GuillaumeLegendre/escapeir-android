package fr.umlv.escape.game;

import java.io.IOException;
import java.util.Iterator;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import fr.umlv.escape.file.IllegalFormatContentFile;
import fr.umlv.escape.front.DisplayableMonitor;
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
	private FrontApplication frontApplication;
	private static Game TheGame;
	private final Gesture gesture;
	private final int height;
	private final int width;
	private final Player player1;
	private Body[] boundPlayer;
	private final CollisionMonitor collisionMonitor;
	private State currentState;
	private Object lock=new Object();
	private boolean isValideLevelState;
	
	
	/**
	 * Enum that represent the state of the game
	 */
	public enum State{
		/**Represent a state where the game is not finished but there are no simulating processing
		 */
		OnMenu,
		/**Represent a state where simulation of the world process.
		 */
		Playing,
		/** Represent the state where the game is over.
		 */
		Ended
	}
	
	/**
	 * Get the player one.
	 * @return The player one.
	 */
	public Player getPlayer1() {
		return player1;
	}

	private Game(){
		this.nbLevel=3;
		this.gesture=new Gesture();
		this.height=480;
		this.width=640;
		this.boundPlayer=new Body[5];
		Ship playerShip=ShipFactory.getTheShipFactory().createShip("DefaultShipPlayer", height/3, width/2, 99, "StraightLine");
		this.player1=new Player("Marc",playerShip,3);
		this.collisionMonitor=new CollisionMonitor();
		this.currentState=State.OnMenu;
		this.currentNbLv=1;
		this.isValideLevelState=false;
	}

	/**
	 * Initialize all that is needed to launch the screen and start playing.
	 * @throws IOException
	 * @throws IllegalFormatContentFile
	 */
	public void initializeGame() throws IOException, IllegalFormatContentFile{	
		currentLevel=LevelFactory.getTheLevelFactory().createLevel("level1");
		frontApplication=new FrontApplication("EscapeIR",height,width,gesture);
		
		//Creating the bounds of the screen
		boundPlayer[0]=Bodys.createBasicRectangle(0, height/3*2-50, width, 1,2); 	//wall 1/3 of the screen
		boundPlayer[1]=Bodys.createBasicRectangle(0, height, width, 1,2);	//wall bot
		boundPlayer[2]=Bodys.createBasicRectangle(0, 0, width, 1,2);		//wall top
		boundPlayer[3]=Bodys.createBasicRectangle(0, 0, 1, height,2);		//wall left
		boundPlayer[4]=Bodys.createBasicRectangle(width, 0, 1, height,2);	//wall right
		Filter filter=new Filter();
		filter.categoryBits=1;
		filter.maskBits=2;
		
		for(Body b : boundPlayer){
			b.getFixtureList().setFilterData(filter);
			b.getFixtureList().m_isSensor=false;
		}
		
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
		//launch the front screen
		frontApplication.launchFrontApplication();
		
		long begin;
		long elapsedWave;
		long elapsedStep;
		boolean isWaveFinished;
		long lastDeath=0;
		
		synchronized(lock){
			while(this.currentState!=State.Playing){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		
		//Process all levels
		LoadLevel:while(currentNbLv<=nbLevel){
			currentLevel=LevelFactory.getTheLevelFactory().createLevel("level"+currentNbLv);
			while(currentLevel.launchNextWave() && isValideLevelState){
				Iterator<Ship> iterShip;
				begin=System.currentTimeMillis();
				elapsedWave=0;
				
				isWaveFinished=false;
				while(!isWaveFinished){
					synchronized(lock){
						int lvBeforeWait=currentNbLv;
						while(this.currentState!=State.Playing){
							try {
								lock.wait();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
						}
						if(lvBeforeWait!=currentNbLv){
							continue LoadLevel;
						}
					}
					//world step every 15ms
					elapsedStep=System.currentTimeMillis();
					if(lastDeath!=0 && (elapsedStep-lastDeath>3000)){
						EscapeWorld.getTheWorld().setActive(player1.getShip().getBody(), true);
						lastDeath=0;
					}
					EscapeWorld.getTheWorld().step();
					collisionMonitor.performPostStepCollision();
					if(lastDeath==0 && (!player1.getShip().getBody().isActive())){
						if(player1.getLife()==0){
							currentState=State.Ended;
							return;
						} else {
							lastDeath=elapsedStep;
						}
					}
					elapsedWave=elapsedStep-begin;
					iterShip=currentLevel.getWaveList().get(currentLevel.getCurrentWave()).getShipList().iterator();
					if(currentLevel.getCurrentDelay()==0){
						isWaveFinished=true;
					}
					while(iterShip.hasNext()){
						Ship ship=iterShip.next();
						ship.move();
						if(ship.shoot(ship.getPosXCenter(),ship.getPosYCenter())){
							ship.fire();	
						}
						if(ship.getBody().isActive()){
							isWaveFinished=false;
						}
					}
					if((currentLevel.getCurrentDelay()!=0) && (elapsedWave>currentLevel.getCurrentDelay())){
						isWaveFinished=true;
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
	 * Get the height of the game.
	 * @return The height of the game.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the width of the game.
	 * @return The width of the game.
	 */
	public int getWidth() {
		return width;
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
	
	/**
	 * Get the current level number.
	 * @return The current level number.
	 */
	public int getCurrentNbLv() {
		return currentNbLv;
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

	/**Entry of the program, create the Game and start it.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Game game=getTheGame();

		try{
			game.initializeGame();
			game.startGame();
		}
		catch(IOException e) {
			System.err.println("An error occured while trying to read some file on your computer. Be sure that all files haves the necessary right and have not been deleted or altered");
		}
		catch(IllegalFormatContentFile e){
			System.err.println("An error occured while trying to read some file on your computer. Be sure that all files haves the necessary right and have not been deleted or altered");
		}
	}
}
