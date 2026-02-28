package com.example.s1_minutanutricional.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.s1_minutanutricional.R
import com.example.s1_minutanutricional.MainActivity

class MinutaWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        appWidgetIds.forEach { widgetId ->
            actualizarWidget(context, appWidgetManager, widgetId)
        }
    }

    private fun actualizarWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        widgetId: Int
    ) {
        // 🔹 Intent que abre la app al tocar el widget
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val views = RemoteViews(context.packageName, R.layout.widget_minuta)

        views.setOnClickPendingIntent(R.id.widget_btn_abrir, pendingIntent)

        appWidgetManager.updateAppWidget(widgetId, views)
    }
}
