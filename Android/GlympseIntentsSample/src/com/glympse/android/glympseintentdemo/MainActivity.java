//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.glympseintentdemo;

import com.glympse.android.intent.GlympseApp;
import com.glympse.android.intent.CreateGlympseParams;
import com.glympse.android.intent.CreateGlympseResult;
import com.glympse.android.intent.ViewGlympseParams;
import com.glympse.android.intent.Helpers;
import com.glympse.android.intent.Recipient;
import com.glympse.android.intent.Common;
import com.glympse.android.intent.UriParser;
import com.glympse.android.intent.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity
{
    static public int ACTIVITY_CREATE_GLYMPSE = 1;
    
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override protected void onStart()
    {
        super.onStart();
        
        // If for some reason we cannot create and/or view a Glympse, then we
        // disable the buttons for those actions.
        findViewById(R.id.button_create).setEnabled(GlympseApp.canCreateGlympse(this));
        findViewById(R.id.button_view).setEnabled(GlympseApp.canViewGlympse(this, true));
    }
    
    public void onClickButtonCreate(View view)
    {
        // Allocate a CreateGlympseParams object. 
        CreateGlympseParams glympseCreateParams = new CreateGlympseParams();
        
        // Since we are just creating a "link" recipient, we hide the recipient chooser.
        glympseCreateParams.setFlags(Common.FLAG_RECIPIENTS_HIDDEN);

        // Grab the default subtype from our UI that we would like to use.
        String subtype = ((EditText)findViewById(R.id.edit_subtype)).getText().toString().trim();        
        
        // Grab the default brand from our UI that we would like to use.
        String brand = ((EditText)findViewById(R.id.edit_brand)).getText().toString().trim();        

        // Specify that we want a single "app" recipient.
        glympseCreateParams.setRecipient(new Recipient(Recipient.TYPE_APP, subtype, brand, null, null));

        // Grab the default duration from our UI that we would like to use.
        try
        {
            int minutes = Integer.parseInt(((EditText)findViewById(R.id.edit_duration)).getText().toString().trim());
            if (minutes >= 0)
            {
                glympseCreateParams.setDuration(minutes * 60 * 1000);
            }
        }
        catch (Throwable e)
        {
        }
        
        // Generate a "create a glympse" Intent and start the activity for it. 
        Intent intent = GlympseApp.createGlympse(this, glympseCreateParams);
        if (null != intent)
        {
            startActivityForResult(intent, ACTIVITY_CREATE_GLYMPSE);
        }
    }    

    public void onClickButtonView(View view)
    {
        // Grab the text buffer from our UI that we would like to parse for Glympse URLs.
        String buffer = ((EditText)findViewById(R.id.edit_buffer)).getText().toString();
        
        // Parse the buffer and check to see if it has any Glympse URLs that
        // contain glympse codes or group names. 
        UriParser parseBufferResult = GlympseApp.parseBuffer(buffer);
        if (parseBufferResult.hasGlympseOrGroup())
        {
            // Allocate a ViewGlympseParams object and tell it what we want to view. 
            ViewGlympseParams glympseViewParams = new ViewGlympseParams();
            glympseViewParams.addAllGlympsesAndGroups(parseBufferResult);

            // Generate a "view a glympse" Intent and start the activity for it. 
            Intent intent = GlympseApp.viewGlympse(this, true, glympseViewParams);
            if (null != intent)
            {
                startActivity(intent);
            }
        }
    }
    
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check to see if a "create a glympse" activity just finished. 
        if (ACTIVITY_CREATE_GLYMPSE == requestCode)
        {
            // Go process the this newly created Glympse.
            processCreateGlympseResult(GlympseApp.getCreateResult(resultCode, data));
        }
        
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processCreateGlympseResult(CreateGlympseResult createResult)
    {
        // Get the list of recipients for the Glympse. There should be just one.
        Recipient[] recipients = createResult.getRecipients();
        
        // Check to see if we have at least one recipient and it has a URL.
        if ((null != recipients) &&
            (recipients.length > 0) &&
            (null != recipients[0].getUrl()) &&
            !Helpers.isEmpty(recipients[0].getUrl()))
        {
            // Get the current text of our "links" section of our UI.
            TextView textView = (TextView)findViewById(R.id.text_links);
            String links = textView.getText().toString();
            
            // If we have no text, start off with some title text.
            if (links.length() <= 0)
            {
                links = "Recently created:";
            }
            
            // Append the URL and duration to the text and update our UI with the new text.
            textView.setText(links + "\n    " + recipients[0].getUrl() + " (" + (createResult.getDuration() / (60 * 1000)) + ")");
            textView.setVisibility(View.VISIBLE);
        }
    }
}
