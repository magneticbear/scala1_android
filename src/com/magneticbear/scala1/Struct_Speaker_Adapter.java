package com.magneticbear.scala1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Struct_Speaker_Adapter extends ArrayAdapter<Struct_Speaker> 
{
	private ArrayList<Struct_Speaker> items;

    public Struct_Speaker_Adapter(Context context, int textViewResourceId, ArrayList<Struct_Speaker> items) 
    {
	    super(context, textViewResourceId, items);
	    
	    // Associate arraylist of struct_event items
	    this.items = items;
	    
	    // Create separators
	    createSeparators();
    }
    
    private void createSeparators()
    {
    	// No items? no seps
    	if(items.size() == 0) return;
    	
    	// Separate by letter, get first letter in first name
    	String current_letter = items.get(0).name.substring(0, 1);
    	
    	// Add first letter right at the top
    	items.add(0, new Struct_Speaker(current_letter));
    	
    	// Compare rest to first letter (skip letter 1 sep and speaker 1)
    	for(int iter = 2; iter < items.size(); iter++)
    	{
    		// Get first letter of name at iter
    		String first_letter_of_name = items.get(iter).name.substring(0, 1);
    		
    		// If that letter is different than the current letter, make a new sep
    		if(!first_letter_of_name.equals(current_letter))
    		{
    			// Letter is different
    			
    			// Mark new letter
    			current_letter = first_letter_of_name;
    			
    			// Make seperator
    			items.add(iter, new Struct_Speaker(current_letter));
    		}
    	}
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        Struct_Speaker speaker = items.get(position);
        if (speaker != null) 
        {
        	// Is this a separator or an actual event
        	if(speaker.isSeparator)
        	{
        		// This is a seperator
        		
        		// If the view isnt built yet, or it isnt a separator view
	            if (convertView == null || convertView.findViewById(R.id.struct_speaker_adapter_row_separator_letter_label) == null) 
	            {
	            	// Get the inflator
	                LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                
	                // Inflate a speaker row separator
	                convertView = inflator.inflate(R.layout.struct_speaker_adapater_row_separator, null);
	            }
	            
	            // Set the title
	            TextView title = (TextView)convertView.findViewById(R.id.struct_speaker_adapter_row_separator_letter_label);
	            title.setText(speaker.seperator_title);
        	}        	
        	else
	        {
        		// This is an actual speaker
        		
        		// If the view isnt built yet, or it is a separator view
	            if (convertView == null || convertView.findViewById(R.id.struct_speaker_adapter_row_separator_letter_label) != null) 
	            {
	            	// Get the inflator
	                LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                
	                // Inflate an event row
	                convertView = inflator.inflate(R.layout.struct_speaker_adapater_row, null);
	            }
	        	
	            // Set the title
				TextView title    = (TextView) convertView.findViewById(R.id.struct_speaker_adapter_row_title);
				if (title != null) 
				{
				      title.setText(speaker.name);                            
				}
        	}
        }
        
        return convertView;
    }
}
