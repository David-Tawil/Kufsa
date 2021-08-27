package com.example.kufsa.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import java.net.URLEncoder;

/**
 * This class defines the tools for various parts of app
 */
public class Tools {

    /**
     * This method defines calling to a phone from an ad
     *
     * @param mContext the context object being used in the dial
     * @param number   the number being inserted for dialing
     */
    public static void callFromDailer(Context mContext, String number) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + number));
            mContext.startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "No SIM Found", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method defines emailing an email in an ad
     *
     * @param mContext  the context object being used
     * @param addresses the email address being used
     * @param subject   the subject of the email
     */
    public static void composeEmail(Context mContext, String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }

    /**
     * This method defines emailing an email in an ad
     *
     * @param mContext the context object being used
     * @param phone    the phone number being used
     * @param msg      the message being added
     */
    public static void sendWhatsAppMessage(Context mContext, String phone, String msg) {
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://wa.me/" + phone + "&text=" + URLEncoder.encode(msg, "UTF-8");
            intent.setPackage("com.whatsapp");
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(packageManager) != null) {
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method verifies the email
     *
     * @param target the text field
     * @return True if email is valid, false otehrwise
     */
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * This method if view is visible
     *
     * @param view the view being checked
     * @return True if view is visible, false otherwise
     */
    public static boolean isVisible(final View view) {
        if (view == null) {
            return false;
        }
        if (!view.isShown()) {
            return false;
        }
        final Rect actualPosition = new Rect();
        view.getGlobalVisibleRect(actualPosition);
        final Rect screen = new Rect(0, 0, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
        return actualPosition.intersect(screen);
    }
}
