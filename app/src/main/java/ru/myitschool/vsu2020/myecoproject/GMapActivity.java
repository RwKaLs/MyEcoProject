package ru.myitschool.vsu2020.myecoproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GMapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    private DatabaseReference gmapDataBase;
    final String pathMarkers = "Markers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_map);
        gmapDataBase = FirebaseDatabase.getInstance().getReference(pathMarkers);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        ValueEventListener vevent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Markers m = ds.getValue(Markers.class);
                    assert m != null;
                    map.addMarker(new MarkerOptions().position(new LatLng(m.lat, m.lon)).title(String.valueOf(m.name)));
                    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(m.lat, m.lon)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        gmapDataBase.addValueEventListener(vevent);
        /*LatLng gallery = new LatLng(51.666287085217945, 39.19173002238678);
        map.addMarker(new MarkerOptions().position(gallery).title("Галерея Чижова (10:00 - 22:00)"));
        map.moveCamera(CameraUpdateFactory.newLatLng(gallery));*/
    }
}