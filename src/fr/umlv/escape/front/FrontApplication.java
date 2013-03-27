package fr.umlv.escape.front;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import fr.umlv.escape.bonus.Bonus;
import fr.umlv.escape.game.Game;
import fr.umlv.escape.game.Game.State;
import fr.umlv.escape.gesture.Gesture;
import fr.umlv.escape.gesture.Gesture.GestureType;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.weapon.Bullet;
import fr.umlv.escape.weapon.Weapon;
import fr.umlv.escape.world.EscapeWorld;
import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;
import fr.umlv.zen2.MotionEvent;

/**This class create and draw the window of the game. All renders operations are done in this class *
 */
public class FrontApplication{
	private final int height;
	private final int width;
	/**
	 * Limit of the world. All objects that exceed this limit should be destroyed
	 */
	public final static int LIMITWORLD = 500;
	private final String name;
	private final Gesture gesture;
	private final BackGroundScroller backgroundScroller;
	private final List<Bullet> listBullet;
	private final List<Bonus> listBonus;
	private boolean showPerfMenu=false;
	
	/**Constructor
	 * 
	 * @param name The name of the game.
	 * @param height The height of the window
	 * @param width The width of the window
	 * @param gesture The gesture detector to use
	 */
	public FrontApplication(String name,int height,int width,Gesture gesture){
		Objects.requireNonNull(name);
		Objects.requireNonNull(gesture);
		if(height<0 || width<0){
			throw new IllegalArgumentException("height or width cannot be negative");
		}
		
		this.name=name;
		this.height=height;
		this.width=width;
		this.gesture=gesture;
		this.listBullet=new ArrayList<>();
		this.listBonus=new ArrayList<>();
		this.backgroundScroller=new BackGroundScroller(height, Game.getTheGame().getCurrentLevel().getName());
		
	}

