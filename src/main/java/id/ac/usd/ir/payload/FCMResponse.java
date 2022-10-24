package id.ac.usd.ir.payload;

import id.ac.usd.ir.entity.Document;

import java.util.HashMap;
import java.util.List;

/**
 * @author Yosafat Willy Christian
 */
public class FCMResponse {

    private int jmlVariable;

    private int jmlData;

//    private double PCI;
//
//    private double MPCI;


    private double[][] pusatCluster;

    private HashMap<Integer, List<Document>> clusterResult = new HashMap<>();

    public FCMResponse() {

    }

//    public FCMResponse(int jmlVariable, int jmlData, double PCI, double MPCI, double[][] pusatCluster, HashMap<Integer, List<Document>> clusterResult) {
//        this.jmlVariable = jmlVariable;
//        this.jmlData = jmlData;
//        this.PCI = PCI;
//        this.MPCI = MPCI;
//        this.pusatCluster = pusatCluster;
//        this.clusterResult = clusterResult;
//    }

    public FCMResponse(int jmlVariable, int jmlData, double[][] pusatCluster, HashMap<Integer, List<Document>> clusterResult) {
        this.jmlVariable = jmlVariable;
        this.jmlData = jmlData;
        this.pusatCluster = pusatCluster;
        this.clusterResult = clusterResult;
    }

    public int getJmlVariable() {
        return jmlVariable;
    }

    public int getJmlData() {
        return jmlData;
    }


//    public double getPCI() {
//        return Util.round(PCI, 5);
//    }
//
//    public double getMPCI() {
//        return Util.round(MPCI, 5);
//    }

    public double[][] getPusatCluster() {
        return pusatCluster;
    }

    public HashMap<Integer, List<Document>> getClusterResult() {
        return this.clusterResult;
    }

    public void setJmlVariable(int jmlVariable) {
        this.jmlVariable = jmlVariable;
    }

    public void setJmlData(int jmlData) {
        this.jmlData = jmlData;
    }

//    public void setPCI(double PCI) {
//        this.PCI = PCI;
//    }
//
//    public void setMPCI(double MPCI) {
//        this.MPCI = MPCI;
//    }

    public void setPusatCluster(double[][] pusatCluster) {
        this.pusatCluster = pusatCluster;
    }

    public void setClusterResult(HashMap<Integer, List<Document>> clusterResult) {
        this.clusterResult = clusterResult;
    }


}
