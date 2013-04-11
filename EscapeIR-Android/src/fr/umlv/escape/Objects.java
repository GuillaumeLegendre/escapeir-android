package fr.umlv.escape;

public class Objects {
	public static void requireNonNull(Object o) {
		if(o == null){
			throw new NullPointerException();
		}
	}
}
