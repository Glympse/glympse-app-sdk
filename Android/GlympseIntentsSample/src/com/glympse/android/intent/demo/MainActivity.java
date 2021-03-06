//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.glympse.android.intent.Common;
import com.glympse.android.intent.CreateGlympseParams;
import com.glympse.android.intent.GlympseApp;
import com.glympse.android.intent.GlympseCallbackParams;
import com.glympse.android.intent.Helpers;
import com.glympse.android.intent.Recipient;
import com.glympse.android.intent.UriParser;
import com.glympse.android.intent.ViewGlympseParams;

public class MainActivity extends Activity implements GlympseApp.StatusListener
{
    protected static final int REQUEST_PICK_PHOTO     = 1;
    protected static final int REQUEST_CREATE_GLYMPSE = 2;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add our build number to our title.
        try
        {
            setTitle(getString(R.string.app_name) + " (" + getPackageManager().getPackageInfo(getPackageName(), 0).versionCode + ")");
        }
        catch (Throwable e)
        {
        }
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
        if ((REQUEST_PICK_PHOTO == requestCode) && (RESULT_OK == resultCode) && (null != intent) && (null != intent.getData()))
        {
            ((EditText)findViewById(R.id.edit_avatar_uri)).setText(intent.getData().toString());
        }

        // Check if we are returning from creating a Glympse. This is only
        // needed when not using a StatusListener to obtain the result.
        else if ((REQUEST_CREATE_GLYMPSE == requestCode) && (RESULT_OK == resultCode) && (null != intent))
        {
            glympseDoneSending(GlympseApp.getCreateResult(intent));
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void onClickButtonCreate(View view)
    {
        // Allocate a CreateGlympseParams object.
        CreateGlympseParams createGlympseParams = new CreateGlympseParams();

        // Recipient picker will be presented in read-only mode.
        createGlympseParams.setFlags(Common.FLAG_RECIPIENTS_READ_ONLY);

        // Grab the default subtype from our UI that we would like to use.
        String subtype = ((EditText)findViewById(R.id.edit_subtype)).getText().toString().trim();

        // Grab the default brand from our UI that we would like to use.
        String brand = ((EditText)findViewById(R.id.edit_brand)).getText().toString().trim();

        // Specify that we want a single "app" recipient. The last parameter to createNew()
        // is a createOnly flag. When set to true, the Glympse app will simply create this
        // invite URL, but will not attempt to send it. This allows the calling application
        // to deliver the URL directly, such as pasting it into a messaging conversation.
        createGlympseParams.setRecipient(Recipient.createNew(Recipient.TYPE_APP, subtype, brand, null, null, true));

        // Grab the default duration from our UI that we would like to use.
        try
        {
            int minutes = Integer.parseInt(((EditText)findViewById(R.id.edit_duration)).getText().toString().trim());
            if (minutes >= 0)
            {
                createGlympseParams.setDuration(minutes * 60 * 1000);
            }
        }
        catch (Throwable e)
        {
        }

        // Grab the default nickname from our UI that we would like to use.
        String nickname = ((EditText)findViewById(R.id.edit_nickname)).getText().toString().trim();
        if (!Helpers.isEmpty(nickname))
        {
            createGlympseParams.setInitialNickname(nickname);
        }

        // Grab the default avatar URI from our UI that we would like to use.
        String avatarUri = ((EditText)findViewById(R.id.edit_avatar_uri)).getText().toString().trim();
        if (!Helpers.isEmpty(avatarUri))
        {
            createGlympseParams.setInitialAvatar(avatarUri);
        }

        // Check to see if we should be using the return intent. This will force
        // the Glympse application to return the result to our onActivityResult()
        // method instead of using the StatusListener.
        if (((CheckBox)findViewById(R.id.check_use_activity_result)).isChecked())
        {
            // Tell Glympse application that caller is waiting for the result in onActivityResult().
            createGlympseParams.setFlags(createGlympseParams.getFlags() | Common.FLAG_USE_ACTIVITY_RESULT);

            // Invoke "Send a Glympse" wizard.
            Intent intent = GlympseApp.getCreateGlympseIntent(this, createGlympseParams);
            startActivityForResult(intent, REQUEST_CREATE_GLYMPSE);
        }

        // Otherwise, we are using the StatusListener to get the Glympse result.
        // This is the recommended way to create a Glympse since it ensures the
        // calling application gets the result of the Glympse creation, even if
        // it happens after the Glympse activity is dismissed.
        else
        {
            createGlympseParams.setStatusListener(this);
            GlympseApp.createGlympse(this, createGlympseParams);
        }
    }

    public void onClickButtonView(View view)
    {
        // Grab the text buffer from our UI that we would like to parse for Glympse URLs.
        String buffer = ((EditText)findViewById(R.id.edit_buffer)).getText().toString();

        // Check if we want to show ourself on the map.
        boolean showSelf = (((CheckBox)findViewById(R.id.check_show_self)).isChecked());

        // Parse the buffer and check to see if it has any Glympse URLs that
        // contain glympse codes or group names.
        UriParser parseBufferResult = GlympseApp.parseBuffer(buffer);
        if (showSelf || parseBufferResult.hasGlympseOrGroup())
        {
            // Allocate a ViewGlympseParams object and tell it what we want to view.
            ViewGlympseParams viewGlympseParams = new ViewGlympseParams();
            viewGlympseParams.addAllGlympsesAndGroups(parseBufferResult);

            // Check if we want to show ourself on the map as well.
            if (showSelf)
            {
                viewGlympseParams.setFlags(Common.FLAG_SHOW_SELF);
            }

            // Generate a "view a glympse" Intent and start the activity for it.
            GlympseApp.viewGlympse(this, true, viewGlympseParams);
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
