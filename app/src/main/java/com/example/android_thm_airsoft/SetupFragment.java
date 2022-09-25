package com.example.android_thm_airsoft;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetupFragment extends Fragment {

   // private TextView textString;
   private EditText GameTimerEditText, BombTimerEditText,ArmPinCodeEditText,DisarmPinCodeEditText;
    private String GameTimerStrValue,BombTimerStrValue,ArmPinCodeStrValue,DisarmPinCodeStrValue;
    private ItemViewModel viewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetupFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetupFragment newInstance(String param1, String param2) {
        SetupFragment fragment = new SetupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SetupFragment", "onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
       // Toast.makeText(getContext(),"SETUP FRAGMENT",Toast.LENGTH_SHORT).show();
        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

    }
    private  TextView textString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_setup, container, false);
       /* View rootView = inflater.inflate(R.layout.fragment_setup, container,    false);
        return rootView;*/
        Log.d("SetupFragment", "onCreateView ");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup,
                container, false);

        Log.d("SetupFragment", "TextView findViewById ");
        GameTimerEditText=(EditText)view.findViewById(R.id.txtGameTime);
        BombTimerEditText=(EditText)view.findViewById(R.id.txtBombTime);
        ArmPinCodeEditText=(EditText)view.findViewById(R.id.txtArmPinCode);
        DisarmPinCodeEditText=(EditText)view.findViewById(R.id.txtDisarmPinCode);

        Button btnStartGame = (Button) view.findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(getContext(),"GAME FRAGMENT",Toast.LENGTH_SHORT).show();

                // save settings on MainActivity if game has started, this will save last setup
               // ((MainActivity)getActivity()).saveSettings();
                saveSettings();
                // open setup fragment on startup

           /*     GameFragment gameFrag= new GameFragment();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.setup_fragment_id, gameFrag, "setup_fragment_id");
                ft.commit();*/

               GameFragment gameFrag= new GameFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.setup_fragment_id, gameFrag, "setup_fragment_id")
                        .addToBackStack(null)
                        .commit();
            }
        });


        Button btnSaveSettings = (Button) view.findViewById(R.id.btnSaveSetting);
        btnSaveSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(),"SAVED",Toast.LENGTH_SHORT).show();
                saveSettings();

                //((MainActivity)getActivity()).saveSettings();
            }
        });


        return view;

    }

    public void setFragTextView(String text){
        TextView textView = (TextView) getView().findViewById(R.id.lblFragString);
        textView.setText(text);
    }
    public void setFragEditText(String text, int R_id_resource){

        EditText FcEditText = (EditText) getView().findViewById(R_id_resource);
        FcEditText.setText(text);

    }
/*    public void setFragEditText(String text,int R_id_resource){

        EditText FcEditText = (EditText) getView().findViewById(R.id.txtGameTime);
        FcEditText.setText(text);

    }*/

    public String GameTime;
    public String BombTime;
    public String ArmPinCode;
    public String DisarmPinCode;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        // restore values on startup
       ((MainActivity)getActivity()).restoreSettings();
        //restoreSettings();
        // get values from viewModel
        GameTime=viewModel.getFragGameTime().getValue();
        BombTime=viewModel.getFragBombTime().getValue();
        ArmPinCode=viewModel.getFragArmPinCode().getValue();
        DisarmPinCode=viewModel.getFragDisarmPinCode().getValue();

       // update UI EditText with values from viewModel
        setFragEditText(GameTime,R.id.txtGameTime);
        setFragEditText(BombTime,R.id.txtBombTime);
        setFragEditText(ArmPinCode,R.id.txtArmPinCode);
        setFragEditText(DisarmPinCode,R.id.txtDisarmPinCode);

    }
    public void saveSettings()
    {
        Log.d("SetupFragment", "saveSettings START");
        // define global textboxes for tags1
        // use getView(). to get current view otherwise wrong findViewById is used and not current text is used
 /*       GameTimerEditText=(EditText)getActivity().findViewById(R.id.txtGameTime);
        BombTimerEditText=(EditText)getActivity().findViewById(R.id.txtBombTime);
        ArmPinCodeEditText=(EditText)getActivity().findViewById(R.id.txtArmPinCode);
        DisarmPinCodeEditText=(EditText)getActivity().findViewById(R.id.txtDisarmPinCode);*/


        // Creating a shared pref object
        // with a file name "MySharedPref"1
        // in private mode
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor mySettings = sharedPreferences.edit();

        Log.d("SetupFragment", "saveSettings GET TEXT FROM EDITTEXT");
        GameTimerStrValue=GameTimerEditText.getText().toString();
        BombTimerStrValue=BombTimerEditText.getText().toString();
        ArmPinCodeStrValue=ArmPinCodeEditText.getText().toString();
        DisarmPinCodeStrValue=DisarmPinCodeEditText.getText().toString();


        Log.d("SetupFragment", "saveSettings SAVING DATA "+GameTimerStrValue+"-"+BombTimerStrValue+"-"+ArmPinCodeStrValue+"-"+DisarmPinCodeStrValue);
        // write all the data entered by the user in SharedPreference and apply
        mySettings.putString("GameTimerStrValue", GameTimerStrValue);
        mySettings.putString("BombTimerStrValue", BombTimerStrValue);
        mySettings.putString("ArmPinCodeStrValue", ArmPinCodeStrValue);
        mySettings.putString("DisarmPinCodeStrValue", DisarmPinCodeStrValue);

        mySettings.apply();
        mySettings.commit();
        Log.d("SetupFragment", "saveSettings DATA SAVED");


        // mainactivity and fragment share data1
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        Log.d("SetupFragment", "saveSettings Setting viewModel data");
        viewModel.setFragGameTime(GameTimerStrValue);
        viewModel.setFragBombTime(BombTimerStrValue);
        viewModel.setFragArmPinCode(ArmPinCodeStrValue);
        viewModel.setFragDisarmPinCode(DisarmPinCodeStrValue);
        Log.d("SetupFragment", "saveSettings FINISHED");

    }


    /*public void restoreSettings()
    {
        Log.d("restoreSettings", "START");

        SharedPreferences sh = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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

    }*/
    }


