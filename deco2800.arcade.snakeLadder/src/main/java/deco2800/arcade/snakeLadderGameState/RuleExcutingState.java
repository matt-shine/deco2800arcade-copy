package deco2800.arcade.snakeLadderGameState;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.*;

import com.badlogic.gdx.Gdx;

public class RuleExcutingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		int playerIndex=context.getturns();
		String rule = context.getMap().getTileList()[context.gamePlayers[playerIndex].newposition()].getRule();
		
		//if no rules specified in this position, transit to waiting state
		if(rule.equals(".")){
			context.gameState = new WaitingState();
			context.statusMessage = "Throw the dice again";
			context.taketurns();
		}
		//else execute the rule
		else
		{
			//Snake and Ladder rules are two special rules hard-coded into game
			if(rule.startsWith("S")||rule.startsWith("L"))
			{
				Rule r = new LadderSnakeRule();
				r.excuteRules(playerIndex, rule, context);
			}
			//search the implementation class for the plugin rules
			else if(!rule.equals("."))
			{
				try {
					Rule r = (Rule) Class.forName("deco2800.arcade.snakeLadderRulePlugin."+context.getRuleMapping().get(rule).getImplementationClass()).newInstance();
					r.excuteRules(playerIndex, rule, context);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
