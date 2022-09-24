package com.example.android_thm_airsoft;

import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Public variables
boolean setupScreen=false;

// share data between activity and fragment
/*private ItemViewModel viewModel;*/
    private ItemViewModel viewModel;
    private EditText GameTimerEditText, BombTimerEditText,ArmPinCodeEditText,DisarmPinCodeEditText;
    private int GameTimerIntValue,BombTimerIntValue,ArmPinCodeIntValue,DisarmPinCodeIntValue;
    private String GameTimerStrValue,BombTimerStrValue,ArmPinCodeStrValue,DisarmPinCodeStrValue;
   // private TextView gameTextViewTimer, bombTextViewTimer;

    // declare timers
    private CountDownTimer gameCountDownTimer;
    private CountDownTimer bombCountDownTimer;
    private boolean gameTimerRunning;
    private boolean bombTimerRunning;
    private static final long START_GAME_TIME_IN_MILLIS = 600000;
    private static final long START_BOMB_TIME_IN_MILLIS = 100000;
    private long gameTimeLeftInMillis = START_GAME_TIME_IN_MILLIS;
    private long bombTimeLeftInMillis = START_BOMB_TIME_IN_MILLIS;
    private String gameTimeLeftStr="";
    private String bombTimeLeftStr="";

    // Instance of our fragment
    GameFragment gameFragmentObj = new GameFragment();
    private GameFragment fragment; //MyFrag is your fragmemt

public void saveData()
{
    Log.d("saveData", "START");
    // define global textboxes for tags1
    GameTimerEditText=(EditText)findViewById(R.id.txtGameTime);
    BombTimerEditText=(EditText)findViewById(R.id.txtBombTime);
    ArmPinCodeEditText=(EditText)findViewById(R.id.txtArmPinCode);
    DisarmPinCodeEditText=(EditText)findViewById(R.id.txtDisarmPinCode);


    // Creating a shared pref object
    // with a file name "MySharedPref"1
    // in private mode
    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
    SharedPreferences.Editor mySettings = sharedPreferences.edit();

    Log.d("saveData", "GET TEXT FROM EDITTEXT");
    GameTimerStrValue=GameTimerEditText.getText().toString();
    BombTimerStrValue=BombTimerEditText.getText().toString();
    ArmPinCodeStrValue=ArmPinCodeEditText.getText().toString();
    DisarmPinCodeStrValue=DisarmPinCodeEditText.getText().toString();

    Log.d("saveData", "SAVING DATA ...");
    // write all the data entered by the user in SharedPreference and apply
    mySettings.putString("GameTimerStrValue", GameTimerStrValue);
    mySettings.putString("BombTimerStrValue", BombTimerStrValue);
    mySettings.putString("ArmPinCodeStrValue", ArmPinCodeStrValue);
    mySettings.putString("DisarmPinCodeStrValue", DisarmPinCodeStrValue);

    mySettings.apply();
    Log.d("saveData", "DATA SAVED");

}


    private void restoreSettings()
    {
        Log.d("restoreSettings", "START");

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Log.d("restoreSettings", "Getting values from SharedPrefs ...");

        String SpGameTimerVal = sh.getString("GameTimerStrValue", "");
        String SpBombTimerVal = sh.getString("BombTimerStrValue", "");
        String SpArmPinCodeVal = sh.getString("ArmPinCodeStrValue", "");
        String SpDisarmPinCodeVal = sh.getString("DisarmPinCodeStrValue", "");
        Log.d("restoreSettings", "Passing values to Fragment with viewModel ...");

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        viewModel.setFragGameTime(SpGameTimerVal);
        viewModel.setFragBombTime(SpBombTimerVal);
        viewModel.setFragArmPinCode(SpArmPinCodeVal);
        viewModel.setFragDisarmPinCode(SpDisarmPinCodeVal);
        viewModel.setFragWinnerName("DEFENDERS");
        Log.d("restoreSettings", "DATA RESTORED");

    }

    //ON RESUME MAIN ACTIVITY EVENT
    @Override
    protected void onPause() {
        super.onPause();
        // NOT USED - DELETE LATER
    }

    //ON STOP MAIN ACTIVITY EVENT
    @Override
    public void onStop() {
        super.onStop();
        // NOT USED - DELETE LATER
    }

    // ON CREATE START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate STARTED");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SetupFragment()).commit();
        Log.d("MainActivity", "SetupFragment COMMITTED");
        setupScreen=true;   // active screen flag

        Log.d("MainActivity", "define global editText's");
        // define global textboxes for tags
        GameTimerEditText=findViewById(R.id.txtGameTime);
        BombTimerEditText=findViewById(R.id.txtBombTime);
        ArmPinCodeEditText=findViewById(R.id.txtArmPinCode);
        DisarmPinCodeEditText=findViewById(R.id.txtDisarmPinCode);

      //  gameTextViewTimer = findViewById(R.id.lblCurrentGameTime);
      //  bombTextViewTimer = findViewById(R.id.lblCurrentArmDefuseTime);

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        restoreSettings();

// settings default values on startup
    /*    viewModel.setFragGameTime("60");
        viewModel.setFragBombTime("300");
        viewModel.setFragArmPinCode("1234");
        viewModel.setFragDisarmPinCode("5678");*/
        fragment = (GameFragment) getSupportFragmentManager().findFragmentById(R.id.game_fragment);

        Button btnStartTimer =(Button) findViewById(R.id.btnStarTimer);
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
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



        Button btnOpenGameFragment = (Button) findViewById(R.id.btnStartGameFragment);
        btnOpenGameFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("btnOpenGameFragment", "onClick STARTED");
                if (setupScreen)
                {
                    // change to game fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GameFragment()).commit();
                    btnOpenGameFragment.setText("END GAME");
                    setupScreen=false;
                    GameFragment fragment = (GameFragment)getSupportFragmentManager().findFragmentById(R.id.game_fragment);
                  //  fragment.update("foo");



                    saveData();
                }
                else{
                    // change back to setup fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SetupFragment()).commit();
                    btnOpenGameFragment.setText("START GAME");
                    setupScreen=true;
                    restoreSettings();
                }

            } // end button on click11

        }); // END onClick


        Button btnStartVictoryFragment = (Button) findViewById(R.id.btnStartVictoryFragment);
        btnStartVictoryFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change to Victory fragment
                Log.d("btnStartVictoryFragment", "onClick STARTED");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new VictoryFragment()).commit();

            }
        });


    } // END onCreate

    // GAME TIMER - START
    private void startGameTimer() {
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
                Log.d("startGameTimer", "Timer FINISHED");
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
                Log.d("startBombTimer", "Timer FINISHED");
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
        changeFragmentTextView(gameTimeLeftFormatted,bombTimeLeftFormatted);

    }

    public void changeFragmentTextView(String gameTime, String bombTime) {

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        // set current times to share prefs
        viewModel.setCurrentGameTime(gameTime);
        viewModel.setCurrentBombTime(bombTime);


        // change/update game fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new GameFragment()).commit();

     /*   FragmentTransaction ft = ((AppCompatActivity) this).getSupportFragmentManager().beginTransaction();
        ft.detach(fragment).attach(fragment).commit();*/

    }



} // End MainActivity class