package com.example.all_in_onecontroller.features.tourguide;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.all_in_onecontroller.R;
import com.example.all_in_onecontroller.features.tourguide.adapter.AttractionAdapter;
import com.example.all_in_onecontroller.features.tourguide.data.TourData;
import com.example.all_in_onecontroller.features.tourguide.model.Attraction;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

/**
 * Screen 1: Home / Discover Screen ("Dalxiis Somalia")
 * Displays hero banner, quick category chips, featured destinations carousel,
 * cultural spotlight card, and full bottom navigation bar across all 5 core sections.
 */
public class TourGuideHomeActivity extends AppCompatActivity implements AttractionAdapter.OnAttractionClickListener {

    public static final String EXTRA_CATEGORY_NAME = "extra_category_name";

    private ImageButton btnBack;
    private RecyclerView rvFeaturedDestinations;
    private TextView tvSeeAllDestinations;
    private MaterialCardView cardCulturalSpotlight;

    // Category Chips
    private MaterialButton chipDestinations;
    private MaterialButton chipCulture;
    private MaterialButton chipCuisine;
    private MaterialButton chipPhrasebook;
    private MaterialButton chipEssentials;

    // Bottom Nav Views
    private LinearLayout navDiscover;
    private LinearLayout navDestinations;
    private LinearLayout navCulture;
    private LinearLayout navPhrasebook;
    private LinearLayout navEssentials;

    private AttractionAdapter featuredAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this,
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_tour_guide_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tourHomeMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupFeaturedCarousel();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBackTourHome);
        rvFeaturedDestinations = findViewById(R.id.rvFeaturedDestinations);
        tvSeeAllDestinations = findViewById(R.id.tvSeeAllDestinations);
        cardCulturalSpotlight = findViewById(R.id.cardCulturalSpotlight);

        chipDestinations = findViewById(R.id.chipDestinations);
        chipCulture = findViewById(R.id.chipCulture);
        chipCuisine = findViewById(R.id.chipCuisine);
        chipPhrasebook = findViewById(R.id.chipPhrasebook);
        chipEssentials = findViewById(R.id.chipEssentials);

        navDiscover = findViewById(R.id.navDiscover);
        navDestinations = findViewById(R.id.navDestinations);
        navCulture = findViewById(R.id.navCulture);
        navPhrasebook = findViewById(R.id.navPhrasebook);
        navEssentials = findViewById(R.id.navEssentials);
    }

    private void setupFeaturedCarousel() {
        featuredAdapter = new AttractionAdapter(this, new ArrayList<>(), this);
        rvFeaturedDestinations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvFeaturedDestinations.setAdapter(featuredAdapter);

        // Load 5 curated featured destinations (Mogadishu, Laas Geel, Berbera, Bosaso, Kismayo)
        ArrayList<Attraction> all = TourData.getAllAttractions();
        ArrayList<Attraction> featured = new ArrayList<>();

        for (Attraction a : all) {
            String name = a.getName().toLowerCase();
            if (name.contains("liido") || name.contains("laas geel") || name.contains("berbera")
                    || name.contains("kismayo") || name.contains("mogadishu") || name.contains("garowe")) {
                featured.add(a);
            }
        }
        if (featured.isEmpty()) {
            featured.addAll(all);
        }
        featuredAdapter.updateList(featured);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        // See All -> AttractionsListActivity
        View.OnClickListener openExplorer = v -> {
            Intent intent = new Intent(TourGuideHomeActivity.this, AttractionsListActivity.class);
            startActivity(intent);
        };
        tvSeeAllDestinations.setOnClickListener(openExplorer);
        chipDestinations.setOnClickListener(openExplorer);
        navDestinations.setOnClickListener(openExplorer);

        // Culture & Heritage / Spotlight
        View.OnClickListener openCulture = v -> {
            Intent intent = new Intent(TourGuideHomeActivity.this, CultureHubActivity.class);
            startActivity(intent);
        };
        chipCulture.setOnClickListener(openCulture);
        chipCuisine.setOnClickListener(openCulture);
        cardCulturalSpotlight.setOnClickListener(openCulture);
        navCulture.setOnClickListener(openCulture);

        // Phrasebook
        View.OnClickListener openPhrasebook = v -> {
            Intent intent = new Intent(TourGuideHomeActivity.this, PhrasebookActivity.class);
            startActivity(intent);
        };
        chipPhrasebook.setOnClickListener(openPhrasebook);
        navPhrasebook.setOnClickListener(openPhrasebook);

        // Travel Essentials
        View.OnClickListener openEssentials = v -> {
            Intent intent = new Intent(TourGuideHomeActivity.this, TravelEssentialsActivity.class);
            startActivity(intent);
        };
        chipEssentials.setOnClickListener(openEssentials);
        navEssentials.setOnClickListener(openEssentials);
    }

    @Override
    public void onAttractionClick(Attraction attraction) {
        Intent intent = new Intent(TourGuideHomeActivity.this, AttractionDetailActivity.class);
        intent.putExtra(AttractionDetailActivity.EXTRA_ATTRACTION, attraction);
        startActivity(intent);
    }
}
