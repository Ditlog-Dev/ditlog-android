package id.ac.itb.ditlog.monitorandperformance;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by RichardMatthew on 03/04/18.
 */

public class Tanggal {

    final Date date = new Date();
     Calendar calendar = new Calendar() {
        @Override
        protected void computeTime() {

        }

        @Override
        protected void computeFields() {

        }

        @Override
        public void add(int i, int i1) {

        }

        @Override
        public void roll(int i, boolean b) {

        }

        @Override
        public int getMinimum(int i) {
            return 0;
        }

        @Override
        public int getMaximum(int i) {
            return 0;
        }

        @Override
        public int getGreatestMinimum(int i) {
            return 0;
        }

        @Override
        public int getLeastMaximum(int i) {
            return 0;
        }
    };

    public Tanggal(){}

    public Tanggal(String stringDate){

        Long longDate = Long.parseLong(stringDate);
        calendar.setTimeInMillis(longDate);
        date.setTime(longDate);
        Log.d("Longdate", longDate.toString());
    }

    public Tanggal(int dayOfMonth, int monthOfYear, int year){
        calendar.set(year, monthOfYear,dayOfMonth);
    }

    public void setTanggal(String stringDate){

        Long longDate = Long.parseLong(stringDate);
        calendar.setTimeInMillis(longDate);
        Log.d("Longdate", longDate.toString());
    }

    public void setTanggal(int dayOfMonth, int monthOfYear, int year){

        calendar.set(year, monthOfYear,dayOfMonth);
    }

    public void setTanggal(Calendar _calendar){

        calendar = _calendar;
    }
    public String getTanggal(){
        String temp;
        Date date = calendar.getTime();
        //Integer hari = calendar.get(Calendar.DAY_OF_MONTH);
        Integer hari = calendar.getTime().getDate();
        Log.d("Longdate","date: " + hari.toString());
        //Integer bulan = calendar.get(Calendar.MONTH);
        Integer bulan = calendar.getTime().getMonth() + 1;
        Log.d("Longdate", "bulan " + bulan.toString());
        //Integer tahun = calendar.get(Calendar.YEAR);
        Integer tahun = calendar.getTime().getYear() +1900;
        Log.d("Longdate", "tahun " + tahun.toString());

        temp= hari.toString() + "/" + bulan.toString() + "/" + tahun.toString();
       return temp;

    }

    public int  getDate(){
        return calendar.getTime().getDate();
    }

    public int  getMonth(){
        return calendar.getTime().getMonth() + 1;
    }


    public int  getYear(){

        return calendar.getTime().getYear() +1900;
    }


    public boolean isBefore(Tanggal tanggalLain){
        return calendar.before(tanggalLain);
    }
}
