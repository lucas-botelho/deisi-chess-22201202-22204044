package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TestGameManager {
    @Test
    public void test_White_Victory(){
        var gameManager = new GameManager();

        var gameLoaded = gameManager.loadGame(new File("test-files/4x4.txt"));

        int[][] moves = {
                {1, 0, 1, 1},
                {1, 2, 1, 1},
                {3, 0, 2, 0},
                {1, 1, 2, 1},
                {2, 0, 1, 1},
                {2, 1, 1, 1}
        };

        for (int[] move : moves) {
            int x0 = move[0];
            int y0 = move[1];
            int x1 = move[2];
            int y1 = move[3];

            gameManager.move(x0, y0, x1, y1);
            if (gameManager.gameOver()){
                break;
            }
        }

        var gameResult = gameManager.getGameResults();

        String expectedText = "JOGO DE CRAZY CHESS,Resultado: VENCERAM AS BRANCAS,---,Equipa das Pretas,0,3,0,Equipa das Brancas,3,3,0";

        ArrayList<String> listOfExpectedTexts = new ArrayList<>(Arrays.asList(expectedText.split(",")));

        for (int i = 0; i < listOfExpectedTexts.size(); i++) {
            Assertions.assertEquals(listOfExpectedTexts.get(i), gameResult.get(i) , "test_White_Victory didn't match at index:" + i);
        }
    }

    @Test
    public void test_White_Victory_1Move(){
        var gameManager = new GameManager();

        var gameLoaded = gameManager.loadGame(new File("test-files/4x4_1move_victory_white.txt"));

        int[][] moves = {
                {0, 0, 1, 1},
                {0, 1, 1, 1}
        };

        for (int[] move : moves) {
            int x0 = move[0];
            int y0 = move[1];
            int x1 = move[2];
            int y1 = move[3];

            gameManager.move(x0, y0, x1, y1);
            if (gameManager.gameOver()){
                break;
            }
        }


        var gameResult = gameManager.getGameResults();

        String expectedText = "JOGO DE CRAZY CHESS,Resultado: VENCERAM AS BRANCAS,---,Equipa das Pretas,0,1,0,Equipa das Brancas,1,1,0";

        ArrayList<String> listOfExpectedTexts = new ArrayList<>(Arrays.asList(expectedText.split(",")));

        for (int i = 0; i < listOfExpectedTexts.size(); i++) {
            Assertions.assertEquals(listOfExpectedTexts.get(i), gameResult.get(i) , "test_White_Victory_1Move didn't match at index:" + i);
        }



    }

    @Test
    public void test_Draw_10_Sequencial_moves_without_capturing(){
        var gameManager = new GameManager();

        var gameLoaded = gameManager.loadGame(new File("test-files/4x4.txt"));

        int[][] moves = {
                {1, 0, 1, 1},//preta
                {1, 2, 1, 1},//branca comeu (captura das brancas)
                {3, 0, 3, 1},//preta
                {1, 1, 0, 1},//branca
                {3, 1, 3, 0},//preta
                {0, 1, 1, 1},//branca
                {3, 0, 3, 1},//preta
                {1, 1, 0, 1},//branca
                {3, 1, 3, 0},//preta
                {0, 1, 1, 1},//branca
                {3, 0, 3, 1},//preta
                {1, 1, 0, 1} //branca
        };

        for (int[] move : moves) {
            int x0 = move[0];
            int y0 = move[1];
            int x1 = move[2];
            int y1 = move[3];

            gameManager.move(x0, y0, x1, y1);
            if (gameManager.gameOver()){
                break;
            }
        }

        var gameResult = gameManager.getGameResults();

        String expectedText = "JOGO DE CRAZY CHESS,Resultado: EMPATE,---,Equipa das Pretas,0,6,0,Equipa das Brancas,1,6,0";

        ArrayList<String> listOfExpectedTexts = new ArrayList<>(Arrays.asList(expectedText.split(",")));

        for (int i = 0; i < listOfExpectedTexts.size(); i++) {
            Assertions.assertEquals(listOfExpectedTexts.get(i), gameResult.get(i) , "test_Draw_10_Sequencial_moves_without_capturing didn't match at index:" + i);
        }


    }
}
