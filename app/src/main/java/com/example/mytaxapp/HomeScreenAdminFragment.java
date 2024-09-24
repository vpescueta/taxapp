package com.example.mytaxapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mytaxapp.databinding.FragmentHomeScreenAdminBinding;

import java.util.ArrayList;


public class HomeScreenAdminFragment extends Fragment {

    FragmentHomeScreenAdminBinding adminBinding;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private User selectedUser = null;
    private UserService userService;

    private final ActivityResultLauncher<Intent> userDetailsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        User updatedUser = data.getParcelableExtra("updated_user_key");
                        if (updatedUser != null) {
                            updateUserInList(updatedUser);
                        }
                    }
                }
            }
    );



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeScreenAdminFragment() {
        // Required empty public constructor
    }

    public static HomeScreenAdminFragment newInstance() {
        return new HomeScreenAdminFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreenAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreenAdminFragment newInstance(String param1, String param2) {
        HomeScreenAdminFragment fragment = new HomeScreenAdminFragment();
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
        // Inflate the layout for this fragment

        userService = UserService.getInstance(getContext());
        adminBinding = FragmentHomeScreenAdminBinding.inflate(getLayoutInflater());
        adminBinding.incHomeAdminHeader.tvTopProfileHeader.setText("Admin Account");

        ImageView logoutBtn = adminBinding.getRoot().findViewById(R.id.iv_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        recyclerView = adminBinding.recyclerView;
        adapter = new RecyclerAdapter(new ArrayList<>());
        adapter.setOnUserClickListener(user -> {
            selectedUser = user;
            getActivity().runOnUiThread(() -> {
                Intent intent = new Intent(getActivity(), CustomerDetailsActivity.class);
                intent.putExtra("user_key", selectedUser);
                startActivity(intent);
            });
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchCustomerData();

        return adminBinding.getRoot();
    }

    private void updateUserInList(User updatedUser) {
        for (int i = 0; i < adapter.getItemCount(); i++) {

            if (adapter.getUserAtPosition(i).getUid() == updatedUser.getUid()) {
                adapter.updateItem(i, updatedUser);
                break;
            }
        }
    }

    private void logout() {
        userService.logout(new UserService.OperationCallback() {
            @Override
            public void onOperationCompleted() {
                getActivity().runOnUiThread(() -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish(); // Close the current activity to prevent back navigation
                });
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getActivity(), "Logout failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void fetchCustomerData() {
        userService.fetchAllCustomers(users -> getActivity().runOnUiThread(() -> adapter.setData(users)));
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchCustomerData();
    }

}