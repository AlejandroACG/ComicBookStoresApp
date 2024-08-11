package com.svalero.comicbookstoresapp.view;

import static com.svalero.comicbookstoresapp.util.Constants.PREFERENCES_ID;
import static com.svalero.comicbookstoresapp.util.Constants.STORE_ID;
import static com.svalero.comicbookstoresapp.util.Constants.ZOOM_IN;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.gson.JsonObject;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.OnAnnotationClickListener;
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.svalero.comicbookstoresapp.R;
import com.svalero.comicbookstoresapp.contract.StoresMapContract;
import com.svalero.comicbookstoresapp.domain.Store;
import com.svalero.comicbookstoresapp.domain.User;
import com.svalero.comicbookstoresapp.presenter.StoresMapPresenter;
import com.svalero.comicbookstoresapp.util.InnerBaseActivity;
import java.util.List;
import java.util.Objects;

public class StoresMapView extends InnerBaseActivity implements StoresMapContract.View, Style.OnStyleLoaded, OnMapClickListener {
    private StoresMapPresenter presenter;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private GesturesPlugin gesturesPlugin;
    private List<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stores_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter = new StoresMapPresenter(this, this);

        initializeMapView();
        initializePointAnnotationManager();
        pointAnnotationManager.deleteAll();
        initializeGesturesPlugin();
        getStores();
        getUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_map) {
            Intent intent = new Intent(this, StoresMapView.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            editor.remove(PREFERENCES_ID);
            editor.apply();
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(this, EditUserView.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeMapView() {
        if (mapView == null) {
            mapView = findViewById(R.id.map_stores);
            mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);
        }
    }

    private void initializePointAnnotationManager() {
        if (pointAnnotationManager == null) {
            AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
            AnnotationConfig annotationConfig = new AnnotationConfig();
            pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
            pointAnnotationManager.addClickListener(annotation -> {
                int markerId = Objects.requireNonNull(annotation.getData()).getAsJsonObject().get("id").getAsInt();

                if (markerId >= 0) {
                    Intent intent = new Intent(StoresMapView.this, StoreDetailsView.class);
                    intent.putExtra(STORE_ID, markerId);
                    startActivity(intent);
                    return true;
                }
                return false;
            });
        }
    }

    private void initializeGesturesPlugin() {
        gesturesPlugin = GesturesUtils.getGestures(mapView);
        gesturesPlugin.addOnMapClickListener(this);
    }


    @Override
    public void getStores() {
        presenter.getStores();
    }

    @Override
    public void addStoreMarkers(List<Store> stores) {
        int i = 0;
        for (Store store : stores) {
            addMarker(store.getLatitude(), store.getLongitude(), store.getName(), i, R.mipmap.purple_marker_foreground);
            i++;
        }
    }

    @Override
    public void showGetStoresErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_stores_get)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    @Override
    public void getUser() {
        presenter.getUser(getPrefs().getLong(PREFERENCES_ID, 0));
    }

    @Override
    public void addUserMarker(User user) {
        addMarker(user.getLatitude(), user.getLongitude(), "Home", -1, R.mipmap.home_marker_foreground);
        mapView.getMapboxMap().setCamera(
                new CameraOptions.Builder()
                        .center(Point.fromLngLat(user.getLongitude(), user.getLatitude()))
                        .zoom(ZOOM_IN)
                        .build()
        );
    }

    @Override
    public void showGetUserErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_user_get)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void addMarker(double latitude, double longitude, String title, Integer markerId, Integer marker) {
        JsonObject markerData = new JsonObject();
        markerData.addProperty("id", markerId);
        markerData.addProperty("title", title);

        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(BitmapFactory.decodeResource(getResources(), marker))
                .withIconAnchor(IconAnchor.BOTTOM)
                .withIconSize(0.5f)
                .withTextField(title)
                .withData(markerData);

        pointAnnotationManager.create(pointAnnotationOptions);
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {}

    @Override
    public boolean onMapClick(@NonNull Point point) {
        return false;
    }
}
