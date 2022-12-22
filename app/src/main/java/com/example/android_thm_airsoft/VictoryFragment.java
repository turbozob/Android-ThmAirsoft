package com.example.android_thm_airsoft;

import android.os.Bundle;

import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
 * Use the {@link VictoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VictoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VictoryFragment() {
        // Required empty public constructor
    }
    // globally
    private  TextView textString;
    public View viewPublic;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VictoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VictoryFragment newInstance(String param1, String param2) {
        VictoryFragment fragment = new VictoryFragment();
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
        //setFragTextView("DEFENDERS WIN");
       // TextView textView = (TextView) getView().findViewById(R.id.lbl_Winner_Victory);
        //TextViewCompat.setTextAppearance(textView, R.style.attackers_win_style);


    }
    public void setFragTextView(String text){
        TextView textView = (TextView) viewPublic.findViewById(R.id.lbl_Winner_Victory);
        textView.setText(text);
    }

    private ItemViewModel viewModel;
    public String WinnerName;
    public TextView winnerText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // -- inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_victory, container,false);
        // mainactivity and fragment share data1
       // viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        viewModel = new ViewModelProvider(getActivity()).get(ItemViewModel.class);

        //viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        // update UI EditText with values from viewModel
        winnerText = (TextView) myInflatedView.findViewById(R.id.lbl_Winner_Victory);
        winnerText.setText(WinnerName);


        return myInflatedView;

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // get values from viewModel
        WinnerName=viewModel.getFragWinnerName().getValue();

        winnerText.setText(WinnerName);

        if (WinnerName=="Attackers WIN"){
            TextViewCompat.setTextAppearance(winnerText, R.style.attackers_win_style);
        }
        else{
            TextViewCompat.setTextAppearance(winnerText, R.style.defenders_win_style);

        }


    }
    public void setText(String text) {
        TextView textView = (TextView) getView().findViewById(R.id.lbl_Winner_Victory);
        textView.setText(text);
    }


}