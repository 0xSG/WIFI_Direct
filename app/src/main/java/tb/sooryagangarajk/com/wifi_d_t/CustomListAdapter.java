package tb.sooryagangarajk.com.wifi_d_t;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sooryagangarajk on 17/03/18.
 */

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    private List<String> personName;
    private List<String> personEmail;

    public CustomListAdapter(Activity context, List<String> personEmail, List<String> personName) {

        super(context, R.layout.listview_row);

        this.context = context;
        this.personName = personEmail;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameTextViewID);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.infoTextViewID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(personName.get(position));
        infoTextField.setText(personEmail.get(position));

        return rowView;

    }

    ;
}
