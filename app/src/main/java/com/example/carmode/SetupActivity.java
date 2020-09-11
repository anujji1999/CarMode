package com.example.carmode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.carmode.MESSAGE";
    private Switch appSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(WelcomeActivity.EXTRA_MESSAGE);

        List<ResolveInfo> appList = getInstalledAppList(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView setupText = new TextView((this));
        setupText.setText(R.string.settingsTop);
        linearLayout.addView(setupText);

        final List<ResolveInfo> allowedApps = new ArrayList<ResolveInfo>();
        for(final ResolveInfo item : appList) {
            Switch appSwitch = new Switch(this);
            appSwitch.setId(ViewCompat.generateViewId());
            final String appName = item.loadLabel(this.getPackageManager()).toString();
            Drawable appIcon = item.loadIcon(this.getPackageManager());
            appSwitch.setText(appName);
            appSwitch.setButtonDrawable(appIcon);
            linearLayout.addView(appSwitch);
            appSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                         allowedApps.add(item);
                    } else{
                        allowedApps.remove(item);
                    }
                }
            });
        }

        Button saveSettings = new Button((this));
        saveSettings.setText(R.string.saveSettings);
        linearLayout.addView(saveSettings);

        setContentView(linearLayout);
    }

    public List<ResolveInfo> getInstalledAppList(Context context){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities( mainIntent, 0);
        return pkgAppsList;
    }

    public void MainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String message = "passing a msg";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}