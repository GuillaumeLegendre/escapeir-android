package fr.umlv.escape.game;

import java.io.IOException;

import android.content.Context;

import fr.umlv.escape.file.IllegalFormatContentFile;
import fr.umlv.escape.file.ParseFile;
import fr.umlv.escape.front.FrontApplication;

/**This class supplies methods to create properly a {@link Level}.
 */
public class LevelFactory {
	private static LevelFactory TheLevelFactory;
	private ParseFile parseLevel;

	private LevelFactory(){
	}
	
	/**
	 * Create a {@link Level}
	 * @param levelName The name of the level.
	 * @return The level created
	 * @throws IOException
	 * @throws IllegalFormatContentFile
	 */
	public Level createLevel(Context context, String levelName) throws IOException, IllegalFormatContentFile{
		Level newLevel = new Level(levelName);
		this.parseLevel=new ParseFile(context, "levels/"+levelName);
		
		String backgroundName=this.parseLevel.getNextLine();;
		//Get the background of the level
		if(backgroundName==null){
			throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
		}
		Game.getTheGame().getFrontApplication().getBattleField().setBackground(FrontApplication.frontImage.getImage(backgroundName));
		
		String waveName=this.parseLevel.getNextLine();
		long delay;		
		//Get all the wave
		if(waveName==null){
			throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
		}
		while(waveName!=null){
			if(!newLevel.addWaveList(WaveFactory.getTheWaveFactory().createWave(context, waveName))){
				throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
			}
			try{
				delay=Long.valueOf(parseLevel.getNextLine());
			}
			catch(NumberFormatException e){
				throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
			}
			if(!newLevel.addDelayList(delay)){
				throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
			}
			waveName=parseLevel.getNextLine();
		}

		return newLevel;
	}
	
	/** Get the unique instance of {@link LevelFactory}.
	 * @return The unique instance of {@link LevelFactory}
	 */
	public static LevelFactory getTheLevelFactory(){
		if(LevelFactory.TheLevelFactory==null){
			LevelFactory.TheLevelFactory = new LevelFactory();
		}
		return LevelFactory.TheLevelFactory;
	}
}
