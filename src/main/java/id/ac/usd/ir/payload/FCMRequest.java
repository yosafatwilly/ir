package id.ac.usd.ir.payload;

public class FCMRequest {
    private int jumlahCluster = 4;
    private int maxIter = 300;
    private double pangkat = 2;
    private double epsilon = 0.0000001;

    public FCMRequest() {
    }

    public FCMRequest(int jumlahCluster, int maxIter, double pangkat, double epsilon) {
        this.jumlahCluster = jumlahCluster;
        this.maxIter = maxIter;
        this.pangkat = pangkat;
        this.epsilon = epsilon;
    }

    public int getJumlahCluster() {
        return jumlahCluster;
    }

    public void setJumlahCluster(int jumlahCluster) {
        this.jumlahCluster = jumlahCluster;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    public double getPangkat() {
        return pangkat;
    }

    public void setPangkat(double pangkat) {
        this.pangkat = pangkat;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
