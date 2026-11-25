package com.example.all_in_onecontroller.features.barodalkaga;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.all_in_onecontroller.R;
import com.example.all_in_onecontroller.features.barodalkaga.data.RegionDatabase;
import com.example.all_in_onecontroller.features.barodalkaga.model.Region;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * SCREEN 3: Interactive Region Detail Screen with Capital City Hero Image.
 * Displays immersive imagery, regional metrics, cultural highlights, and interactive share actions.
 */
public class RegionDetailActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ImageButton btnShare;
    private ImageView ivRegionHero;
    private ImageView ivDetailFlag;
    private TextView tvDetailZoneBadge;
    private TextView tvDetailHeroYear;
    private TextView tvDetailRegionName;
    private TextView tvDetailCapitalBanner;
    private TextView tvDetailCapital;
    private TextView tvDetailEstablished;
    private TextView tvDetailFamousFor;
    private TextView tvDetailDescription;
    private TextView tvDetailSubDistricts;
    private TextView tvDetailDistrictCount;
    private LinearLayout layoutDistrictsContainer;
    private MaterialButton btnShareBottom;

    private String regionName = "Awdal";
    private Region currentRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_region_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.regionDetailMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Receive Intent extras passed from Screen 1 or Screen 2
        if (getIntent() != null && getIntent().hasExtra(RegionsListActivity.EXTRA_REGION_NAME)) {
            String name = getIntent().getStringExtra(RegionsListActivity.EXTRA_REGION_NAME);
            if (name != null) {
                regionName = name;
            }
        }

        initViews();
        displayRegionDetails();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        ivRegionHero = findViewById(R.id.ivRegionHero);
        ivDetailFlag = findViewById(R.id.ivDetailFlag);
        tvDetailZoneBadge = findViewById(R.id.tvDetailZoneBadge);
        tvDetailHeroYear = findViewById(R.id.tvDetailHeroYear);
        tvDetailRegionName = findViewById(R.id.tvDetailRegionName);
        tvDetailCapitalBanner = findViewById(R.id.tvDetailCapitalBanner);
        tvDetailCapital = findViewById(R.id.tvDetailCapital);
        tvDetailEstablished = findViewById(R.id.tvDetailEstablished);
        tvDetailFamousFor = findViewById(R.id.tvDetailFamousFor);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvDetailSubDistricts = findViewById(R.id.tvDetailSubDistricts);
        tvDetailDistrictCount = findViewById(R.id.tvDetailDistrictCount);
        layoutDistrictsContainer = findViewById(R.id.layoutDistrictsContainer);
        btnShareBottom = findViewById(R.id.btnShareBottom);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnShare.setOnClickListener(v -> shareRegionFacts());
        if (btnShareBottom != null) {
            btnShareBottom.setOnClickListener(v -> shareRegionFacts());
        }
    }

    private void displayRegionDetails() {
        // Fetch the region data model from our repository
        currentRegion = RegionDatabase.getRegionByName(regionName);

        if (currentRegion != null) {
            tvDetailRegionName.setText(currentRegion.getName());
            tvDetailCapitalBanner.setText("Capital: " + currentRegion.getCapitalCity());
            tvDetailZoneBadge.setText(currentRegion.getZone() + " Zone");
            tvDetailHeroYear.setText("Est. " + currentRegion.getEstablishedYear());

            tvDetailCapital.setText(currentRegion.getCapitalCity());
            tvDetailEstablished.setText(currentRegion.getEstablishedYear());
            tvDetailFamousFor.setText(currentRegion.getFamousFor());
            tvDetailDescription.setText(currentRegion.getDescription());

            // Render Sub-districts as full-width Material Cards
            if (layoutDistrictsContainer != null) {
                layoutDistrictsContainer.removeAllViews();
                List<String> districts = currentRegion.getSubDistricts();
                if (districts != null && !districts.isEmpty()) {
                    if (tvDetailDistrictCount != null) {
                        tvDetailDistrictCount.setText(districts.size() + " Districts");
                    }
                    for (String districtName : districts) {
                        MaterialCardView card = new MaterialCardView(this);
                        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        cardParams.setMargins(0, 0, 0, 10); // Spacing between cards
                        card.setLayoutParams(cardParams);
                        card.setRadius(24f); // Rounded corners
                        card.setCardElevation(2f);
                        card.setCardBackgroundColor(getResources().getColor(R.color.surface_background));
                        card.setStrokeColor(getResources().getColor(R.color.card_stroke));
                        card.setStrokeWidth(1);

                        LinearLayout cardContent = new LinearLayout(this);
                        cardContent.setOrientation(LinearLayout.HORIZONTAL);
                        cardContent.setPadding(28, 20, 28, 20);
                        cardContent.setGravity(Gravity.CENTER_VERTICAL);

                        ImageView pinIcon = new ImageView(this);
                        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(36, 36);
                        pinIcon.setLayoutParams(iconParams);
                        pinIcon.setImageResource(R.drawable.ic_location_pin);
                        pinIcon.setColorFilter(getResources().getColor(R.color.color_active_green));

                        TextView districtText = new TextView(this);
                        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                        );
                        textParams.setMarginStart(16);
                        districtText.setLayoutParams(textParams);
                        districtText.setText("Degmada " + districtName);
                        districtText.setTextColor(getResources().getColor(R.color.text_primary));
                        districtText.setTextSize(14f);
                        districtText.setTypeface(null, Typeface.BOLD);

                        cardContent.addView(pinIcon);
                        cardContent.addView(districtText);
                        card.addView(cardContent);

                        layoutDistrictsContainer.addView(card);
                    }
                } else {
                    if (tvDetailDistrictCount != null) tvDetailDistrictCount.setText("N/A");
                }
            }

            // Load Capital City Hero Image
            int heroRes = currentRegion.getImageResId(this);
            if (heroRes != 0) {
                ivRegionHero.setImageResource(heroRes);
            } else {
                ivRegionHero.setImageResource(R.drawable.bg_region_hero_default);
            }

            if (ivDetailFlag != null) {
                int flagRes = currentRegion.getFlagResId(this);
                if (flagRes != 0) {
                    ivDetailFlag.setImageResource(flagRes);
                }
            }
        }
    }

    private void shareRegionFacts() {
        if (currentRegion == null) return;

        String shareText = "Explore Somalia - " + currentRegion.getName() + " Region\n\n"
                + "• Capital City: " + currentRegion.getCapitalCity() + "\n"
                + "• Established: " + currentRegion.getEstablishedYear() + "\n"
                + "• Geographic Zone: " + currentRegion.getZone() + " Somalia\n"
                + "• Famous For: " + currentRegion.getFamousFor() + "\n\n"
                + currentRegion.getDescription();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, currentRegion.getName() + " - Somalia");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        try {
            startActivity(Intent.createChooser(shareIntent, "Share " + currentRegion.getName() + " Facts"));
        } catch (Exception e) {
            Toast.makeText(this, "Unable to share at this moment", Toast.LENGTH_SHORT).show();
        }
    }
}
