/*
        Copyright (c) 2012, 2013, 2014, Groupon, Inc.
        All rights reserved.

        Redistribution and use in source and binary forms, with or without
        modification, are permitted provided that the following conditions
        are met:

        Redistributions of source code must retain the above copyright notice,
        this list of conditions and the following disclaimer.

        Redistributions in binary form must reproduce the above copyright
        notice, this list of conditions and the following disclaimer in the
        documentation and/or other materials provided with the distribution.

        Neither the name of GROUPON nor the names of its contributors may be
        used to endorse or promote products derived from this software without
        specific prior written permission.

        THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
        IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
        TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
        PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
        HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
        SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
        TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
        PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
        LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
        NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
        SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.groupon.roboremote.example.helloworld;

import android.app.ListActivity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HelloWorld extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // storing string resources into Array
        String[] pages = getResources().getStringArray(R.array.pages);

        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, pages));

        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
              // selected item
              String product = ((TextView) view).getText().toString();

              // Launching new Activity on selecting single List Item
              Intent i = new Intent(getApplicationContext(), TestActivity.class);
              i.putExtra("product", product.concat(" page"));

              if (product.equals("WebView")) {
                  i = new Intent(getApplicationContext(), WebviewActivity.class);
              } else if (product.equals("Notification")) {
                  // generate a notification instead of starting a new activity
                  NotificationManager notificationManager = (NotificationManager)
                          getSystemService(NOTIFICATION_SERVICE);

                  PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);

                  Notification n  = new Notification.Builder(getApplicationContext())
                          .setContentTitle(getString(R.string.notification_title))
                          .setContentText(getString(R.string.notification_text))
                          .setContentIntent(pIntent)
                          .setSmallIcon(android.R.drawable.ic_dialog_alert)
                          .setAutoCancel(true).build();

                  notificationManager.notify(0, n);
                  System.out.println("HelloWorld:: Sending notification");

                  return;
              }

              // sending data to new activity
              startActivity(i);
          }
        });
    }
}