package fr.umlv.escape.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import fr.umlv.escape.file.IllegalFormatContentFile;
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
	Player player1;
	private final CollisionMonitor collisionMonitor;
	private final EscapeWorld escapeWorld;
	
	private Game(){
		this.nbLevel=3; //TODO chercher dans fichier
		this.collisionMonitor=new CollisionMonitor();
		this.currentNbLv=1;
		this.escapeWorld = EscapeWorld.getTheWorld();
	}

	/**
	 * Initialize all that is needed to launch the screen and start playing.
	 * @throws IOException
	 * @throws IllegalFormatContentFile
	 */
	public void initializeGame() throws IOException, IllegalFormatContentFile{	
		currentLevel=LevelFactory.getTheLevelFactory().createLevel("level1");
		
		Ship playerShip=ShipFactory.getTheShipFactory().createShip("DefaultShipPlayer", height/3, width/2, 99, "StraightLine");
		player1=new Player("Marc",playerShip,3);
		/*		//Initialize the player
		filter.categoryBits=2;
		filter.maskBits=53;
		this.player1.getShip().setMoveBehaviour(new PlayerMove());
		this.player1.getShip().setShootBehaviour(new ShootPlayer());
		this.player1.getShip().getBody().setActive(true);
		this.player1.getShip().getBody().getFixtureList().setSensor(false);
		this.player1.getShip().getBody().getFixtureList().setFilterData(filter);
		this.player1.getShip().getBody().setLinearDamping(3);
		isValideLevelState=true;*/
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
				ArrayList<Ship> shipList;
				begin=System.currentTimeMillis();
				elapsedWave=0;
				
				isWaveFinished=false;
				while(!isWaveFinished){
					//world step every 15ms
					elapsedStep=System.currentTimeMillis();
					elapsedWave=elapsedStep-begin;
					
					escapeWorld.step();
					collisionMonitor.performPostStepCollision(); // Process post collision treatment
	
					isWaveFinished = true;
					shipList=currentLevel.waveList.get(currentLevel.currentWave).shipList;
					for(int i=0; i<shipList.size();++i){
						Ship ship=shipList.get(i);
						if(ship.isAlive()){
							ship.move();			// Move all ship of the wave
							if(ship.shoot(ship.getPosXCenter(),ship.getPosYCenter())){
								ship.fire();		// Make all ship shooting
							}
							isWaveFinished=false; // If their still are ship the wave is not finished
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
			currentNbLv+=1;
		}
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
}
