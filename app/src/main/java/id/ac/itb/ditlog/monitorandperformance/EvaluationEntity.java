package id.ac.itb.ditlog.monitorandperformance;

/**
 * Created by ASUS on 09/04/2018.
 */

public class EvaluationEntity {
    public String paramEvaluation;
    public String gradeEvaluation;

    public int id;

    public EvaluationEntity(){
        id=-1;
        paramEvaluation="";
        gradeEvaluation= "100";
    }
    public EvaluationEntity(int id, String name, String grade){
        this.id = id;
        this.paramEvaluation = name;
        this.gradeEvaluation = grade;
    }

    public String getParamEvaluation() {
        return paramEvaluation;
    }

    public void setParamId(int id) {
        this.id = id;
    }

    public void setParamEvaluation(String paramEvaluation) {
        this.paramEvaluation = paramEvaluation;
    }

    public String getGradeEvaluation() {
        return gradeEvaluation;
    }

    public void setGradeEvaluation(String gradeEvaluation) {
        this.gradeEvaluation = gradeEvaluation;
    }
}
