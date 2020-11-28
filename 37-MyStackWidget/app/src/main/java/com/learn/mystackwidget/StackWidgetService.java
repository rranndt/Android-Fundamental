package com.learn.mystackwidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
