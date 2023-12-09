package pt.ulusofona.lp2.deisichess;

import pt.ulusofona.lp2.deisichess.pieces.*;

public class PieceFactory {

    public static Piece createPiece(String pieceLine) throws InvalidGameInputException {
        var lineElements = pieceLine.split(":");
        var isPieceFileLine = lineElements.length == GameManager.NUM_OF_PIECE_PARAMETERS_ON_FILE;

        if (!isPieceFileLine) {
            throw new InvalidGameInputException();
        }

        var uId = Integer.parseInt(lineElements[0]);
        var type = Integer.parseInt(lineElements[1]);
        var team = Integer.parseInt(lineElements[2]);

        var isInvalidTeam = team != Piece.BLACK_TEAM && team !=  Piece.WHITE_TEAM;
        if (isInvalidTeam){
            throw new InvalidGameInputException();
        }

        var nickName = lineElements[3];

        return switch (type) {
            case 0 -> new Rei(uId, type, team, nickName);
            case 1 -> new Rainha(uId, type, team, nickName);
            case 2 -> new PoneiMagico(uId, type, team, nickName);
            case 3 -> new PadreDaVila(uId, type, team, nickName);
            case 4 -> new TorreHorizontal(uId, type, team, nickName);
            case 5 -> new TorreVertical(uId, type, team, nickName);
//            case 6 -> new HomerSimpson(uId, type, team, nickName);
//            case 7 -> new Joker(uId, type, team, nickName);
//            default -> throw new InvalidGameInputException();
            default -> new Rainha(uId, type, team, nickName);
        };

    }
}
