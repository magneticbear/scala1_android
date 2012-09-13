package com.magneticbear.scala1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Struct_Event_Adapter extends ArrayAdapter<Struct_Event> 
{
	private ArrayList<Struct_Event> items;

    public Struct_Event_Adapter(Context context, int textViewResourceId, ArrayList<Struct_Event> items) 
    {
	    super(context, textViewResourceId, items);
	    
	    // Associate arraylist of struct_event items
	    this.items = items;
	    
	    // Create separators
	    //createSeparators();
    }
    
    private void createSeparators()
    {
    	// No items? no seps
    	if(items.size() == 0) return;
    	
    	// Separate by day
    	int current_day = 1;
    	
    	// Add day 1 right at the top
    	items.add(0, new Struct_Event("Day " + current_day));
    	
    	// Compare rest to first day
    	for(int iter = 1; iter < items.size(); iter++)
    	{
    		// Calculate how many days between this event and the last
    		int day_distance = (int)( (items.get(iter).start_date.getTime() - items.get(iter - 1).start_date.getTime()) / (1000 * 60 * 60 * 24));
    		
    		// If there is more than one day
    		if(day_distance > 0)
    		{
    			// There is a day gap we need a new sep
    			current_day += day_distance;
    			
    			// Add day sep right above current event (that fell on a new day)
    	    	items.add(iter, new Struct_Event("Day " + current_day));
    	    	
    	    	// This will cause us to iterate the same event again if we dont manual step past it
    	    	iter++;
    	    	
    	    	// Move to next event different
    	    	continue;
    		}
    		else
    		{
    			// There is no day gap, no sep here
    			continue;
    		}
    	}
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {

        
        Struct_Event event = items.get(position);
        if (event != null) 
        {
        	// Is this a separator or an actual event
        	if(event.isSeparator)
        	{
        		// This is a seperator
        		
        		// If the view isnt built yet, or it isnt a separator view
	            if (convertView == null || convertView.findViewById(R.id.struct_event_adapter_row_separator_day_label) == null) 
	            {
	            	// Get the inflator
	                LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                
	                // Inflate an event row separator
	                convertView = inflator.inflate(R.layout.struct_event_adapater_row_separator, null);
	            }
        	}        	
        	else
	        {
        		// This is an actual event
        		
        		// If the view isnt built yet, or it is a separator view
	            if (convertView == null || convertView.findViewById(R.id.struct_event_adapter_row_separator_day_label) != null) 
	            {
	            	// Get the inflator
	                LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                
	                // Inflate an event row
	                convertView = inflator.inflate(R.layout.struct_event_adapater_row, null);
	            }
	        	
	            // Set the title and subtitle
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
        }
        
        return convertView;
    }
}
