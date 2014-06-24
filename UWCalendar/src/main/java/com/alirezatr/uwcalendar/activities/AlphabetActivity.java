package com.alirezatr.uwcalendar.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.AlphabetListAdapter;
import com.alirezatr.uwcalendar.adapters.AlphabetListAdapter.Item;
import com.alirezatr.uwcalendar.listeners.SubjectsListener;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.network.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class AlphabetActivity extends ListActivity {
    private AlphabetListAdapter adapter = new AlphabetListAdapter();
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private NetworkManager networkManager;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_alphabet);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Subjects");
        actionBar.setSubtitle(getResources().getString(R.string.app_name));

        networkManager = new NetworkManager(this);
        dialog = new ProgressDialog(this);
        loadSubjects();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        AlphabetListAdapter.Item rowItem = (AlphabetListAdapter.Item) this.getListAdapter().getItem
                (position);
        Intent intent = new Intent(getListView().getContext(), CoursesActivity.class);
        Log.d("UWCC:", rowItem.title);
        intent.putExtra("SUBJECT", rowItem.title);
        getListView().getContext().startActivity(intent);
    }

    private void setAdapter(ArrayList<Subject> subjects) {
        List rows = new ArrayList();
        ArrayList<String> blacklist = filterSubjects();
        int start = 0;
        int end = 0;
        String previousLetter = null;
        Object[] tmpIndexItem = null;
        Pattern numberPattern = Pattern.compile("[0-9]");
        Subject subject;

        for(int i = 0; i < subjects.size(); i++) {
            subject = subjects.get(i);
            if(blacklist.contains(subject.getSubject())) {
                subjects.remove(subject);
            }
            else {
                String firstLetter = subject.getSubject().substring(0, 1);

                if (numberPattern.matcher(firstLetter).matches()) {
                    firstLetter = "#";
                }

                if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                    end = rows.size() - 1;
                    tmpIndexItem = new Object[3];
                    tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                    tmpIndexItem[1] = start;
                    tmpIndexItem[2] = end;
                    alphabet.add(tmpIndexItem);

                    start = end + 1;
                }

                if (!firstLetter.equals(previousLetter)) {
                    rows.add(new AlphabetListAdapter.Section(firstLetter));
                    sections.put(firstLetter, start);
                }

                rows.add(new Item(subject.getSubject(), subject.getDescription()));
                previousLetter = firstLetter;
            }
        }

        if(previousLetter != null) {
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        adapter.setRows(rows);
        setListAdapter(adapter);
    }

    public void loadSubjects() {
        dialog.setMessage("Loading subjects");
        dialog.show();
        networkManager.getSubjects(new SubjectsListener() {
            @Override
            public void onSuccess(ArrayList<Subject> subjects) {
                setAdapter(subjects);
                dialog.dismiss();
            }

            @Override
            public void onError(Exception error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error loading subjects, please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<String> filterSubjects() {
        ArrayList<String> blacklist = new ArrayList<String>();
        blacklist.add("AADMS");
        blacklist.add("AB");
        blacklist.add("ACC");
        blacklist.add("ACINTY");
        blacklist.add("ADMGT");
        blacklist.add("AES");
        blacklist.add("ARCHL");
        blacklist.add("ART");
        blacklist.add("ASIAN");
        blacklist.add("BE");
        blacklist.add("BOT");
        blacklist.add("CCIV");
        blacklist.add("CDNST");
        blacklist.add("CEDEV");
        blacklist.add("CM");
        blacklist.add("CMW");
        blacklist.add("COMST");
        blacklist.add("COMPT");
        blacklist.add("CONST");
        blacklist.add("COOP");
        blacklist.add("COGSCI");
        blacklist.add("CT");
        blacklist.add("CULMG");
        blacklist.add("CULT");
        blacklist.add("DANCE");
        blacklist.add("DES");
        blacklist.add("DEVIS");
        blacklist.add("DM");
        blacklist.add("ELE");
        blacklist.add("ELPE");
        blacklist.add("EVSY");
        blacklist.add("FILM");
        blacklist.add("FINAN");
        blacklist.add("FRCS");
        blacklist.add("GEMCC");
        blacklist.add("GEOE");
        blacklist.add("GEOL");
        blacklist.add("GGOV");
        blacklist.add("GLOBAL");
        blacklist.add("GS");
        blacklist.add("HEBRW");
        blacklist.add("HRCS");
        blacklist.add("HS");
        blacklist.add("HUNGN");
        blacklist.add("IFS");
        blacklist.add("INTERN");
        blacklist.add("INTTS");
        blacklist.add("ISS");
        blacklist.add("GRAD");
        blacklist.add("JS");
        blacklist.add("KPE");
        blacklist.add("LANG");
        blacklist.add("LATM");
        blacklist.add("LATAM");
        blacklist.add("LED");
        blacklist.add("LSC");
        blacklist.add("MEDST");
        blacklist.add("MENV");
        blacklist.add("MES");
        blacklist.add("MI");
        blacklist.add("MISC");
        blacklist.add("MSE");
        blacklist.add("NATST");
        blacklist.add("NES");
        blacklist.add("PAS");
        blacklist.add("PDENG");
        blacklist.add("PDARCH");
        blacklist.add("PDPHRM");
        blacklist.add("PED");
        blacklist.add("PERST");
        blacklist.add("PHS");
        blacklist.add("PS");
        blacklist.add("POLSH");
        blacklist.add("QIC");
        blacklist.add("RELC");
        blacklist.add("SEQ");
        blacklist.add("SOCIN");
        blacklist.add("SIPAR");
        blacklist.add("SOCWL");
        blacklist.add("SPD");
        blacklist.add("SUSM");
        blacklist.add("SWREN");
        blacklist.add("TAX");
        blacklist.add("THTRE");
        blacklist.add("TN");
        blacklist.add("TOUR");
        blacklist.add("TPM");
        blacklist.add("TPPE");
        blacklist.add("TS");
        blacklist.add("UKRAN");
        blacklist.add("UN");
        blacklist.add("UNIV");
        blacklist.add("URBAN");
        blacklist.add("UU");
        blacklist.add("WATER");
        blacklist.add("WHMIS");
        blacklist.add("ZOOL");

        return blacklist;
    }
}
