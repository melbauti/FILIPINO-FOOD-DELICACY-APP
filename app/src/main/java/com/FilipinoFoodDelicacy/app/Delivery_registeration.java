package com.FilipinoFoodDelicacy.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.FilipinoFoodDelicacy.app.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Delivery_registeration extends AppCompatActivity {


    String[] Region = {"NCR"};

    TextInputLayout Fname, Lname, Pass, cfpass, mobileno, houseno, area, postcode, Email;
    Spinner regionspin, cityspin;
    Button signup;
    TextView login;
    CountryCodePicker Cpp;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth FAuth;
    String role = "DeliveryPerson";
    String region,city, fname, lname, mobile, confirmpassword, password, Area, Postcode, house, emailid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_registeration);

        Fname = (TextInputLayout) findViewById(R.id.deliveryFullname);
        Lname = (TextInputLayout) findViewById(R.id.deliveryUsername);
        Pass = (TextInputLayout) findViewById(R.id.deliverypassword);
        Email = (TextInputLayout) findViewById(R.id.deliveryEmail);
        cfpass = (TextInputLayout) findViewById(R.id.deliveryconfirmpassword);
        mobileno = (TextInputLayout) findViewById(R.id.deliverymobileno);
        houseno = (TextInputLayout) findViewById(R.id.deliveryCurrentAddress);
        area = (TextInputLayout) findViewById(R.id.deliveryPlateNo);
        postcode = (TextInputLayout) findViewById(R.id.deliveryMotorBrand);
        regionspin = (Spinner) findViewById(R.id.deliveryRegion);
        cityspin = (Spinner) findViewById(R.id.deliveryProvince);
        signup = (Button) findViewById(R.id.deliverySignupp);
        login = (TextView) findViewById(R.id.GoLogin);
        Cpp = (CountryCodePicker) findViewById(R.id.deliveryctrycode);
        final ProgressDialog mDialog = new ProgressDialog(Delivery_registeration.this);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);


        regionspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                region = value.toString().trim();
                if (region.equals("NCR")) {
                    ArrayList<String> list = new ArrayList<>();
                    list.addAll(Arrays.asList(Region));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Delivery_registeration.this, android.R.layout.simple_spinner_item, list);

                    cityspin.setAdapter(arrayAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cityspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                city = value.toString().trim();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent q = new Intent(Delivery_registeration.this, Delivery_Login.class);
                startActivity(q);
                finish();
            }
        });



        databaseReference = firebaseDatabase.getInstance().getReference("DeliveryPerson");
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confirmpassword = cfpass.getEditText().getText().toString().trim();
                Area = area.getEditText().getText().toString().trim();
                house = houseno.getEditText().getText().toString().trim();
                Postcode = postcode.getEditText().getText().toString().trim();

                if (isValid()) {


                    mDialog.setMessage("Registering please wait...");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMappp = new HashMap<>();
                                        hashMappp.put("Area", Area);
                                        hashMappp.put("City", city);
                                        hashMappp.put("ConfirmPassword", confirmpassword);
                                        hashMappp.put("EmailID", emailid);
                                        hashMappp.put("Fname", fname);
                                        hashMappp.put("House", house);
                                        hashMappp.put("Lname", lname);
                                        hashMappp.put("Mobile", mobile);
                                        hashMappp.put("Password", password);
                                        hashMappp.put("Postcode", Postcode);
                                        hashMappp.put("Region", region);
                                        firebaseDatabase.getInstance().getReference("DeliveryPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMappp).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();

                                                FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_registeration.this);
                                                            builder.setMessage("Account Registered Successfully,Please Verify your Email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();

                                                                    String phonenumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                    Intent b = new Intent(Delivery_registeration.this, Delivery_VerifyPhone.class);
                                                                    b.putExtra("phonenumber", phonenumber);
                                                                    startActivity(b);

                                                                }
                                                            });
                                                            AlertDialog alert = builder.create();
                                                            alert.show();

                                                        } else {
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.ShowAlert(Delivery_registeration.this, "Error", task.getException().getMessage());

                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });


                            } else {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(Delivery_registeration.this, "Error", task.getException().getMessage());
                            }

                        }
                    });


                }
            }
        });




    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cfpass.setErrorEnabled(false);
        cfpass.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");
        postcode.setErrorEnabled(false);
        postcode.setError("");

        boolean isValidname = false, isvalidpassword = false, isValidemail = false, isvalidconfirmpassword = false, isvalid = false, isvalidmobileno = false, isvalidlname = false, isvalidhousestreetno = false, isvalidarea = false, isvalidpostcode = false;
        if (TextUtils.isEmpty(fname)) {
            Fname.setErrorEnabled(true);
            Fname.setError("Field cannot be empty");
        } else {
            isValidname = true;
        }
        if (TextUtils.isEmpty(lname)) {
            Lname.setErrorEnabled(true);
            Lname.setError("Field cannot be empty");
        } else {
            isvalidlname = true;
        }
        if (TextUtils.isEmpty(emailid)) {
            Email.setErrorEnabled(true);
            Email.setError("Field cannot be empty");
        } else {
            if (emailid.matches(emailpattern)) {
                isValidemail = true;
            } else {
                Email.setErrorEnabled(true);
                Email.setError("Enter a valid Email Address");
            }

        }

        if (TextUtils.isEmpty(password)) {
            Pass.setErrorEnabled(true);
            Pass.setError("Password is required");
        } else {
            if (password.length() < 6) {
                Pass.setErrorEnabled(true);
                Pass.setError("password too weak");
            } else {
                isvalidpassword = true;
            }
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            cfpass.setErrorEnabled(true);
            cfpass.setError("Confirm Password is required");
        } else {
            if (!password.equals(confirmpassword)) {
                Pass.setErrorEnabled(true);
                Pass.setError("Password doesn't match");
            } else {
                isvalidconfirmpassword = true;
            }
        }
        if (TextUtils.isEmpty(mobile)) {
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile number is required");
        } else {
            if (mobile.length() < 10) {
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid mobile number");
            } else {
                isvalidmobileno = true;
            }
        }
        if (TextUtils.isEmpty(house)) {
            houseno.setErrorEnabled(true);
            houseno.setError("Field cannot be empty");
        } else {
            isvalidhousestreetno = true;
        }
        if (TextUtils.isEmpty(Area)) {
            area.setErrorEnabled(true);
            area.setError("Field cannot be empty");
        } else {
            isvalidarea = true;
        }
        if (TextUtils.isEmpty(Postcode)) {
            postcode.setErrorEnabled(true);
            postcode.setError("Field cannot be empty");
        } else {
            isvalidpostcode = true;
        }

        isvalid = (isValidname && isvalidpostcode && isValidemail && isvalidlname && isvalidconfirmpassword && isvalidpassword && isvalidmobileno && isvalidarea && isvalidhousestreetno) ? true : false;
        return isvalid;
    }
}