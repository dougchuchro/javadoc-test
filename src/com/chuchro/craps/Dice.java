package com.chuchro.craps;

import com.chuchro.craps.Die;

/**
 * This class represents the pair of dice that are used in a craps game.
 * All member fields are kept private to prevent tampering of the dice.
 * @author Doug Chuchro (doug@chuchro.net)
 */
public class Dice {
	/** First of the two dice in the pair.	*/
	private Die dice1;
	/** Second of the two dice in the pair.	*/
	private Die dice2;
	/** Sum of the current dice roll.	*/
	private int rollSum;

	/**	
	 * Simple constructor which initializes the member fields.
	 */
	public Dice()	{
		dice1 = new Die();
		dice2 = new Die();
		rollSum = 0;
	}

	/**
	 * Getter method for the roll sum.
	 * @return this.rollSum
	 */
	public int getRollSum() {return rollSum;}

	/**
	 * Roll the dice.
	 * @return Sum outcome of the roll.
	 */
	public int rollDice()	{
		System.out.println("Ready to roll ... press enter to roll the dice");
//		Session.getUserInput();
		rollSum = dice1.roll() + dice2.roll();
		System.out.println("DICE ROLLED: " + dice1.getSideString() + " / " + dice2.getSideString() + " (" + rollSum + ")");
		return rollSum;
	}
	
}