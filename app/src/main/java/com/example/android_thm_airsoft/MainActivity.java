package com.example.android_thm_airsoft;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
  /*      viewModel.getSelectedItem().observe(this, item -> {
            // Perform an action with the latest item data
        });*/
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



         /*       FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ExampleFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();*/


            } // end button on click
        });








    }
    // ON CREATE END
/*    public class SharedViewModel extends ViewModel {
        private final MutableLiveData<ClipData.Item> selected = new MutableLiveData<ClipData.Item>();

        public void select(ClipData.Item item) {
            selected.setValue(item);
        }

        public LiveData<ClipData.Item> getSelected() {
            return selected;
        }
    }*/




} // MainActivity class