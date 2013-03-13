package com.chuchro.craps;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class is the super class of all Bet objects; each will extend Bet, or another bet object that extends Bet (ad infinitum)
 * @author Doug Chuchro (doug@chuchro.net)
 */
abstract class Bet {
	/**	Name of the bet, ex: "Pass Line bet", "Come line bet", "Field bet"	*/
	public String betName;
	/**	Current status of this bet, based on BetStatus enumeration defined below	*/
	public BetStatus betStatus;
	/** Amount of the bet	*/
	public int amount;
	/** Expressed as x/y, how much the house pays ($x) for every $y bet, ex: 6/5 pays $6 on a $5 bet	*/
	public Double payoutRatio;
	/**	All the rolls that win this bet	*/
	public List<Integer> winners;
	/**	All the rolls that lose this bet	*/
	public List<Integer> losers;
	/**	Minimum amount of bet	*/
	public int minAmount;
	/**	Maximum amount of bet	*/
	public int maxAmount;
	/**	Some bets can only be a multiple of a certain number for the odds payout to not be fractional therefore
	 *  the house stipulates that the bet must be a multiple of a certain amount (1=no multiple requirement) */
	public int amountMultiple;
	
	Bet(String betName, int minAmt, int maxAmt, int amountMultiple)	{
		this.betName		= betName;
		this.minAmount		= minAmt;
		this.maxAmount		= maxAmt;
		this.amountMultiple	= amountMultiple;
		this.amount			= promptBetAmount();
		this.betStatus		= Bet.BetStatus.BET_ON;
		this.winners		= new ArrayList<Integer>();
		this.losers			= new ArrayList<Integer>();
		System.out.println(betName + " for the amount of $" + this.amount + 
						" is placed, bet amount deducted from your chip count");
	}

	private int promptBetAmount()	{
		int betAmount = -1;
		System.out.println("Place your " + betName + " , which must be at least $" + minAmount);
		while (betAmount == -1)	{
			try {
//				betAmount = Integer.parseInt(Session.getUserInput());
			} catch (Exception e) {
				System.out.println("Please enter you bet amount as an integer");
				betAmount = -1;
				continue;
			}
			if (betAmount > maxAmount) {
				System.out.println("You bet cannot exceed the maximum allowable bet amount, which is currently $" + maxAmount);
				betAmount = -1;
				continue;				
			}
			if (betAmount < minAmount) {
				System.out.println("You bet must be at least the minimum amount, which is $" + minAmount);
				betAmount = -1;
				continue;				
			}
			if (!checkBetMultiple(betAmount))	{
				System.out.println(betName +" must be a multiple of " + amountMultiple);
				betAmount = -1;
				continue;				
			}
		}
		return betAmount;
	}

	/**
	 * Empty method to print the Odds bet attached to this bet. Called by Session.printBetSumamry() which iterates
	 * over all bet so it must be present in this super object. Subclass LineBet will override this method to 
	 * actually print out info about the Odds bet.
	 * @see LineBet
	 */
	public String getOddsBetString()	{
		return "";
	}

	private boolean checkBetMultiple(int amount)	{
		if (amount % this.amountMultiple == 0)	{return true;} else {return false;}	
	}
	
	public int checkBet(Dice dice)	{
		int result = 0;
		if (this.betStatus != Bet.BetStatus.BET_ON)	{	// if the bet is not active, then just return zero
			return result;
		}
		if (winners.contains(dice.getRollSum())) {		// check to see if this roll was a winner
			int payoutAmt =(int)Math.round(amount * payoutRatio);
			System.out.println("Your " + betName + " of $" + amount + " is a  winner!");
			System.out.println("The bet winnings of $" + payoutAmt + ", plus your original " +
								"bet amount of $" + amount + " is added to your chip count.\n");
			betStatus = BetStatus.BET_WON;
			result = payoutAmt + amount;
		}
		if (losers.contains(dice.getRollSum())) {		// check to see if this roll was a loser
			// because we deducted the bet amount from the chip count when the bet was placed
			// we don't need to deduct it again here, so if it's a loser match, just return 0
			System.out.println("Sorry, you lost your " + betName + " of $" + amount);
			betStatus = BetStatus.BET_LOST;
		}
		return result;
	}
	
	/**
	 * Enumerates the different states a bet can be in at any given time.
	 * @author Doug Chuchro (doug@chuchro.net)
	 */
	public enum BetStatus	{
		/** the bet is currently on and will be evaluated after the next roll	*/											BET_ON,			
		/** the bet is still on the table but will be inactive for the next roll, can neither be won nor lost	*/			BET_OFF,
		/** the previous roll has been evaluated as a winner for this bet, player will be credited	*/						BET_WON,
		/** the previous roll has been evaluated as a loser for this bet, house will take the player's bet $	*/			BET_LOST,
		/** the previous roll has been evaluated as a "push" for this bet, player gets bet amount back but no winnings	*/	BET_PUSHED,
		/** the bet has been either won or lost and the player has been credited or debited, bet is no longer relevant	*/	BET_SETTLED,	
		/** the player has chosen to take the bet off the table (only valid for certain bet types)	*/						BET_PULLED,		
	}

}