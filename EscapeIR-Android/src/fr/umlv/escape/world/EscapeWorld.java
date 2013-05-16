package fr.umlv.escape.world;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.umlv.escape.Objects;

/**
 * This class represent the world simulation of the Escape game. It is the link with the Jbox2d lib.
 * All uses of the jbox2d should be done using this class. This class is thread safe so that many thread can
 * interact with the world at the same time.
 */
public class EscapeWorld {
	private static EscapeWorld TheWorld; // EscapeWorld Singleton
	
	private final World world;
    private final float timeStep; 			// This defines how much simulation should advance in one second
    private final int velocityIterations;   // This define how accurately velocity will be simulated
    private final int positionIterations;   // This define how accurately position will be simulated
    /**Constant that represent the scale between the world simulation and the world draw in the screen.
     */
    public final static float SCALE=50;
    
    //Categories for collisions
    public final static int CATEGORY_DECOR=1;
    public final static int CATEGORY_PLAYER=2;
    public final static int CATEGORY_ENNEMY=4;
    public final static int CATEGORY_BONUS=8;
    public final static int CATEGORY_BULLET_PLAYER=16;
    public final static int CATEGORY_BULLET_ENNEMY=32;
    
    private final Object lock = new Object();

	private EscapeWorld(){
		 Vec2  gravity = new Vec2(0,0f); // No gravity in space
		 this.world = new World(gravity);
		 this.timeStep = 1.0f/60.0f;
		 this.velocityIterations = 6;
		 this.positionIterations = 3;
	}
	
	/** Get the unique instance of {@link EscapeWorld}
	 * @return The unique instance of {@link EscapeWorld}
	 */
	public static EscapeWorld getTheWorld(){
		if(EscapeWorld.TheWorld==null){
			EscapeWorld.TheWorld = new EscapeWorld();
		}
		return EscapeWorld.TheWorld;
	}
	
	/**Process the next step simulation of the world.
	 */
	public void step(){
		synchronized(lock){
			world.step(timeStep, velocityIterations, positionIterations);
		}
	}
	
	/**Create a body in the world.
	 * @param def The body definition of the body to create.
	 * @return The body created.
	 */
	public Body createBody(BodyDef def){
		Objects.requireNonNull(def);
		
		synchronized(lock){
			return this.world.createBody(def);
		}
	}
	
	/**Set a contact listener which will be notify for some event like collisions.
	 * @param listener The listener to set.
	 */
	public void setContactListener(ContactListener listener){
		world.setContactListener(listener);
	}
	
	/**
	 * Destroy a body in the world
	 * @param body Body to destroy.
	 */
	public void destroyBody(Body body){
		Objects.requireNonNull(body);
		
		synchronized(lock){
			world.destroyBody(body);
		}
	}

	/**
	 * Create a fixture for a given body
	 * @param body Body to which create the fixture.
	 * @param fd fixture definition for the new fixture
	 */
	public void createFixture(Body body, FixtureDef fd) {
		Objects.requireNonNull(body);
		Objects.requireNonNull(fd);
		
		synchronized(lock){
			body.createFixture(fd);
		}
	}
	
	/**
	 * Set a body to active or inactive. An inactive body does not act in the simulation of the world.
	 * @param body to set the state.
	 * @param state The new state of the body.
	 */
	public void setActive(Body body, boolean state){
		Objects.requireNonNull(body);
		
		synchronized(lock){
			body.setActive(state);
		}		
	}
}
