package edu.udel.jtown.EscapeGame;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * A simple pop up style menu that uses an alertdialog with a nested
 * listview as the content.
 * 
 * @author jatlas
 *
 */
public class PopupMenu implements OnItemClickListener {
    private Context context;
    private ListView options;
    private String header;
    private OnItemClickListener listener;
    private AlertDialog dialog;
    
    public PopupMenu(Context context, String header, String[] menuOptions,
                     OnItemClickListener listener, int textSize) {
        this.context = context;
        this.options = new ListView(context);
        this.options.setAdapter(new SimpleTextViewArrayAdapter<String>(context, menuOptions, textSize));
        this.options.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        this.options.setOnItemClickListener(this);
        this.header = header;
        this.listener = listener;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(header);
        builder.setView(options);
        dialog = builder.create();
    }
    
    public void show() {
        dialog.show();
    }
    
    /**
     * Pass the event on the the listener and then close the dialog
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onItemClick(parent, view, position, id);
        dialog.dismiss();
    }
}
