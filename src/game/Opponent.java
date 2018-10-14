/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.GameItems.Card;
import game.GameItems.GamePiece;
import java.util.ArrayList;

/**
 *
 * @author kiran
 */
public class Opponent extends Player {

    Opponent(int num, ArrayList<Card> h, GamePiece p) {

        //Pass to Player.
        super(num, h, p);
    }
}
