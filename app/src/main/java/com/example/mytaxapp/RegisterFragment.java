package com.example.mytaxapp;

import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.mytaxapp.databinding.FragmentRegisterBinding;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends BaseFragment {


    FragmentRegisterBinding registerBinding;
    private UserService userService;
    private EditText firstName, lastName, userName,
            password, email, streetAddr, suiteAddr,
            cityAddr, zipcode, phone, website, companyName,
            compCatchPhrase, compBS;
    private Button cancelBtn, regBtn;
    String strUsername, strPassword, strFirstName, strLastName, strEmail,
            strRole = "customer", strStreet, strSuite, strCity, strZipCode, strPhone, strWebsite,
            strCompanyName, strBuzzWord, strCatchPhrase, strCountry, fullAddress, strProcessStatus = "awaited";
    Address geoMapAddress;
    Double geoLong, geoLat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

        registerBinding = FragmentRegisterBinding.inflate(getLayoutInflater());
        getRegDetails();

        final ScrollView scrollView = registerBinding.svCustomerScreen;
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                scrollView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = scrollView.getRootView().getHeight();
                int keypadHeight = screenHeight - rect.bottom;

                if (keypadHeight > screenHeight * 0.20) {
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getBottom());
                        }
                    });
                }
            }
        });


        return registerBinding.getRoot();
    }

    private void getRegDetails() {
        regBtn = registerBinding.btnRegRegister;
        cancelBtn = registerBinding.btnRegCancel;
        userName = registerBinding.etRegUsername;
        password = registerBinding.etRegPassword;
        firstName = registerBinding.etRegFirstname;
        lastName = registerBinding.etRegLastname;
        email = registerBinding.etRegLastname;
        streetAddr = registerBinding.etRegStreet;
        suiteAddr = registerBinding.etRegSuite;
        cityAddr = registerBinding.etRegCity;
        zipcode = registerBinding.etRegZipcode;
        phone = registerBinding.etRegPhone;
        website = registerBinding.etRegWebsite;
        companyName = registerBinding.etRegCompanyName;
        compBS = registerBinding.etRegCompanyBs;
        compCatchPhrase = registerBinding.etRegCompanyCatchPhrase;

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getEnteredDetails()){
                    String username = userName.getText().toString();
                    userService.handleRegistration(username, new UserRegistrationCallback() {
                        @Override
                        public void onResult(boolean isRegistered) {
                            if (isRegistered){
                                getActivity().runOnUiThread(() ->
                                        Toast.makeText(getActivity(), "Username: " + username + " already exists", Toast.LENGTH_SHORT).show()
                                );
                                userName.setText("");
                            } else{

                                User user = new User(strUsername, strFirstName, strLastName, strPassword,  strRole, strEmail,
                                        strStreet, strSuite, strCity, strZipCode, geoLat, geoLong, strPhone,
                                        strWebsite, strCompanyName, strCatchPhrase, strBuzzWord, strProcessStatus);
                                userService.insertUser(user, new UserService.OperationCallback() {
                                    @Override
                                    public void onOperationCompleted() {

                                        replaceFragment(new SignInFragment(), R.id.fl_login_frame_layout);
                                    }

                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });

                            }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(), "Please complete blank fields", Toast.LENGTH_LONG).show();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(SignInFragment.newInstance(), R.id.fl_login_frame_layout);
            }
        });

    }

    private boolean getEnteredDetails() {

        strUsername = userName.getText().toString().trim();
        strPassword = password.getText().toString().trim();
        strFirstName = firstName.getText().toString().trim();
        strLastName = lastName.getText().toString().trim();
        strEmail = email.getText().toString().trim();
        strStreet = streetAddr.getText().toString().trim();
        strSuite = suiteAddr.getText().toString().trim();
        strCity = cityAddr.getText().toString().trim();
        strZipCode = zipcode.getText().toString().trim();
        strPhone = phone.getText().toString().trim();
        strWebsite = website.getText().toString().trim();
        strCompanyName = companyName.getText().toString().trim();
        strBuzzWord = compBS.getText().toString().trim();
        strCatchPhrase = compCatchPhrase.getText().toString().trim();
        strCountry = "Canada";

        if (strUsername.isEmpty() ||
                strPassword.isEmpty() ||
                strFirstName.isEmpty() ||
                strLastName.isEmpty() ||
                strEmail.isEmpty() ||
                strStreet.isEmpty() ||
                strSuite.isEmpty() ||
                strCity.isEmpty() ||
                strZipCode.isEmpty() ||
                strPhone.isEmpty() ||
                strWebsite.isEmpty() ||
                strCompanyName.isEmpty() ||
                strBuzzWord.isEmpty() ||
                strCatchPhrase.isEmpty()) {
            return false;
        }

        fullAddress = strStreet + ", " + strCity + ", " + strCountry + ", " + strZipCode;
        geoMapAddress = performGeocoding(fullAddress);
        if (geoMapAddress != null){
            geoLong = geoMapAddress.getLongitude();
            geoLat = geoMapAddress.getLatitude();
        }


        return true;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userService = UserService.getInstance(getActivity());
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


}