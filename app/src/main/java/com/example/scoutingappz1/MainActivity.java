package com.example.scoutingappz1;


import static java.util.Arrays.sort;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    static ArrayList<String> added = new ArrayList<>();
    static String scouterName;
    static int teamNumber;
    static int matchNumber;
    static int elap = 0;
    static inputText time;
    static Runnable runnable;
    static boolean teleop = false;
    static int algaeFloor;
    static int netShot;
    static int deepBarge = 0;
    static int shallowBarge = 0;
    static int FcoralL1;
    static int FcoralL2;
    static int FcoralL3;
    static int FcoralL4;
    static int BcoralL1;
    static int BcoralL2;
    static int BcoralL3;
    static int BcoralL4;
    static int source;
    static int processor;
    static int algaeRemoved;


    static int AalgaeRemoved;


    static int AalgaeFloor;
    static int AnetShot;
    static int AFcoralL1;
    static int AFcoralL2;
    static int AFcoralL3;
    static int AFcoralL4;
    static int ABcoralL1;
    static int ABcoralL2;
    static int ABcoralL3;
    static int ABcoralL4;
    static int Aprocessor;
    static int netMiss;
    static int processorMiss;
    static boolean inMenu = false;


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        layout.setBackgroundColor(Color.BLACK);
        getSupportActionBar().hide();


        But playButton = new But(312, 100, 400, 100, Color.BLACK, this, layout);
        TextView pText = new TextView(this);
        pText.setTypeface(Typeface.MONOSPACE);
        pText.setElevation(100);
        pText.setText("START");
        pText.setTextColor(Color.WHITE);
        pText.setX(390);
        pText.setY(94);
        pText.setTextSize(80);
        pText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        TextView cText = new TextView(this);
        cText.setText("Change Info");
        cText.setTypeface(Typeface.MONOSPACE);
        cText.setElevation(100);
        cText.setTextColor(Color.WHITE);
        cText.setX(350);
        cText.setY(310);
        cText.setTextSize(55);
        cText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        layout.addView(pText);
        layout.addView(cText);
        But changeInfo = new But(312, 300, 400, 100, Color.BLACK, this, layout);
        playButton.setOnClickEvent(e -> {
            layout.removeAllViews();
            But backButtonPlay = new But(0, 0, 2000, 2000, Color.rgb(171, 27, 17), this, layout);
            backButtonPlay.setOnClickEvent(en -> {
                layout.setBackgroundColor(Color.BLACK);
                layout.removeAllViews();
                layout.addView(playButton.myButton);
                layout.addView(changeInfo.myButton);
                layout.addView(pText);
                layout.addView(cText);
            });
            But StartGame = new But(250, 0, 2000, 2000, Color.rgb(46, 143, 39), this, layout);
            StartGame.setOnClickEvent(en -> {
                layout.setBackgroundColor(Color.BLACK);
                layout.removeAllViews();
                ImageView field = new ImageView(this);
                field.setImageResource(R.drawable.field);
                layout.addView(field);
                // add buttons here
                But Floor = new But(70, 57, 880, 400, Color.TRANSPARENT, this, layout);
                Floor.myButton.setTranslationZ(-1000000);
                Floor.flash(Color.BLACK, "algaeFloor", -100000);
                But BlueNet = new But(484, 67, 55, 195, Color.TRANSPARENT, this, layout);
                BlueNet.flash(Color.BLACK, "netShot", 100);
                But RedNet = new But(484, 265, 55, 195, Color.TRANSPARENT, this, layout);
                RedNet.flash(Color.BLACK, "netShot", 100);
                But NetMiss = new But(170, 5, 90, 45, Color.DKGRAY, this, layout);
                NetMiss.flash(Color.BLACK, "netMiss", 100);
                NetMiss.setText(" Net Miss", 20);
                NetMiss.tp.setElevation(10000);
                But ProcessorMiss = new But(265, 5, 147, 45, Color.DKGRAY, this, layout);
                ProcessorMiss.flash(Color.BLACK, "processorMiss", 100);
                ProcessorMiss.setText(" Processor Miss", 20);
                ProcessorMiss.tp.setElevation(10000);
                But AlgaeIntake = new But(417, 5, 155, 45, Color.DKGRAY, this, layout);
                AlgaeIntake.flash(Color.BLACK, "algaeRemoved", 100);
                AlgaeIntake.setText(" Algae Removed", 20);
                AlgaeIntake.tp.setElevation(10000);
                //buttons menu
                But L1 = new But(150, 370, 75, 75, Color.rgb(116, 134, 173), this, layout);
                L1.myButton.setElevation(200);
                L1.myButton.setStateListAnimator(null);
                L1.setText("L1", 40);
                L1.remove(layout);
                But L2 = new But(300, 370, 75, 75, Color.rgb(116, 134, 173), this, layout);
                L2.myButton.setElevation(200);
                L2.myButton.setStateListAnimator(null);
                L2.setText("L2", 40);
                L2.remove(layout);
                But L3 = new But(450, 370, 75, 75, Color.rgb(116, 134, 173), this, layout);
                L3.myButton.setElevation(200);
                L3.myButton.setStateListAnimator(null);
                L3.setText("L3", 40);
                L3.remove(layout);
                But L4 = new But(600, 370, 75, 75, Color.rgb(116, 134, 173), this, layout);
                L4.myButton.setElevation(200);
                L4.myButton.setStateListAnimator(null);
                L4.setText("L4", 40);
                L4.remove(layout);
                But PLAYBACK = new But(750, 370, 75, 75, Color.RED, this, layout);
                PLAYBACK.remove(layout);
                PLAYBACK.myButton.setElevation(200);
                PLAYBACK.myButton.setStateListAnimator(null);
                Rectangle BG = new Rectangle(0, 320, 1000, 300, this, layout);
                BG.rectangleView.setElevation(190);
                BG.setOpacity(.8F);
                BG.remove(layout);
                But CoralBlueFront = new But(251, 217, 55, 100, Color.YELLOW, this, layout);
                CoralBlueFront.setColor(Color.TRANSPARENT);
                CoralBlueFront.myButton.setOnClickListener(a -> {


                    if (!inMenu) {
                        inMenu = true;
                        CoralBlueFront.setColor(Color.rgb(38, 56, 158));
                        BG.add(layout);
                        L1.add(layout);
                        L2.add(layout);
                        L3.add(layout);
                        L4.add(layout);
                        L1.myButton.setElevation(200);
                        L2.myButton.setElevation(200);
                        L3.myButton.setElevation(200);
                        L4.myButton.setElevation(200);
                        PLAYBACK.myButton.setElevation(200);
                        BG.rectangleView.setElevation(190);
                        PLAYBACK.add(layout);
                        PLAYBACK.myButton.setOnClickListener(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                        });
                        L1.setOnClickEvent(b -> {
//                            L1.myButton.setElevation(-20000);
                            L1.remove(layout);
//                            L1.setColor(Color.RED);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL1");
                        });
                        L2.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL2");
                        });
                        L3.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL3");

                        });
                        L4.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL4");
                        });
                    }
                });
                But CoralBlueBack = new But(313, 217, 55, 100, Color.DKGRAY, this, layout);
                CoralBlueBack.setColor(Color.TRANSPARENT);
                CoralBlueBack.myButton.setOnClickListener(a -> {


                    if (!inMenu) {
                        inMenu = true;
                        CoralBlueBack.setColor(Color.rgb(38, 56, 158));
                        BG.add(layout);
                        L1.add(layout);
                        L2.add(layout);
                        L3.add(layout);
                        L4.add(layout);
                        L1.myButton.setElevation(200);
                        L2.myButton.setElevation(200);
                        L3.myButton.setElevation(200);
                        L4.myButton.setElevation(200);
                        PLAYBACK.myButton.setElevation(200);
                        BG.rectangleView.setElevation(190);
                        PLAYBACK.add(layout);
                        PLAYBACK.myButton.setOnClickListener(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                        });
                        L1.setOnClickEvent(b -> {
//                            L1.myButton.setElevation(-20000);
                            L1.remove(layout);
//                            L1.setColor(Color.RED);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL1");

                        });
                        L2.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL2");

                        });
                        L3.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL3");

                        });
                        L4.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralBlueBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL4");
                        });
                    }
                });
                But CoralRedBack = new But(657, 217, 55, 100, Color.DKGRAY, this, layout);
                CoralRedBack.setColor(Color.TRANSPARENT);
                CoralRedBack.myButton.setOnClickListener(a -> {


                    if (!inMenu) {
                        inMenu = true;
                        CoralRedBack.setColor(Color.rgb(186, 47, 47));
                        BG.add(layout);
                        L1.add(layout);
                        L2.add(layout);
                        L3.add(layout);
                        L4.add(layout);
                        L1.myButton.setElevation(200);
                        L2.myButton.setElevation(200);
                        L3.myButton.setElevation(200);
                        L4.myButton.setElevation(200);
                        PLAYBACK.myButton.setElevation(200);
                        BG.rectangleView.setElevation(190);
                        PLAYBACK.add(layout);
                        PLAYBACK.myButton.setOnClickListener(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                        });
                        L1.setOnClickEvent(b -> {
//                            L1.myButton.setElevation(-20000);
                            L1.remove(layout);
//                            L1.setColor(Color.RED);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL1");

                        });
                        L2.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL2");

                        });
                        L3.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL3");

                        });
                        L4.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedBack.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("BL4");
                        });
                    }
                });
                But CoralRedFront = new But(719, 217, 55, 100, Color.YELLOW, this, layout);
                CoralRedFront.setColor(Color.TRANSPARENT);
                CoralRedFront.myButton.setOnClickListener(a -> {


                    if (!inMenu) {
                        inMenu = true;
                        CoralRedFront.setColor(Color.rgb(186, 47, 47));
                        BG.add(layout);
                        L1.add(layout);
                        L2.add(layout);
                        L3.add(layout);
                        L4.add(layout);
                        L1.myButton.setElevation(200);
                        L2.myButton.setElevation(200);
                        L3.myButton.setElevation(200);
                        L4.myButton.setElevation(200);
                        PLAYBACK.myButton.setElevation(200);
                        BG.rectangleView.setElevation(190);
                        PLAYBACK.add(layout);
                        PLAYBACK.myButton.setOnClickListener(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                        });
                        L1.setOnClickEvent(b -> {
//                            L1.myButton.setElevation(-20000);
                            L1.remove(layout);
//                            L1.setColor(Color.RED);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL1");
                        });
                        L2.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL2");
                        });
                        L3.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL3");
                        });
                        L4.setOnClickEvent(b -> {
                            L1.remove(layout);
                            L2.remove(layout);
                            L3.remove(layout);
                            L4.remove(layout);
                            BG.remove(layout);
                            PLAYBACK.remove(layout);
                            CoralRedFront.setColor(Color.TRANSPARENT);
                            inMenu = false;
                            But.bullshit("FL4");
                        });
                    }
                });
                CoralRedFront.myButton.setTranslationZ(-10000);
                But BlueDeepBarge = new But(440, 113, 40, 40, Color.rgb(18, 35, 64), this, layout);
                BlueDeepBarge.flashWithTimer(Color.WHITE, "deepBarge", 10000);
                But BlueShallowBarge = new But(440, 163, 40, 40, Color.rgb(93, 137, 212), this, layout);
                BlueShallowBarge.flashWithTimer(Color.WHITE, "shallowBarge", 1000);
                But RedDeepBarge = new But(543, 374, 40, 40, Color.rgb(18, 35, 64), this, layout);
                RedDeepBarge.flashWithTimer(Color.WHITE, "deepBarge", 10000);
                But RedShallowBarge = new But(543, 324, 40, 40, Color.rgb(93, 137, 212), this, layout);
                RedShallowBarge.flashWithTimer(Color.WHITE, "shallowBarge", 10000);
                But BlueSourceTop = new But(94, 28, 45, 120, Color.TRANSPARENT, this, layout);
                BlueSourceTop.setRotation(53);
                BlueSourceTop.flash(Color.BLACK, "source", 100);
                But BlueSourceBottom = new But(94, 386, 45, 120, Color.TRANSPARENT, this, layout);
                BlueSourceBottom.setRotation(127);
                BlueSourceBottom.flash(Color.BLACK, "source", 100);
                But RedSourceTop = new But(889, 28, 45, 120, Color.TRANSPARENT, this, layout);
                RedSourceTop.setRotation(127);
                RedSourceTop.flash(Color.BLACK, "source", 100);
                But RedSourceBottom = new But(889, 386, 45, 120, Color.TRANSPARENT, this, layout);
                RedSourceBottom.setRotation(53);
                RedSourceBottom.flash(Color.BLACK, "source", 100);
                But BlueProcessor = new But(330, 445, 120, 50, Color.TRANSPARENT, this, layout);
                BlueProcessor.flash(Color.BLACK, "processor", 100);
                But RedProcessor = new But(560, 25, 125, 50, Color.TRANSPARENT, this, layout);
                RedProcessor.flash(Color.BLACK, "processor", 100);
                But Undo = new But(30, 450, 60, 50, Color.rgb(161, 51, 43), this, layout);
                Undo.flash(Color.BLACK, "undo", 100);
                But finishData = new But(950, 450, 50, 50, Color.WHITE, this, layout);
                But Back = new But(750, 30, 100, 100, Color.rgb(171, 27, 17), this, layout);
                Back.remove(layout);
                Back.myButton.setOnClickListener(c -> {
                    layout.removeAllViews();
                    playButton.add(layout);
                    changeInfo.add(layout);
                    layout.addView(pText);
                    layout.addView(cText);
                    elap = 0;
                    MainActivity.teamNumber = 0;
                    MainActivity.matchNumber += 1;
                    MainActivity.AFcoralL1 = 0;
                    MainActivity.AFcoralL2 = 0;
                    MainActivity.AFcoralL3 = 0;
                    MainActivity.AFcoralL4 = 0;
                    MainActivity.ABcoralL1 = 0;
                    MainActivity.ABcoralL2 = 0;
                    MainActivity.ABcoralL3 = 0;
                    MainActivity.ABcoralL4 = 0;
                    MainActivity.AalgaeRemoved = 0;
                    MainActivity.AnetShot = 0;
                    MainActivity.Aprocessor = 0;
                    MainActivity.FcoralL1 = 0;
                    MainActivity.BcoralL1 = 0;
                    MainActivity.FcoralL2 = 0;
                    MainActivity.BcoralL2 = 0;
                    MainActivity.FcoralL3 = 0;
                    MainActivity.BcoralL3 = 0;
                    MainActivity.FcoralL4 = 0;
                    MainActivity.BcoralL4 = 0;
                    MainActivity.algaeRemoved = 0;
                    MainActivity.netShot = 0;
                    MainActivity.netMiss = 0;
                    MainActivity.processor = 0;
                    MainActivity.processorMiss = 0;
                    MainActivity.source = 0;
                    MainActivity.algaeFloor = 0;
                    MainActivity.deepBarge = 0;
                    MainActivity.shallowBarge = 0;
                });
                finishData.setOnClickEvent(eer -> {
                    System.out.println(elap);

                    if (elap == 16) {
                        teleop = true;
                        int interval = 1000;
                        Handler handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                time.textBar.setText(Integer.toString(elap));
                                elap += 1;

                                if (elap == 11) {
                                    time.textBar.setX(950);
                                }

                                if (elap == 15) { // Stop at 15 seconds and show popup
                                    handler.removeCallbacks(this);
                                    showPopupToContinue(handler, layout, Back);
                                } else if (elap == 151) { // Stop completely at 150 seconds
                                    handler.removeCallbacks(this);
                                    layout.removeAllViews();
                                    Back.add(layout);
                                    saveCsvFile(MainActivity.this, layout);
                                } else {
                                    handler.postDelayed(this, interval);
                                }
                            }
                        };
                        handler.post(runnable);
                    }
                });

