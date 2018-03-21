package id.ac.itb.ditlog.monitorandperformance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 18/03/2018.
 */

public class ContractPerformancePreference {
    public static final String contractPerformance = "contract_performance";
    public static final String number = "number_performance";
    public static final String title = "title_performance";
    public static final String vendor = "vendor_performance";
    public static final String date = "date_performance";
    public static final String eval = "eval_performance";

    public void setNumber(Context context, String contractNumber) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(number,contractNumber);
        editor.commit();
    }

    public String getNumber(Context context) {
        SharedPreferences sharedPref;
        String contractNumber;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        contractNumber = sharedPref.getString(number, "-");
        return contractNumber;
    }

    public void setTitle(Context context, String contractTitle) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(title,contractTitle);
        editor.commit();
    }

    public String getTitle(Context context) {
        SharedPreferences sharedPref;
        String contractTitle;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        contractTitle = sharedPref.getString(title, "-");
        return contractTitle;
    }

    public void setVendor(Context context, String contractVendor) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(vendor,contractVendor);
        editor.commit();
    }

    public String getVendor(Context context) {
        SharedPreferences sharedPref;
        String contractVendor;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        contractVendor = sharedPref.getString(vendor, "-");
        return contractVendor;
    }

    public void setDate(Context context, String contractDate) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(date,contractDate);
        editor.commit();
    }

    public String getDate(Context context) {
        SharedPreferences sharedPref;
        String contractDate;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        contractDate = sharedPref.getString(date, "-");
        return contractDate;
    }

    public void setEval(Context context, float contractEval) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putFloat(eval,contractEval);
        editor.commit();
    }

    public float getEval(Context context) {
        SharedPreferences sharedPref;
        float contractEval;
        sharedPref = context.getSharedPreferences(contractPerformance, Context.MODE_PRIVATE);
        contractEval = sharedPref.getFloat(eval, 0);
        return contractEval;
    }
}
