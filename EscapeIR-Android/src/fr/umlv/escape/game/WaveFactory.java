package fr.umlv.escape.game;

import java.io.IOException;

import android.content.Context;

import fr.umlv.escape.Objects;
import fr.umlv.escape.bonus.BonusFactory;
import fr.umlv.escape.file.IllegalFormatContentFile;
import fr.umlv.escape.file.ParseFile;
import fr.umlv.escape.front.FrontApplication;
import fr.umlv.escape.ship.ShipFactory;

/**This class supplies methods to create properly a {@link Wave}.
 */
public class WaveFactory {
	private static WaveFactory TheWaveFactory;
	private ParseFile parseWave;

	private WaveFactory(){
	}
	
	/**
	 * Create a {@link Wave}.
	 * 
	 * @param waveName
	 * @return the {@link Wave} created.
	 * @throws IOException
	 * @throws IllegalFormatContentFile
	 */
	public Wave createWave(Context context, String waveName) throws IOException, IllegalFormatContentFile{
		Objects.requireNonNull(waveName);
		
		Wave newWave = new Wave(waveName);
		this.parseWave=new ParseFile(context, "levels/waves/"+waveName);

		String shipName;
		String trajectory;
		int posX,posY,health;
		String wallReference;

		//Get all the ships of the wave
		while((shipName=this.parseWave.getNextLine())!=null){
			try{
				wallReference = this.parseWave.getNextLine();
				if(wallReference.equals("leftwall")){
					posX=Integer.parseInt(this.parseWave.getNextLine());	
				} else if(wallReference.equals("rightwall")){
					posX=FrontApplication.WIDTH+Integer.parseInt(this.parseWave.getNextLine());
				} else if(wallReference.equals("middle")){
					posX=FrontApplication.WIDTH/2+Integer.parseInt(this.parseWave.getNextLine());
				} else {
					throw new IllegalFormatContentFile("the file "+parseWave.getFileName()+" is corrupted");
				}
				wallReference = this.parseWave.getNextLine();
				if(wallReference.equals("topwall")){
					posY=Integer.parseInt(this.parseWave.getNextLine());	
				} else if(wallReference.equals("botwall")){
					posY=FrontApplication.HEIGHT+Integer.parseInt(this.parseWave.getNextLine());
				} else if(wallReference.equals("middle")){
					posY=FrontApplication.HEIGHT/2+Integer.parseInt(this.parseWave.getNextLine());
				} else {
					throw new IllegalFormatContentFile("the file "+parseWave.getFileName()+" is corrupted");
				}
				health=Integer.parseInt(this.parseWave.getNextLine());
				trajectory=this.parseWave.getNextLine();
				if(!this.parseWave.getNextLine().equals("-")){
					throw new IllegalFormatContentFile("the file "+parseWave.getFileName()+" is corrupted");
				}
			}
			catch(NumberFormatException e){
				throw new IllegalFormatContentFile("the file "+parseWave.getFileName()+" is corrupted");
			}
			// Call the shipFactory to create the ship that was described in the file and then add it
			// To the wave to create.
			newWave.shipList.add(ShipFactory.getTheShipFactory().createShip(shipName,posX,posY,health,trajectory));
		}
		return newWave;
	}
	
	/** Get the unique instance of {@link BonusFactory}
	 * @return The unique instance of {@link BonusFactory}
	  */
	public static WaveFactory getTheWaveFactory(){
		if(WaveFactory.TheWaveFactory==null){
			WaveFactory.TheWaveFactory = new WaveFactory();
		}
		return WaveFactory.TheWaveFactory;
	}
}