// Initialize Timer Immediately
                elap = 0;
                time = new inputText(20, 480, 230, 100, 100, Color.BLUE, Integer.toString(elap), layout, this);
                time.textBar.setElevation(10000000);

                Handler handler = new Handler();
                int interval = 1000;

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        time.textBar.setText(Integer.toString(elap));
                        elap += 1;

                        if (elap == 11) {
                            time.textBar.setX(950);
                        }

                        if (elap == 15) {
                            handler.removeCallbacks(this);
                            showPopupToContinue(handler, layout, Back);
                            teleop = true;
                        } else {
                            handler.postDelayed(this, interval);
                        }
                    }
                };

                handler.post(runnable);

            });
        });
        changeInfo.setOnClickEvent(e -> {
            layout.removeAllViews();
            // Create TextView for display
            TextView nameDisplay = new TextView(this);
            nameDisplay.setText("Scouter Name"); // Default display text
            if(MainActivity.scouterName != null)
                nameDisplay.setText(MainActivity.scouterName);
            nameDisplay.setTextColor(Color.WHITE);
            nameDisplay.setTextSize(50);
            nameDisplay.setX(350);
            nameDisplay.setY(79);
            nameDisplay.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
            layout.addView(nameDisplay);


            But nameS = new But(325, 40, 400, 150, Color.TRANSPARENT, this, layout);
            nameS.setOnClickEvent(b -> {
                // Remove existing AutoCompleteTextView (if any) to avoid duplicates
                layout.removeView(layout.findViewWithTag("nameDropdown"));


                // Create AutoCompleteTextView
                AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(this);
                autoCompleteTextView.setTag("nameDropdown"); // Unique tag to identify it later
                String[] names = {
                        "Ajitesh Bachina",
                        "YiJun Bai",
                        "Ujaan Bakshi",
                        "Rushi Bansal",
                        "Aryan Bhardwaj",
                        "Anika Datta",
                        "Dhruvsai Dhulipudi",
                        "Jeffrey Guan",
                        "Aahana Jain",
                        "Arisu Jeong",
                        "Daksh Jhanjee",
                        "John Kin",
                        "Karthik Konduri",
                        "Arnav Mote",
                        "Hubert Nguyen",
                        "Mesut Ozcan",
                        "Sreya Pamidighantam",
                        "Ishanth Pulla",
                        "Anika Raghunath",
                        "Sohan Raya",
                        "Bhavyansh Sachwani",
                        "Pranav Sinthoju",
                        "Balaskanda Sivanandame",
                        "Shakthi Vikram",
                        "Abhinav Yamujala",
                        "Chaitanya Abburi",
                        "Saesha Abraham",
                        "Abhinav Acharyabhatta",
                        "Muthiah Aravind Annamalai",
                        "Aryan Awati",
                        "Vaibhav Badam",
                        "Vishnu Badam",
                        "Arjun Bajaj",
                        "Veer Bhatt",
                        "Anisha Bhattacharya",
                        "Arjun Bhor",
                        "Bishanmeet (Ekam) Bindra",
                        "Joel Biswas",
                        "Samyak Chatterjee",
                        "Rishith Chintala",
                        "Preetika Chunduru",
                        "Dorothy Dossett",
                        "Sai Shravan Elaiya Raja",
                        "Saatvik Sai Elaiya Raja",
                        "Yash Gandhi",
                        "Abhinav Gandreddi",
                        "Loehith Gokul",
                        "Ziqi Guan",
                        "Ava Harris",
                        "Chris Hayes",
                        "Tian Hu",
                        "Ayush Jaiswal",
                        "Akshay Jayaraman",
                        "Eugene Jeong",
                        "Nikhil Kancharla",
                        "Karthik Kanneganti",
                        "Vivaan Khowal",
                        "Sriharsha Kommula",
                        "Satvik Koppu",
                        "Denith Koralege",
                        "Hasini Koya",
                        "Anay Kshatriya",
                        "Sanel Lathiya",
                        "Jonathan Ledvina",
                        "Jerry Liu",
                        "Vyshakh Menon",
                        "Anvi Mundra",
                        "Agastya Munnangi",
                        "Ajay Murugappan",
                        "Naren Nandakumar",
                        "Krithvik Naveen",
                        "Advay Nittala",
                        "Dylan Patel",
                        "Akshat Prasanna",
                        "Arav Raghunathan",
                        "Ayan Raghunathan",
                        "Kush Ray",
                        "Soham Sakpal",
                        "Gaargi Sanghavi",
                        "Brady Selagea",
                        "Thaman Sevalur Vinothkumar",
                        "Aadi Shah",
                        "Samvit Surya",
                        "Sankalp Surya",
                        "Keane Treechantapagorn",
                        "Aidan Unsworth",
                        "Sanjay Venkadeshmeena",
                        "Bhavyasri Vundi",
                        "Siyuan (Mark) Wu",
                        "Dima Yosif"};
                sort(names);


                // Set up the adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setHint("Select a name");


                // Define layout parameters
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(350,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                params.topMargin = 100; // Below "Scouter Name"


                // Handle selection from dropdown
                autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                    MainActivity.scouterName = (String) parent.getItemAtPosition(position); // Store selection
                    nameDisplay.setText(MainActivity.scouterName); // Update TextView
                    Toast.makeText(MainActivity.this, "Selected: " + MainActivity.scouterName, Toast.LENGTH_SHORT).show();
                    autoCompleteTextView.dismissDropDown();
                    layout.removeView(autoCompleteTextView); // Remove dropdown after selection
                });


                // Close dropdown when Enter key is pressed
                autoCompleteTextView.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ||
                            (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        autoCompleteTextView.dismissDropDown();
                        layout.removeView(autoCompleteTextView); // Remove dropdown
                        return true;
                    }
                    return false;
                });


                // Add AutoCompleteTextView to layout
                layout.addView(autoCompleteTextView, params);


                // Force dropdown to show
                autoCompleteTextView.requestFocus();
                autoCompleteTextView.showDropDown();
            });


            inputText TNT = new inputText(50, 180, 115, 400, 200, Color.WHITE, "Team Number", layout, this);
            TNT.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
            inputText DNT = new inputText(50, 180, 194, 400, 200, Color.WHITE, "Match Number", layout, this);
            DNT.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
            if(MainActivity.matchNumber > 0){
                DNT.editText(String.valueOf(MainActivity.matchNumber));
            }
            But backButton = new But(50, 50, 50, 50, Color.rgb(171, 27, 17), this, layout);
            backButton.setOnClickEvent(ev -> {
                layout.removeAllViews();
                layout.addView(playButton.myButton);
                layout.addView(changeInfo.myButton);
                layout.addView(pText);
                layout.addView(cText);
            });
        });
        // end of button adding area
        setContentView(layout);


    }

    //I LOVE TAYLOR SWIFT SO MUCH
    //TAYTAY ICON SLAY <3
    private void showPopupToContinue(Handler handler, FrameLayout layout, But back) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Timer Paused");
        builder.setMessage("");

        builder.setPositiveButton("Switch to Teleop", (dialog, which) -> {
            resumeTimer(handler, layout, back);
            teleop = true;
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void resumeTimer(Handler handler, FrameLayout layout, But back) {
        int interval = 1000;

        runnable = new Runnable() {
            @Override
            public void run() {
                time.textBar.setText(Integer.toString(elap));
                elap += 1;

                if (elap == 11) {
                    time.textBar.setX(950);
                }

                if (elap == 151) { // Stop completely at 155 seconds
                    handler.removeCallbacks(this);
                    layout.removeAllViews();
                    back.add(layout);
                    saveCsvFile(MainActivity.this, layout);
                } else {
                    handler.postDelayed(this, interval);
                }
            }
        };

        handler.post(runnable);
    }


    public void saveCsvFile(Context context, FrameLayout layout) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            // For Android 10 (API 29) and above, use MediaStore to write files
//            ContentValues values = new ContentValues();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            String timeOfCreation = sdf.format(new Date());
//            values.put(MediaStore.Downloads.DISPLAY_NAME, "data_" + timeOfCreation + "_" + scouterName + ".csv");  // File name
//            values.put(MediaStore.Downloads.MIME_TYPE, "text/csv");      // MIME type
//            values.put(MediaStore.Downloads.RELATIVE_PATH, "Download/");  // Downloads folder
//            // Insert the content values into the MediaStore
//            Uri uri = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//                uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
//            }
//            try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
//                if (outputStream != null) {
//                    // Write to the file
//                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
//                    writer.write("Team Number, Match Number, Scouter Name, Auton Front Coral L1, Auton Front Coral L2, Auton Front Coral L3, Auton Front Coral L4, Auton Back Coral L1, Auton Back Coral L2, Auton Back Coral L3, Auton Back Coral L4, Auton Algae Removed, Auton Net Shots, Auton Processor, Coral L1F, Coral L1B, Coral L2F, Coral L2B, Coral L3F, Coral L3B, Coral L4F, Coral L4B, Algae Removed, Algae Net, Algae Net Miss, Processor, Processor Miss, Coral Station Intake, Algae Intake, Deep Climb, Shallow Climb\n");
//                    writer.write(MainActivity.teamNumber + ", " + MainActivity.matchNumber + ", " + MainActivity.scouterName + ", " + MainActivity.AFcoralL1 + ", " + MainActivity.AFcoralL2 + ", " + MainActivity.AFcoralL3 + ", " + MainActivity.AFcoralL4 + ", " + MainActivity.ABcoralL1 + ", " + MainActivity.ABcoralL2 + ", " + MainActivity.ABcoralL3 + ", " + MainActivity.ABcoralL4 + ", " + MainActivity.AalgaeRemoved + ", " + MainActivity.AnetShot + ", " + MainActivity.Aprocessor + ", " + MainActivity.FcoralL1 + ", " + MainActivity.BcoralL1 + ", " + MainActivity.FcoralL2 + ", " + MainActivity.BcoralL2 + ", " + MainActivity.FcoralL3 + ", " + MainActivity.BcoralL3 + ", " + MainActivity.FcoralL4 + ", " + MainActivity.BcoralL4 + ", " + MainActivity.algaeRemoved + ", " + MainActivity.netShot + ", " + MainActivity.netMiss + ", " + MainActivity.processor + ", " + MainActivity.processorMiss + ", " + MainActivity.source + ", " + MainActivity.algaeFloor + ", " + MainActivity.deepBarge + ", " + MainActivity.shallowBarge + "\n");
//                    writer.flush();
//                    writer.close();
//                    System.out.println("Team Number, Match Number, Scouter Name, Auton Front Coral L1, Auton Front Coral L2, Auton Front Coral L3, Auton Front Coral L4, Auton Back Coral L1, Auton Back Coral L2, Auton Back Coral L3, Auton Back Coral L4, Auton Algae Removed, Auton Net Shots, Auton Processor, Coral L1F, Coral L1B, Coral L2F, Coral L2B, Coral L3F, Coral L3B, Coral L4F, Coral L4B, Algae Removed, Algae Net, Algae Net Miss, Processor, Processor Miss, Coral Station Intake, Algae Intake, Deep Climb, Shallow Climb\n");
//                    System.out.println(MainActivity.teamNumber + ", " + MainActivity.matchNumber + ", " + MainActivity.scouterName + ", " + MainActivity.AFcoralL1 + ", " + MainActivity.AFcoralL2 + ", " + MainActivity.AFcoralL3 + ", " + MainActivity.AFcoralL4 + ", " + MainActivity.ABcoralL1 + ", " + MainActivity.ABcoralL2 + ", " + MainActivity.ABcoralL3 + ", " + MainActivity.ABcoralL4 + ", " + MainActivity.AalgaeRemoved + ", " + MainActivity.AnetShot + ", " + MainActivity.Aprocessor + ", " + MainActivity.FcoralL1 + ", " + MainActivity.BcoralL1 + ", " + MainActivity.FcoralL2 + ", " + MainActivity.BcoralL2 + ", " + MainActivity.FcoralL3 + ", " + MainActivity.BcoralL3 + ", " + MainActivity.FcoralL4 + ", " + MainActivity.BcoralL4 + ", " + MainActivity.algaeRemoved + ", " + MainActivity.netShot + ", " + MainActivity.netMiss + ", " + MainActivity.processor + ", " + MainActivity.processorMiss + ", " + MainActivity.source + ", " + MainActivity.algaeFloor + ", " + MainActivity.deepBarge + ", " + MainActivity.shallowBarge + "\n");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // For older versions, you can use the older methods (like External Storage)
//            System.out.println("somethings fucked up");
//        }

        String csvData = ("Team Number, Match Number, Scouter Name, Auton Front Coral L1, Auton Front Coral L2, Auton Front Coral L3, Auton Front Coral L4, Auton Back Coral L1, Auton Back Coral L2, Auton Back Coral L3, Auton Back Coral L4, Auton Algae Removed, Auton Net Shots, Auton Processor, Coral L1F, Coral L1B, Coral L2F, Coral L2B, Coral L3F, Coral L3B, Coral L4F, Coral L4B, Algae Removed, Algae Net, Algae Net Miss, Processor, Processor Miss, Coral Station Intake, Algae Intake, Deep Climb, Shallow Climb\n" + MainActivity.teamNumber + ", " + MainActivity.matchNumber + ", " + MainActivity.scouterName + ", " + MainActivity.AFcoralL1 + ", " + MainActivity.AFcoralL2 + ", " + MainActivity.AFcoralL3 + ", " + MainActivity.AFcoralL4 + ", " + MainActivity.ABcoralL1 + ", " + MainActivity.ABcoralL2 + ", " + MainActivity.ABcoralL3 + ", " + MainActivity.ABcoralL4 + ", " + MainActivity.AalgaeRemoved + ", " + MainActivity.AnetShot + ", " + MainActivity.Aprocessor + ", " + MainActivity.FcoralL1 + ", " + MainActivity.BcoralL1 + ", " + MainActivity.FcoralL2 + ", " + MainActivity.BcoralL2 + ", " + MainActivity.FcoralL3 + ", " + MainActivity.BcoralL3 + ", " + MainActivity.FcoralL4 + ", " + MainActivity.BcoralL4 + ", " + MainActivity.algaeRemoved + ", " + MainActivity.netShot + ", " + MainActivity.netMiss + ", " + MainActivity.processor + ", " + MainActivity.processorMiss + ", " + MainActivity.source + ", " + MainActivity.algaeFloor + ", " + MainActivity.deepBarge + ", " + MainActivity.shallowBarge + "\n");


// Generate QR code bitmap
        Bitmap qrBitmap = generateQRCode(csvData);
        // Assuming 'layout' is your ViewGroup (like LinearLayout or RelativeLayout)

// Create new ImageView
        ImageView qrImage = new ImageView(this);

// Optionally set size & position by layout params
// For example, using Absolute or FrameLayout params if needed (adjust accordingly)
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(400, 400);
        params.leftMargin = 320;  // X position
        params.topMargin = 50;   // Y position
        qrImage.setLayoutParams(params);

// Set the QR code bitmap here after generating it
        qrImage.setImageBitmap(qrBitmap);

// Add ImageView to your layout
        layout.addView(qrImage);

// Find your ImageView and set the QR code bitmap
    }
    public Bitmap generateQRCode(String data) {
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 800, 800);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}


