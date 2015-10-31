/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import junit.framework.TestCase;

/**
 *
 * @author poebr_000
 */
public class CheckersBoardTest extends TestCase {
    
    public CheckersBoardTest(String testName) {
        super(testName);
    }

    /**
     * Test of newGame method, of class CheckersBoard.
     */
    public void testNewGame() {
        System.out.println("newGame");
        CheckersBoard instance = new CheckersBoard();
        instance.newGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoard method, of class CheckersBoard.
     */
    public void testGetBoard() {
        System.out.println("getBoard");
        CheckersBoard instance = new CheckersBoard();
        int[][] expResult = null;
        int[][] result = instance.getBoard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
