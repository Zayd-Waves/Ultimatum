package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.electricpanda.ultimatum.misc.PreferencesManager;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {

    private TextView usernameText;
    private Context mContext;
    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mContext = this;
        usernameText = (TextView) findViewById(R.id.usernameText);
        usernameText.setText(PreferencesManager.getUsername(mContext));

        /* Set up our custom ActionMenuView. */
        Toolbar t = (Toolbar) findViewById(R.id.tToolbar);
        amvMenu = (ActionMenuView) t.findViewById(R.id.amvMenu);
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
        TextView title = (TextView) t.findViewById(R.id.title);
        title.setText("About");
        setSupportActionBar(t);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about_screen, amvMenu.getMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;
    }
}
