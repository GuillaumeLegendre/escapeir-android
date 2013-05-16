package fr.umlv.escape.ship;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import android.graphics.Bitmap;

import fr.umlv.escape.Objects;
import fr.umlv.escape.bonus.BonusFactory;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.move.DownMove;
import fr.umlv.escape.move.KamikazeMove;
import fr.umlv.escape.move.LeftDampedMove;
import fr.umlv.escape.move.LeftMove;
import fr.umlv.escape.move.LeftRightMove;
import fr.umlv.escape.move.Movable;
import fr.umlv.escape.move.RightDampedMove;
import fr.umlv.escape.move.RightMove;
import fr.umlv.escape.move.SquareLeft;
import fr.umlv.escape.move.SquareRight;
import fr.umlv.escape.move.UpMove;
import fr.umlv.escape.weapon.BatShipShoot;
import fr.umlv.escape.weapon.DoNotShoot;
import fr.umlv.escape.weapon.FirstBossShoot;
import fr.umlv.escape.weapon.ListWeapon;
import fr.umlv.escape.weapon.SecondBossShoot;
import fr.umlv.escape.weapon.ShootDown;
import fr.umlv.escape.weapon.ThirdBossShoot;
import fr.umlv.escape.world.Bodys;
import fr.umlv.escape.world.EscapeWorld;

/**This class supplies methods to create properly a {@link Ship}.
 */
public class ShipFactory {
	private static ShipFactory TheShipFactory;
	private static int ghostShoot=0;

	private ShipFactory(){
	}

	/**
	 * Create a {@link Ship}.
	 * 
	 * @param shipName the name of the ship to create.
	 * @param posX the x position of the ship to create.
	 * @param posY the y position of the ship to create.
	 * @param health the health of the ship to create.
	 * @param trajectory the name of the move behavior of the ship.
	 * @return the ship created.
	 */
	public Ship createShip(String shipName,int posX, int posY,int health, int trajectoryID){
		Objects.requireNonNull(shipName);
		
		Movable move;
		switch (trajectoryID){
			case 1: //"LeftMove":
				move=new LeftMove();
				break;
			case 2: //"RightMove":
				move=new RightMove();
				break;
			case 3: //"DownMove":
				move=new DownMove();
				break;
			case 4: //"UpMove":
				move=new UpMove();
				break;
			case 5: //"SquareRight":
				move=new SquareRight();
				break;
			case 6: //"SquareLeft":
				move=new SquareLeft();
				break;
			case 7: //"StraightLine":
				move=new UpMove();
				break;
			case 8: //"LeftDampedMove":
				move=new LeftDampedMove();
				break;
			case 9: //"RightDampedMove":
				move=new RightDampedMove();
				break;
			case 10: //"KamikazeMove":
				move=new KamikazeMove();
				break;
			case 11: //"LeftRight":
				move=new LeftRightMove();
				break;
			default:
				throw new IllegalArgumentException(trajectoryID+"not accepted");
		}
		FrontApplication.frontImage.addImages(shipName);
		
		Bitmap img = FrontApplication.frontImage.getImage(shipName);
		Body body=Bodys.createBasicRectangle((posX+((float)img.getWidth()/2)), (posY+((float)img.getHeight()/2)), img.getWidth(), img.getHeight(), 0);
		Filter filter=new Filter();
		if(shipName.equals("DefaultShipPlayer")){
			filter.categoryBits=EscapeWorld.CATEGORY_PLAYER;
			filter.maskBits=EscapeWorld.CATEGORY_ENNEMY | EscapeWorld.CATEGORY_BULLET_ENNEMY;
		} else{
			filter.categoryBits=EscapeWorld.CATEGORY_ENNEMY;
			filter.maskBits=EscapeWorld.CATEGORY_PLAYER | EscapeWorld.CATEGORY_BULLET_PLAYER;
		}
		body.getFixtureList().setFilterData(filter);
		body.setActive(false);
		Ship ship;
		switch (shipName){
		case "DefaultShip":
			ship = new Ship("DefaultShip",body,health,move,new ShootDown());
			ship.getCurrentWeapon().setInfinityQty();
			ship.getCurrentWeapon().setGhostShoot(ghostShoot);
			ghostShoot=(ghostShoot+1)%7;
			DisplayableMonitor.addShip(ship);
			break;
		case "DefaultShipPlayer":
			ship=  new Ship("DefaultShipPlayer",body,health,move,new ShootDown());
			ship.getListWeapon().setCurrentWeapon("MissileLauncher");
			ship.getCurrentWeapon().addQte(ListWeapon.BASIC_QTY_BULLET);
			break;
		case "KamikazeShip":
			ship = new Ship("KamikazeShip",body,health,move,new DoNotShoot());
			ship.getCurrentWeapon().addQte(Integer.MIN_VALUE);
			DisplayableMonitor.addShip(ship);
			break;
		case "BatShip":
			ship = new Ship("BatShip",body,health,move,new BatShipShoot());
			ship.getListWeapon().addWeapon("FlameThrower",Integer.MIN_VALUE);
			ship.getListWeapon().setCurrentWeapon("FlameThrower");
			DisplayableMonitor.addShip(ship);
			break;
		case "FirstBoss":
			ship = new FirstBoss(body,health,move,new ShootDown());
			ship.setShootBehaviour(new FirstBossShoot((FirstBoss) ship));
			ship.getListWeapon().addWeapon("ShiboleetThrower",Integer.MIN_VALUE);
			ship.getListWeapon().setCurrentWeapon("ShiboleetThrower");
			DisplayableMonitor.addShip(ship);
			for(int i=2;i<=7;++i){
				img=BitmapFactory.getTheImagesFactory().createShipImage(shipName+i);
				FrontImages.addImages(shipName+i,img);
			}
			break;
		case "SecondBoss":
			ship = new SecondBoss(body,health,move,new ShootDown());
			ship.setShootBehaviour(new SecondBossShoot((SecondBoss) ship));
			ship.getListWeapon().addWeapon("ShiboleetThrower",Integer.MIN_VALUE);
			ship.getListWeapon().setCurrentWeapon("ShiboleetThrower");
			DisplayableMonitor.addShip(ship);
			for(int i=2;i<=7;++i){
				img=BitmapFactory.getTheImagesFactory().createShipImage(shipName+i);
				FrontImages.addImages(shipName+i,img);
			}
			break;
		case "ThirdBoss":
			ship = new ThirdBoss(body,health,move,new ShootDown());
			ship.setShootBehaviour(new ThirdBossShoot((ThirdBoss) ship));
			ship.getListWeapon().addWeapon("MissileLauncher",Integer.MIN_VALUE);
			ship.getListWeapon().addWeapon("FlameThrower",Integer.MIN_VALUE);
			ship.getListWeapon().addWeapon("ShiboleetThrower",Integer.MIN_VALUE);
			ship.getListWeapon().setCurrentWeapon("BasicMissile");
			DisplayableMonitor.addShip(ship);
			for(int i=2;i<=5;++i){
				img=BitmapFactory.getTheImagesFactory().createShipImage(shipName+i);
				FrontImages.addImages(shipName+i,img);
			}
			break;
		default:
			throw new IllegalArgumentException(shipName+"not accepted");
		}
		
		return ship;
	}
	
	/** Get the unique instance of {@link BonusFactory}
	 * @return The unique instance of {@link BonusFactory}
	  */
	public static ShipFactory getTheShipFactory(){
		if(ShipFactory.TheShipFactory==null){
			ShipFactory.TheShipFactory = new ShipFactory();
		}
		return ShipFactory.TheShipFactory;
	}
}
