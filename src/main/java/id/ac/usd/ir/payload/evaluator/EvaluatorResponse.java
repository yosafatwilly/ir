package id.ac.usd.ir.payload.evaluator;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EvaluatorResponse {

    private List<Evaluator> listRecallPrecision;
    private Map<String, Double> average;

    public EvaluatorResponse() {

    }

    public EvaluatorResponse(List<Evaluator> listRecallPrecision, Map<String, Double> average) {
        this.listRecallPrecision = listRecallPrecision;
        this.average = average;
    }

    public List<Evaluator> getListRecallPrecision() {
        return listRecallPrecision;
    }

    public void setListRecallPrecision(List<Evaluator> listRecallPrecision) {
        this.listRecallPrecision = listRecallPrecision;
    }

    public Map<String, Double> getAverage() {
        return average;
    }

    public void setAverage(Map<String, Double> average) {
        this.average = average;
    }

    public long averageTime(){
        long sum = 0L;
        for (Evaluator evaluator : listRecallPrecision) {
            sum += evaluator.getTime();
        }
        return sum/listRecallPrecision.size();
    }

    public TreeMap<Double, Double> getInterpolationAverage(){
        double[] stdRec = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        TreeMap<Double, Double> map  = new TreeMap<>();
        for (double v : stdRec) {
            double temp = 0.0;
            for (Evaluator evaluator : listRecallPrecision) {
                temp += evaluator.getInterpolation().get(v);
            }
            map.put(v, temp/listRecallPrecision.size());
        }
        return map;
    }

    public Double getAveragePrecision(){
        double[] stdRec = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        double temp2 = 0.0;
        TreeMap<Double, Double> map  = new TreeMap<>();
        for (double v : stdRec) {
            double temp = 0.0;
            for (Evaluator evaluator : listRecallPrecision) {
                temp += evaluator.getInterpolation().get(v);
            }
            temp2 += temp/listRecallPrecision.size();
        }
        return temp2/11;
    }

}
