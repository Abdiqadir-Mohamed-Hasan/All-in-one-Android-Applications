package com.example.all_in_onecontroller.features.tourguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.all_in_onecontroller.R;
import com.example.all_in_onecontroller.features.tourguide.model.Attraction;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class AttractionDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ATTRACTION = "extra_attraction";
    private static final String PREF_BOOKMARKS = "dalxiis_bookmarks";

    private ImageButton btnBack;
    private ImageButton btnBookmark;
    private ImageView ivAttractionHero;
    private TextView tvDetailCategoryBadge;
    private TextView tvDetailAttractionTitle;
    private TextView tvDetailLocationBanner;
    private TextView tvDetailType;
    private TextView tvDetailCoordinates;
    private TextView tvDetailFullDescription;
    private MaterialButton btnOpenGoogleMaps;

    // Audio Guide Player Views
    private ImageButton btnPlayAudioGuide;
    private SeekBar seekBarAudio;
    private TextView tvAudioCurrentTime;

    private Attraction currentAttraction;
    private boolean isBookmarked = false;
    private boolean isAudioPlaying = false;
    private int audioProgress = 0;
    private Handler audioHandler = new Handler(Looper.getMainLooper());
    private Runnable audioRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this,
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_attraction_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.attractionDetailMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent() != null && getIntent().hasExtra(EXTRA_ATTRACTION)) {
            currentAttraction = (Attraction) getIntent().getSerializableExtra(EXTRA_ATTRACTION);
        }

        initViews();
        displayAttractionDetails();
        checkBookmarkState();
        setupClickListeners();
        setupAudioPlayer();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBackDetail);
        btnBookmark = findViewById(R.id.btnBookmark);
        ivAttractionHero = findViewById(R.id.ivAttractionHero);
        tvDetailCategoryBadge = findViewById(R.id.tvDetailCategoryBadge);
        tvDetailAttractionTitle = findViewById(R.id.tvDetailAttractionTitle);
        tvDetailLocationBanner = findViewById(R.id.tvDetailLocationBanner);
        tvDetailType = findViewById(R.id.tvDetailType);
        tvDetailCoordinates = findViewById(R.id.tvDetailCoordinates);
        tvDetailFullDescription = findViewById(R.id.tvDetailFullDescription);
        btnOpenGoogleMaps = findViewById(R.id.btnOpenGoogleMaps);

        btnPlayAudioGuide = findViewById(R.id.btnPlayAudioGuide);
        seekBarAudio = findViewById(R.id.seekBarAudio);
        tvAudioCurrentTime = findViewById(R.id.tvAudioCurrentTime);
    }

    private void displayAttractionDetails() {
        if (currentAttraction == null) {
            Toast.makeText(this, "Attraction details not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvDetailCategoryBadge.setText(currentAttraction.getCategory());
        tvDetailAttractionTitle.setText(currentAttraction.getName());
        tvDetailLocationBanner.setText(currentAttraction.getLocation());
        tvDetailType.setText(currentAttraction.getCategory());

        String coords = String.format(Locale.US, "%.4f° N, %.4f° E",
                currentAttraction.getLatitude(), currentAttraction.getLongitude());
        tvDetailCoordinates.setText(coords);

        tvDetailFullDescription.setText(currentAttraction.getDescription());

        int imgRes = currentAttraction.getImageResource();
        if (imgRes != 0) {
            ivAttractionHero.setImageResource(imgRes);
        } else {
            ivAttractionHero.setImageResource(R.drawable.liido_beach);
        }
    }

    private void checkBookmarkState() {
        if (currentAttraction == null) return;
        SharedPreferences prefs = getSharedPreferences(PREF_BOOKMARKS, Context.MODE_PRIVATE);
        isBookmarked = prefs.getBoolean(currentAttraction.getName(), false);
        updateBookmarkIcon();
    }

    private void toggleBookmark() {
        if (currentAttraction == null) return;
        isBookmarked = !isBookmarked;
        SharedPreferences prefs = getSharedPreferences(PREF_BOOKMARKS, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(currentAttraction.getName(), isBookmarked).apply();
        updateBookmarkIcon();

        String message = isBookmarked ? "Saved to Offline Bookmarks!" : "Removed from Bookmarks";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateBookmarkIcon() {
        if (btnBookmark != null) {
            btnBookmark.setImageResource(isBookmarked ? R.drawable.ic_bookmark : R.drawable.ic_bookmark);
            btnBookmark.setColorFilter(isBookmarked ? Color.parseColor("#F59E0B") : Color.WHITE);
        }
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnBookmark.setOnClickListener(v -> toggleBookmark());
        btnOpenGoogleMaps.setOnClickListener(v -> openInGoogleMaps());
    }

    private void setupAudioPlayer() {
        audioRunnable = new Runnable() {
            @Override
            public void run() {
                if (isAudioPlaying) {
                    audioProgress += 2;
                    if (audioProgress > 100) {
                        audioProgress = 0;
                        isAudioPlaying = false;
                        btnPlayAudioGuide.setImageResource(R.drawable.ic_play);
                    }
                    if (seekBarAudio != null) {
                        seekBarAudio.setProgress(audioProgress);
                    }
                    int totalSec = 270; // 4 mins 30 secs
                    int currSec = (int) (totalSec * (audioProgress / 100.0f));
                    int min = currSec / 60;
                    int sec = currSec % 60;
                    if (tvAudioCurrentTime != null) {
                        tvAudioCurrentTime.setText(String.format(Locale.US, "%02d:%02d", min, sec));
                    }
                    if (isAudioPlaying) {
                        audioHandler.postDelayed(this, 500);
                    }
                }
            }
        };

        if (btnPlayAudioGuide != null) {
            btnPlayAudioGuide.setOnClickListener(v -> {
                isAudioPlaying = !isAudioPlaying;
                if (isAudioPlaying) {
                    btnPlayAudioGuide.setImageResource(R.drawable.ic_pause);
                    audioHandler.post(audioRunnable);
                } else {
                    btnPlayAudioGuide.setImageResource(R.drawable.ic_play);
                    audioHandler.removeCallbacks(audioRunnable);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (audioHandler != null && audioRunnable != null) {
            audioHandler.removeCallbacks(audioRunnable);
        }
        super.onDestroy();
    }

    private void openInGoogleMaps() {
        if (currentAttraction == null) return;

        double lat = currentAttraction.getLatitude();
        double lng = currentAttraction.getLongitude();
        String label = Uri.encode(currentAttraction.getName());

        Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lng + "?q=" + lat + "," + lng + "(" + label + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        try {
            startActivity(mapIntent);
        } catch (Exception e) {
            Uri webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + lat + "," + lng);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webIntent);
        }
    }
}
