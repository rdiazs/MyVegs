package org.rdiazs.android.myvegs.addveg.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.rdiazs.android.myvegs.MyVegsApp;
import org.rdiazs.android.myvegs.R;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegPresenter;
import org.rdiazs.android.myvegs.entities.Veg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Actividad para añadir un cultivo.
 */
public class AddVegActivity extends AppCompatActivity implements AddVegView {
    @Bind(R.id.container)
    RelativeLayout container;
    @Bind(R.id.image)
    CircleImageView image;
    @Bind(R.id.inputName)
    EditText inputName;
    @Bind(R.id.inputTime)
    EditText inputTime;
    @Bind(R.id.btnIncrease)
    Button btnIncrease;
    @Bind(R.id.btnDecrease)
    Button btnDecrease;
    @Bind(R.id.txtLight)
    TextView txtLight;
    @Bind(R.id.ratingLight)
    RatingBar ratingLight;
    @Bind(R.id.txtWater)
    TextView txtWater;
    @Bind(R.id.ratingWater)
    RatingBar ratingWater;
    @Bind(R.id.txtGround)
    TextView txtGround;
    @Bind(R.id.ratingGround)
    RatingBar ratingGround;
    @Bind(R.id.btnClear)
    Button btnClear;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private final int TIME_STEP_CHANGE = 1;
    private final int REQUEST_PICTURE = 2;

    @Inject
    AddVegPresenter presenter;

    private MyVegsApp app;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_veg);
        ButterKnife.bind(this);

        app = (MyVegsApp) getApplication();

        setupInjection();

        presenter.onCreate();
    }

    /**
     * Configura la inyección de dependencias.
     */
    private void setupInjection() {
        app.getAddVegComponent(this).inject(this);
    }

    /**
     * Pulsación sobre el botón de incrementar el tiempo de germinación.
     */
    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease() {
        hideKeyboard();
        handleTimeChange(TIME_STEP_CHANGE);
    }

    /**
     * Pulsación sobre el botón de decrementar el tiempo de germinación.
     */
    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease() {
        hideKeyboard();
        handleTimeChange(-TIME_STEP_CHANGE);
    }

    /**
     * Pulsación sobre el botón de añadir cultivo.
     */
    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit() {
        hideKeyboard();

        String strName = inputName.getText().toString().trim();
        String strGermination = inputTime.getText().toString().trim();
        float fLight = ratingLight.getRating();
        float fWater = ratingWater.getRating();
        float fGround = ratingGround.getRating();

        if (!strName.isEmpty() && !strGermination.isEmpty()
                && fLight > 0 && fWater > 0 && fGround > 0) {
            int germination = Integer.parseInt(strGermination);

            final Veg veg = new Veg(strName, germination, fLight, fWater, fGround);
            veg.setImage(photoPath);

            presenter.addVeg(veg);
        } else {
            Toast.makeText(this, R.string.addveg_error_message_fields, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Pulsación sobre el botón que limpia los campos.
     */
    @OnClick(R.id.btnClear)
    public void handleClickClear() {
        hideKeyboard();
        resetFields();
        enableFields(true);
    }

    /**
     * Gestiona el valor del tiempo de germinación.
     * @param change valor de cambio del tiempo.
     */
    private void handleTimeChange(int change) {
        int timeGermination = 0;
        String strTimeGermination = inputTime.getText().toString().trim();

        if (!strTimeGermination.isEmpty()) {
            timeGermination = Integer.parseInt(strTimeGermination);
        } else {
            inputTime.setText(String.valueOf(0));
        }

        timeGermination += change;

        if (timeGermination > 0) {
            inputTime.setText(String.valueOf(timeGermination));
        }
        if (timeGermination <= 0) {
            Toast.makeText(this, getString(R.string.info_germination_time), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void enableFields(boolean enable) {
        inputName.setEnabled(enable);
        inputTime.setEnabled(enable);
        btnIncrease.setEnabled(enable);
        btnDecrease.setEnabled(enable);

        image.setEnabled(enable);

        ratingLight.setEnabled(enable);
        ratingWater.setEnabled(enable);
        ratingGround.setEnabled(enable);

        btnClear.setEnabled(enable);
        btnSubmit.setEnabled(enable);
    }

    @Override
    public void resetFields() {
        inputName.setText("");
        inputTime.setText("");

        ratingLight.setRating(0f);
        ratingWater.setRating(0f);
        ratingGround.setRating(0f);

        image.setImageResource(android.R.drawable.ic_menu_camera);
    }

    @Override
    public void vegAdded() {
        Snackbar.make(container, getString(R.string.addveg_notice_message_veg_added),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void vegAdding() {
        Snackbar.make(container, getString(R.string.addveg_notice_message_veg_adding),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void vegNotAdded() {
        Snackbar.make(container, getString(R.string.addveg_error_addveg),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        try {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICTURE) {
            if (resultCode == RESULT_OK) {
                boolean fromCamera = (data == null | data.getData() == null);

                if (fromCamera) {
                    image.setImageURI(Uri.parse(photoPath));
                } else {
                    photoPath = getRealPathFromUri(data.getData());
                    image.setImageURI(Uri.parse(photoPath));
                }
            }
        }
    }

    /**
     * Pulsación sobre el botón de añadir imagen del cultivo.
     */
    @OnClick(R.id.image)
    public void takePictureImage() {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra("return-data", true);

        File photoFile = getFile();

        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                intentList = addIntentsToList(intentList, cameraIntent);
            }
        }

        if (pickIntent.resolveActivity(getPackageManager()) != null) {
            intentList = addIntentsToList(intentList, pickIntent);
        }

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    getString(R.string.main_message_picture_source));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new
                    Parcelable[]{}));
        }

        if (chooserIntent != null) {
            startActivityForResult(chooserIntent, REQUEST_PICTURE);
        }
    }

    private File getFile() {
        File photoFile = null;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilename = "JPEG_" + timestamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_PICTURES);

        try {
            photoFile = File.createTempFile(imageFilename, ".jpg", storageDir);
        } catch (IOException e) {
            Snackbar.make(container, R.string.main_error_dispatch_camera, Snackbar.LENGTH_SHORT)
                    .show();
        }
        photoPath = photoFile.getAbsolutePath();

        return photoFile;
    }

    private List<Intent> addIntentsToList(List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetIntent = new Intent(intent);

            targetIntent.setPackage(packageName);
            list.add(targetIntent);
        }

        return list;
    }

    private String getRealPathFromUri(Uri data) {
        String result = null;
        Cursor cursor = getContentResolver().query(data, null, null, null, null);

        if (cursor == null) {
            result = data.getPath();
        } else {
            if (data.toString().contains("mediaKey")) {
                cursor.close();

                try {
                    File file = File.createTempFile("tempImg", ".jpg", getCacheDir());
                    InputStream input = getContentResolver().openInputStream(data);
                    OutputStream output = new FileOutputStream(file);

                    try {
                        byte[] buffer = new byte[4 * 1024];
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }

                        output.flush();
                        result = file.getAbsolutePath();
                    } finally {
                        output.close();
                        input.close();
                    }
                } catch (Exception e) {
                }
            } else { // Cuando se usa la galería
                cursor.moveToFirst();
                int dataColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(dataColumn);
                cursor.close();
            }
        }

        return result;
    }
}
