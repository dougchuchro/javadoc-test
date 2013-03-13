package com.chuchro.craps;

import java.util.Random;

/**
 * Represents a single die to be used in a craps game.
 * All member fields are kept private to prevent tampering of the die.
 * @author Doug Chuchro (doug@chuchro.net)
 */
public class Die {
	/**	The side of the dice that faces up when rolled.	*/
	private int side;
	/**	English word for the number of the side.	*/
	private	DieNumber sideString;
	/**	Object used to produce a random number for the die roll.	*/
	private Random random;
	/**	The side of the dice that faces up when rolled	*/
	private static int NUM_SIDES_OF_DIE = 6;

	/**
	 * Getter method for the string representation of the die side.
	 * @return this.sideString
	 */
	public DieNumber getSideString()	{return sideString;}
	
	/**	
	 * Simple constructor which initializes the member fields.
	 */
	public Die()	{
		side = 0;
		sideString = null;
		random = new Random();
	}

	/**	
	 * Rolls the die, results in a random value from one to six (inclusive).
	 * @return	The result of the rolled die.
	 */
	public int roll()	{
		side = random.nextInt(NUM_SIDES_OF_DIE) + 1;
		sideString = DieNumber.values()[side-1];
		return side;
	}

	/**
	 * Valid values of the six sides of a die, as strings.
	 */
	public enum DieNumber {
		ONE, TWO, THREE, FOUR, FIVE, SIX,
	}
}