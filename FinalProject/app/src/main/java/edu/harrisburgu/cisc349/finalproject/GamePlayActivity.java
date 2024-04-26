package edu.harrisburgu.cisc349.finalproject;

import android.animation.ObjectAnimator;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.View;

import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class GamePlayActivity extends AppCompatActivity {
    private ImageView CharacterImg, SaladImg, GyozaImg, FastfoodImg, RegisterImg, Customer1Img, Customer2Img, GoImg, FCounterImg, BCounterImg, OrderImg, YouWinImg;

    private Queue<Integer> foodQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_test_model);

// Initializes the images to be displayed
        CharacterImg = findViewById(R.id.Character);
        SaladImg = findViewById(R.id.FoodOption3);
        GyozaImg =  findViewById(R.id.FoodOption2);
        FastfoodImg =  findViewById(R.id.FoodOption1);
        RegisterImg =  findViewById(R.id.CashRegister);
        Customer1Img =  findViewById(R.id.Customer1);
        Customer2Img =  findViewById(R.id.Customer2);
        GoImg =  findViewById(R.id.GoButton);
        FCounterImg =  findViewById(R.id.FrontCounter);
        BCounterImg =  findViewById(R.id.BackCounter);
        OrderImg =  findViewById(R.id.Order_Change);
        YouWinImg = findViewById(R.id.YouWin);
        foodQueue = new LinkedList<>();

// Makes all of the images hidden until player hits the go button
        CharacterImg.setVisibility(View.GONE);
        SaladImg.setVisibility(View.GONE);
        GyozaImg.setVisibility(View.GONE);
        FastfoodImg.setVisibility(View.GONE);
        RegisterImg.setVisibility(View.GONE);
        Customer1Img.setVisibility(View.GONE);
        Customer2Img.setVisibility(View.GONE);
        FCounterImg.setVisibility(View.GONE);
        BCounterImg.setVisibility(View.GONE);
        OrderImg.setVisibility(View.GONE);
        YouWinImg.setVisibility(View.GONE);


// When player clicks Go button to start the game
        GoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterImg.setVisibility(View.VISIBLE);
                SaladImg.setVisibility(View.VISIBLE);
                GyozaImg.setVisibility(View.VISIBLE);
                FastfoodImg.setVisibility(View.VISIBLE);
                RegisterImg.setVisibility(View.VISIBLE);
                YouWinImg.setVisibility(View.GONE);


                CustomerMovingForward();
                CustomerOrdering();

                FCounterImg.setVisibility(View.VISIBLE);
                BCounterImg.setVisibility(View.VISIBLE);
                GoImg.setVisibility(View.GONE);

            }
        });


// When player clicks salad
        SaladImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterTowardsSalad();
                // Add the salad to the queue when clicked
                foodQueue.add(R.drawable.salad);
                //Log.d("Food Queue", "Salad added to queue");
            }
        });


// When player clicks gyoza
        GyozaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterTowardsGyoza();
                foodQueue.add(R.drawable.gyoza);
            }
        });

// When player clicks fastfood
        FastfoodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterTowardsFastFood();
                foodQueue.add(R.drawable.fastfood);
            }
        });



// When player clicks register
        RegisterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterTowardsRegister();
            }
        });
    }


    private void CustomerOrdering(){
        // Randomizes which food customers get
        int[] foodImages = {R.drawable.salad, R.drawable.gyoza, R.drawable.fastfood};
        final ImageView Order_Change = findViewById(R.id.Order_Change);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int randomIndex = new Random().nextInt(foodImages.length);
                Order_Change.setImageResource(foodImages[randomIndex]);

                // Movement for Customers
                float orderX = 650.0f;
                float orderY = 550.0f;

                ObjectAnimator animatorX = ObjectAnimator.ofFloat(Order_Change, View.TRANSLATION_X, orderX);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(Order_Change, View.TRANSLATION_Y, orderY);

                animatorX.start();
                animatorY.start();

                Order_Change.setVisibility(View.VISIBLE);
            }
        }, 7000);
    }


    private void CustomerMovingForward() {

        // Randomizes which customers come in
        int[] customerimages = {R.drawable.customer_1, R.drawable.customer_2};
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int randomIndex = new Random().nextInt(customerimages.length);
                Customer1Img.setImageResource(customerimages[randomIndex]);
                Customer1Img.setVisibility(View.VISIBLE);

                // Movement for Customers
                float customerX = 50.0f;
                float customerY = 2.0f;

                float registerX = 300.0f;
                float registerY = 520.0f;

                float deltaX = registerX - customerX;
                float deltaY = registerY - customerY;

                Log.d("Character Location", "X: " + customerX + ", Y: " + customerY);
                Log.d("Register Location", "X: " + registerX + ", Y: " + registerY);


                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                // Speed
                long duration = (long) (distance / 500 * 1000);

                ObjectAnimator animatorX = ObjectAnimator.ofFloat(Customer1Img, View.TRANSLATION_X, deltaX);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(Customer1Img, View.TRANSLATION_Y, deltaY);
                animatorX.setDuration(duration);
                animatorY.setDuration(duration);
                animatorX.start();
                animatorY.start();
            }
        }, 5000);
    }


    private void CustomerMovingBackward() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Movement for Customers
                float customerX = 995.0f;
                float customerY = 995.0f;

                ObjectAnimator animatorX = ObjectAnimator.ofFloat(Customer1Img, View.TRANSLATION_X, customerX);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(Customer1Img, View.TRANSLATION_Y, customerY);

                animatorX.start();
                animatorY.start();

                Customer1Img.setVisibility(View.GONE);
            }
        }, 2000);
    }



