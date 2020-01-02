package com.example.mymovies.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mymovies.R;
import com.example.mymovies.SQLiteDB.SQLiteHelper;
import com.example.mymovies.Models.User;

import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    User currentUser;
    User originalUser;
    EditText UserName;
    EditText Email;
    EditText Password;
    ImageView ProfilePic;

    Button Reset;
    Button Save;

    SQLiteHelper sqliteHelper;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        originalUser = new User(currentUser);
        sqliteHelper = new SQLiteHelper(this);
        initViews();

    }

    private void initViews() {
        Email = findViewById(R.id.profile_et_email);
        Password = findViewById(R.id.profile_et_passwd);
        UserName = findViewById(R.id.profile_et_username);
        Reset = findViewById(R.id.profile_btn_reset);
        Save = findViewById(R.id.profile_btn_save);
        ProfilePic = findViewById(R.id.profile_iv_profilepic);

        ProfilePic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                return false;
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetDefaults();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveChanges();
            }
        });

        Email.setText(currentUser.email);
        Password.setText(currentUser.password);
        UserName.setText(currentUser.userName);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if((currentUser.imageURI!=null)&&(!currentUser.imageURI.equals(""))){
            Bitmap bitmap = BitmapFactory.decodeFile(currentUser.imageURI, options);
            ProfilePic.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 300, false));
            /*try {
                File f=new File(imgPath, "imgName.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                ProfilePic.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }*/
        }else{
            ProfilePic.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {

            if(data != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ProfilePic.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 300, false));
                    //MEG KELL ADNI AZ INTERNAL SORAGE HELZET VALAHOGZ
                    FileOutputStream out = new FileOutputStream(getFilesDir() + "\\" + currentUser.id + ".png");
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        out.close();
                    }
                    currentUser.imageURI = getFilesDir() + "\\" + currentUser.id + ".png";
                    /*ContentValues cv = new ContentValues();
                    cv.put(SQLiteHelper.KEY_IMAGE, currentUser.id + ".png"); //TODO:change this
                    (sqliteHelper.getWritableDatabase()).update(SQLiteHelper.TABLE_USERS, cv, SQLiteHelper.KEY_ID + "= ?", new String[]{currentUser.id});
*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            /*try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ProfilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                //InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }

    void SaveChanges(){
        currentUser.email = Email.getText().toString();
        currentUser.password = Password.getText().toString();
        currentUser.userName = UserName.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(SQLiteHelper.KEY_IMAGE, currentUser.imageURI);
        cv.put(SQLiteHelper.KEY_EMAIL, currentUser.email);
        cv.put(SQLiteHelper.KEY_PASSWORD, currentUser.password);
        cv.put(SQLiteHelper.KEY_USER_NAME, currentUser.userName);

        (sqliteHelper.getWritableDatabase()).update(SQLiteHelper.TABLE_USERS, cv, SQLiteHelper.KEY_ID + "= ?", new String[]{currentUser.id});

        originalUser = new User(currentUser);
    }

    void ResetDefaults(){
        Email.setText(originalUser.email);
        Password.setText(originalUser.password);
        UserName.setText(originalUser.userName);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if((originalUser.imageURI!=null)&&(!originalUser.imageURI.equals(""))){
            Bitmap bitmap = BitmapFactory.decodeFile(originalUser.imageURI, options);
            ProfilePic.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 300, false));
        }else{
            ProfilePic.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
