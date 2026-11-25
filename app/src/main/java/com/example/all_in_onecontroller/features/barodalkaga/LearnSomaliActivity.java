package com.example.all_in_onecontroller.features.barodalkaga;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.all_in_onecontroller.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Educational Activity offering essential Somali language vocabulary, common phrases,
 * and pronunciation guides sourced from Indiana University, Tatoeba, and Wiktionary.
 */
public class LearnSomaliActivity extends AppCompatActivity {

    private ImageButton btnBackLearn;
    private RecyclerView rvPhrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(
            this,
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        );
        setContentView(R.layout.activity_learn_somali);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.learnSomaliMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBackLearn = findViewById(R.id.btnBackLearn);
        rvPhrases = findViewById(R.id.rvPhrases);

        btnBackLearn.setOnClickListener(v -> finish());

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        PhraseAdapter adapter = new PhraseAdapter(getPhrases());
        rvPhrases.setAdapter(adapter);
    }

    private List<Phrase> getPhrases() {
        List<Phrase> phrases = new ArrayList<>();

        // Greetings & Social Etiquette
        phrases.add(new Phrase("Greetings", "Welcome", "Soo dhawoow", "Soo dha-wow"));
        phrases.add(new Phrase("Greetings", "Hello / Peace", "Nabad", "Na-bad"));
        phrases.add(new Phrase("Greetings", "How are you?", "Sidee tahay?", "Si-dee ta-hay"));
        phrases.add(new Phrase("Greetings", "I am fine / Peaceful", "Waan fiicanahay / Nabad waa", "Waan fee-ca-na-hay"));
        phrases.add(new Phrase("Greetings", "Thank you very much", "Mahadsanid aad u badan", "Ma-had-sa-nid aad u ba-dan"));
        phrases.add(new Phrase("Greetings", "Good morning", "Subax wanaagsan", "Su-bax wa-naag-san"));
        phrases.add(new Phrase("Greetings", "Good afternoon", "Galab wanaagsan", "Ga-lab wa-naag-san"));
        phrases.add(new Phrase("Greetings", "Good night", "Habeen wanaagsan", "Ha-been wa-naag-san"));

        // Identity & Conversation
        phrases.add(new Phrase("Conversation", "What is your name?", "Magacaa?", "Ma-ga-caa"));
        phrases.add(new Phrase("Conversation", "My name is...", "Magacaygu waa...", "Ma-ga-cay-gu waa"));
        phrases.add(new Phrase("Conversation", "Where are you from?", "Xaggee ka timid?", "Xag-gee ka ti-mid"));
        phrases.add(new Phrase("Conversation", "I am from Somalia", "Waan ka imid Soomaaliya", "Waan ka i-mid Soomaaliya"));
        phrases.add(new Phrase("Conversation", "Yes", "Haa", "Haa"));
        phrases.add(new Phrase("Conversation", "No", "Maya", "Ma-ya"));

        // Essential Needs & Travel
        phrases.add(new Phrase("Essentials", "Water", "Biyo", "Bi-yo"));
        phrases.add(new Phrase("Essentials", "Food / Meal", "Cunto", "Cun-to"));
        phrases.add(new Phrase("Essentials", "Where is the market?", "Xaggee buu ku yaal suuqu?", "Xag-gee buu ku yaal suu-qu"));
        phrases.add(new Phrase("Essentials", "How much is this?", "Pricetag / Immisa waa kan?", "Im-mi-sa waa kan"));
        phrases.add(new Phrase("Essentials", "Country", "Dal", "Dal"));
        phrases.add(new Phrase("Essentials", "Learn", "Baro", "Ba-ro"));

        // Numbers
        phrases.add(new Phrase("Numbers", "One", "Kow", "Kow"));
        phrases.add(new Phrase("Numbers", "Two", "Laba", "La-ba"));
        phrases.add(new Phrase("Numbers", "Three", "Saddex", "Sad-dex"));
        phrases.add(new Phrase("Numbers", "Four", "Afar", "A-far"));
        phrases.add(new Phrase("Numbers", "Five", "Shan", "Shan"));

        return phrases;
    }

    static class Phrase {
        final String category;
        final String english;
        final String somali;
        final String pronunciation;

        Phrase(String category, String english, String somali, String pronunciation) {
            this.category = category;
            this.english = english;
            this.somali = somali;
            this.pronunciation = pronunciation;
        }
    }

    static class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder> {

        private final List<Phrase> phrases;

        PhraseAdapter(List<Phrase> phrases) {
            this.phrases = phrases;
        }

        @NonNull
        @Override
        public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_somali_phrase, parent, false);
            return new PhraseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {
            Phrase phrase = phrases.get(position);
            holder.tvEnglish.setText(phrase.category + " • " + phrase.english);
            holder.tvSomali.setText(phrase.somali);
            holder.tvPronunciation.setText("Pronunciation: " + phrase.pronunciation);
        }

        @Override
        public int getItemCount() {
            return phrases.size();
        }

        static class PhraseViewHolder extends RecyclerView.ViewHolder {
            TextView tvEnglish, tvSomali, tvPronunciation;

            PhraseViewHolder(@NonNull View itemView) {
                super(itemView);
                tvEnglish = itemView.findViewById(R.id.tvEnglish);
                tvSomali = itemView.findViewById(R.id.tvSomali);
                tvPronunciation = itemView.findViewById(R.id.tvPronunciation);
            }
        }
    }
}