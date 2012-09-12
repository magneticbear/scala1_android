package com.magneticbear.scala1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Struct_Event_Adapter extends ArrayAdapter<Struct_Event> 
{
	private ArrayList<Struct_Event> items;

    public Struct_Event_Adapter(Context context, int textViewResourceId, ArrayList<Struct_Event> items) 
    {
	    super(context, textViewResourceId, items);
	    this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        if (convertView == null) 
        {
            LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.struct_event_adapater_row, null);
        }
        
        Struct_Event event = items.get(position);
        if (event != null) 
        {
			TextView title    = (TextView) convertView.findViewById(R.id.struct_event_adapter_row_title);
			TextView subtitle = (TextView) convertView.findViewById(R.id.struct_event_adapter_row_subtitle);
			if (title != null) 
			{
			      title.setText(event.title);                            
			}
			if(subtitle != null)
			{
			      subtitle.setText(event.location);
			}
        }
        
        return convertView;
    }
}
