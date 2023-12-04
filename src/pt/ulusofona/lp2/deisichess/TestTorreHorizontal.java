package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestTorreHorizontal {
    @Test
    public void test_TorreHorizontal_valid_horizontal() {

        var x = 3;
        var y = 4;

        int[][] moves = {
                {x, y, x + 1, y},  // Horizontal move to the right (one step)
                {x, y, x + 3, y},  // Horizontal move to the right (three step)
                {x, y, x - 1, y},  // Horizontal move to the left (one step)
                {x, y, x - 3, y},  // Horizontal move to the left (three step)

        };


        for (int[] move : moves) {
            var gameManager = new GameManager();

            try {
                //ensures that piece is always starting on the same place
                gameManager.loadGame(new File("test-files/test-torre-horizontal/8x8.txt"));
            } catch (Exception e) {
            }

            int startingX = move[0];
            int startingY = move[1];
            int endingX = move[2];
            int endingY = move[3];


            Assertions.assertTrue(gameManager.move(startingX, startingY, endingX, endingY),
                    new StringBuilder("test_TorreHorizontal_valid_horizontal failed moving from: (")
                            .append(startingX).append(":")
                            .append(startingY).append(")")
                            .append(" to: (")
                            .append(endingX).append(":")
                            .append(endingY).append(")")
                            .toString());
        }
    }

    @Test
    public void test_TorreHorizontal_invalid_diagonals(){

        var x = 3;
        var y = 4;

        //bad diagonals
        int[][] moves = {
                {x, y, x + 1, y + 1},  // Diagonal move (invalid)
                {x, y, x - 1, y + 1},  // Diagonal move (invalid)
                {x, y, x + 1, y - 1},  // Diagonal move (invalid)
                {x, y, x - 1, y - 1},  // Diagonal move (invalid)
        };


        for (int[] move : moves) {
            var gameManager = new GameManager();

            try {
                gameManager.loadGame(new File("test-files/test-torre-horizontal/8x8.txt"));
            } catch (Exception e) {
            }

            int startingX = move[0];
            int startingY = move[1];
            int endingX = move[2];
            int endingY = move[3];


            Assertions.assertFalse(gameManager.move(startingX, startingY, endingX, endingY),
                    new StringBuilder("test_TorreHorizontal_invalid_diagonals moved successfuly: (")
                            .append(startingX).append(":")
                            .append(startingY).append(")")
                            .append(" to: (")
                            .append(endingX).append(":")
                            .append(endingY).append(")")
                            .toString());
        }
    }

    @Test
    public void test_TorreHorizontal_invalid_verticals(){

        var x = 3;
        var y = 4;

        //vertical moves
        int[][] moves = {
                {x, y, x, y + 2},
                {x, y, x, y - 1},
        };


        for (int[] move : moves) {
            var gameManager = new GameManager();

            try {
                gameManager.loadGame(new File("test-files/test-torre-horizontal/8x8.txt"));
            } catch (Exception e) {
            }

            int startingX = move[0];
            int startingY = move[1];
            int endingX = move[2];
            int endingY = move[3];


            Assertions.assertFalse(gameManager.move(startingX, startingY, endingX, endingY),
                    new StringBuilder("test_TorreHorizontal_invalid_verticals moved successfuly: (")
                            .append(startingX).append(":")
                            .append(startingY).append(")")
                            .append(" to: (")
                            .append(endingX).append(":")
                            .append(endingY).append(")")
                            .toString());
        }
    }
    @Test
    public void test_TorreHorizontal_collisions(){

        var x = 3;
        var y = 4;

        int[][] moves = {
                {x, y, x + 2, y},
                {x, y, x - 2, y},
        };


        for (int[] move : moves) {
            var gameManager = new GameManager();

            try {
                gameManager.loadGame(new File("test-files/test-torre-horizontal/8x8-colisao.txt"));
            } catch (Exception e) {
            }

            int startingX = move[0];
            int startingY = move[1];
            int endingX = move[2];
            int endingY = move[3];


            Assertions.assertFalse(gameManager.move(startingX, startingY, endingX, endingY),
                    new StringBuilder("test_TorreHorizontal_collisions moved successfuly: (")
                            .append(startingX).append(":")
                            .append(startingY).append(")")
                            .append(" to: (")
                            .append(endingX).append(":")
                            .append(endingY).append(")")
                            .toString());
        }
    }



}
