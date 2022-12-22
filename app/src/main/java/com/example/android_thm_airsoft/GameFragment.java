package com.example.android_thm_airsoft;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // declare timers
    private CountDownTimer gameCountDownTimer;
    private CountDownTimer bombCountDownTimer;
    private boolean GameTimerRunning;
    private boolean BombTimerRunning;
    private static final long START_GAME_TIME_IN_MILLIS = 22222;
    private static final long START_BOMB_TIME_IN_MILLIS = 11111;
    private long GameTimeLeftInMillis = START_GAME_TIME_IN_MILLIS;
    private long BombTimeLeftInMillis = START_BOMB_TIME_IN_MILLIS;
    private String GameTimeLeft = "";
    private String BombTimeLeft = "";
    private String WinnerName = "";

    // declare booleans
    private boolean StateBombArmed = false;
    private boolean GameStarted = false;
    private boolean BombTimerFinished = false;
    private boolean GameTimerFinished = false;
    private boolean GameFinished = false;

    // declare buttons
    private Button BtnArmDisarmBtn;

    // declare TextView's
    private TextView TxtEnteredPinCode, LblGameStatusArmDisarmed, LblGameTime, LblBombTime;


    // View model variables
    private ItemViewModel viewModel;
    public String GameTime;
    public String BombTime;
    public String ArmPinCode;
    public String DisarmPinCode;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Toast.makeText(getContext(),"GAME FRAGMENT",Toast.LENGTH_SHORT).show();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_game,
                container, false);


        // update UI EditText with values from viewModel
      /*  LblGameTime = (TextView) view.findViewById(R.id.lblCurrentGameTime);
        LblGameTime.setText(GameTime);
        LblBombTime = (TextView) view.findViewById(R.id.lblCurrentBombTime);
        LblBombTime.setText(BombTime);*/


        TxtEnteredPinCode = (TextView) view.findViewById(R.id.txtGamePinCode);
        LblGameStatusArmDisarmed = (TextView) view.findViewById(R.id.lblGameStatus);
        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        // restore values on startup
        // ((MainActivity)getActivity()).restoreSettings();
        // restoreSettings();
        // get values from viewModel
// BUTTON START TIMER
        Button btnDebug = (Button) view.findViewById(R.id.btnDebugAttackerWin);
        btnDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start game timer
                GameFinished = true;
                WinnerName = "DEBUG WIN";
                UpdateGameStatus();
            } // end onClick
        });

// BUTTON START TIMER
/*        Button btnStartTimer = (Button) view.findViewById(R.id.btnStartTimer);
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start game timer
                if (GameTimerRunning) {
                    pauseGameTimer();
                    pauseBombTimer();
                } else {
                    startGameTimer();

                } // end if

            } // end onClick
        });*/

// BUTTON ARM DEFUSE
        Button btnArmDefuse = (Button) view.findViewById(R.id.btnArmDefuse);
        BtnArmDisarmBtn = (Button) view.findViewById(R.id.btnArmDefuse);
        btnArmDefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getContext(),"ARMED",Toast.LENGTH_SHORT).show();
                // restoreSettings();
                String txtEnteredPin = TxtEnteredPinCode.getText().toString();
                if (txtEnteredPin != null && !txtEnteredPin.isEmpty()) {
                    Log.d("startGameTimer", "btnArmDefuse if - STRING VALUE = " + txtEnteredPin);
                } else {
                    txtEnteredPin = "0";
                    Log.d("startGameTimer", "btnArmDefuse else - STRING VALUE = " + txtEnteredPin);
                }

                BombArmDisarm(StateBombArmed, txtEnteredPin);

            }
        });

