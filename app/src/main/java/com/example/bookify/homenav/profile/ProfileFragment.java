package com.example.bookify.homenav.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookify.Delete;
import com.example.bookify.MainActivity;
import com.example.bookify.R;
import com.example.bookify.Upload;
import com.example.bookify.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button logout, upload, delete;
    private TextView profileTextEmail, profileTextName, textContributor;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference reference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = root.findViewById(R.id.buttonLogout);
        logout.setOnClickListener(this);

        profileTextEmail = root.findViewById(R.id.profileTextEmail);
        profileTextName = root.findViewById(R.id.profileTextName);
        textContributor = root.findViewById(R.id.textContributor);
        upload = root.findViewById(R.id.buttonUpload);
        upload.setOnClickListener(this);
        upload.setVisibility(View.GONE);

        delete = root.findViewById(R.id.buttonDelete);
        delete.setOnClickListener(this);
        delete.setVisibility(View.GONE);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String name = userProfile.getName();
                    String email = userProfile.getEmail();
                    if (userProfile.isContributor()){
                        textContributor.setText("Contributor : Yes");
                        upload.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);

                    }else{
                        textContributor.setText("Contributor : No");
                    }

                    profileTextName.setText("Name : " + name);
                    profileTextEmail.setText("Email : " + email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Profile Failed", Toast.LENGTH_SHORT).show();
            }
        });


        return root;


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogout:

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                Toast.makeText(getActivity(), "Successfully Logout!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No, Nevermind", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
            case R.id.buttonUpload:
                Intent intentupload = new Intent(getActivity(), Upload.class);
                startActivity(intentupload);
                break;
            case R.id.buttonDelete:
                Intent intentdelete = new Intent(getActivity(), Delete.class);
                startActivity(intentdelete);
                break;
            default:
                break;
        }
    }
}