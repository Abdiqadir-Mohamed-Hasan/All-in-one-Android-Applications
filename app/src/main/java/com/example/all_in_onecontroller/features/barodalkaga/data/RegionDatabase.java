package com.example.all_in_onecontroller.features.barodalkaga.data;

import com.example.all_in_onecontroller.features.barodalkaga.model.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Local repository providing accurate historical, geographical, administrative,
 * regional flag mapping, and official sub-district data for all 18 administrative regions of Somalia.
 * Sources: NBS Somalia (National Bureau of Statistics) & UN-OCHA (HumData).
 */
public class RegionDatabase {

    public static List<Region> getAllRegions() {
        List<Region> regions = new ArrayList<>();

        // 1. Awdal (Northern)
        regions.add(new Region(
                "awdal",
                "Awdal",
                "Borama",
                "1984",
                "Amoud University, lush agriculture, and the ancient port of Zeila (Saylac)",
                "Awdal is situated in the northwestern corner of Somalia. It has a rich history tied to the medieval Adal Sultanate, lush mountainous terrain, and holds Amoud University—the oldest modern university in Somalia.",
                "Northern",
                "hero_borama",
                "flag_somaliland",
                Arrays.asList("Borama", "Zeila (Saylac)", "Baki", "Lughaya")
        ));

        // 2. Waqooyi Galbeed / Maroodi Jeex (Northern)
        regions.add(new Region(
                "waqooyi_galbeed",
                "Waqooyi Galbeed",
                "Hargeysa",
                "1960",
                "Laas Geel prehistoric cave paintings, vibrant livestock markets, and Somali poetry",
                "Waqooyi Galbeed is a pivotal cultural and trade hub in northern Somalia. Its capital, Hargeysa, is famous for vibrant commerce, arts, and the world-renowned Neolithic rock art site at Laas Geel (c. 3,500 BC).",
                "Northern",
                "laas_geel",
                "flag_somaliland",
                Arrays.asList("Hargeysa", "Berbera", "Gabiley", "Salahlay")
        ));

        // 3. Togdheer (Northern)
        regions.add(new Region(
                "togdheer",
                "Togdheer",
                "Burao",
                "1973",
                "Burao Grand Livestock Market, classical poets, and the seasonal Togdheer River",
                "Togdheer is named after the Togdheer River flowing through it. The region is celebrated as the cradle of legendary Somali poetry, folklore, and one of the largest livestock trading hubs in the Horn of Africa.",
                "Northern",
                "hero_burao",
                "flag_somaliland",
                Arrays.asList("Burao", "Oodweyne", "Buhoodle", "Sheikh")
        ));

        // 4. Sanaag (Northern)
        regions.add(new Region(
                "sanaag",
                "Sanaag",
                "Erigavo (Ceerigaabo)",
                "1984",
                "Mount Shimbiris (highest peak in Somalia), Cal Madow forests, natural frankincense harvesting",
                "Sanaag is the largest region in northern Somalia, renowned for its dramatic mountain ranges, Daallo National Forest, natural frankincense and myrrh harvesting, and pristine Gulf of Aden coastline.",
                "Northern",
                "cal_madow",
                "flag_somaliland",
                Arrays.asList("Erigavo (Ceerigaabo)", "El Afweyn", "Badhan", "Dhahar", "Las Khorey")
        ));

        // 5. Bari (Northern)
        regions.add(new Region(
                "bari",
                "Bari",
                "Bosaso (Boosaaso)",
                "1960",
                "Major commercial deep seaport, date palm plantations, Cape Guardafui, and ancient spice trade",
                "Bari occupies the tip of the Horn of Africa. Boosaaso is an energetic port connecting Somalia to the Arabian Peninsula. The region features historic lighthouses, date groves, and marine fisheries.",
                "Northern",
                "hero_bosaso",
                "flag_puntland",
                Arrays.asList("Bosaso (Boosaaso)", "Qandala", "Caluula", "Iskushuban", "Bander Beyla", "Carmo")
        ));

        // 6. Sool (Northern)
        regions.add(new Region(
                "sool",
                "Sool",
                "Las Anod (Laascaanood)",
                "1984",
                "Taleh historical Dervish stone forts, camel pastoralism, and patriotic heritage",
                "Sool is steeped in history as the capital of the anti-colonial Dervish movement led by Sayid Mohamed Abdullah Hassan in Taleh. The region is famous for nomadic livestock wealth and rich oral poetry.",
                "Northern",
                "hero_las_anod",
                "flag_somaliland",
                Arrays.asList("Las Anod (Laascaanood)", "Taleh", "Khudun", "Ainabo")
        ));

        // 7. Nugaal (Central)
        regions.add(new Region(
                "nugaal",
                "Nugaal",
                "Garowe (Garoowe)",
                "1973",
                "Nugaal Valley pastoral grazing lands, administrative institutions, and central crossroads",
                "Nugaal connects northern and central Somalia through its scenic valley. Garowe is an important political and educational center with a booming government and commercial sector.",
                "Central",
                "hero_garowe",
                "flag_puntland",
                Arrays.asList("Garowe (Garoowe)", "Eyl", "Burtinle", "Dengorayo")
        ));

        // 8. Mudug (Central)
        regions.add(new Region(
                "mudug",
                "Mudug",
                "Galkayo (Gaalkacyo)",
                "1960",
                "Central trade crossroads, Hobyo ancient sultanate coastal port, and livestock trade",
                "Mudug sits in central Somalia with an expansive coastline along the Indian Ocean featuring the historic sultanate town of Hobyo. Galkayo serves as a major commercial transit hub.",
                "Central",
                "hero_galkayo",
                "flag_galmudug",
                Arrays.asList("Galkayo (Gaalkacyo)", "Hobyo", "Jariban", "Harardhere", "Goldogob")
        ));

        // 9. Galguduud (Central)
        regions.add(new Region(
                "galguduud",
                "Galguduud",
                "Dhusamareb (Dhuusamarreeb)",
                "1973",
                "Pastoral heritage, traditional governance, and central commercial trade routes",
                "Galguduud is located in central Somalia, bordered by Ethiopia and the Indian Ocean. It is known for resilient livestock pastoralism and strategic crossroads towns like Cadaado and Guriceel.",
                "Central",
                "hero_dhusamareb",
                "flag_galmudug",
                Arrays.asList("Dhusamareb", "Abudwak", "Adado", "El Buur", "El Dher")
        ));

        // 10. Hiran (Central)
        regions.add(new Region(
                "hiran",
                "Hiran",
                "Beledweyne",
                "1960",
                "Shabelle River agriculture, sweet melons, and the historic Beledweyne River Bridge",
                "Hiran is bisected by the Shabelle River, creating fertile agricultural lands along its banks. Beledweyne is an ancient commercial and transportation hub connecting southern and central Somalia.",
                "Central",
                "hero_beledweyne",
                "flag_hirshabelle",
                Arrays.asList("Beledweyne", "Buloburde", "Jalalaqsi", "Mataban", "Mahas")
        ));

        // 11. Middle Shabelle (Southern)
        regions.add(new Region(
                "middle_shabelle",
                "Middle Shabelle",
                "Jowhar",
                "1973",
                "Sugarcane plantations, rice and maize farming, and the Shabelle River irrigation delta",
                "Middle Shabelle (Shabeellaha Dhexe) has historically been an agricultural breadbasket, known for sugar production, tropical fruits, and scenic riverine farming villages along the Shabelle River.",
                "Southern",
                "hero_jowhar",
                "flag_hirshabelle",
                Arrays.asList("Jowhar", "Bal'ad", "Adan Yabal", "Cadale", "Mahaday")
        ));

        // 12. Banaadir (Southern)
        regions.add(new Region(
                "banaadir",
                "Banaadir",
                "Mogadishu (Muqdisho)",
                "1960",
                "National Capital City, Indian Ocean beaches (Liido), historic Hamar Weyne, and trade",
                "Banaadir is the smallest yet most populous region, encompassing the national capital Mogadishu. It boasts centuries-old mosques, Italian colonial architecture, universities, and major deepwater ports.",
                "Southern",
                "liido_beach",
                "flag_banaadir",
                Arrays.asList("Hamar Weyne", "Hodan", "Howlwadaag", "Karan", "Wadajir", "Dharkenley", "Daynile", "Yaqshid", "Boondhere", "Shibis", "Waberi", "Shangani", "Hamar Jajab", "Abdiaziz", "Kahda")
        ));

        // 13. Lower Shabelle (Southern)
        regions.add(new Region(
                "lower_shabelle",
                "Lower Shabelle",
                "Merca (Marka)",
                "1984",
                "Banana and citrus plantations, Indian Ocean fisheries, and historic coastal architecture",
                "Lower Shabelle (Shabeellaha Hoose) is famous for fertile farmland producing bananas, mangoes, and papayas, as well as historic trade ports like Merca and Baraawe with distinct white stone architecture.",
                "Southern",
                "hero_merca",
                "flag_southwest",
                Arrays.asList("Merca (Marka)", "Baraawe", "Afgooye", "Wanlaweyn", "Qoryoley", "Kurtunwarey", "Sablaale")
        ));

        // 14. Bakool (Southern)
        regions.add(new Region(
                "bakool",
                "Bakool",
                "Hudur (Xudur)",
                "1973",
                "Sorghum agriculture, cattle breeding, and resilient regional market towns",
                "Bakool is situated in southwestern Somalia bordering Ethiopia. It is known for dryland grain farming (sorghum), pastoral communities, and resilient local trade centers.",
                "Southern",
                "hero_hudur",
                "flag_southwest",
                Arrays.asList("Hudur (Xudur)", "El Barde", "Rabdhure", "Tieglo", "Waajid")
        ));

        // 15. Bay (Southern)
        regions.add(new Region(
                "bay",
                "Bay",
                "Baidoa (Baydhabo)",
                "1960",
                "City of Sorghum ('Baydhabo Janaay'), natural water springs, and rich agricultural harvests",
                "Bay is widely called the granary of Somalia. Baidoa, its capital, enjoys abundant underground water springs, limestone formations, and produces a major share of the nation's grains.",
                "Southern",
                "hero_baidoa",
                "flag_southwest",
                Arrays.asList("Baidoa (Baydhabo)", "Burhakaba", "Dinsoor", "Qansax Dheere")
        ));

        // 16. Gedo (Southern)
        regions.add(new Region(
                "gedo",
                "Gedo",
                "Garbahareey",
                "1980",
                "Juba River valleys, tri-border trade (Kenya & Ethiopia), and lush riverine farms",
                "Gedo is located in southwestern Somalia, traversed by the Juba River and sharing borders with both Ethiopia and Kenya. Important economic towns include Luuq, Baardheere, and Beled Xaawo.",
                "Southern",
                "hero_garbahareey",
                "flag_jubaland",
                Arrays.asList("Garbahareey", "Luuq", "Bardhere", "Beled Hawo", "El Wak", "Dollow")
        ));

        // 17. Middle Juba (Southern)
        regions.add(new Region(
                "middle_juba",
                "Middle Juba",
                "Bu'aale",
                "1984",
                "Rich agricultural river plains, natural forestry, and traditional artisanal river fishing",
                "Middle Juba (Jubbada Dhexe) lies along the lower reaches of the Juba River. Its fertile alluvial soil supports extensive farming, tropical wildlife, and natural forests.",
                "Southern",
                "hero_buale",
                "flag_jubaland",
                Arrays.asList("Bu'aale", "Jilib", "Sakow")
        ));

        // 18. Lower Juba (Southern)
        regions.add(new Region(
                "lower_juba",
                "Lower Juba",
                "Kismayo (Kismaayo)",
                "1960",
                "Kismayo deepwater port, Bajuni Islands, livestock export, and Indian Ocean fisheries",
                "Lower Juba (Jubbada Hoose) sits at the southern tip of Somalia. Kismayo is an economic powerhouse with an active port, beautiful coastline, nearby national parks, and fertile river mouths.",
                "Southern",
                "hero_kismayo",
                "flag_jubaland",
                Arrays.asList("Kismayo (Kismaayo)", "Afmadow", "Badhadhe", "Jamame")
        ));

        return regions;
    }

    public static Region getRegionByName(String name) {
        if (name == null) return null;

        String normalizedName = name.trim();
        if (normalizedName.equalsIgnoreCase("Woqooyi Galbeed")) normalizedName = "Waqooyi Galbeed";
        if (normalizedName.equalsIgnoreCase("Galgaduud")) normalizedName = "Galguduud";
        if (normalizedName.equalsIgnoreCase("Hiraan")) normalizedName = "Hiran";
        if (normalizedName.equalsIgnoreCase("Banadir")) normalizedName = "Banaadir";

        for (Region r : getAllRegions()) {
            if (r.getName().equalsIgnoreCase(normalizedName)) {
                return r;
            }
        }
        return null;
    }

    public static List<Region> getRegionsByZone(String zone) {
        if (zone == null || zone.equalsIgnoreCase("All")) {
            return getAllRegions();
        }
        List<Region> filtered = new ArrayList<>();
        for (Region r : getAllRegions()) {
            if (r.getZone().equalsIgnoreCase(zone.trim())) {
                filtered.add(r);
            }
        }
        return filtered;
    }
}
