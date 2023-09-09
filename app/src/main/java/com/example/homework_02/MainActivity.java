package com.example.homework_02;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework_02.dao.UserDAO;
import com.example.homework_02.database.AppDatabase;
import com.example.homework_02.databinding.ActivityMainBinding;
import com.example.homework_02.databinding.GetstudentBinding;
import com.example.homework_02.databinding.MenuBinding;
import com.example.homework_02.model.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MenuBinding menuBinding;
    GetstudentBinding getstudentBinding;
    private String selectedImageUrie;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        menuBinding = MenuBinding.inflate(getLayoutInflater());
        setContentView(menuBinding.getRoot());
        GetstudentBinding getstudentBinding = GetstudentBinding.inflate(getLayoutInflater());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        User user = new User();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mydb")
                .allowMainThreadQueries().build();

        UserDAO userDao = db.userDao();


       //button Insert
        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = binding.name.getText().toString();
                String passwords = binding.password.getText().toString();
                user.setName(names);
                user.setPassword(passwords);
                user.setImage(selectedImageUrie);
                userDao.insert(user);

            }
        });


//        custom action bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Set custom layout for the action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.menu);
//        end custom action bar



        TextView textViewShowgetData=getSupportActionBar().getCustomView().findViewById(R.id.show);
        //        textView for show getAllData
        textViewShowgetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View dialogLayout = inflater.inflate(R.layout.getstudent, null);
                TextView textView = dialogLayout.findViewById(R.id.getAll);
                List<User> allUsers = userDao.getAllUsers();
                StringBuilder userText = new StringBuilder();
                for (User u : allUsers) {
                    userText.append(u.name).append("\n");
                }
                // Set the user text in the TextView
                textView.setText(userText.toString());
                builder.setView(dialogLayout);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Set OnClickListener on image selection
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    openImagePicker();
                } else {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
                }
            }
        });

}

// Open the image picker
        private void openImagePicker() {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Retrieve the selected image URI
            Uri selectedImageUri = data.getData();
            // Convert the URI to a string and store it
           selectedImageUrie= selectedImageUri.toString();
           binding.image.setImageURI(selectedImageUri);
        }
    }



}

