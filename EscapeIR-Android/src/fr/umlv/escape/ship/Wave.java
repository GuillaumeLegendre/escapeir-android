package fr.umlv.escape.ship;

import java.util.ArrayList;
import java.util.Iterator;

import fr.umlv.escape.Objects;
import fr.umlv.escape.ship.Ship;

/**
 * Class that represent a wave of {@link Ship}.
 */
public class Wave {
	private final String name;
	private final ArrayList<Ship> shipList;
	
	/**
	 * Constructor.
	 * @param name The name of the wave.
	 */
	public Wave(String name){
		Objects.requireNonNull(name);
		
		this.name=name;
		this.shipList=new ArrayList<Ship>();
	}
	
	/**
	 * Active and launch all {@link Ship} containing in the wave.
	 */
	public void startWave(){
		Iterator<Ship> iterShip=this.shipList.iterator();
		while(iterShip.hasNext()){
			Ship ship=iterShip.next();
			ship.body.setActive(true);
			ship.move();
		}
	}

	/**
	 * Get the list of {@link Ship} that compose the wave.
	 * @return The
	 */
	public List<Ship> getShipList() {
		return shipList;
	}
	
	@Override
	public String toString(){
		String res=this.name;
		Iterator<Ship> iterShip=this.shipList.iterator();
		while(iterShip.hasNext()){
			res+=" "+iterShip.next().toString();
		}
		return res;
	}
}
