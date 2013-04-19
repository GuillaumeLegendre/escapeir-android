package fr.umlv.escape.weapon;

import org.jbox2d.common.Vec2;

import android.graphics.Point;

import fr.umlv.escape.Objects;
import fr.umlv.escape.game.Game;
import fr.umlv.escape.move.DownUpMove;
import fr.umlv.escape.move.LeftRightMove;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.ship.ThirdBoss;
import fr.umlv.escape.world.EscapeWorld;

/**
 * Class that represent the shoot behavior of the third boss
 * @implements {@link Shootable}.
 */
public class ThirdBossShoot implements Shootable{
	private long lastDownMove;
	private final ThirdBoss bossShip;
	private long currentTime;
	private int initialYpos;
	private boolean isDownUp;
	private long lastShoot;
	private int currentWeapon;

	/**
	 * Constructor
	 * @param bossShip The {@link Ship} of the boss.
	 */
	public ThirdBossShoot(ThirdBoss bossShip) {
		Objects.requireNonNull(bossShip);

		this.bossShip=bossShip;
		this.lastDownMove=0;
		this.initialYpos=bossShip.getPosYCenter();
		this.isDownUp=false;
		this.lastShoot=0;
		this.lastDownMove=System.currentTimeMillis();
		this.currentWeapon=0;
	}

	/**
	 * Manage how the boss have to shoot depending to his state.
	 */
	@Override
	public boolean shoot(Weapon weapon, int x, int y) {
		Objects.requireNonNull(weapon);

		int state=bossShip.getState();
		if(state>5){
			bossShip.setAlive(false);
			EscapeWorld.getTheWorld().setActive(bossShip.getBody(),false);
			return false;
		}
		Ship playerShip=Game.getTheGame().getPlayer1().getShip();
		currentTime=System.currentTimeMillis();
		Point positionShip = new Point(x,y);
		if((!isDownUp) && (currentTime-lastDownMove>10000) && (bossShip.getPosXCenter()<playerShip.getPosXCenter()+10 && bossShip.getPosXCenter()>playerShip.getPosXCenter()-10)){
			bossShip.setMoveBehaviour(new DownUpMove());
			isDownUp=true;
			lastDownMove=currentTime;
			return false;
		}
		else if(isDownUp && (bossShip.getPosYCenter()<=initialYpos) && (currentTime-lastDownMove>1000)){
			bossShip.setMoveBehaviour(new LeftRightMove());
			isDownUp=false;
			lastDownMove=currentTime;
			return false;
		}
		if(!isDownUp){
			if(weapon.getLoadingBullet()!=null){
				weapon.getLoadingBullet().loadPower();
				if(weapon.getLoadingBullet().getCurrentLoad() == weapon.getLoadingBullet().getMaxLoad()){
					return true;
				}
				return false;
			}
			else{
				
				switch (currentWeapon){
				case 0:
					if(state<=3){
						positionShip.x=x-50;
						bossShip.getListWeapon().setCurrentWeapon("MissileLauncher");					
					}
					else{
						if(currentTime-lastShoot<1000){
							return false;
						}
						lastShoot=currentTime;
						currentWeapon=(currentWeapon+1)%4;
					}		
					break;
				case 1:
					if(state<=1){
						positionShip.x=x-20;
						bossShip.getListWeapon().setCurrentWeapon("MissileLauncher");					
					}
					else{
						if(currentTime-lastShoot<1000){
							return false;
						}
						lastShoot=currentTime;
						currentWeapon=(currentWeapon+1)%4;
					}
					break;
				case 2:
					if(state<=4){
						positionShip.x=x+50;
						bossShip.getListWeapon().setCurrentWeapon("MissileLauncher");
					}
					else{
						if(currentTime-lastShoot<1000){
							return false;
						}
						lastShoot=currentTime;
						currentWeapon=(currentWeapon+1)%4;
					}
					break;
				case 3:
					if(state<=2){
						positionShip.x=x+20;
						bossShip.getListWeapon().setCurrentWeapon("ShiboleetThrower");
					}
					else{
						if(currentTime-lastShoot<1000){
							return false;
						}
						lastShoot=currentTime;
						currentWeapon=(currentWeapon+1)%4;
					}
					break;
				default:
					throw new AssertionError();
				}
			}
			if(currentTime-lastShoot>1000 && weapon.getLoadingBullet()==null){
				
				bossShip.getCurrentWeapon().setLoadingBullet(bossShip.getCurrentWeapon().fire(positionShip));
				lastShoot=currentTime;
				currentWeapon=(currentWeapon+1)%4;
				return false;
			}
		}
		return false;
	}

	@Override
	public void fire(Weapon weapon) {
		Objects.requireNonNull(weapon);

		if(weapon == null) throw new IllegalArgumentException("Weapon can't be Null");
		Bullet b = weapon.getLoadingBullet();
		if(b == null) throw new IllegalStateException("Bullet can't be Null");
		b.fire(new Vec2(0f,2f));
		weapon.setLoadingBullet(null);
	}
}
