package ai;

import model.AbstractState.MOVE;
import model.State;

public class FixedRo extends AbstractPlayer {

	private int numRollouts = 2000;
	private double totalScore = 0;
	private long startTime = 0;
	private int moveTime = 0;

	@Override
	public MOVE getMove(State game) {
		startTime = System.currentTimeMillis();
		System.out.println("Current Score: " + game.getScore());
		double bestScore = 0;
		MOVE bestMove = null;
		pause();

		for (MOVE move : game.getMoves()) {
			game.move(move);
			for (int i = 0; i < numRollouts; i++) {
				totalScore += game.rollout();
			}
			System.out.println(move + ": Average: " + totalScore / numRollouts + ", Score: " + totalScore
					+ ", Rollouts: " + numRollouts);
			if (bestScore < (totalScore / numRollouts)) {
				bestScore = (totalScore / numRollouts);
				bestMove = move;
			}
			totalScore = 0;
			game.undo();
		}
		moveTime = (int) (System.currentTimeMillis() - startTime);
		System.out.println("Best Average: " + bestScore + ", Move: " + bestMove + ", Time: " + moveTime);
		return bestMove;
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
