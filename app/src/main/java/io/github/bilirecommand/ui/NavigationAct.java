package io.github.bilirecommand.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import io.github.bilirecommand.R;

public class NavigationAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        DrawerLayout drawerLayout = findViewById(R.id.dl_layout);
        ImageView imageView = findViewById(R.id.iv_menu);
        TextView title = findViewById(R.id.tv_title);

        imageView.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        NavigationView navigationView = findViewById(R.id.nv_content);
        navigationView.setItemIconTintList(null);


        NavController navController = Navigation.findNavController(this,R.id.fg_nav_host);
        NavigationUI.setupWithNavController(navigationView,navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            title.setText(destination.getLabel());
        });
    }
}