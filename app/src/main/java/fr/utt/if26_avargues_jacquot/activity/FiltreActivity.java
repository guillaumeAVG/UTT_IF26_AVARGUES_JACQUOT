package fr.utt.if26_avargues_jacquot.activity;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 04/12/2015.
 */

public class FiltreActivity extends ExpandableListActivity {
    private static final String arrGroupElements[] = { "Bon plans Ephémères", "Bon plans Statiques", "Commerces",
            "Santé", "Sport", "Adminstrations" };
    /**
     * strings for child elements
     */
    private static final String arrChildElements[][] = {
            { "Evènements étudiants","Réductions à court terme"},
            { "Réductions à long terme"},
            { "Alimentation","Laveries", "Distributeurs de billets, Stations essences","Garages" },
            { "Hopitaux","Médecins", "Pharmacies" },
            { "Salle de sport","Terrains de sport", "Stades" },
            { "CAF","Mairie", "Agences_de_transports", "Assurance maladie et mutuelles", "Emploi", "Maison des étudiants", "Police"}
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bon_plan);
        setListAdapter(new ExpandableListAdapter(this));
    }
    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context myContext;
        public ExpandableListAdapter(Context context) {
            myContext = context;
        }
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) myContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(
                        R.layout.article_list_child_item_layout, null);
            }
            TextView yourSelection = (TextView) convertView
                    .findViewById(R.id.articleContentTextView);
            yourSelection
                    .setText(arrChildElements[groupPosition][childPosition]);
            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return arrChildElements[groupPosition].length;
        }
        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }
        @Override
        public int getGroupCount() {
            return arrGroupElements.length;
        }
        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) myContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(
                        R.layout.article_list_item_layout, null);
            }
            TextView groupName = (TextView) convertView
                    .findViewById(R.id.articleHeaderTextView);
            groupName.setText(arrGroupElements[groupPosition]);
            return convertView;
        }
        @Override
        public boolean hasStableIds() {
            return false;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
