package com.idnp2025b.loginsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.idnp2025b.loginsample.databinding.ActivityMainBinding;

public class AccountActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnAccept = findViewById(R.id.btnOK);
        Button btnCancel = findViewById(R.id.btnCancel);
        EditText edtUsername = findViewById(R.id.edtUsername2);
        EditText edtPassword = findViewById(R.id.edtPassword2);
        EditText edtFirstname = findViewById(R.id.edtFirstname);
        EditText edtLastname = findViewById(R.id.edtLastname);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPhone = findViewById(R.id.edtPhone);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = edtFirstname.getText().toString().trim();
                String lastname  = edtLastname.getText().toString().trim();
                String email     = edtEmail.getText().toString().trim();
                String phone     = edtPhone.getText().toString().trim();
                String username  = edtUsername.getText().toString().trim();
                String password  = edtPassword.getText().toString().trim();

                if (firstname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Coloca tus nombres!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lastname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Coloca tus apellidos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Coloca tu correo!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Coloca tu telefono!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Coloca tu nombre de usuario!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Coloca tu contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AccountEntity account = new AccountEntity(
                        firstname,
                        lastname,
                        email,
                        phone,
                        username,
                        password
                );
                Intent result = new Intent();
                result.putExtra("account_json", account.toJson());
                setResult(RESULT_OK, result);
                Toast.makeText(getApplicationContext(), "Se registro la cuenta!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}