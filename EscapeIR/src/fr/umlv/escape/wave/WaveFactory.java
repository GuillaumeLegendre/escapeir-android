package fr.umlv.escape.wave;

import java.io.IOException;
import java.util.Objects;

import fr.umlv.escape.bonus.BonusFactory;
import fr.umlv.escape.file.IllegalFormatContentFile;
import fr.umlv.escape.file.ParseFile;
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
	public Wave createWave(String waveName) throws IOException, IllegalFormatContentFile{
		Objects.requireNonNull(waveName);
		
		Wave newWave = new Wave(waveName);
		this.parseWave=new ParseFile("Levels/Waves/"+waveName);

		String shipName;
		String trajectory;
		int posX,posY,health;

		//Get all the ships of the wave
		while((shipName=this.parseWave.getNextLine())!=null){
			try{
				posX=Integer.parseInt(this.parseWave.getNextLine());
				posY=Integer.parseInt(this.parseWave.getNextLine());
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
			newWave.getShipList().add(ShipFactory.getTheShipFactory().createShip(shipName,posX,posY,health,trajectory));
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
