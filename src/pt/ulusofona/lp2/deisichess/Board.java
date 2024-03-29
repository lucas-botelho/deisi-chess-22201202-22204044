package pt.ulusofona.lp2.deisichess;

import pt.ulusofona.lp2.deisichess.pieces.Piece;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board implements Cloneable {
    private int boardSize;
    private int amountOfPieces;
    private ArrayList<Piece> boardPieces;
    private int currentTeamId;

    public Board() {
        defineStartingTeam(Piece.BLACK_TEAM);
        boardPieces = new ArrayList<>();
    }

    public Board(int size) {
        defineStartingTeam(Piece.BLACK_TEAM);
        boardPieces = new ArrayList<>();
        boardSize = size;
    }

    private void defineStartingTeam(int startingTeam) {
        currentTeamId = startingTeam;
    }

    public void setCurrentTeamId(int currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    public int getCurrentTeamId() {
        return currentTeamId;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getAmountOfPieces() {
        return amountOfPieces;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setAmountOfPieces(int amountOfPieces) {
        this.amountOfPieces = amountOfPieces;
    }

    public void addPiece(Piece piece) {
        boardPieces.add(piece);
    }

    public ArrayList<Piece> pieces() {
        return boardPieces;
    }

    public Piece getPieceById(int Id) {
        for (Piece piece : this.pieces()) {
            if (piece.getUniqueId() == Id) {
                return piece;
            }
        }

        return null;
    }

    public void createPiecesFromFile(BufferedReader reader, int numPieces) throws InvalidGameInputException, IOException {
        String line;
        int countLine = 1;

        boardPieces = new ArrayList<>();

        try {
            for (; countLine <= numPieces; countLine++) {
                line = reader.readLine();
                addPiece(PieceFactory.createPieceFromFileLine(line));
            }
        } catch (InvalidGameInputException e) {
            throw new InvalidGameInputException(countLine + 2, e.getProblem(), e.getAmountOfData(), e.getExpected());
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public void setPiecesStatusFromFile(BufferedReader reader) throws IOException {
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                getPieceById(Integer.parseInt(line)).getCaptured();
            }
        } catch (Exception e) {
            throw new IOException();
        }

    }

    public void buildBoardFromFile(BufferedReader reader) throws InvalidGameInputException, IOException {
        String line;
        int y = 0;
        int countLine = 1;
        try {
            for (int i = 0; i < boardSize; i++) {
                line = reader.readLine();
                var lineElements = line.split(":");
                var isBoardFileLine = lineElements.length == boardSize;
                if (!isBoardFileLine) {

                    var problem = lineElements.length > boardSize
                            ? InvalidGameInputException.DADOS_A_MAIS
                            : InvalidGameInputException.DADOS_A_MENOS;

                    throw new InvalidGameInputException(
                            amountOfPieces + 2 + countLine, problem,
                            lineElements.length,
                            boardSize);
                }

                this.processBoardFileLine(lineElements, y);
                y++;
            }
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public boolean squareHasPiece(int x, int y) {
        for (int i = 0; i < pieces().size(); i++) {
            Piece piece = getPieceById(i);
            if (piece.getX() == x && piece.getY() == y) {
                return true;
            }
        }

        return false;
    }

    public boolean hasAdjacentPieceFromSameTeam(int x, int y){

        var hasPieceAbove = squareHasPiece(x, y-1);
        var hasPieceAboveLeft = squareHasPiece(x-1, y-1);
        var hasPieceAboveRight = squareHasPiece(x+1, y-1);
        var hasPieceBelow = squareHasPiece(x, y+1);
        var hasPieceBelowLeft = squareHasPiece(x-1, y+1);
        var hasPieceBelowRight = squareHasPiece(x+1, y+1);
        var hasPieceLeft = squareHasPiece(x-1, y);
        var hasPieceRight = squareHasPiece(x+1, y);

        return hasPieceAbove || hasPieceAboveLeft || hasPieceAboveRight || hasPieceBelow || hasPieceBelowLeft
                || hasPieceBelowRight || hasPieceLeft || hasPieceRight;
    }

    public static Piece getPieceAt(int x, int y, ArrayList<Piece> boardPieces) {

        for (Piece piece : boardPieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }

        return null;
    }

    public String[] squareInfoToArray(Piece piece) {
        String[] properties = new String[5];
        properties[0] = String.valueOf(piece.getUniqueId());
        properties[1] = String.valueOf(piece.getType());
        properties[2] = String.valueOf(piece.getTeam());
        properties[3] = piece.getNickName();
        properties[4] = piece.getPng();

        return properties;
    }

    public static boolean isValidCoordinate(int x, int y, int boardSize) {
        var isValidX = x >= 0 && x < boardSize;
        var isValidY = y >= 0 && y < boardSize;
        return isValidX && isValidY;
    }

    public void switchPlayingTeam() {
        setCurrentTeamId(getCurrentTeamId() == Piece.BLACK_TEAM ? Piece.WHITE_TEAM : Piece.BLACK_TEAM);

    }

    public int countPiecesIngame(int teamId) {
        var count = 0;
        for (Piece piece : this.pieces()) {
            var pieceIsInGame = piece.getStatus().equals(Piece.PIECE_IN_GAME);
            var belongsToTeam = piece.getTeam() == teamId;
            if (pieceIsInGame && belongsToTeam) {
                count++;
            }
        }

        return count;
    }

    public void processBoardFileLine(String[] lineElements, int y) {
        var x = 0;

        for (String lineElement : lineElements) {

            var lineHasPieceId = Integer.parseInt(lineElement) != 0;
            if (lineHasPieceId) {
                Piece piece = getPieceById(Integer.parseInt(lineElement));
                if (piece != null) {
                    piece.initiateIngame(x, y);
                }
            }

            x++;
        }
    }

    public boolean isKingInGame(int teamId) {
        for (Piece boardPiece : boardPieces) {
            var isKing = boardPiece.getType() == 0 && !boardPiece.isJoker();
            if (isKing && boardPiece.getTeam() == teamId) {
                return boardPiece.getStatus().equals(Piece.PIECE_IN_GAME);
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        try {
            Board clonedBoard = (Board) super.clone();
            clonedBoard.boardPieces = new ArrayList<>(this.boardPieces.size());
            for (Piece piece : this.boardPieces) {
                clonedBoard.boardPieces.add((Piece) piece.clone());
            }
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Board otherBoard = (Board) obj;
        return boardSize == otherBoard.boardSize &&
                amountOfPieces == otherBoard.amountOfPieces &&
                currentTeamId == otherBoard.currentTeamId &&
                Objects.equals(boardPieces, otherBoard.boardPieces);
    }
}
