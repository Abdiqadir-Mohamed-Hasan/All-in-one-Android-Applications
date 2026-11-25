package com.example.all_in_onecontroller.features.tourguide;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.all_in_onecontroller.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhrasebookActivity extends AppCompatActivity {

    private RecyclerView rvPhrasebook;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this,
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_phrasebook);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.phrasebookMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBackPhrasebook);
        rvPhrasebook = findViewById(R.id.rvPhrasebook);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Initialize TextToSpeech engine
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        PhraseAdapter adapter = new PhraseAdapter(getPhraseData(), (phrase) -> {
            if (tts != null) {
                tts.speak(phrase.somali, TextToSpeech.QUEUE_FLUSH, null, "phrase_tts");
            }
            Toast.makeText(PhrasebookActivity.this, "Audio: " + phrase.somali, Toast.LENGTH_SHORT).show();
        });
        rvPhrasebook.setAdapter(adapter);
    }

    private List<PhraseItem> getPhraseData() {
        List<PhraseItem> list = new ArrayList<>();

        // Greetings
        list.add(new PhraseItem("Greetings", "Welcome", "Soo dhawoow", "Soo dha-wow"));
        list.add(new PhraseItem("Greetings", "Hello / Peace", "Nabad gelyo", "Na-bad gel-yo"));
        list.add(new PhraseItem("Greetings", "How are you?", "Sidee tahay?", "Si-dee ta-hay"));
        list.add(new PhraseItem("Greetings", "Thank you very much", "Mahadsanid aad u badan", "Ma-had-sa-nid aad u ba-dan"));

        // Getting Around
        list.add(new PhraseItem("Getting Around", "Where is the hotel?", "Xaggee buu ku yaal huteelku?", "Xag-gee buu ku yaal hu-teel-ku"));
        list.add(new PhraseItem("Getting Around", "Take me to the beach", "I gee xeebta", "I gee xee-bta"));
        list.add(new PhraseItem("Getting Around", "Airport", "Garoonka diyaaradaha", "Ga-roon-ka di-yaa-ra-da-ha"));

        // Food & Dining
        list.add(new PhraseItem("Food & Dining", "I would like water", "Biyo baan rabaa", "Bi-yo baan ra-baa"));
        list.add(new PhraseItem("Food & Dining", "The food is delicious", "Cuntadu waa fantastik", "Cun-ta-du waa fan-tas-tik"));
        list.add(new PhraseItem("Food & Dining", "Spiced Tea", "Shaah Cadays", "Shaah Ca-days"));

        // Market & Bargaining
        list.add(new PhraseItem("Market & Bargaining", "How much is this?", "Immisa waa kan?", "Im-mi-sa waa kan"));
        list.add(new PhraseItem("Market & Bargaining", "Can you reduce the price?", "Qiimaha miyaad dhimi kartaa?", "Qii-ma-ha mi-yaad dhi-mi kar-taa"));

        // Emergencies
        list.add(new PhraseItem("Emergencies", "Help me!", "I caawi!", "I caa-wi"));
        list.add(new PhraseItem("Emergencies", "Where is the hospital?", "Xaggee buu ku yaal cisbitalku?", "Xag-gee buu ku yaal cis-bi-tal-ku"));

        return list;
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    interface OnPlayClickListener {
        void onPlay(PhraseItem phrase);
    }

    static class PhraseItem {
        final String category;
        final String english;
        final String somali;
        final String phonetic;

        PhraseItem(String category, String english, String somali, String phonetic) {
            this.category = category;
            this.english = english;
            this.somali = somali;
            this.phonetic = phonetic;
        }
    }

    static class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.ViewHolder> {

        private final List<PhraseItem> list;
        private final OnPlayClickListener listener;

        PhraseAdapter(List<PhraseItem> list, OnPlayClickListener listener) {
            this.list = list;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour_phrase, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PhraseItem item = list.get(position);
            holder.tvCategory.setText(item.category);
            holder.tvEnglishPhrase.setText(item.english);
            holder.tvSomaliPhrase.setText(item.somali);
            holder.tvPhonetic.setText("Phonetic: " + item.phonetic);

            holder.btnPlayAudio.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPlay(item);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvCategory, tvEnglishPhrase, tvSomaliPhrase, tvPhonetic;
            ImageButton btnPlayAudio;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvCategory = itemView.findViewById(R.id.tvCategory);
                tvEnglishPhrase = itemView.findViewById(R.id.tvEnglishPhrase);
                tvSomaliPhrase = itemView.findViewById(R.id.tvSomaliPhrase);
                tvPhonetic = itemView.findViewById(R.id.tvPhonetic);
                btnPlayAudio = itemView.findViewById(R.id.btnPlayAudio);
            }
        }
    }
}
