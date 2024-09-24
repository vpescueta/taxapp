package com.example.mytaxapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mytaxapp.databinding.FragmentSignInBinding;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends BaseFragment {

    private FragmentSignInBinding signInBinding;
    private UserService userService;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signInBinding = FragmentSignInBinding.inflate(getLayoutInflater());
        return signInBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userService = UserService.getInstance(getActivity());

        signInBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameEntered = signInBinding.etUsername.getText().toString();
                String passwordEntered = signInBinding.etPassword.getText().toString();
                handleLogin(usernameEntered, passwordEntered);
            }
        });

        signInBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(RegisterFragment.newInstance(), R.id.fl_login_frame_layout);
            }
        });
    }

    // function to check if username and password entered is same as with the records in the database
    private void handleLogin(String username, String password) {
        userService.fetchAllUsers(users -> {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    // Login successful, route to main activity/home screen
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("user_obj_key", user);
                    startActivity(intent);
                    return;
                }
            }
            // Login failed
            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show());
        });
    }


}
