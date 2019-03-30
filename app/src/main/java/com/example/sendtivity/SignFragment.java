package com.example.sendtivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignFragment extends Fragment {
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        //Fragmentin Sol üst kısmında geri buttonu için tanımlandı
        view.findViewById(R.id.SignBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginScreen.class);
                startActivity(intent);
            }
        });
        // Sign ekranındaki EditTextlerin referanslarını oluşturdum
        final EditText NameET = view.findViewById(R.id.SignNameET);
        final EditText LastnameET = view.findViewById(R.id.SignLastnameET);
        final EditText EmailET = view.findViewById(R.id.SignEmailET);
        final EditText PasswordET = view.findViewById(R.id.SignPasswordET);
        final EditText Password2ET = view.findViewById(R.id.SignPassword2ET);

        view.findViewById(R.id.SignSuccesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = new User();
                user.Name= NameET.getText().toString();
                user.LastName = LastnameET.getText().toString();
                user.Email = EmailET.getText().toString();
                final String password = PasswordET.getText().toString();
                final String password2 = Password2ET.getText().toString();
                if(password.equals(password2)){
                    Toast.makeText(getActivity(),"Şifre Başarılı", Toast.LENGTH_SHORT).show();

                    mAuth.createUserWithEmailAndPassword(user.Email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(),"Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(),"Parolanız uyuşmuyor", Toast.LENGTH_SHORT).show();
                }

            }
        });





        return view;
    }

}