class But {
    float xPos;
    float yPos;
    float width;
    float height;
    String shape;
    int color;
    int flasher;
    Button myButton;
    Context tt;
    FrameLayout ll;
    TextView tp;
    // Timer variables
    private long shallowStartTime = 0; // Start time for shallow climb
    private long deepStartTime = 0; // Start time for deep climb


    public But(float xp, float yp, float w, float h, int c, Context t, FrameLayout l) {
        tt = t;
        ll = l;
        xPos = xp;
        yPos = yp;
        width = w;
        color = c;
        height = h;
        myButton = new Button(t);
        myButton.setX(xPos);
        myButton.setY(yPos);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                (int) w,  // Width in pixels
                (int) h   // Height in pixels
        );
        myButton.setLayoutParams(params);
        myButton.setBackgroundColor(c);
        l.addView(myButton);
    }

    public void setOnClickEvent(View.OnClickListener listener) {
        myButton.setOnClickListener(listener);
    }

    public void setColor(int c) {
        myButton.setBackgroundColor(c);
        color = c;
    }

    public void remove(FrameLayout layout) {
        layout.removeView(myButton);
        if (tp != null) {
            layout.removeView(tp);
        }


    }

    public void add(FrameLayout layout) {
        layout.addView(myButton);
        if (tp != null) {
            layout.addView(tp);
        }
    }

    public void setRotation(float angle) {
        // Rotate the button by the specified angle (in degrees)
        myButton.setRotation(angle);
    }

    public void setElevation(int l) {
        myButton.setElevation(l);
    }

    public void setZ(float zIndex) {
        myButton.setZ(zIndex);
    }
    // Inside your But class


    // Static variables to store final values for climb times

    public void setText(String txt, int ss) {
        tp = new TextView(tt);
        tp.setText(txt);
        tp.setTextSize(ss);
        tp.setX(xPos);
        tp.setY(yPos);
        tp.setElevation(myButton.getElevation() + 10);
        ll.addView(tp);
    }

    public void setText(String txt, int ss, int color) {
        // Remove the previous text if it exists
        if (tp != null) {
            ll.removeView(tp);
        }

        // Create new TextView
        tp = new TextView(tt);
        tp.setText(txt);
        tp.setTextSize(ss);
        tp.setTextColor(color);
        tp.setGravity(Gravity.CENTER);

        // Set layout parameters to center text inside button
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                myButton.getWidth(), myButton.getHeight()
        );
        params.leftMargin = (int) xPos;
        params.topMargin = (int) yPos;
        tp.setLayoutParams(params);

        tp.setElevation(myButton.getElevation() + 10); // Ensure text appears above button

        // Add new text to layout
        ll.addView(tp);
    }

    public void flash(int col, String dataType, int el) {


        this.setOnClickEvent(n -> {
            this.myButton.setZ(el);
            this.myButton.setBackgroundColor(col);
            this.myButton.setTranslationZ(el);
            bullshit(dataType);
            flasher = 0;
            int interval = 150;
            Handler handler = new Handler();
            Runnable runnable2 = new Runnable() {
                @Override
                public void run() {
                    flasher++;
                    if (flasher == 2) {
                        flasher = 0;
                        handler.removeCallbacks(this);
                        myButton.setBackgroundColor(color);
                        myButton.setTranslationZ(el);
                    } else {
//                        myButton.setBackgroundColor(col);
                        handler.postDelayed(this, interval);
                    }
                }
            };
            handler.post(runnable2);
        });
    }

    public static void bullshit(String dataType) {
        String last = "";
        if (!dataType.equals("undo"))
            MainActivity.added.add(dataType);
        if (!MainActivity.added.isEmpty())
            last = MainActivity.added.get(MainActivity.added.size() - 1);

        if (MainActivity.teleop) {
            if (dataType.equals("undo") && !MainActivity.added.isEmpty()) {
                MainActivity.added.remove(MainActivity.added.size() - 1);
                if (last.equals("algaeRemoved"))
                    MainActivity.algaeRemoved--;
                if (last.equals("algaeFloor"))
                    MainActivity.algaeFloor--;
                if (last.equals("netShot"))
                    MainActivity.netShot--;


                if (last.equals("netMiss"))
                    MainActivity.netMiss--;
                if (last.equals("processorMiss"))
                    MainActivity.processorMiss--;


                if (last.equals("FL1"))
                    MainActivity.FcoralL1--;
                if (last.equals("FL2"))
                    MainActivity.FcoralL2--;
                if (last.equals("FL3"))
                    MainActivity.FcoralL3--;
                if (last.equals("FL4"))
                    MainActivity.FcoralL4--;


                if (last.equals("BL1"))
                    MainActivity.BcoralL1--;
                if (last.equals("BL2"))
                    MainActivity.BcoralL2--;
                if (last.equals("BL3"))
                    MainActivity.BcoralL3--;
                if (last.equals("BL4"))
                    MainActivity.BcoralL4--;


                if (last.equals("deepBarge"))
                    MainActivity.deepBarge--;
                if (last.equals("shallowBarge"))
                    MainActivity.shallowBarge--;


                if (last.equals("source"))
                    MainActivity.source--;
                if (last.equals("processor"))
                    MainActivity.processor--;
            }
            if (dataType.equals("algaeRemoved"))
                MainActivity.algaeRemoved++;
            if (dataType.equals("algaeFloor"))
                MainActivity.algaeFloor++;
            if (dataType.equals("netShot"))
                MainActivity.netShot++;


            if (dataType.equals("netMiss"))
                MainActivity.netMiss++;
            if (dataType.equals("processorMiss"))
                MainActivity.processorMiss++;


            if (dataType.equals("FL1"))
                MainActivity.FcoralL1++;
            if (dataType.equals("FL2"))
                MainActivity.FcoralL2++;
            if (dataType.equals("FL3"))
                MainActivity.FcoralL3++;
            if (dataType.equals("FL4"))
                MainActivity.FcoralL4++;


            if (dataType.equals("BL1"))
                MainActivity.BcoralL1++;
            if (dataType.equals("BL2"))
                MainActivity.BcoralL2++;
            if (dataType.equals("BL3"))
                MainActivity.BcoralL3++;
            if (dataType.equals("BL4"))
                MainActivity.BcoralL4++;


            if (dataType.equals("deepBarge"))
                MainActivity.deepBarge++;
            if (dataType.equals("shallowBarge"))
                MainActivity.shallowBarge++;


            if (dataType.equals("source"))
                MainActivity.source++;
            if (dataType.equals("processor"))
                MainActivity.processor++;
        } else {
            if (dataType.equals("undo") && !MainActivity.added.isEmpty()) {
                MainActivity.added.remove(MainActivity.added.size() - 1);
                if (last.equals("algaeRemoved"))
                    MainActivity.AalgaeRemoved--;
                if (last.equals("algaeFloor"))
                    MainActivity.AalgaeFloor--;
                if (last.equals("netShot"))
                    MainActivity.AnetShot--;

                if (last.equals("FL1"))
                    MainActivity.AFcoralL1--;
                if (last.equals("FL2"))
                    MainActivity.AFcoralL2--;
                if (last.equals("FL3"))
                    MainActivity.AFcoralL3--;
                if (last.equals("FL4"))
                    MainActivity.AFcoralL4--;


                if (last.equals("BL1"))
                    MainActivity.ABcoralL1--;
                if (last.equals("BL2"))
                    MainActivity.ABcoralL2--;
                if (last.equals("BL3"))
                    MainActivity.ABcoralL3--;
                if (last.equals("BL4"))
                    MainActivity.ABcoralL4--;

                if (last.equals("processor"))
                    MainActivity.Aprocessor--;
            }
            if (dataType.equals("algaeRemoved"))
                MainActivity.AalgaeRemoved++;
            if (dataType.equals("algaeFloor"))
                MainActivity.AalgaeFloor++;
            if (dataType.equals("netShot"))
                MainActivity.AnetShot++;


            if (dataType.equals("FL1"))
                MainActivity.AFcoralL1++;
            if (dataType.equals("FL2"))
                MainActivity.AFcoralL2++;
            if (dataType.equals("FL3"))
                MainActivity.AFcoralL3++;
            if (dataType.equals("FL4"))
                MainActivity.AFcoralL4++;


            if (dataType.equals("BL1"))
                MainActivity.ABcoralL1++;
            if (dataType.equals("BL2"))
                MainActivity.ABcoralL2++;
            if (dataType.equals("BL3"))
                MainActivity.ABcoralL3++;
            if (dataType.equals("BL4"))
                MainActivity.ABcoralL4++;


            if (dataType.equals("processor"))
                MainActivity.Aprocessor++;
        }
        System.out.println(MainActivity.added);
    }

    /**
     * Resumes the timer from where it left off.
     */

    // Method to handle climb timing and store the result in the respective variable
    private void handleClimbTimer(String climbType) {
        if (climbType.equals("shallowBarge")) {
            if (shallowStartTime == 0) {
                // Start the shallow climb timer
                shallowStartTime = System.currentTimeMillis();
                Log.d("ClimbTimer", "Shallow climb timer started");
            } else {
                // Stop the shallow climb timer and calculate elapsed time
                long elapsedTime = System.currentTimeMillis() - shallowStartTime;
                shallowStartTime = 0; // Reset for the next shallow climb
                MainActivity.shallowBarge += (int) elapsedTime; // Store elapsed time as total
                Log.d("ClimbTimer", "Shallow climb elapsed time: " + elapsedTime + " ms");
                System.out.println(MainActivity.shallowBarge);
            }
        } else if (climbType.equals("deepBarge")) {
            if (deepStartTime == 0) {
                // Start the deep climb timer
                deepStartTime = System.currentTimeMillis();
                Log.d("ClimbTimer", "Deep climb timer started");
            } else {
                // Stop the deep climb timer and calculate elapsed time
                long elapsedTime = System.currentTimeMillis() - deepStartTime;
                deepStartTime = 0; // Reset for the next deep climb
                MainActivity.deepBarge += (int) elapsedTime; // Store elapsed time as total
                Log.d("ClimbTimer", "Deep climb elapsed time: " + elapsedTime + " ms");
                System.out.println(MainActivity.deepBarge);
            }
        }
    }

    // Method to combine flashing and climb timing
    public void flashWithTimer(int col, String climbType, int el) {
        this.setOnClickEvent(n -> {
            // Handle the climb timer logic
            handleClimbTimer(climbType);


            // Perform flashing logic
            myButton.setZ(el);
            myButton.setBackgroundColor(col);
            myButton.setTranslationZ(el);


            // Flashing effect logic
            flasher = 0;
            int interval = 150; // Flash interval in milliseconds
            Handler handler = new Handler();
            Runnable runnable2 = new Runnable() {
                @Override
                public void run() {
                    flasher++;
                    if (flasher == 2) {
                        flasher = 0;
                        handler.removeCallbacks(this);
                        myButton.setBackgroundColor(color);
                        myButton.setTranslationZ(el);
                    } else {
                        handler.postDelayed(this, interval);
                    }
                }
            };
            handler.post(runnable2);
        });
    }


}


