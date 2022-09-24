package com.example.android_thm_airsoft;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
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


        Button button = (Button) view.findViewById(R.id.btnArmDefuse);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(),"GAME FRAGMENT",Toast.LENGTH_SHORT).show();
                // do something
                winnerText.setText("WTF");
            }
        });
        return view;

    }



    private ItemViewModel viewModel;
    public String currentGameTime;
    public String currentBombTime;
    public TextView lblCurrentGameTimeTxt;
    public Button btnPlant;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lblCurrentGameTimeTxt = (TextView) view.findViewById(R.id.lblCurrentGameTime);

        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        // get values from viewModel
        currentGameTime=viewModel.getCurrentGameTime().getValue();
        currentBombTime=viewModel.getCurrentBombTime().getValue();

        // update UI EditText with values from viewModel
        int R_ID= R.id.lblCurrentGameTime;
        String R_ID_STR="";
        setFragTextView(currentGameTime,R.id.lblCurrentGameTime);
        R_ID= R.id.lblCurrentArmDefuseTime;
        setFragTextView(currentBombTime,R.id.lblCurrentArmDefuseTime);
        R_ID= R.id.textViewTEST;

        setFragTextView("DAFUQ",R.id.textViewTEST);





    }

    public void setFragTextView(String text, int R_id_resource){
        TextView FcTextView = (TextView) getView().findViewById(R_id_resource);
        FcTextView.setText(text);
    }

    // Your new method
   public void setNewText(String text) {
       setFragTextView(currentBombTime,R.id.textViewTEST);
    }


}