// Movement for Character getting Salad
    private void moveCharacterTowardsSalad() {

        float characterX = 180.0f;
        float characterY = 310.0f;

        float saladX = 516.0f;

        float saladY = 290.0f;

        float deltaX = saladX - characterX;
        float deltaY = saladY - characterY;

        Log.d("Character Location", "X: " + characterX  + ", Y: " + characterY);
        Log.d("Salad Location", "X: " + saladX + ", Y: " + saladY);


        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Speed
        long duration = (long) (distance / 500 * 1000);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_X, deltaX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_Y, deltaY);
        animatorX.setDuration(duration);
        animatorY.setDuration(duration);
        animatorX.start();
        animatorY.start();
    }


// Movement for Character getting Gyoza
    private void moveCharacterTowardsGyoza() {

        float characterX = 180.0f;
        float characterY = 310.0f;

        float gyozaX = 765.0f;
        float gyozaY = 290.0f;

        float deltaX = gyozaX - characterX;
        float deltaY = gyozaY - characterY;

        Log.d("Character Location", "X: " + characterX  + ", Y: " + characterY);
        Log.d("Gyoza Location", "X: " + gyozaX + ", Y: " + gyozaY);


        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Speed
        long duration = (long) (distance / 500 * 1000);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_X, deltaX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_Y, deltaY);
        animatorX.setDuration(duration);
        animatorY.setDuration(duration);
        animatorX.start();
        animatorY.start();

    }


// Movement for Character getting Fastfood
    private void moveCharacterTowardsFastFood() {

        float characterX = 180.0f;
        float characterY = 310.0f;

        float fastfoodX = 990.0f;
        float fastfoodY = 290.0f;

        float deltaX = fastfoodX - characterX;
        float deltaY = fastfoodY - characterY;

        Log.d("Character Location", "X: " + characterX  + ", Y: " + characterY);
        Log.d("FastFood Location", "X: " + fastfoodX + ", Y: " + fastfoodY);


        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Speed
        long duration = (long) (distance / 500 * 1000);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_X, deltaX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_Y, deltaY);
        animatorX.setDuration(duration);
        animatorY.setDuration(duration);
        animatorX.start();
        animatorY.start();

    }



    // Movement for Character going to Register
    private void moveCharacterTowardsRegister() {

        float characterX = 180.0f;
        float characterY = 310.0f;

        float registerX = 300.0f;
        float registerY = 780.0f;

        float deltaX = registerX - characterX;
        float deltaY = registerY - characterY;

        Log.d("Character Location", "X: " + characterX  + ", Y: " + characterY);
        Log.d("Register Location", "X: " + registerX + ", Y: " + registerY);


        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Speed
        long duration = (long) (distance / 500 * 1000);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_X, deltaX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(CharacterImg, View.TRANSLATION_Y, deltaY);
        animatorX.setDuration(duration);
        animatorY.setDuration(duration);
        animatorX.start();
        animatorY.start();


        List<Integer> foodList = new ArrayList<>(foodQueue);
        final ImageView Order_Change = findViewById(R.id.Order_Change);

        int i = 0;
        while (i < 3) { // Continue looping until i reaches 3
            if (foodList.contains(R.drawable.salad)) {
                Log.d("Food Queue", "Salad is in the queue");
                Order_Change.setVisibility(View.GONE);
                CustomerMovingBackward();
                CustomerMovingForward();
                CustomerOrdering();
            } else if (foodList.contains(R.drawable.gyoza)) {
                Log.d("Food Queue", "Gyoza is in the queue");
                Order_Change.setVisibility(View.GONE);
                CustomerMovingBackward();
                CustomerMovingForward();
                CustomerOrdering();
            } else if (foodList.contains(R.drawable.fastfood)) {
                Log.d("Food Queue", "FastFood is in the queue");
                Order_Change.setVisibility(View.GONE);
                CustomerMovingBackward();
                CustomerMovingForward();
                CustomerOrdering();
            } else {
                Log.d("Food Queue", "Pick a valid food");
            }
            i++;
        }
    }
}