class ButCir {
    public Button myButton;
    GradientDrawable background;
    private final float xPos;
    private final float yPos;
    private final float radius;


    public ButCir(float xp, float yp, float r, int color, Context context, FrameLayout layout) {
        xPos = xp;
        yPos = yp;
        radius = r;
        myButton = new Button(context);
        myButton.setX(xPos);
        myButton.setY(yPos);
        // Set layout parameters for the button
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                (int) (2 * radius),  // Width = diameter
                (int) (2 * radius)   // Height = diameter
        );
        myButton.setLayoutParams(params);
        // Create a circular background
        background = new GradientDrawable();
        background.setShape(GradientDrawable.OVAL); // Makes the background circular
        background.setColor(color); // Sets the background color
        myButton.setBackground(background);
        layout.addView(myButton);
    }


    public void setOnClickEvent(View.OnClickListener listener) {
        myButton.setOnClickListener(listener);
    }


    public void setColor(int color) {
        // Update the button's background color dynamically
        GradientDrawable background = (GradientDrawable) myButton.getBackground();
        background.setColor(color);
    }


    public void remove(FrameLayout layout) {
        layout.removeView(myButton);
    }
}


class Rectangle {
    public View rectangleView;


    public Rectangle(float xPos, float yPos, float width, float height, Context context, FrameLayout layout) {
        // Create the rectangle view
        rectangleView = new View(context); //fuck younah ur short gay faggy twig


        // Set position, size, and background color
        rectangleView.setX(xPos);
        rectangleView.setY(yPos);
        rectangleView.setBackgroundColor(Color.BLACK); // Black rectangle
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) width, (int) height);
        rectangleView.setLayoutParams(params);


        // Add the rectangle to the layout
        layout.addView(rectangleView);
    }


    // Remove the rectangle from the layout
    public void remove(FrameLayout layout) {
        layout.removeView(rectangleView);
    }


    public void setZ(float zIndex) {
        rectangleView.setZ(zIndex);
    }


    public void add(FrameLayout layout) {
        layout.addView(rectangleView);
    }


    public void setOpacity(float alpha) {
        rectangleView.setAlpha(alpha); // Alpha value: 0.0f to 1.0f
    }
}


