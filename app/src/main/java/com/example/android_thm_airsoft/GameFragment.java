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

    public GameFragment() {
        // Required empty public constructor
    }
    private TextView gameTextViewTimer, bombTextViewTimer;
    private  TextView textString;
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



    // declare timers
    private CountDownTimer gameCountDownTimer;
    private CountDownTimer bombCountDownTimer;
    private boolean gameTimerRunning;
    private boolean bombTimerRunning;
    private static final long START_GAME_TIME_IN_MILLIS = 200000;
    private static final long START_BOMB_TIME_IN_MILLIS = 30000;
    private long gameTimeLeftInMillis = START_GAME_TIME_IN_MILLIS;
    private long bombTimeLeftInMillis = START_BOMB_TIME_IN_MILLIS;
    private String gameTimeLeftStr="";
    private String bombTimeLeftStr="";


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
        TextView winnerText = (TextView) view.findViewById(R.id.lblCurrentGameTime);
        winnerText.setText("WTF");

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        // restore values on startup
        // ((MainActivity)getActivity()).restoreSettings();
       // restoreSettings();
        // get values from viewModel


// BUTTON START TIMER
        Button btnStartTimer =(Button) view.findViewById(R.id.btnStartTimer);
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start game timer
                if (gameTimerRunning) {
                    pauseGameTimer();
                    pauseBombTimer();

                } else {
                    startGameTimer();
                    startBombTimer();
                }
                // fragment.setNewText("Your Text");
                // code here without if
                // gameFragmentObj.setNewText("Hello world");


            }
        });

// BUTTON ARM DEFUSE
        Button btnArmDefuse = (Button) view.findViewById(R.id.btnArmDefuse);
        btnArmDefuse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(getContext(),"ARMED",Toast.LENGTH_SHORT).show();
                restoreSettings();

            }
        });

