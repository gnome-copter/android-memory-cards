package com.example.utts.memorycards;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends Activity implements Animation.AnimationListener {
    private Animation   facedown;
    private Animation   faceup;
    private ImageView[] Cards;
    private Map<String, Integer> CardImages;
    private Map<String, Integer> Animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up the cards
        Cards      = new ImageView[7];
        Cards[1]   = (ImageView) findViewById(R.id.card1);
        Cards[2]   = (ImageView) findViewById(R.id.card2);
        Cards[3]   = (ImageView) findViewById(R.id.card3);
        Cards[4]   = (ImageView) findViewById(R.id.card4);
        Cards[5]   = (ImageView) findViewById(R.id.card5);
        Cards[6]   = (ImageView) findViewById(R.id.card6);
        CardImages = new HashMap();
        // set up the animals
        Animals = new HashMap();
        Animals.put("cat", R.mipmap.cat);
        Animals.put("dog", R.mipmap.dog);
        Animals.put("cow", R.mipmap.cow);
        // set the animation for turning a card facedown
        facedown = AnimationUtils.loadAnimation(this, R.anim.facedown);
        facedown.setAnimationListener(this);
        // set the animation for turning a card faceup
        faceup = AnimationUtils.loadAnimation(this, R.anim.faceup);
        faceup.setAnimationListener(this);
        // set up a new game
        newGame();
        // set up the onclick listeners for the cards
        for (ImageView Card : Cards) {
            Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView Card = (ImageView) v;
                    Card.setEnabled(false);
                    if (Card.getTag() == R.mipmap.card_back) {
                        // facedown card
                        // disable the card so that we can set its image
                        Card.setEnabled(false);
                        // get the current known animal ID for this card
                        Integer AnimalID = CardImages.get(Card.getId());
                        // set the card's image to the animal ID
                        Card.setImageResource(AnimalID);
                        // flip the card over to show the animal
                        Card.clearAnimation();
                        Card.setAnimation(faceup);
                        Card.startAnimation(faceup);
                        // check to see if we're the first in the set
                        // TODO - implement the set
                        // if we're the second in the set, see if we have a match
                        // TODO - implement the set
                        // if there's no match in the current set, turn the cards back over
                        // TODO - implement the set
                        // increase the number of tries
                        TextView Tries = (TextView) findViewById(R.id.number_of_tries);
                        Integer TryNum = Integer.getInteger(Tries.getText().toString());
                        TryNum++;
                        Tries.setText(TryNum);
                    } else {
                        // faceup card
                        // TODO - what should it do if you're clicking on a faceup card, and how did that happen?
                    }
                }
            });
        }
        // set up the onclick listener for the new game button
        Button NewGame = (Button) findViewById(R.id.new_game);
        NewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    public void newGame() {
        // get a RNG to place the cards randomly with
        Random RNG = new Random();
        // make a stack to put the random card numbers on
        Stack CardStack = new Stack();
        // populate the cardset with random card numbers
        Integer TotalCards = (Cards.length - 1);
        for (Integer i = 1; i <= TotalCards; i++) {
            CardStack.push(RNG.nextInt(TotalCards));
        }
        // go through each animal in the set
        for (String Animal : Animals.keySet()) {
            // for the first two cards (randomized at this point) set the image resource to the
            // the current animal we're on
            Integer FirstCard  = (Integer) CardStack.pop();
            Integer SecondCard = (Integer) CardStack.pop();
            // get the mipmap ID of the current animal so we can set the resource using it
            Integer AnimalID = Animals.get(Animal);
            // store the card's current animal
            CardImages.put("card" + FirstCard,  AnimalID);
            CardImages.put("card" + SecondCard, AnimalID);
            // set the cards back to facedown position
            Cards[FirstCard].setAnimation(facedown);
            Cards[SecondCard].setAnimation(facedown);
            Cards[FirstCard].startAnimation(facedown);
            Cards[SecondCard].startAnimation(facedown);
            // tag the cards as having the facedown image on them
            Cards[FirstCard].setTag(R.mipmap.card_back);
            Cards[SecondCard].setTag(R.mipmap.card_back);
        }
        // at this point we've flipped the cards all face down, and assigned them a random image
        // in pairs of two for each of the animals we have. We've also tagged them as having the
        // card back image on them so that the next on click will know if they are face down or not
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }
}