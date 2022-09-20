package com.example.android_thm_airsoft;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    // Public variables
boolean setupScreen=false;

// share data between activity and fragment
/*private ItemViewModel viewModel;*/
    private ItemViewModel viewModel;
    private EditText GameTimerEditText, BombTimerEditText,ArmPinCodeEditText,DisarmPinCodeEditText;
    private int GameTimerIntValue,BombTimerIntValue,ArmPinCodeIntValue,DisarmPinCodeIntValue;
    private String GameTimerStrValue,BombTimerStrValue,ArmPinCodeStrValue,DisarmPinCodeStrValue;


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
    Log.d("saveData", "DATA IS SAVED");

}




    public void loadData()
    {
        setContentView(R.layout.fragment_setup);
        // Fetching the stored data
        // from the SharedPreference
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("GameTimerValue", "");
        String s2 = sh.getString("BombTimerValue", "");
        String s3 = sh.getString("ArmPinCodeValue", "");
        String s4 = sh.getString("DisarmPinCodeValue", "");
      //  int a = sh.getInt("age", 0);

        // Setting the fetched data
        // in the EditTexts

        GameTimerEditText.setText(s1);
        BombTimerEditText.setText(s2);
        ArmPinCodeEditText.setText(s3);
        DisarmPinCodeEditText.setText(s4);

       // age.setText(String.valueOf(a));
        Log.v("loadData", "DATA IS LOADED");
    }

    //ON RESUME
 /*   @Override
    protected void onPause() {
        super.onPause();
        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor mySettings = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
    *//*mySettings.putString("GameTimerValue", GameTimerValue.getText().toString());
    mySettings.putString("BombTimerValue", BombTimerValue.getText().toString());
    mySettings.putString("ArmPinCodeValue", ArmPinCodeValue.getText().toString());
    mySettings.putString("DisarmPinCodeValue"1, DisarmPinCodeValue.getText().toString());*//*
        mySettings.putInt("editTextNumberValue", Integer.parseInt(editTextNumberValue.getText().toString()));
        mySettings.putInt("value1", Integer.parseInt(String.valueOf(GameTimerIntValue)));

    *//*    mySettings.putInt("GameTimerValue", Integer.parseInt(GameTimerValue.getText().toString()));
        mySettings.putInt("GameTimerValue", Integer.parseInt(GameTimerValue.getText().toString()));
        mySettings.putInt("BombTimerValue", Integer.parseInt(BombTimerValue.getText().toString()));
        mySettings.putInt("ArmPinCodeValue", Integer.parseInt(ArmPinCodeValue.getText().toString()));
        mySettings.putInt("DisarmPinCodeValue", Integer.parseInt(DisarmPinCodeValue.getText().toString()));*//*
        mySettings.apply();

    }
*/

    private void restoreSettings()
    {
        Log.d("restoreSettings", "START");

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Log.d("restoreSettings", "Getting values from SharedPrefs ...");
        String s1 = sh.getString("GameTimerStrValue", "");
        String s2 = sh.getString("BombTimerStrValue", "");
        String s3 = sh.getString("ArmPinCodeStrValue", "");
        String s4 = sh.getString("DisarmPinCodeStrValue", "");

        Log.d("restoreSettings", "Passing values to Fragment with viewModel ...");
        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        viewModel.setFragGameTime(s1);
        viewModel.setFragBombTime(s2);
        viewModel.setFragArmPinCode(s3);
        viewModel.setFragDisarmPinCode(s4);

        Log.d("restoreSettings", "DATA IS RESTORED");

    }




    // ON CREATE START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SetupFragment()).commit();

        setupScreen=true;   // active screen flag

        // define global textboxes for tags
        GameTimerEditText=findViewById(R.id.txtGameTime);
        BombTimerEditText=findViewById(R.id.txtBombTime);
        ArmPinCodeEditText=findViewById(R.id.txtArmPinCode);
        DisarmPinCodeEditText=findViewById(R.id.txtDisarmPinCode);

        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        restoreSettings();

// settings default values on startup
    /*    viewModel.setFragGameTime("60");
        viewModel.setFragBombTime("300");
        viewModel.setFragArmPinCode("1234");
        viewModel.setFragDisarmPinCode("5678");*/

        Button btnOpenGameFragment = (Button) findViewById(R.id.btnStartGameFragment);
        btnOpenGameFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (setupScreen)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GameFragment()).commit();
                    btnOpenGameFragment.setText("END GAME");
                    setupScreen=false;
                    saveData();
                }
                else{

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SetupFragment()).commit();
                    btnOpenGameFragment.setText("START GAME");
                    setupScreen=true;
                    restoreSettings();
                   // loadData();
                }

            } // end button on click11

        }); // END onClick












    } // END onCreate


} // End MainActivity class