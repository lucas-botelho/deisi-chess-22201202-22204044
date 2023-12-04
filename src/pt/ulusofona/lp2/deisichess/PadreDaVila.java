package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PadreDaVila extends Piece {
    static final String BLACK_PNG = "BishopGrey.png";
    static final String WHITE_PNG = "BishopBeije.png";
    static final int DEFAULT_OFFSET = 1000;

    public PadreDaVila(int uniqueId, int type, int team, String nickName) {
        super(uniqueId, type, team, nickName, PadreDaVila.DEFAULT_OFFSET);

        switch (team) {
            case Piece.BLACK_TEAM -> this.png = PadreDaVila.BLACK_PNG;
            case Piece.WHITE_TEAM -> this.png = PadreDaVila.WHITE_PNG;
        }

        addBehaviour(new BehaviourDiagonal());
    }

    @Override
    public boolean isValidMove(ArrayList<Piece> boardPieces, int x, int y) {
        var behaviourData = new BehaviourData(getX(), getY(), x, y);
        Behaviour behaviour;

        try {
            behaviour = Behaviour.getValidMovementBehaviour(behaviourData, getBehaviours(), movementRange);
        } catch (InvalidBehaviourException e) {
            return false;
        }

        behaviour.calculateDirection(behaviourData);


        return !behaviour.hasCollision(behaviourData, boardPieces);
    }
}
