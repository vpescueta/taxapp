package com.example.mytaxapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mytaxapp.databinding.ActivityCustomerDetailsBinding;

public class CustomerDetailsActivity extends AppCompatActivity {

    private UserService userService;
    private ActivityCustomerDetailsBinding customerInfoBinding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customerInfoBinding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(customerInfoBinding.getRoot());
        userService = UserService.getInstance(this);

        user = getIntent().getParcelableExtra("user_key");
        setUserDetailsInTextView(user);

        Spinner spinner = customerInfoBinding.spinnerCustomerInfoProcessStatus;

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.process_status, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        int spinnerPos = spinnerAdapter.getPosition(user.getProcessStatus().toString().trim());
        spinner.setSelection(spinnerPos);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(CustomerDetailsActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
                user.setProcessStatus(selectedItem);

                userService.updateUser(user, new UserService.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("updated_user_key", user);
                        setResult(RESULT_OK, resultIntent);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle error
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView findMyLocview = customerInfoBinding.tvCustomerInfoFindLocation;
        findMyLocview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerDetailsActivity.this, "Find my Location clicked", Toast.LENGTH_LONG).show();
                customerInfoBinding.cvCustomerInfoCardBg.setVisibility(View.GONE);
                replaceFragment(MapFragment.newInstance(user));
            }
        });



        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_user_key", user);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void setUserDetailsInTextView(User user) {
        customerInfoBinding.tvCustomerInfoName.setText(user.getFirstName().toString() + " " + user.getLastName().toString());
        customerInfoBinding.tvCustomerInfoCity.setText(user.getCity().toString());
        customerInfoBinding.tvCustomerInfoPhone.setText(user.getPhone().toString());
        customerInfoBinding.tvCustomerInfoEmail.setText("Email: " + user.getEmail().toString());
        customerInfoBinding.tvCustomerInfoWebsite.setText("Website: " + user.getWebsite().toString());
        customerInfoBinding.tvCustomerInfoZipcode.setText("Zip Code: " + user.getZipcode().toString());
        customerInfoBinding.tvCustomerInfoCompanyName.setText("Company Name: " + user.getCompanyName().toString());
        customerInfoBinding.tvCustomerInfoCompanyPhrase.setText("Company Phrase: " + user.getCompanyCatchPhrase().toString());
        customerInfoBinding.tvCustomerInfoCompanyBuzzWord.setText("Company BS: " + user.getCompanyBS().toString());
        customerInfoBinding.tvCustomerInfoStreet.setText("Street: " + user.getStreet().toString());
        customerInfoBinding.tvCustomerInfoUsername.setText("Username: " +  user.getUsername().toString());

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(customerInfoBinding.flCustomerInfoFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}



