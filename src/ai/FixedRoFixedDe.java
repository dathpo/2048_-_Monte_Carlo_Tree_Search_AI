package ai;

import model.AbstractState.MOVE;
import model.State;

public class FixedRoFixedDe extends AbstractPlayer {

	private int numSamples = 500;
	private int totalScore = 0;
	private double totalTime = 0;
	private double finalTime = 0;
	private int depth = 0;

	@Override
	public MOVE getMove(State game) {
		int bestScore = 0;
		MOVE bestMove = null;
		pause();
		
		for(MOVE move : game.getMoves()) {
			game.move(move);
//			if(finalTime < 30000)
//				depth = 4;
//			else
//				depth = 3;
//			
			
				totalScore += montyRecur(game,depth);
			
			if(bestScore < totalScore) {
				bestScore = totalScore;
				System.out.println("CHANGED MOVE");
				bestMove = move;
			}
			
			totalScore = 0;
			game.undo();
		}
		System.out.println("MOVED");
		totalTime = 0;
		return bestMove;
	}
	
	private int deepMove(State game,int depth){
		int bestScore = 0;
		
		pause();

		for(MOVE move : game.getMoves()) {
			game.move(move);
			
				totalScore += montyRecur(game,depth);
			
			if(bestScore < totalScore){
				bestScore = totalScore;
			}
			
			totalScore = 0;
			game.undo();
		}
		
		return bestScore;
	}

	private int montyRecur(State game,int depth){
		int limit = 0;
		System.out.println(depth);
		if(limit < depth){
			return deepMove(game,--depth);
		}else
			return monty(game);
	}
	
	private int monty(State game){
		int bestScore = 0;
		
		for(MOVE move : game.getMoves()) {
			game.move(move);
			for(int i = 0; i < numSamples; i++){
				double start = System.nanoTime();
				totalScore += game.rollout();
				totalTime += (System.nanoTime() - start);
			}
			
			if(bestScore < totalScore/numSamples){
				bestScore = totalScore/numSamples;
			}
			finalTime = totalTime/numSamples;
			System.out.println("TOTAL" + finalTime);
			totalScore = 0;
			totalTime = 0;
			game.undo();
		}
		return bestScore;
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
