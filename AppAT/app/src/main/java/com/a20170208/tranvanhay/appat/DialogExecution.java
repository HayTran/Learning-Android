package com.a20170208.tranvanhay.appat;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Van Hay on 29-May-17.
 */

public class DialogExecution {
    Context context;
    ProgressDialog progressDialog;
    String message;
    public DialogExecution(Context context, String message) {
        this.context = context;
        this.message = message;
        showProgressDialog();
    }
    public void showProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
    public void dismissProgressDialog(){
        progressDialog.dismiss();
    }
}