class Texter {
    private final TextView text;


    public Texter(int col, String t, int xPos, int yPos, int size, Typeface font, Context c, FrameLayout fl) {
        text = new TextView(c);
        text.setText(t);
        text.setTextColor(col);
        text.setX(xPos);
        text.setY(yPos);
        text.setTextSize(size);
        text.setTypeface(font);
        fl.addView(text);
    }


    public void setElevation(int l) {
        text.setElevation(l);
    }


    public void remove(FrameLayout layout) {
        layout.removeView(text);
    }
}


class inputText {
    public int n;
    TextView textBar;


    public inputText(int size, int xp, int yp, int w, int h, int co, String text, FrameLayout layout, Context c) {
        textBar = new TextView(c);
        textBar.setText("Click to Enter Text");
        textBar.setX(xp);
        textBar.setY(yp);
        textBar.setText(text);
        textBar.setTextColor(co);
        textBar.setTextSize(size);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(w, h);
        params.leftMargin = xp;
        params.topMargin = yp;
        textBar.setLayoutParams(params);


        boolean y = false;
        if (text.equalsIgnoreCase("Team number")) {
            n = 2;
            y = true;
        } else if (text.equalsIgnoreCase("Match Number")) {
            n = 3;
            y = true;
        }


        if (y) {
            textBar.setOnClickListener(v -> {
                // Create an EditText for input
                EditText input = new EditText(c);
                input.setInputType(InputType.TYPE_CLASS_NUMBER); // Restricts input to numbers only


                // Create an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Enter a Number")
                        .setView(input)
                        .setPositiveButton("OK", (dialog, which) -> {
                            String userInputString = input.getText().toString().trim();


                            if (userInputString.isEmpty()) {
                                textBar.setText("Invalid Input: Empty");
                                Toast.makeText(c, "Error: Input cannot be empty!", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            try {
                                int userInput = Integer.parseInt(userInputString);


                                if (n == 2) {
                                    MainActivity.teamNumber = userInput;
                                } else if (n == 3) {
                                    MainActivity.matchNumber = userInput;
                                }


                                textBar.setText(String.valueOf(userInput)); // Update text with valid input
                                Toast.makeText(c, "Value updated", Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                textBar.setText("Invalid Input");
                                Toast.makeText(c, "Error: Please enter a valid number!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        }
        layout.addView(textBar);
    }

    public void editText(String s){
            textBar.setText("Match Num: " + s);
    }

    public void setTypeface(Typeface x, int y){
        textBar.setTypeface(x,y);
    }
}