// BUTTON END GAME
        Button btnEndGame = (Button) view.findViewById(R.id.btnEndGame);
        btnEndGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(),"END GAME",Toast.LENGTH_SHORT).show();

                // cancel "pause" and reset both timer on end game
                if (gameTimerRunning) {
                    pauseGameTimer();
                    resetGameTimer();

                    pauseBombTimer();
                    resetBombTimer();
                }

                // Show setup fragment
               SetupFragment setupFrag= new SetupFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.game_fragment_id, setupFrag, "game_fragment_id")
                        .addToBackStack(null)
                        .commit();

            }
        });


        return view;

    }




    public String currentGameTime;
    public String currentBombTime;
    public TextView lblCurrentGameTimeTxt;
    public Button btnPlant;


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("GameFragment", "onViewCreated");
        restoreSettings();

    }


    // GAME TIMER - START
    private void startGameTimer() {
        // timer config
         int gameTimeInt= Integer.parseInt(GameTime);
        gameTimeLeftInMillis=gameTimeInt*60000;


        gameCountDownTimer = new CountDownTimer(gameTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameTimeLeftInMillis = millisUntilFinished;
                Log.d("startGameTimer", "Timer Value:"+gameTimeLeftInMillis);
                updateGameCountDownText();

            }

            @Override
            public void onFinish() {
                gameTimerRunning = false;
                gameTimeLeftInMillis = 0;
                Log.d("startGameTimer", "Timer FINISHED");
                updateGameCountDownText();
                // mButtonStartPause.setText("Start");
                //mButtonStartPause.setVisibility(View.INVISIBLE);
                //mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        Log.d("startGameTimer", "Timer STARTED");
        gameTimerRunning = true;
        //mButtonStartPause.setText("pause");
        //mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseGameTimer() {
        gameCountDownTimer.cancel();
        gameTimerRunning = false;
        Log.d("pauseGameTimer", "Timer PAUSED");
        //mButtonStartPause.setText("Start");
        // mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetGameTimer() {
        gameTimeLeftInMillis = START_GAME_TIME_IN_MILLIS;
        // updateGameCountDownText();
        //mButtonReset.setVisibility(View.INVISIBLE);
        // mButtonStartPause.setVisibility(View.VISIBLE);
        Log.d("resetGameTimer", "Timer RESET");
    }
    // GAME TIMER - END

    // BOMB TIMER - START
    private void startBombTimer() {
        // timer config
        int gameTimeInt= Integer.parseInt(BombTime.toString());
        bombTimeLeftInMillis=gameTimeInt*60000;

        bombCountDownTimer = new CountDownTimer(bombTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bombTimeLeftInMillis = millisUntilFinished;
                Log.d("startBombTimer", "Timer Value:"+bombTimeLeftInMillis);
                updateGameCountDownText();

            }

            @Override
            public void onFinish() {
                bombTimerRunning = false;
                bombTimeLeftInMillis = 0;
                Log.d("startBombTimer", "Timer FINISHED");
                updateGameCountDownText();
                // mButtonStartPause.setText("Start");
                //mButtonStartPause.setVisibility(View.INVISIBLE);
                //mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        Log.d("startBombTimer", "Timer STARTED");
        bombTimerRunning = true;
        //mButtonStartPause.setText("pause");
        //mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseBombTimer() {
        bombCountDownTimer.cancel();
        bombTimerRunning = false;
        Log.d("pauseBombTimer", "Timer PAUSED");
        //mButtonStartPause.setText("Start");
        // mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetBombTimer() {
        bombTimeLeftInMillis = START_BOMB_TIME_IN_MILLIS;
        // updateGameCountDownText();
        //mButtonReset.setVisibility(View.INVISIBLE);
        // mButtonStartPause.setVisibility(View.VISIBLE);
        Log.d("resetBombTimer", "Timer RESET");
    }
    // BOMB TIMER - END


    public void setFragTextView(String text, int R_id_resource){
        TextView FcTextView = (TextView) getView().findViewById(R_id_resource);
        FcTextView.setText(text);
    }


    private void updateGameCountDownText() {
     /*   int minutes = (int) (gameTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (gameTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
*/

        // format game timer
        int gameTmrMinutes = (int) (gameTimeLeftInMillis / 1000) / 60;
        int gameTmrSeconds = (int) (gameTimeLeftInMillis / 1000) % 60;

        String gameTimeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", gameTmrMinutes, gameTmrSeconds);

        // format bomb timer
        int bombTmrMinutes = (int) (bombTimeLeftInMillis / 1000) / 60;
        int bombTmrSeconds = (int) (bombTimeLeftInMillis / 1000) % 60;

        String bombTimeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", bombTmrMinutes, bombTmrSeconds);

        // finish
        gameTimeLeftStr=gameTimeLeftFormatted;
        bombTimeLeftStr=bombTimeLeftFormatted;

        // update game time
        setFragTextView(gameTimeLeftStr,R.id.lblCurrentGameTime);
        // update bomb time
        setFragTextView(bombTimeLeftStr,R.id.lblCurrentBombTime);

    }

    public void changeFragmentTextView(String gameTime, String bombTime) {

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        // set current times to share prefs
        viewModel.setCurrentGameTime(gameTime);
        viewModel.setCurrentBombTime(bombTime);

    }


    public void bombArmDisarm (boolean bombArmed, String pinEnterCodeStr){

        int pinCodeEntInt = Integer.parseInt(pinEnterCodeStr.toString()); // entered pin code
        int pinCodeArmReqInt = Integer.parseInt(pinEnterCodeStr.toString());
        int pinCodeDisarmReqInt = Integer.parseInt(pinEnterCodeStr.toString());

        if (bombArmed && pinCodeEntInt==pinCodeEntInt) {
            Log.d("bombArmDisarm", "Bomb DISARM");

       /*     pauseBombTimer();
            resetBombTimer();*/
        }
        else if (!bombArmed && pinCodeEntInt==pinCodeEntInt){

            Log.d("bombArmDisarm", "Bomb ARMED");

        }
        else{

            Log.d("bombArmDisarm", "UNDEFINED STATE");
        }

    }

    private ItemViewModel viewModel;
    public String GameTime;
    public String BombTime;
    public String ArmPinCode;
    public String DisarmPinCode;

    public void restoreSettings()
    {
        Log.d("GameFragment", "restoreSettings START");

        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        Log.d("GameFragment", "restoreSettings Getting values from SharedPrefs ...");

        String SpGameTimerVal = sh.getString("GameTimerStrValue", "");
        String SpBombTimerVal = sh.getString("BombTimerStrValue", "");
        String SpArmPinCodeVal = sh.getString("ArmPinCodeStrValue", "");
        String SpDisarmPinCodeVal = sh.getString("DisarmPinCodeStrValue", "");
        Log.d("GameFragment", "restoreSettings Passing values to Fragment with viewModel ...");

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        viewModel.setFragGameTime(SpGameTimerVal);
        viewModel.setFragBombTime(SpBombTimerVal);
        viewModel.setFragArmPinCode(SpArmPinCodeVal);
        viewModel.setFragDisarmPinCode(SpDisarmPinCodeVal);
        viewModel.setFragWinnerName("DEFENDERS");
        Log.d("GameFragment", "restoreSettings viewModel setValues DONE");

        GameTime=viewModel.getFragGameTime().getValue();
        BombTime=viewModel.getFragBombTime().getValue();
        ArmPinCode=viewModel.getFragArmPinCode().getValue();
        DisarmPinCode=viewModel.getFragDisarmPinCode().getValue();
        Log.d("GameFragment", "restoreSettings viewModel getValues DONE "+GameTime+"-"+BombTime+"-"+ArmPinCode+"-"+DisarmPinCode);
    }

}