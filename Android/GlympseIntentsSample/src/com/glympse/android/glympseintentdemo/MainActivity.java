//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.glympseintentdemo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.glympse.android.intent.CreateGlympseParams;
import com.glympse.android.intent.GlympseApp;
import com.glympse.android.intent.GlympseCallbackParams;
import com.glympse.android.intent.Helpers;
import com.glympse.android.intent.Recipient;
import com.glympse.android.intent.UriParser;
import com.glympse.android.intent.ViewGlympseParams;
import com.glympse.android.intent.demo.R;

public class MainActivity extends Activity implements GlympseApp.StatusListener
{
    protected static final int REQUEST_PICK_PHOTO = 1;

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

    public void onClickPickAvatar(View view)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, REQUEST_PICK_PHOTO);
        }
        catch (Throwable e)
        {
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        // Check if we are returning from our OS photo picker.
        if ((REQUEST_PICK_PHOTO == requestCode) && (RESULT_OK == resultCode) && (null != intent))
        {
            // Extract a file URI from the Intent result.
            Cursor cursor  = null;
            String fileUri = null;
            try
            {
                cursor = MediaStore.Images.Media.query(getContentResolver(), intent.getData(), null);
                cursor.moveToFirst();
                fileUri = Uri.fromFile(new File(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)))).toString();
            }
            catch (Throwable e)
            {
            }
            finally
            {
                if (null != cursor)
                {
                    cursor.close();
                    cursor = null;
                }
            }

            ((EditText)findViewById(R.id.edit_avatar_uri)).setText((null != fileUri) ? fileUri : "");
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void onClickButtonCreate(View view)
    {
        // Allocate a CreateGlympseParams object. 
        CreateGlympseParams glympseCreateParams = new CreateGlympseParams();
        
        // Grab the default subtype from our UI that we would like to use.
        String subtype = ((EditText)findViewById(R.id.edit_subtype)).getText().toString().trim();        
        
        // Grab the default brand from our UI that we would like to use.
        String brand = ((EditText)findViewById(R.id.edit_brand)).getText().toString().trim();        

        // Specify that we want a single "app" recipient.
        glympseCreateParams.setRecipient(Recipient.createNew(Recipient.TYPE_APP, subtype, brand, null, null));

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

        // Grab the default nickname from our UI that we would like to use.
        String nickname = ((EditText)findViewById(R.id.edit_nickname)).getText().toString().trim();
        if (!nickname.isEmpty())
        {
            glympseCreateParams.setInitialNickname(nickname);
        }

        // Grab the default avatar URI from our UI that we would like to use.
        String avatarUri = ((EditText)findViewById(R.id.edit_avatar_uri)).getText().toString().trim();
        if (!avatarUri.isEmpty())
        {
            glympseCreateParams.setInitialAvatar(avatarUri);
        }
        
        // Generate a "create a glympse" Intent and start the activity for it. 
        GlympseApp.createGlympse(this, glympseCreateParams, this);
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
            GlympseApp.viewGlympse(this, true, glympseViewParams);            
        }
    }
    
    /**
     * GlympseApp.StatusListener
     */
    
    public void glympseDoneSending(GlympseCallbackParams params)
    {
        // Get the list of recipients for the Glympse. There should be just one.
        Recipient[] recipients = params.getRecipients();
        
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
            textView.setText(links + "\n    " + recipients[0].getUrl() + " (" + (params.getDuration() / (60 * 1000)) + ")");
            textView.setVisibility(View.VISIBLE);
        }
    }
    
    public void glympseFailedToCreate(GlympseCallbackParams params)
    {
        TextView textView = (TextView)findViewById(R.id.text_links);
        textView.setText("Failed to send a Glympse");
        textView.setVisibility(View.VISIBLE);
    }
}