	/**
	 *Launch a window and managed all event received in it. This method draw all images in this window
	 *using the {@link DisplayableMonitor}.
	 */
	public void launchFrontApplication(){
		final ApplicationCode applicationCode=new ApplicationCode(){
			@Override
			public void run(final ApplicationContext context) {
				final PlayerSprites playerSprite=DisplayableMonitor.getPlayersprite();
				long lastDraw=System.currentTimeMillis();
				long lastImpulsePlayer=lastDraw;

				final Ship playerShip=Game.getTheGame().getPlayer1().getShip();
				final Image imgPlayer=FrontImages.getImage(playerShip.getName());

				boolean isDetecting=true;
				boolean isFirstClickOnPlayer=false;
				boolean hasDetected=false;

				PlayerSprites.loadImagesSprites();
				SpriteExplosion.loadImagesSprites();
				SpriteBullet.loadImagesSprites();
				
				UserInterface.initUserInterface();

				for(;;) {
					isDetecting=true;
					for(;isDetecting;){
						Bullet b = playerShip.getCurrentWeapon().getLoadingBullet();
						if(b != null){
							b.loadPower();
						}
						MotionEvent event;
						for(int i=0;(((event=context.pollMotion())!=null))||i>30;++i){
							Point point=new Point(event.getX(),event.getY());
							gesture.addPoint(point);
							isDetecting=true;
							if((event.getKind()==MotionEvent.Kind.ACTION_DOWN)){
								hasDetected=false;
								gesture.clear();

								int posXcenter=playerShip.getPosXCenter();
								int posYcenter=playerShip.getPosYCenter();
								
								Game game = Game.getTheGame();
								State state = game.getCurrentState();
								
								if(state == Game.State.OnMenu){
									int level = UserInterface.getLevelToLaunch(point);
									if(level!=-1){
										game.setCurrentNbLv(level);
									}
								} else if (state == Game.State.Playing){
									Weapon w = UserInterface.clickIsWeaponSelect(point);
									if(w != null){
										playerShip.getListWeapon().setCurrentWeapon(w.getName());
										isFirstClickOnPlayer=false;
									}
									else if ((point.getX()>posXcenter-(imgPlayer.getWidth(null)/2)) &&
											 (point.getX()<posXcenter+(imgPlayer.getWidth(null)/2)) &&
											 (point.getY()>posYcenter-(imgPlayer.getHeight(null)/2)) &&
											 (point.getY()<posYcenter+(imgPlayer.getHeight(null)/2))){
										isFirstClickOnPlayer=true;
										playerShip.shoot(posXcenter,posYcenter);
									}
									else{
										isFirstClickOnPlayer=false;
									}
								}
							}
							if((event.getKind()==MotionEvent.Kind.ACTION_UP)){
								isDetecting=false;
								break;
							}
						}
						final Iterator<Point> iterPoint=gesture.getListPoint().iterator();
						final long currentTime=System.currentTimeMillis();
						if(currentTime-lastDraw>=15){
							backgroundScroller.verticalScroll();
							if(lastDraw-lastImpulsePlayer>1000){
								if(hasDetected){
									if(gesture.getLastGestureDetected()==GestureType.LEFT_CIRCLE ||
									   gesture.getLastGestureDetected()==GestureType.RIGHT_CIRCLE)
									EscapeWorld.getTheWorld().setActive(playerShip.getBody(), true);
									hasDetected=false;
									gesture.clear();
								}
								if(playerShip.getBody().isActive()){
									DisplayableMonitor.getPlayersprite().setCurrentSprite(PlayerSprites.SpriteType.BASIC_IMAGE);
								}
								else if(!playerShip.isAlive()){
									DisplayableMonitor.getPlayersprite().setCurrentSprite(PlayerSprites.SpriteType.DEAD_SHIP);
								}
							}
							
							
							final boolean colorSuccess=hasDetected;
							final long fpsTmp=lastDraw;
							final boolean showStat=showPerfMenu;
							context.render(new ApplicationRenderCode() {
								@Override
								public void render(Graphics2D graphics) {	
									DisplayableMonitor.drawBackground(backgroundScroller);
									State state = Game.getTheGame().getCurrentState();
									if(state == Game.State.OnMenu){
										DisplayableMonitor.drawMenu();
										DisplayableMonitor.display(graphics);
									}
									else if(state==Game.State.Ended){//End game or start game
										if(!playerShip.getBody().isActive()){
											DisplayableMonitor.getPlayersprite().setCurrentSprite(PlayerSprites.SpriteType.DEAD_SHIP);
											DisplayableMonitor.drawPlayer();
											DisplayableMonitor.drawFullText("GAME OVER");
											DisplayableMonitor.display(graphics);
										}
										else{
											DisplayableMonitor.drawPlayer();
											DisplayableMonitor.drawFullText("VICTORY");
											DisplayableMonitor.display(graphics);
										}
									}
									else {
										DisplayableMonitor.drawShip();
										DisplayableMonitor.drawPlayer();
										DisplayableMonitor.drawBullets();
										DisplayableMonitor.drawBonus();
										DisplayableMonitor.drawSpriteExplosion();
										DisplayableMonitor.drawUIScoresAndLife();
										DisplayableMonitor.drawUIWeapons();
										if(showStat){
											DisplayableMonitor.drawPerformanceMenu((int)(1000/(currentTime-fpsTmp)));
										}
										DisplayableMonitor.display(graphics);
										if(colorSuccess){
											graphics.setPaint(Color.GREEN);
										}
										else{
											graphics.setPaint(Color.RED);
										}
										while(iterPoint.hasNext()){
											Point p=iterPoint.next();
											graphics.drawRect(p.x, p.y, 5, 5);
										}
									}
								}
							});
							lastDraw=System.currentTimeMillis();
						}
					}
					if(isFirstClickOnPlayer && playerShip.getBody().isActive()){
						if(gesture.calculForceFromLine()){
							playerShip.shoot(playerShip.getPosXCenter(),playerShip.getPosYCenter()-(imgPlayer.getHeight(null)));
							playerShip.fire();
							
							hasDetected=true;
						} else {
							hasDetected=false;
							gesture.clear();
						}
					}
					else if(playerShip.getBody().isActive()){
						gesture.detectGesture();
						switch(gesture.getLastGestureDetected()){
						case NOT_GESTURE:
							hasDetected=false;
							gesture.clear();
							break;
						case BACK_OFF:
							if(playerShip.getCurrentWeapon().getLoadingBullet() == null){
								playerShip.move();
								playerSprite.setCurrentSprite(PlayerSprites.SpriteType.BASIC_IMAGE);
							}
							hasDetected=true;
							break;
						case LEFT_DIAG:
							if(playerShip.getCurrentWeapon().getLoadingBullet() == null){
								playerShip.move();
								playerSprite.setCurrentSprite(PlayerSprites.SpriteType.LEFT_MOVE);
							}
							hasDetected=true;
							break;
						case RIGHT_DIAG:
							if(playerShip.getCurrentWeapon().getLoadingBullet() == null){
								playerShip.move();
								playerSprite.setCurrentSprite(PlayerSprites.SpriteType.RIGHT_MOVE);
							}
							hasDetected=true;
							break;
						case HORIZONTAL_LEFT:
							if(playerShip.getCurrentWeapon().getLoadingBullet() == null){
								playerShip.move();
								playerSprite.setCurrentSprite(PlayerSprites.SpriteType.LEFT_MOVE);
							}
							hasDetected=true;
							break;
						case HORIZONTAL_RIGHT:
							if(playerShip.getCurrentWeapon().getLoadingBullet() == null){
								playerShip.move();
								playerSprite.setCurrentSprite(PlayerSprites.SpriteType.RIGHT_MOVE);
							}
							hasDetected=true;
							break;
						case LEFT_CIRCLE:
							EscapeWorld.getTheWorld().setActive(playerShip.getBody(), false);
							playerSprite.setCurrentSprite(PlayerSprites.SpriteType.LEFT_VRILLE);
							hasDetected=true;
							break;
						case RIGHT_CIRCLE:
							EscapeWorld.getTheWorld().setActive(playerShip.getBody(), false);
							playerSprite.setCurrentSprite(PlayerSprites.SpriteType.RIGHT_VRILLE);
							hasDetected=true;
							break;
						case CHEAT_CODE:
							Game.getTheGame().getPlayer1().codeKonami();
							hasDetected=true;
							break;
						case STATS:
							hasDetected=true;
							showPerfMenu=!showPerfMenu;
							break;
						case NOT_DETECTED:
							break;
						default:
							break;
						}
					}
					lastImpulsePlayer=System.currentTimeMillis();
				}
			}
		};
		Application.run(name, width, height, applicationCode);
	}

	/**
	 * Get the Height of the window
	 * @return The Height of the window
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the width of the window
	 * @return The width if the window
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the background of the window
	 * @param levelName The level name
	 */
	public void setBackGround(String levelName) {
		Objects.requireNonNull(levelName);
		
		Image img=ImagesFactory.getTheImagesFactory().createBackGroundImage(levelName);
		FrontImages.addImages(levelName, img);
		backgroundScroller.setBackgroundImage(img);
	}
}
