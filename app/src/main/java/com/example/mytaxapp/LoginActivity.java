package com.example.mytaxapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.mytaxapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private SignInFragment signInFragment;
    private ActivityLoginBinding loginBinding;

    private UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        userService = UserService.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(loginBinding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(savedInstanceState == null) {
            signInFragment = new SignInFragment();
            replaceFragment(new SignInFragment());
        } else {
            signInFragment = (SignInFragment) getSupportFragmentManager().findFragmentById(R.id.fl_login_frame_layout);
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(loginBinding.flLoginFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}