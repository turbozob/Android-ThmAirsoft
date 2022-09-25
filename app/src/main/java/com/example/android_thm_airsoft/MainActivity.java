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
    public String GameTimerStrValue,BombTimerStrValue,ArmPinCodeStrValue,DisarmPinCodeStrValue;



    //ON RESUME MAIN ACTIVITY EVENT
    @Override
    protected void onPause() {
        super.onPause();
        // NOT USED - DELETE LATER
        Log.d("MainActivity", "onPause STARTED");
      //  saveSettings();
    }

    //ON STOP MAIN ACTIVITY EVENT
    @Override
    public void onStop() {
        super.onStop();
        // NOT USED - DELETE LATER
        Log.d("MainActivity", "onStop STARTED");
      //  SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
    }

    //ON STOP MAIN ACTIVITY EVENT
    @Override
    public void onDestroy() {
        super.onDestroy();
        // NOT WORKING!!!
        Log.d("MainActivity", "onDestroy STARTED");
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

       // restoreSettings();

// settings default values on startup
    /*    viewModel.setFragGameTime("60");
        viewModel.setFragBombTime("300");
        viewModel.setFragArmPinCode("1234");
        viewModel.setFragDisarmPinCode("5678");*/

// open setup fragment on startup
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SetupFragment()).commit();

    } // END onCreate

        public void saveSettings()
        {
            Log.d("saveSettings", "START");
            // define global textboxes for tags1
            // use getView(). to get current view otherwise wrong findViewById is used and not current text is used
            //setContentView(R.layout.activity_main);
           // setContentView(R.layout.fragment_setup);
            GameTimerEditText=findViewById(R.id.txtGameTime);
            BombTimerEditText=(EditText)findViewById(R.id.txtBombTime);
            ArmPinCodeEditText=(EditText)findViewById(R.id.txtArmPinCode);
            DisarmPinCodeEditText=(EditText)findViewById(R.id.txtDisarmPinCode);

            // Creating a shared pref object
            // with a file name "MySharedPref"1
            // in private mode
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor mySettings = sharedPreferences.edit();

            Log.d("saveSettings", "GET TEXT FROM EDITTEXT");
            GameTimerStrValue=GameTimerEditText.getText().toString();
            BombTimerStrValue=BombTimerEditText.getText().toString();
            ArmPinCodeStrValue=ArmPinCodeEditText.getText().toString();
            DisarmPinCodeStrValue=DisarmPinCodeEditText.getText().toString();


            Log.d("saveSettings", "SAVING DATA "+GameTimerStrValue+"-"+BombTimerStrValue+"-"+ArmPinCodeStrValue+"-"+DisarmPinCodeStrValue);
            // write all the data entered by the user in SharedPreference and apply
            mySettings.putString("GameTimerStrValue", GameTimerStrValue);
            mySettings.putString("BombTimerStrValue", BombTimerStrValue);
            mySettings.putString("ArmPinCodeStrValue", ArmPinCodeStrValue);
            mySettings.putString("DisarmPinCodeStrValue", DisarmPinCodeStrValue);

            mySettings.apply();
            Log.d("saveSettings", "DATA SAVED");

        }


    public void restoreSettings()
    {
        Log.d("restoreSettings", "START");

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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
} // End MainActivity class