package com.svalero.comicbookstoresapp.view;

import static com.svalero.comicbookstoresapp.util.Constants.LOCATION_PERMISSION_REQUEST_CODE;
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
import com.svalero.comicbookstoresapp.contract.RegisterContract;
import com.svalero.comicbookstoresapp.presenter.RegisterPresenter;
import com.svalero.comicbookstoresapp.util.OuterBaseActivity;

public class RegisterView extends OuterBaseActivity implements RegisterContract.View, Style.OnStyleLoaded, OnMapClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private RegisterPresenter presenter;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private GesturesPlugin gesturesPlugin;
    private Point currentPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter = new RegisterPresenter(this, this);

        initializeMapView();
        initializePointAnnotationManager();
        initializeGesturesPlugin();
        checkLocationPermissions();
        setupInputFields();
    }

    private void initializeMapView() {
        mapView = findViewById(R.id.map_register);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);
    }

    private void initializePointAnnotationManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    private void initializeGesturesPlugin() {
        gesturesPlugin = GesturesUtils.getGestures(mapView);
        gesturesPlugin.addOnMapClickListener(this);
    }

    private void setupInputFields() {
        etUsername = findViewById(R.id.username_register);
        etPassword = findViewById(R.id.password_register);
        etEmail = findViewById(R.id.email_register);
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
        CameraState cameraState = mapView.getMapboxMap().getCameraState();
        addMarker(cameraState.getCenter().latitude(), cameraState.getCenter().longitude(), getString(R.string.no_gps_signal));
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

    public void addUser(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        presenter.saveUser(username, email, password, (float) currentPoint.latitude(), (float) currentPoint.longitude());
    }

    @Override
    public void showSaveUserSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    navigateToStoresMap();
                })
                .show();
    }

    @Override
    public void showSaveUserErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_user_register)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void navigateToStoresMap() {
        Intent intent = new Intent(this, StoresMapView.class);
        startActivity(intent);
        finish();
    }
}
