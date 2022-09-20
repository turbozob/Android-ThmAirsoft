package com.example.android_thm_airsoft;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetupFragment extends Fragment {

   // private TextView textString;
   private EditText GameTimerEditText, BombTimerEditText,ArmPinCodeEditText,DisarmPinCodeEditText;

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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
       // Toast.makeText(getContext(),"SETUP FRAGMENT",Toast.LENGTH_SHORT).show();


    }
    private  TextView textString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_setup, container, false);
        View rootView = inflater.inflate(R.layout.fragment_setup, container,    false);
        return rootView;

    }

    public void setFragTextView(String text){
        TextView textView = (TextView) getView().findViewById(R.id.txtFragString);
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

    private ItemViewModel viewModel;
    public String GameTime;
    public String BombTime;
    public String ArmPinCode;
    public String DisarmPinCode;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        GameTime=viewModel.getFragGameTime().getValue();
        BombTime=viewModel.getFragBombTime().getValue();
        ArmPinCode=viewModel.getFragArmPinCode().getValue();
        DisarmPinCode=viewModel.getFragDisarmPinCode().getValue();
       // setFragTextView(GameTime);
        setFragEditText(GameTime,R.id.txtGameTime);
        setFragEditText(BombTime,R.id.txtBombTime);
        setFragEditText(ArmPinCode,R.id.txtArmPinCode);
        setFragEditText(DisarmPinCode,R.id.txtDisarmPinCode);

    }


    }


