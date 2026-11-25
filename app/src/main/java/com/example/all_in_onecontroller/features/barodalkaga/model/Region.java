package com.example.all_in_onecontroller.features.barodalkaga.model;

import android.content.Context;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data model representing a Somali region (Gobol).
 * Holds administrative attributes, geographic zone, sub-districts, and hero imagery metadata.
 */
public class Region implements Serializable {
    private final String id;
    private final String name;
    private final String capitalCity;
    private final String establishedYear;
    private final String famousFor;
    private final String description;
    private final String zone;              // "Northern", "Central", "Southern"
    private final String imageDrawableName; // Resource identifier for the capital hero image
    private final String flagDrawableName;  // Resource identifier for regional state flag
    private final List<String> subDistricts;

    public Region(String id, String name, String capitalCity, String establishedYear,
                  String famousFor, String description, String zone, String imageDrawableName,
                  String flagDrawableName, List<String> subDistricts) {
        this.id = id;
        this.name = name;
        this.capitalCity = capitalCity;
        this.establishedYear = establishedYear;
        this.famousFor = famousFor;
        this.description = description;
        this.zone = zone;
        this.imageDrawableName = imageDrawableName;
        this.flagDrawableName = flagDrawableName;
        this.subDistricts = subDistricts != null ? subDistricts : new ArrayList<>();
    }

    public Region(String id, String name, String capitalCity, String establishedYear,
                  String famousFor, String description, String zone, String imageDrawableName,
                  List<String> subDistricts) {
        this(id, name, capitalCity, establishedYear, famousFor, description, zone, imageDrawableName, "ic_somalia_flag", subDistricts);
    }

    public Region(String id, String name, String capitalCity, String establishedYear,
                  String famousFor, String description, String zone, String imageDrawableName) {
        this(id, name, capitalCity, establishedYear, famousFor, description, zone, imageDrawableName, "ic_somalia_flag", new ArrayList<>());
    }

    public String getFlagDrawableName() {
        return flagDrawableName;
    }

    /**
     * Resolves the regional flag drawable resource ID.
     */
    public int getFlagResId(Context context) {
        if (context == null) return 0;
        if (flagDrawableName != null && !flagDrawableName.trim().isEmpty()) {
            int resId = context.getResources().getIdentifier(flagDrawableName.trim(), "drawable", context.getPackageName());
            if (resId != 0) return resId;
        }
        return context.getResources().getIdentifier("ic_somalia_flag", "drawable", context.getPackageName());
    }

    public List<String> getSubDistricts() {
        return subDistricts;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public String getEstablishedYear() {
        return establishedYear;
    }

    public String getFamousFor() {
        return famousFor;
    }

    public String getDescription() {
        return description;
    }

    public String getZone() {
        return zone != null ? zone : "Somalia";
    }

    public String getImageDrawableName() {
        return imageDrawableName;
    }

    /**
     * Dynamically resolves the hero image drawable resource ID.
     * Looks up the primary imageDrawableName, attempts normalized variations,
     * and maps to real regional landscape photography (Laas Geel, Cal Madow, Liido Beach)
     * so that every single region displays a authentic photograph.
     */
    public int getImageResId(Context context) {
        if (context == null) return 0;

        // 1. Try explicit image drawable name
        if (imageDrawableName != null && !imageDrawableName.trim().isEmpty()) {
            int resId = context.getResources().getIdentifier(imageDrawableName.trim(), "drawable", context.getPackageName());
            if (resId != 0) return resId;
        }

        // 2. Try city name prefix (e.g. "hero_borama", "borama")
        if (capitalCity != null) {
            String sanitized = capitalCity.toLowerCase().split("[^a-z0-9]")[0];
            int res = context.getResources().getIdentifier("hero_" + sanitized, "drawable", context.getPackageName());
            if (res != 0) return res;

            res = context.getResources().getIdentifier(sanitized, "drawable", context.getPackageName());
            if (res != 0) return res;
        }

        // 3. Fallback to real photography assets according to zone
        if ("Northern".equalsIgnoreCase(zone)) {
            int res = context.getResources().getIdentifier("laas_geel", "drawable", context.getPackageName());
            if (res != 0) return res;
        } else if ("Southern".equalsIgnoreCase(zone)) {
            int res = context.getResources().getIdentifier("liido_beach", "drawable", context.getPackageName());
            if (res != 0) return res;
        }

        int calMadow = context.getResources().getIdentifier("cal_madow", "drawable", context.getPackageName());
        if (calMadow != 0) return calMadow;

        return context.getResources().getIdentifier("somalia_map", "drawable", context.getPackageName());
    }
}
