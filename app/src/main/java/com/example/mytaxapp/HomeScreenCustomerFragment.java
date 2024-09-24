package com.example.mytaxapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.location.Address;
import android.location.Geocoder;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mytaxapp.databinding.FragmentHomeScreenCustomerBinding;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeScreenCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeScreenCustomerFragment extends Fragment {

    FragmentHomeScreenCustomerBinding customerBinding;
    Button editBtn;
    private UserService userService;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_USER = "user_obj_key";

    // TODO: Rename and change types of parameters
    private User user;
    private String mParam2;
    private String mParam1;

    public HomeScreenCustomerFragment() {
        // Required empty public constructor
    }

    public static HomeScreenCustomerFragment newInstance(){
        return new HomeScreenCustomerFragment();
    }

    public static HomeScreenCustomerFragment newInstance(User user){
        HomeScreenCustomerFragment fragment = new HomeScreenCustomerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreenCustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreenCustomerFragment newInstance(String param1, String param2) {
        HomeScreenCustomerFragment fragment = new HomeScreenCustomerFragment();
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
            user = getArguments().getParcelable(ARG_USER);
        }

        userService = UserService.getInstance(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        customerBinding = FragmentHomeScreenCustomerBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment

        setCustomerDetails(user);
        setUpBtn(user);

        ImageView logoutBtn = customerBinding.getRoot().findViewById(R.id.iv_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return customerBinding.getRoot();
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

    private void setUpBtn(User user) {
        editBtn = customerBinding.btnCustHomeScreenEdit;
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCustomerDetails(v);
            }
        });
    }

    private void editCustomerDetails(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Profile");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);

        final EditText inputFirstName = dialogView.findViewById(R.id.edit_first_name);
        final EditText inputLastName = dialogView.findViewById(R.id.edit_last_name);
        final EditText inputStreet = dialogView.findViewById(R.id.edit_street);
        final EditText inputSuite = dialogView.findViewById(R.id.edit_suite);
        final EditText inputCity = dialogView.findViewById(R.id.edit_city);
        final EditText inputZipCode = dialogView.findViewById(R.id.edit_zip_code);
        final EditText inputPhone = dialogView.findViewById(R.id.edit_phone);
        final EditText inputWebsite = dialogView.findViewById(R.id.edit_website);
        final EditText inputCompanyName = dialogView.findViewById(R.id.edit_company_name);
        final EditText inputCatchPhrase = dialogView.findViewById(R.id.edit_catch_phrase);
        final EditText inputBuzzWord = dialogView.findViewById(R.id.edit_buzz_word);

        inputFirstName.setText(user.getFirstName());
        inputLastName.setText(user.getLastName());
        inputStreet.setText(user.getStreet());
        inputSuite.setText(user.getSuite());
        inputZipCode.setText(user.getZipcode());
        inputCity.setText(user.getCity());
        inputPhone.setText(user.getPhone());
        inputWebsite.setText(user.getWebsite());
        inputCompanyName.setText(user.getCompanyName());
        inputCatchPhrase.setText(user.getCompanyCatchPhrase());
        inputBuzzWord.setText(user.getCompanyBS());

        builder.setView(dialogView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.setFirstName(inputFirstName.getText().toString().trim());
                user.setLastName(inputLastName.getText().toString().trim());
                user.setStreet(inputStreet.getText().toString().trim());
                user.setSuite(inputSuite.getText().toString().trim());
                user.setZipcode(inputZipCode.getText().toString().trim());
                user.setCity(inputCity.getText().toString().trim());
                user.setPhone(inputPhone.getText().toString().trim());
                user.setWebsite(inputWebsite.getText().toString().trim());
                user.setCompanyName(inputCompanyName.getText().toString().trim());
                user.setCompanyCatchPhrase(inputCatchPhrase.getText().toString().trim());
                user.setCompanyBS(inputBuzzWord.getText().toString().trim());
                String strCountry = "Canada";
                String fullAddress = user.getStreet() + ", " + user.getCity() + ", " + strCountry + ", " + user.getZipcode();
                Address geoAddress = performGeocoding(fullAddress);
                user.setGeoLat(geoAddress.getLatitude());
                user.setGeoLng(geoAddress.getLongitude());

                userService.updateUser(user, new UserService.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {
                        setCustomerDetails(user);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle the error
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
    }

    private Address performGeocoding(String fullAddress) {
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            List<Address> addresses = geocoder.getFromLocationName(fullAddress, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address geoAddress = addresses.get(0);
                String coords = "Coordinates: " + geoAddress.getLatitude() + ", " + geoAddress.getLongitude();
                Log.d("MainActivity", coords);
                return geoAddress;
            } else {
                Toast.makeText(getActivity(), "Address not found", Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void setCustomerDetails(User user) {
        if (user != null){
            customerBinding.etRegUsername.setText(user.getUsername());
            customerBinding.etRegFirstname.setText(user.getFirstName());
            customerBinding.etRegLastname.setText(user.getLastName());
            customerBinding.etRegEmail.setText(user.getEmail());
            customerBinding.etRegStreet.setText(user.getStreet());
            customerBinding.etRegSuite.setText(user.getSuite());
            customerBinding.etRegCity.setText(user.getCity());
            customerBinding.etRegZipCode.setText(user.getZipcode());
            customerBinding.etRegPhone.setText(user.getPhone());
            customerBinding.etRegCompanyName.setText(user.getCompanyName());
            customerBinding.etRegCompanyCatchPhrase.setText(user.getCompanyCatchPhrase());
            customerBinding.etRegCompanyBs.setText(user.getCompanyBS());
            customerBinding.etRegWebsite.setText(user.getWebsite());
        }

        customerBinding.incHomeCustomerHeader.tvTopProfileHeader.setText("Customer Account");
    }
}
