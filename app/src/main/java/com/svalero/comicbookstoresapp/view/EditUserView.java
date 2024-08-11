package com.svalero.comicbookstoresapp.view;

import static com.svalero.comicbookstoresapp.util.Constants.LOCATION_PERMISSION_REQUEST_CODE;
import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;
import static com.svalero.comicbookstoresapp.util.Constants.ZOOM_IN;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.CameraState;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.EditUserContract;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.dto.UserDTO;
import com.svalero.comicbookstoresapp.presenter.EditUserPresenter;
import com.svalero.comicbookstoresapp.util.InnerBaseActivity;

public class EditUserView extends InnerBaseActivity implements EditUserContract.View, Style.OnStyleLoaded, OnMapClickListener {
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditUserPresenter presenter;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private GesturesPlugin gesturesPlugin;
    private User user;
    private Point currentPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter = new EditUserPresenter(this, this);

        initializeMapView();
        initializePointAnnotationManager();
        initializeGesturesPlugin();
        presenter.getUser(getPrefs().getLong(PREFERENCES_ID, 0));
    }

    @Override
    public void setupUserData(User user) {
        this.user = user;
        showLocation(user.getLatitude(), user.getLongitude());
        setupInputFields();
    }

    @Override
    public void showGetUserErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_user_get)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void initializeMapView() {
        if (mapView == null) {
            mapView = findViewById(R.id.map_edit);
            mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);
        }
    }

    private void initializePointAnnotationManager() {
        if (pointAnnotationManager == null) {
            AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
            AnnotationConfig annotationConfig = new AnnotationConfig();
            pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
        }
    }

    private void initializeGesturesPlugin() {
        gesturesPlugin = GesturesUtils.getGestures(mapView);
        gesturesPlugin.addOnMapClickListener(this);
    }

    private void setupInputFields() {
        etUsername = findViewById(R.id.username_edit);
        etEmail = findViewById(R.id.email_edit);
        etPassword = findViewById(R.id.password_edit);

        etUsername.setText(user.getUsername());
        etEmail.setText(user.getEmail());
    }

    private void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE );
        } else {
            presenter.requestLocation();
        }
    }

    public void checkLocationPermissions(View view) {
        checkLocationPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.requestLocation();
            } else {
                showPermissionDeniedError();
                addNoGPSMarker();
            }
        }
    }

    @Override
    public void showPermissionDeniedError() {
        Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLocation(double latitude, double longitude) {
        setCamera(latitude, longitude, "", ZOOM_IN);
    }

    @Override
    public void showLocationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void addNoGPSMarker() {
        if (pointAnnotationManager.getAnnotations().isEmpty()) {
            CameraState cameraState = mapView.getMapboxMap().getCameraState();
            addMarker(cameraState.getCenter().latitude(), cameraState.getCenter().longitude(), getString(R.string.no_gps_signal));
        }
    }

    private void setCamera(double latitude, double longitude, String message, double zoom) {
        currentPoint = Point.fromLngLat(longitude, latitude);
        addMarker(latitude, longitude, message);
        mapView.getMapboxMap().setCamera(
                new CameraOptions.Builder()
                        .center(Point.fromLngLat(longitude, latitude))
                        .zoom(zoom)
                        .build()
        );
    }

    private void addMarker(double latitude, double longitude, String title) {
        pointAnnotationManager.deleteAll();
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker_foreground))
                .withIconAnchor(IconAnchor.BOTTOM)
                .withIconSize(0.5f)
                .withTextField(title);
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {}

    @Override
    public boolean onMapClick(@NonNull Point point) {
        currentPoint = point;
        addMarker(point.latitude(), point.longitude(), "");
        return false;
    }

    public void editUser(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.edit_dialog) + " " + user.getUsername())
                .setMessage(R.string.submit_changes)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    UserDTO userDTO = new UserDTO(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(),
                            (float) currentPoint.latitude(), (float) currentPoint.longitude());

                    presenter.updateUser(getPrefs().getLong(PREFERENCES_ID, 0), userDTO);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void showUpdateUserSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    navigateToStoresMap();
                })
                .show();
    }

    @Override
    public void showUpdateUserErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_user_update)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    public void deleteUser(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_dialog) + " " + user.getUsername())
                .setMessage(R.string.submit_delete)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    presenter.deleteUser(prefs.getLong(PREFERENCES_ID, 0));
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void showDeleteUserSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    navigateToMain();
                })
                .show();
    }

    @Override
    public void showDeleteUserErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_user_delete)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    public void navigateToStoresMap() {
        Intent intent = new Intent(this, StoresMapView.class);
        startActivity(intent);
        finish();
    }

    public void navigateToMain() {
        editor.remove(PREFERENCES_ID);
        editor.apply();
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
        finish();
    }
}
