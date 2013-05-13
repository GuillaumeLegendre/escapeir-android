package fr.umlv.escape.bonus;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;

import android.graphics.Bitmap;

import fr.umlv.escape.Objects;
import fr.umlv.escape.front.BattleField;
import fr.umlv.escape.front.FrontImages;
import fr.umlv.escape.world.Bodys;
import fr.umlv.escape.world.EscapeWorld;

/**This class supplies methods to create properly a {@link Bonus}.
 */
public class BonusFactory {
	private final FrontImages frontImages;
	private final BattleField battleField;
	
	public BonusFactory(FrontImages frontImages, BattleField battleField){
		this.frontImages = frontImages;
		this.battleField = battleField;
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
		Bitmap img;
		
		if(bonusName.equals("WeaponReloader")){
			switch(type){
			case 1:
				stringType="MissileLauncher";
				frontImages.addImages(bonusName+stringType);
				img=frontImages.getImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(), img.getHeight(), 0);
				bonus= new WeaponReloader(50, body,stringType);
				break;
			case 2:
				stringType="FlameThrower";
				frontImages.addImages(bonusName+stringType);
				img=frontImages.getImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(), img.getHeight(), 0);
				bonus= new WeaponReloader(25, body,stringType);
				break;
			case 3:
				stringType="ShiboleetThrower";
				frontImages.addImages(bonusName+stringType);
				img=frontImages.getImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(), img.getHeight(), 0);
				bonus= new WeaponReloader(10, body,stringType);
				break;
			case 4:
				stringType="LaserBeam";
				frontImages.addImages(bonusName+stringType);
				img=frontImages.getImage(bonusName+stringType);
				body=Bodys.createBasicRectangle(posX, posY, img.getWidth(), img.getHeight(), 0);
				bonus= new WeaponReloader(10, body,stringType);
				break;
			default : throw new IllegalArgumentException(type+" isn't a legal type");
			}
		} else{
			throw new IllegalArgumentException(bonusName+" isn't a legal bonus");
		}
		
		Filter filter=new Filter();
		filter.categoryBits=EscapeWorld.CATEGORY_BONUS;
		filter.maskBits=EscapeWorld.CATEGORY_PLAYER;
		body.getFixtureList().setFilterData(filter);
		body.setActive(true);

		battleField.addBonus(bonus);
		return bonus;
	}
}
