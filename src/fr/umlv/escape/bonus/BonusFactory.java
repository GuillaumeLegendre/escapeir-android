package fr.umlv.escape.bonus;

import java.awt.Image;
import java.util.Objects;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import fr.umlv.escape.front.DisplayableMonitor;
import fr.umlv.escape.front.FrontImages;
import fr.umlv.escape.front.ImagesFactory;
import fr.umlv.escape.world.Bodys;

/**This class supplies methods to create properly a {@link Bonus}.
 */
public class BonusFactory {
	private static BonusFactory theBonusFactory;

	private BonusFactory(){
	}

	 /** Create a {@link Bonus}.
	  * 
	  * @param bonusName The name of the bonus to create.
	  * @param posX The x position of the bonus to create.
	  * @param posY The y position of the bonus to create.
	  * @param type The type of the bonus to create. Each {@link Bonus} can have their owns types.
	  * @return the bonus created.
	  */
	public Bonus createBonus(String bonusName, int posX, int posY, int type) {
		Objects.requireNonNull(bonusName);
		
		Bonus bonus;
		String stringType;
		Body body;
		Image img;
		
		switch(bonusName){
		case "WeaponReloader" :
			switch(type){
			case 1:
				stringType="MissileLauncher";
				img=ImagesFactory.getTheImagesFactory().createBonusImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(null), img.getHeight(null), 0);
				bonus= new WeaponReloader(50, body,stringType);
				break;
			case 2:
				stringType="FlameThrower";
				img=ImagesFactory.getTheImagesFactory().createBonusImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(null), img.getHeight(null), 0);
				bonus= new WeaponReloader(25, body,stringType);
				break;
			case 3:
				stringType="ShiboleetThrower";
				img=ImagesFactory.getTheImagesFactory().createBonusImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(null), img.getHeight(null), 0);
				bonus= new WeaponReloader(10, body,stringType);
				break;
			case 4:
				stringType="LaserBeam";
				img=ImagesFactory.getTheImagesFactory().createBonusImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(null), img.getHeight(null), 0);
				bonus= new WeaponReloader(10, body,stringType);
				break;
			default : throw new IllegalArgumentException(type+" isn't a legal type");
			}
			break;
		default : throw new IllegalArgumentException(bonusName+" isn't a legal bonus");
		}
		
		FrontImages.addImages(bonusName+stringType, img);
		Filter filter=new Filter();
		filter.categoryBits=16;
		filter.maskBits=2;
		body.getFixtureList().setFilterData(filter);
		body.setActive(true);

		DisplayableMonitor.addBonus(bonus);
		return bonus;
	}

	/** Get the unique instance of {@link BonusFactory}.
	 * @return The unique instance of {@link BonusFactory}
	 */
	public static BonusFactory getTheBonusFactory(){
		if(BonusFactory.theBonusFactory==null){
			BonusFactory.theBonusFactory = new BonusFactory();
		}
		return BonusFactory.theBonusFactory;
	}
}
