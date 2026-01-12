package com.origin.launcher;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.origin.launcher.animation.DynamicAnim;

public class AutoFixActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autofix);
        setTitle("Shader Options");
        
        DynamicAnim.applyPressScaleRecursively(findViewById(android.R.id.content));
        
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
        backButton.setImageResource(R.drawable.ic_arrow_down);
        backButton.setRotation(0);
        
        ListView versionList = findViewById(R.id.version_list);
        String[] versions = {"v1.18.30", "v1.19.60", "v1.20.80", "v1.21.20", "v1.21.110+"};
        versionList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, versions));
        versionList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        
        findViewById(R.id.btn_apply_versions).setOnClickListener(v -> {
            android.util.SparseBooleanArray checked = versionList.getCheckedItemPositions();
            java.util.ArrayList<String> selected = new java.util.ArrayList<>();
            for (int i = 0; i < checked.size(); i++) {
                if (checked.valueAt(i)) selected.add(versions[checked.keyAt(i)]);
            }
            FeatureSettings.getInstance().setAutofixVersions(selected.toArray(new String[0]));
            finish();
        });
    }
}