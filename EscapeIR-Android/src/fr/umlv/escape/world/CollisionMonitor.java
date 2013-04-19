package fr.umlv.escape.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.escape.bonus.Bonus;
import fr.umlv.escape.bonus.BonusFactory;
import fr.umlv.escape.front.DisplayableMonitor;
import fr.umlv.escape.game.Game;
import fr.umlv.escape.game.Player;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.weapon.Bullet;
import fr.umlv.escape.weapon.ListWeapon;

/**
 * This class manage what should happen when body collide. It is a contact listener of the {@link EscapeWorld}
 * @implements Conctlistener
 */
public class CollisionMonitor implements ContactListener{
	private final List<Body> elemToDelete;
	private boolean createBonus;
	private final Random random = new Random(0);
	private final static int PROBABILITY_NEW_BONUS = 20;
	
	/**
	 * Constructor.
	 */
	public CollisionMonitor(){
		EscapeWorld.getTheWorld().setContactListener(this);
		this.elemToDelete=new ArrayList<>();
	}
	
	/**
	 * As no operations can be done on bodies while there are colliding some operations are postponed
	 * and are done in this method which have to be called just after a step.
	 */
	public void performPostStepCollision(){
		Iterator<Body> iterDeadBody=this.elemToDelete.iterator();
		
		while(iterDeadBody.hasNext()){
			Body body=iterDeadBody.next();
			EscapeWorld.getTheWorld().setActive(body, false);
			if(this.createBonus){
				Bonus bonus=BonusFactory.getTheBonusFactory().createBonus("WeaponReloader", (int)((body.getPosition().x*EscapeWorld.SCALE)), (int)((body.getPosition().y*EscapeWorld.SCALE)), (int)(Math.random()*(4-1))+1);
				bonus.move();
				DisplayableMonitor.addBonus(bonus);
				this.createBonus=false;
			}
			iterDeadBody.remove();
		}
	}
	
	@Override
	public void beginContact(Contact arg0) {
		Player player=Game.getTheGame().getPlayer1();
		Ship shipPlayer=player.getShip();
		Body body;
		Body body2;
		Ship enemy;
		Bullet bullet;
		Bonus bonus;
		
		//if one of the two body that collided is the body of the player's ship
		if((arg0.getFixtureA().getBody()==shipPlayer.getBody())||
		   (arg0.getFixtureB().getBody()==shipPlayer.getBody())){
			//get the other body that collided
			if(arg0.getFixtureA().getBody()==shipPlayer.getBody()){
				body=arg0.getFixtureB().getBody();
			} else {
				body=arg0.getFixtureA().getBody();
			}
			//if the second body that collided is an enemy
			if((enemy=DisplayableMonitor.getShip(body))!=null){
				shipPlayer.takeDamage(10);
				enemy.takeDamage(20);
				if(!enemy.isAlive()){
					impactEnemyDead(enemy,player);
					elemToDelete.add(body);
				}
			} //else if the second body that collided is a bullet 
			else if((bullet=DisplayableMonitor.getBullet(body))!=null){
				shipPlayer.takeDamage(bullet.getDamage());
				elemToDelete.add(body);
			} //else if the second body that collided is a bonus 
			else if((bonus=DisplayableMonitor.getBonus(body))!=null){
				ListWeapon playerWeapons = shipPlayer.getListWeapon();
				playerWeapons.addWeapon(bonus.getType(), bonus.getQuantity());
				elemToDelete.add(body);
			}
			if(!shipPlayer.isAlive()){
				if(player.getLife()>=1){
					player.setLife(player.getLife()-1);
					shipPlayer.setHealth(99);
				}
				elemToDelete.add(shipPlayer.getBody());
			}
		} else {
			body=arg0.getFixtureA().getBody();
			body2=arg0.getFixtureB().getBody();

			if((enemy=DisplayableMonitor.getShip(body))!=null){
				if((bullet=DisplayableMonitor.getBullet(body2))==null){
					throw new AssertionError();
				}
				enemy.takeDamage(bullet.getDamage());
				if(bullet == player.getShip().getCurrentWeapon().getLoadingBullet()){
					player.getShip().getCurrentWeapon().setLoadingBullet(null);
				}
				if(!enemy.isAlive()){
					elemToDelete.add(body);
					int score=0;
					switch(enemy.getBasicName()){
					case "DefaultShip":score=10;break;
					case "KamikazeShip":score=25;break;
					case "BatShip":score=50;break;
					case "FirstBoss":
					case "SecondBoss":
					case "ThirdBoss":score=1000;break;
					default:
						throw new AssertionError();
					}
					player.addScore(score);
					if(random.nextInt(100) <= PROBABILITY_NEW_BONUS){
						this.createBonus=true;
					}
				}
				if((!bullet.getNameLvl().equals("FireBall_3")) && (!bullet.getName().equals("XRay"))){
					elemToDelete.add(body2);
				}
			} else {
				bullet=DisplayableMonitor.getBullet(body);
				enemy=DisplayableMonitor.getShip(body2);
				if((bullet==null)||(enemy==null)){
					throw new AssertionError();
				}
				enemy.takeDamage(bullet.getDamage());
				if(bullet == player.getShip().getCurrentWeapon().getLoadingBullet()){
					player.getShip().getCurrentWeapon().setLoadingBullet(null);
				}
				if(!enemy.isAlive()){
					elemToDelete.add(body2);
					if(random.nextInt(100) <= PROBABILITY_NEW_BONUS){
						this.createBonus=true;
					}
					impactEnemyDead(enemy, player);
				}
				if((!bullet.getNameLvl().equals("FireBall_3"))&&(!bullet.getName().equals("XRay"))){
					elemToDelete.add(body);
				}
			}
		}
	}

	@Override
	public void endContact(Contact arg0) {
		//nothing to do
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		//Nothing to do	
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		//Nothing to do
	}
	
	private void impactEnemyDead(Ship enemy, Player player){
		int score=0;
		switch(enemy.getBasicName()){
		case "DefaultShip":score=10;break;
		case "KamikazeShip":score=25;break;
		case "BatShip":score=50;break;
		case "FirstBoss":
		case "SecondBoss":
		case "ThirdBoss":score=1000;break;
		default:
			throw new AssertionError();
		}
		player.addScore(score);
	}
}