// BUTTON END GAME
        Button btnEndGame = (Button) view.findViewById(R.id.btnEndGame);
        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "END GAME", Toast.LENGTH_SHORT).show();

                // cancel "pause" and reset both timer on end game
                if (GameTimerRunning) {
                    pauseGameTimer();
                    resetGameTimer();

                    pauseBombTimer();
                    resetBombTimer();
                }

                // Show setup fragment
                SetupFragment setupFrag = new SetupFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.game_fragment_id, setupFrag, "game_fragment_id")
                        .addToBackStack(null)
                        .commit();

            }
        });


        return view;

    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("GameFragment", "onViewCreated");
        restoreSettings();
        StartGameOnOpen();

        // get timer values on startup, bomb time startup fix because is not started
        int gameTimeInt = Integer.parseInt(GameTime);
        GameTimeLeftInMillis = gameTimeInt * 60000L;

        int bombTimeInt = Integer.parseInt(BombTime);
        BombTimeLeftInMillis = bombTimeInt * 60000L;

        UpdateGameCountDownText();
    }


    private void StartGameOnOpen() {
        if (GameTimerRunning) {
            pauseGameTimer();
            pauseBombTimer();

        } else {
            startGameTimer();
            // startBombTimer();
        }


    }

    // GAME TIMER - START
    private void startGameTimer() {
        // timer config
        int gameTimeInt = Integer.parseInt(GameTime);
        GameTimeLeftInMillis = gameTimeInt * 6000L;// CHANGE BACK TO 60000 when finished testing


        gameCountDownTimer = new CountDownTimer(GameTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                GameTimeLeftInMillis = millisUntilFinished;
                Log.d("startGameTimer", "Timer Value:" + GameTimeLeftInMillis);
                UpdateGameCountDownText();

            }

            @Override
            public void onFinish() {
                GameTimerRunning = false;
                GameTimerFinished = true;
                GameTimeLeftInMillis = 0;
                GameFinished = true;
                Log.d("startGameTimer", "Timer FINISHED");
                UpdateGameCountDownText();
                UpdateGameStatus();
                // mButtonStartPause.setText("Start");
                //mButtonStartPause.setVisibility(View.INVISIBLE);
                //mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        Log.d("startGameTimer", "Timer STARTED");
        GameTimerRunning = true;
        //mButtonStartPause.setText("pause");
        //mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseGameTimer() {
        if (GameTimerRunning) {
            gameCountDownTimer.cancel();
            GameTimerRunning = false;
            GameTimerFinished = false;
            Log.d("pauseGameTimer", "Timer PAUSED");
        }

        //mButtonStartPause.setText("Start");
        // mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetGameTimer() {
        GameTimeLeftInMillis = START_GAME_TIME_IN_MILLIS;
        // updateGameCountDownText();
        //mButtonReset.setVisibility(View.INVISIBLE);
        // mButtonStartPause.setVisibility(View.VISIBLE);
        Log.d("resetGameTimer", "Timer RESET");
    }
    // GAME TIMER - END

    // BOMB TIMER - START
    private void startBombTimer() {
        // timer config
        int bombTimeInt = Integer.parseInt(BombTime.toString());
        BombTimeLeftInMillis = bombTimeInt * 6000L; // CHANGE BACK TO 60000 when finished testing

        bombCountDownTimer = new CountDownTimer(BombTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                BombTimeLeftInMillis = millisUntilFinished;
                Log.d("startBombTimer", "Timer Value:" + BombTimeLeftInMillis);
                UpdateGameCountDownText();

            }

            @Override
            public void onFinish() {
                BombTimerRunning = false;
                BombTimeLeftInMillis = 0;
                BombTimerFinished = true;
                GameFinished = true;
                Log.d("startBombTimer", "Timer FINISHED");
                UpdateGameCountDownText();
                UpdateGameStatus();
                // mButtonStartPause.setText("Start");
                //mButtonStartPause.setVisibility(View.INVISIBLE);
                //mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        Log.d("startBombTimer", "Timer STARTED");
        BombTimerRunning = true;
        //mButtonStartPause.setText("pause");
        //mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseBombTimer() {
        if (BombTimerRunning) {
            bombCountDownTimer.cancel();
            BombTimerRunning = false;
            BombTimerFinished = false;
            Log.d("pauseBombTimer", "Timer PAUSED");
        }

        //mButtonStartPause.setText("Start");
        // mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetBombTimer() {
        // when bomb time is reset, get init value for display
        int bombTimeInt = Integer.parseInt(BombTime);
        BombTimeLeftInMillis = bombTimeInt * 60000L;
        // BombTimeLeftInMillis = START_BOMB_TIME_IN_MILLIS;
        // updateGameCountDownText();
        //mButtonReset.setVisibility(View.INVISIBLE);
        // mButtonStartPause.setVisibility(View.VISIBLE);
        Log.d("resetBombTimer", "Timer RESET");
    }
    // BOMB TIMER - END


    public void SetFragTextView(String text, int r_id_resource) {
        Log.d("SetFragTextView", text + "-" + r_id_resource);
        TextView FcTextView = (TextView) getView().findViewById(r_id_resource);
        FcTextView.setText(text);
    }


    private void UpdateGameCountDownText() {
        // Log.d("UpdateGameCountDownText", "Format game timer");
        // format game timer
        int gameTmrMinutes = (int) (GameTimeLeftInMillis / 1000) / 60;
        int gameTmrSeconds = (int) (GameTimeLeftInMillis / 1000) % 60;

        String gameTimeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", gameTmrMinutes, gameTmrSeconds);
        // Log.d("UpdateGameCountDownText", "Format bomb timer");
        // format bomb timer
        int bombTmrMinutes = (int) (BombTimeLeftInMillis / 1000) / 60;
        int bombTmrSeconds = (int) (BombTimeLeftInMillis / 1000) % 60;

        String bombTimeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", bombTmrMinutes, bombTmrSeconds);

        //  Log.d("UpdateGameCountDownText", "Format strings to global");
        // timer strings ready
        GameTimeLeft = gameTimeLeftFormatted;
        BombTimeLeft = bombTimeLeftFormatted;

        Log.d("UpdateGameCountDownText", "SetFragTextView UI with timer values " + GameTimeLeft + "-" + BombTimeLeft);
        // update game and bomb time
        SetFragTextView(GameTimeLeft, R.id.lblCurrentGameTime);
        SetFragTextView(BombTimeLeft, R.id.lblCurrentBombTime);

    }

    public void BombArmDisarm(boolean isBombArmed, String pinEnterCodeStr) {

        int pinCodeEntInt = Integer.parseInt(pinEnterCodeStr.toString()); // entered pin code
        int pinCodeArmReqInt = Integer.parseInt(ArmPinCode.toString());
        int pinCodeDisarmReqInt = Integer.parseInt(DisarmPinCode.toString());

        if (isBombArmed && pinCodeEntInt == pinCodeDisarmReqInt) {

            pauseBombTimer();
            resetBombTimer();   // disarmed, reset timer
            StateBombArmed = false; // bomb disarmed

            TxtEnteredPinCode.setText(""); // clear entered code
            BtnArmDisarmBtn.setText("ARM");
            LblGameStatusArmDisarmed.setText("BOMB DISARMED");
            UpdateGameStatus();
            Log.d("bombArmDisarm", "Bomb DISARMED." + pinCodeEntInt + "-" + pinCodeDisarmReqInt + "-" + StateBombArmed);
            //  Toast.makeText(getContext(), "Bomb DISARMED", Toast.LENGTH_SHORT).show();

        } else if (!isBombArmed && pinCodeEntInt == pinCodeArmReqInt) {
            startBombTimer();   // armed, start timer
            StateBombArmed = true;      // bomb is armed

            TxtEnteredPinCode.setText(""); // clear entered code
            BtnArmDisarmBtn.setText("DISARM");
            LblGameStatusArmDisarmed.setText("!!! BOMB ARMED !!!");
            UpdateGameStatus();
            Log.d("bombArmDisarm", "Bomb ARMED." + "pinCodeEntInt." + pinCodeEntInt + "-pinCodeDisarmReqInt:"
                    + pinCodeDisarmReqInt + "-StateBombArmed=" + StateBombArmed);
            //  Toast.makeText(getContext(), "Bomb ARMED", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("bombArmDisarm", "WRONG PIN" + pinCodeEntInt + "-" + pinCodeDisarmReqInt + "-" + StateBombArmed);
            TxtEnteredPinCode.setText(""); // clear entered code
            Toast.makeText(getContext(), "WRONG PIN", Toast.LENGTH_SHORT).show();

        }

    }


    public void restoreSettings() {
        // Log.d("GameFragment", "restoreSettings START");

        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        //  Log.d("GameFragment", "restoreSettings Getting values from SharedPrefs ...");

        String SpGameTimerVal = sh.getString("GameTimerStrValue", "10");
        String SpBombTimerVal = sh.getString("BombTimerStrValue", "5");
        String SpArmPinCodeVal = sh.getString("ArmPinCodeStrValue", "1234");
        String SpDisarmPinCodeVal = sh.getString("DisarmPinCodeStrValue", "5678");
        //  Log.d("GameFragment", "restoreSettings Passing values to Fragment with viewModel ...");

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        viewModel.setFragGameTime(SpGameTimerVal);
        viewModel.setFragBombTime(SpBombTimerVal);
        viewModel.setFragArmPinCode(SpArmPinCodeVal);
        viewModel.setFragDisarmPinCode(SpDisarmPinCodeVal);
        viewModel.setFragWinnerName(WinnerName); // global
        //   Log.d("GameFragment", "restoreSettings viewModel setValues DONE");

        GameTime = viewModel.getFragGameTime().getValue();
        BombTime = viewModel.getFragBombTime().getValue();
        ArmPinCode = viewModel.getFragArmPinCode().getValue();
        DisarmPinCode = viewModel.getFragDisarmPinCode().getValue();
        Log.d("GameFragment", "restoreSettings viewModel getValues DONE " + GameTime + "-" + BombTime + "-" + ArmPinCode + "-" + DisarmPinCode);
    }

    public void UpdateGameStatus() { //String btnArmValue, String lblGameStatusValue

        if (StateBombArmed) {
            TxtEnteredPinCode.setText(""); // clear entered code
            LblGameStatusArmDisarmed.setTextAppearance(getContext(), R.style.ArmedText);
        } else {
            TxtEnteredPinCode.setText(""); // clear entered code
            LblGameStatusArmDisarmed.setTextAppearance(getContext(), R.style.DisarmedText);
        }
        UpdateGameCountDownText(); // calling this updates UI immediate after state changed, not waiting for timer tick
        BlinkGameStatus(StateBombArmed);

        // is timer finished, set who won
        if (GameTimerFinished) {
            WinnerName = "Defenders WIN";

            GameFinished = true;
        } else if (BombTimerFinished) {
            WinnerName = "Attackers WIN";
            GameFinished = true;
        } else {
            WinnerName = "DRAW";
            //  GameFinished=true;
        }

        // if game ended show winner name
        if (GameFinished) {
            pauseGameTimer();
            pauseBombTimer();
            resetGameTimer();
            resetBombTimer();

            GameFinished = false;

            viewModel = new ViewModelProvider(getActivity()).get(ItemViewModel.class);
            viewModel.setFragWinnerName(WinnerName);
            VictoryFragment victoryFrag = new VictoryFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.game_fragment_id, victoryFrag, "game_fragment_id")
                    .addToBackStack(null)
                    .commit();
        }

    }

    public void BlinkGameStatus(boolean isArmed) {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);

        if (isArmed) {
            anim.setDuration(150); //You can manage the blinking time with this parameter
            anim.setStartOffset(120);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            LblGameStatusArmDisarmed.startAnimation(anim);
            Log.d("GameFragment", "BlinkGameStatus BLINK ON");
        } else {
            LblGameStatusArmDisarmed.clearAnimation();

            Log.d("GameFragment", "BlinkGameStatus BLINK OFF");
        }


    }

}