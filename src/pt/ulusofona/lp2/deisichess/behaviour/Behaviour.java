package pt.ulusofona.lp2.deisichess.behaviour;

import pt.ulusofona.lp2.deisichess.InvalidBehaviourException;
import pt.ulusofona.lp2.deisichess.pieces.Piece;

import java.util.ArrayList;

public abstract class Behaviour implements Cloneable {

    protected Direction direction;

    protected int virtualX;
    protected int virtualY;

    public abstract boolean hasCollision(BehaviourData behaviorData, ArrayList<Piece> boardPieces);

    public abstract boolean isValid(BehaviourData behaviorData);

    public abstract void calculateDirection(BehaviourData behaviorData);

    public abstract ArrayList<ArrayList<Integer>> forseeMovements(BehaviourData behaviorData, ArrayList<Piece> boardPieces, int range, int boardSize);

    protected boolean isDifferentThanStartingPosition(BehaviourData behaviorData) {
        return behaviorData.xStart != behaviorData.xEnd || behaviorData.yStart != behaviorData.yEnd;
    }

    public boolean isInMovementRange(BehaviourData behaviorData, int movementRange) {
        var isXMovementShorterThanMaximumRange = Math.abs(behaviorData.xEnd - behaviorData.xStart) <= movementRange;
        var isYMovementShorterThanMaximumRange = Math.abs(behaviorData.yEnd - behaviorData.yStart) <= movementRange;
        return isXMovementShorterThanMaximumRange && isYMovementShorterThanMaximumRange;
    }

    public static Behaviour getValidMovementBehaviour(BehaviourData behaviourData, ArrayList<Behaviour> behaviours, int movementRange) throws InvalidBehaviourException {
        for (Behaviour behaviour : behaviours) {
            var isValidBehaviour = behaviour.isValid(behaviourData);
            var isValidRangeOfMovement = behaviour.isInMovementRange(behaviourData, movementRange);
            if (isValidBehaviour && isValidRangeOfMovement) {
                return behaviour;
            }
        }

        throw new InvalidBehaviourException();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    protected void moveUp() {
        virtualY -= 1;
    }

    protected void moveDown() {
        virtualY += 1;
    }

    protected void moveLeft() {
        virtualX -= 1;
    }

    protected void moveRight() {
        virtualX += 1;
    }

    protected void moveUpLeft() {
        moveUp();
        moveLeft();
    }

    protected void moveUpRight() {
        moveUp();
        moveRight();
    }

    protected void moveDownLeft() {
        moveLeft();
        moveDown();
    }

    protected void moveDownRight() {
        moveRight();
        moveDown();
    }

    @Override
    public Object clone() {
        try {
            Behaviour clonedBehaviour = (Behaviour) super.clone();
            clonedBehaviour.direction = this.direction;
            return clonedBehaviour;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    protected void setInitialVirtualPosition(BehaviourData behaviorData) {
        virtualX = behaviorData.xStart;
        virtualY = behaviorData.yStart;
    }

    protected boolean isOneSlotBehindDestination(int currentX, int currentY, int endX, int endY) {
        return Math.abs(currentX - endX) <= 1 && Math.abs(currentY - endY) <= 1;
    }
}
