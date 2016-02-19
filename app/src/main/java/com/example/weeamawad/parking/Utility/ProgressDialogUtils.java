package com.example.weeamawad.parking.Utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.weeamawad.parking.R;

/**
 * Created by Weeam Awad on 2/18/2016.
 */

public class ProgressDialogUtils {
    private static ProgressDialog mDialog;

    /**
     * Method is used to display progress dialog
     *
     * @param context
     */
    public static void showProgress(Context context) {
        if (!Utils.checkIfNull(mDialog) && mDialog.isShowing()) {
            return;
        }

        try {
            mDialog = new ProgressDialog(context, R.style.CustomProgressDialog);
            mDialog.show();
            mDialog.setContentView(R.layout.dialog_progress);
            mDialog.setCancelable(false);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is used to dismiss progress dialog
     */
    public static void dismissDialog() {
        try {
            if (!Utils.checkIfNull(mDialog) && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}