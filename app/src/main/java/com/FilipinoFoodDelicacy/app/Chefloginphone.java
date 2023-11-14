package com.FilipinoFoodDelicacy.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Chefloginphone extends AppCompatActivity {

    EditText num;
    Button signinemail;
    TextView txtsignup;
    CountryCodePicker cpp;
    FirebaseAuth FAuth;
    String numberr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefloginphone);

        num=(EditText)findViewById(R.id.number);
        cpp=(CountryCodePicker)findViewById(R.id.CountryCode);
        signinemail=(Button)findViewById(R.id.btnEmail);
        txtsignup=(TextView)findViewById(R.id.acsignup);


        FAuth=FirebaseAuth.getInstance();


        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(Chefloginphone.this,ChefRegisteration.class);
                startActivity(a);
                finish();
            }
        });

        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent em=new Intent(Chefloginphone.this, ChefLogin.class);
                startActivity(em);
                finish();
            }
        });

    }
}
