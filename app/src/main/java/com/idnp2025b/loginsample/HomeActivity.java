package com.idnp2025b.loginsample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.idnp2025b.loginsample.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private static final String FILE = "cuentas.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText edtUsername = binding.edtUsername;
        EditText edtPassword = binding.edtPassword;
        Button btnLogin = binding.btnLogin;
        Button btnAddAccount = binding.btnAddAccount;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUsername.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                List<AccountEntity> accounts = readAccounts();
                boolean found = false;
                for (AccountEntity acc : accounts) {
                    if (acc.validate(user, pass)) {
                        Toast.makeText(getApplicationContext(),
                                "Bienvenido " + acc.getUsername(),
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Login exitoso: " + acc.getUsername());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(getApplicationContext(),"Cuenta no encontrada",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

    }
    private void saveAccount(AccountEntity account) {
        try {
            FileOutputStream fos = openFileOutput(FILE, MODE_APPEND);
            fos.write((account.export_txt() + "\n").getBytes());
            fos.close();
            Log.d(TAG, "Cuenta guardada: " + account.getUsername());
        } catch (Exception e) {
        }
    }
    private List<AccountEntity> readAccounts() {
        List<AccountEntity> accounts = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    accounts.add(AccountEntity.import_txt(line));
                } catch (Exception e) {
                    Log.e(TAG, "Error al importar línea: " + line, e);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Archivo no encontrado, se creará cuando se guarde la primera cuenta");
        } catch (Exception e) {
        }
        return accounts;
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    int resultCode = activityResult.getResultCode();
                    Intent data = activityResult.getData();
                    if (resultCode == RESULT_OK && data != null) {
                        String accountJson = data.getStringExtra("account_json");

                        if (accountJson != null) {
                            AccountEntity account = AccountEntity.fromJson(accountJson);

                            if (account != null) {
                                saveAccount(account);
                                Log.d("HomeActivity", "Usuario: " + account.getUsername());
                                Toast.makeText(getApplicationContext(),
                                        "Se guardo la cuenta correctamente",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Se cancelo la operacion!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}