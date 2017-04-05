package ai;

import model.AbstractState.MOVE;
import model.State;

public class DavidPocock2048 extends AbstractPlayer {

	private int leftRollouts, upRollouts, rightRollouts, downRollouts = 0;
	private double leftScore, upScore, rightScore, downScore = 0;
	private double bestAverage, leftAverage, upAverage, rightAverage, downAverage = 0;
	private long startTime = 0;
	private MOVE bestMove = null;

	@Override
	public MOVE getMove(State game) {
		startTime = System.currentTimeMillis();
		pause();
		while (System.currentTimeMillis() < startTime + 984) {
			for (MOVE move : game.getMoves()) {
				game.move(move);
				if (move.toString().equals("LEFT")) {
					leftScore += game.rollout();
					leftRollouts++;
				} else if (move.toString().equals("UP")) {
					upScore += game.rollout();
					upRollouts++;
				} else if (move.toString().equals("RIGHT")) {
					rightScore += game.rollout();
					rightRollouts++;
				} else if (move.toString().equals("DOWN")) {
					downScore += game.rollout();
					downRollouts++;
				}
				game.undo();
			}
		}
		if (leftRollouts != 0) {
		leftAverage = leftScore/leftRollouts;
		setBest(MOVE.LEFT, leftAverage);
		}
		if (upRollouts != 0) {
		upAverage = upScore/upRollouts;
		setBest(MOVE.UP, upAverage);
		}
		if (rightRollouts != 0) {
		rightAverage = rightScore/rightRollouts;
		setBest(MOVE.RIGHT, rightAverage);
		}
		if (downRollouts != 0) {
		downAverage = downScore/downRollouts;
		setBest(MOVE.DOWN, downAverage);
		}
				
		bestAverage = 0; leftScore = 0; upScore = 0; rightScore = 0; downScore = 0;
		leftRollouts = 0; upRollouts = 0; rightRollouts = 0; downRollouts = 0;
		
		return bestMove;
	}

	public void setBest(MOVE move, double average) {
		if (bestAverage < average) {
			bestAverage = average;
			bestMove = move;
		}
	}

	@Override
	public int studentID() {
		return 201303098;
	}

	@Override
	public String studentName() {
		return "David T. Pocock";
	}
}
