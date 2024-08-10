package com.svalero.comicbookstoresapp.view;

import android.Manifest;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
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
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.presenter.RegisterPresenter;

public class RegisterView extends AppCompatActivity implements RegisterContract.View, Style.OnStyleLoaded, OnMapClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private RegisterContract.Presenter presenter;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private GesturesPlugin gesturesPlugin;
    private Point currentPoint;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

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

        presenter = new RegisterPresenter(this, this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        initializeMapView();
        initializePointAnnotationManager();
        initializeGesturesPlugin();
        setupInputFields();
        checkLocationPermissions();
    }

    private void initializeMapView() {
        mapView = findViewById(R.id.mapView);
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
        etUsername = findViewById(R.id.username_etxt);
        etPassword = findViewById(R.id.password_etxt);
        etEmail = findViewById(R.id.email_etxt);
    }

    private void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            presenter.requestLocation();
        }
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
                setupMapWithoutGPS();
            }
        }
    }

    @Override
    public void showPermissionDeniedError() {
        Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLocation(double latitude, double longitude) {
        setStartingMap(latitude, longitude, "", 12);
    }

    @Override
    public void showLocationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void setupMapWithoutGPS() {
        setStartingMap(0, 0, getString(R.string.no_gps_signal), 2);
    }

    private void setStartingMap(double latitude, double longitude, String message, double zoom) {
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
    public void showSaveSuccessDialog(User user, String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    navigateToStoresMap(user);
                })
                .show();
    }

    @Override
    public void navigateToStoresMap(User user) {
        Intent intent = new Intent(this, StoresMapView.class);
        startActivity(intent);
    }

    @Override
    public void showSaveErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_user_register)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
