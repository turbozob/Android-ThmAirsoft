package com.example.android_thm_airsoft;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    // Public variables
boolean setupScreen=false;

// share data between activity and fragment
/*private ItemViewModel viewModel;*/
private ItemViewModel viewModel;


    // ON CREATE START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SetupFragment()).commit();

        setupScreen=true;   // active screen flag

        // mainactivity and fragment share data
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);


// settings default values on startup
        viewModel.setFragGameTime("60");
        viewModel.setFragBombTime("300");
        viewModel.setFragArmPinCode("1234");
        viewModel.setFragDisarmPinCode("5678");

        Button btnOpenGameFragment = (Button) findViewById(R.id.btnOpenGameFragment);
        btnOpenGameFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (setupScreen)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GameFragment()).commit();
                    btnOpenGameFragment.setText("Back");
                    setupScreen=false;

                }
                else{

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SetupFragment()).commit();
                    btnOpenGameFragment.setText("Next");
                    setupScreen=true;
                }

            } // end button on click

        }); // END onClick

    } // END onCreate


} // End MainActivity class