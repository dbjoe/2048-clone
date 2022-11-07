package project3;

public class Tile {
	private int value;
	
//	Every function that takes a value must check if that value is a valid power of 2
//	and throw a new IllegalArgumentException() if it is not.
	
//	Make a default constructor that sets the value to 2.
	Tile() {
		value = 2;
	}
	
//	Write a constructor that takes a value to set the tile's value to.
	Tile(int val) {
		if (!power2(val)) {
			throw new IllegalArgumentException();
		}
		value = val;
	}
	

	/******************************************************************
	 * Sets the value of the tile
	 * @param val the value the tile is being set to
	 * @throws IllegalArgumentException if the parameter is not a 
	 * 				power of 2
	 *****************************************************************/
	public void setValue(int val) {
		if (!power2(val)) {
			throw new IllegalArgumentException();
		}
		
		value = val;
	}
	
	/******************************************************************
	 * Gets the value of the tile
	 * @return value the value of the tile
	 *****************************************************************/
	public int getValue() {
		return value;
	}
	
//	Code a method called power2(double value) (Yes, double!).  
// 	This function is recursive.  A call to this function must recursively divide the value by 2 
//	to determine if it is a valid power of 2.  If you are unable to write this function recursively, 
//	you may do so iteratively but will lose 5 points on the assignment.
	public boolean power2(double value) {
		if (value == 2) {
			return true;
		}
		else if (value < 2) {
			return false;
		}
		else {
			power2(value / 2);
			return false;
		}	
	}
	
//	Make a toString() method that simply returns the value as a String.
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
