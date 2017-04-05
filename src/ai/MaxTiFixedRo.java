package ai;

import model.AbstractState.MOVE;
import model.State;

public class MaxTiFixedRo extends AbstractPlayer {

	private int numRollouts = 0;
	private double totalScore = 0;
	private long startTime = 0;
	private int moveTime = 0;

	@Override
	public MOVE getMove(State game) {
		System.out.println("Current Score: " + game.getScore());
		startTime = System.currentTimeMillis();
		double bestScore = 0;
		MOVE bestMove = null;
		pause();
		while (System.currentTimeMillis() < startTime + 997) {
			for (MOVE move : game.getMoves()) {
				game.move(move);
				for (int i = 0; i < 3000; i++) {
					totalScore += game.rollout();
					numRollouts++;
				}
				System.out.println(move + ": Average: " + totalScore / numRollouts + ", Total: " + totalScore
						+ ", Rollouts: " + numRollouts);
				if (bestScore < (totalScore / numRollouts)) {
					bestScore = (totalScore / numRollouts);
					bestMove = move;
				}

				totalScore = 0;
				numRollouts = 0;
				game.undo();
			}
			moveTime = (int) (System.currentTimeMillis() - startTime);
			System.out.println("Best Average: " + bestScore + ", Move: " + bestMove + ", Time: " + moveTime);
		}
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
