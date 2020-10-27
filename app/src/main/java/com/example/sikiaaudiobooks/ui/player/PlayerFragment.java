package com.example.sikiaaudiobooks.ui.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sikiaaudiobooks.R;


public class PlayerFragment extends Fragment {

    private PlayerViewModel playerViewModel;
    private Button playBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        playerViewModel =
                ViewModelProviders.of(this).get(PlayerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_player, container, false);
//        final TextView textView = root.findViewById(R.id.text_player);
        playerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        playBtn = root.findViewById(R.id.player_button_play_pause);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPlayer = new Intent(getContext(), PlayerActivity.class);
                Bundle data = new Bundle();
                startActivity(gotoPlayer);
            }
        });

        return root;
    }
}