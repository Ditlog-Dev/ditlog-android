package id.ac.itb.ditlog.monitorandperformance;

/**
 * Created by root on 08/03/18.
 */

public class IndicatorEntity {
    public int id;
    public String name;
    public int idUser;
    public IndicatorEntity(){
        id=-1;
        name="";
        idUser=-1;
    }
    public IndicatorEntity(int id, String name, int idUser){
        this.id = id;
        this.name = name;
        this.idUser = idUser;
    }
}
