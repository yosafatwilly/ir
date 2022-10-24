package id.ac.usd.ir.service;

import id.ac.usd.ir.payload.FCMRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smile.math.Math;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FCMClusterDebug {
    private Logger log = LoggerFactory.getLogger(FCMClusterDebug.class);

    public double[][] X; //data yang akan dicluster
    public double[][] u; //darajat keanggotaan
    public double[][] V; //pusat cluster
    public int n; //jumlah data / dokumen
    public int m; //jumlah variabel / term
    public int c; //jumlah cluster
    public double w; //fuzziness/pangkat
//    public double PCI; //partition coefficient index
//    public double MPCI; //modification partition coefficient index

    public FCMClusterDebug(double[][] data, FCMRequest fcmRequest) {
        this.X = data;
        this.n = data.length;
        this.m = data[0].length;
        this.c = fcmRequest.getJumlahCluster();
        this.w = fcmRequest.getPangkat();

        if (fcmRequest.getJumlahCluster() < 2)
            throw new IllegalArgumentException ("Invalid parameter jumlah Cluster = " + c);
        if (fcmRequest.getPangkat() < 1)
            throw new IllegalArgumentException ("Invalid parameter pembobot = " + w);

        log.info("Memulai Clustering ...");

        V = new double[c][m];
        u = new double[n][c];
        matrikRandomStatic();
        double P0 = 0;
        double P1;
        for (int i = 1; i <= fcmRequest.getMaxIter(); i++) {
            log.info("Iterasi ke " + i);
            hitungPusatCluster();
            P1 = hitungFungsiObjektif();
            hitungPerubahanMatriks();
            if (Math.abs(P1 - P0) < fcmRequest.getEpsilon())
                break;
            P0 = P1;
        }
//        hitungValidasi();
    }

    private void matrikRandom() {
        for (int j = 0; j < n; j++) {
            double Q = 0;
            for (int k = 0; k < c; k++) {
                u[j][k] = 10 + Math.random();
                Q += u[j][k];
            }
            for (int k = 0; k < c; k++) {
                u[j][k] /= Q;
            }
        }
    }

    private void hitungPusatCluster() {
        double atas, bawah;
        for (int k = 0; k < c; k++) {
            for (int j = 0; j < m; j++) {
                atas = bawah = 0.0;
                for (int i = 0; i < n; i++) {
                    atas += Math.pow(u[i][k], w) * X[i][j];
                    bawah += Math.pow(u[i][k], w);
                }
                V[k][j] = atas / bawah;
                System.out.println("Pusat Cluster ke "+k+", attribut ke "+j+" = "+V[k][j]);
            }
        }
    }

    private double hitungFungsiObjektif() {
        double fo = 0;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < c; k++) {
                double temp = 0;
                double qd = 0, q2 = 0, d2 = 0;
                for (int j = 0; j < m; j++) {
                    qd += V[k][j] * X[i][j];
                    q2 += Math.pow(V[k][j], 2);
                    d2 += Math.pow(X[i][j], 2);
                    System.out.println();
                }
                temp = qd / (Math.sqrt(q2) * Math.sqrt(d2));
                System.out.println("Cosine Vk="+k+" - Xi="+i+" = "+temp);
                fo += temp * Math.pow(u[i][k], w);
            }
        }
        return fo;
    }

    private double hitungFungsiObyektifEuclidean() {
        double fo = 0;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < c; k++) {
                double temp = 0;
                for (int j = 0; j < m; j++) {
                    temp += Math.pow((X[i][j] - V[k][j]), 2);
                }
                fo += temp * Math.pow(u[i][k], w);
            }
        }
        return fo;
    }

    private void hitungPerubahanMatriks() {
        for (int i = 0; i < n; i++) {
            double bawah = 0;
            for (int k = 0; k < c; k++) {
                double temp = 0;
                double qd = 0, q2 = 0, d2 = 0;
                for (int j = 0; j < m; j++) {
                    qd += V[k][j] * X[i][j];
                    q2 += Math.pow(V[k][j], 2);
                    d2 += Math.pow(X[i][j], 2);
                }
                temp = qd / (Math.sqrt(q2) * Math.sqrt(d2));
                bawah += Math.pow(temp, -1 / (w - 1));
            }
            for (int k = 0; k < c; k++) {
                double atas;
                double temp = 0;
                double qd = 0, q2 = 0, d2 = 0;
                for (int j = 0; j < m; j++) {
                    qd += V[k][j] * X[i][j];
                    q2 += Math.pow(V[k][j], 2);
                    d2 += Math.pow(X[i][j], 2);
                }
                temp = qd / (Math.sqrt(q2) * Math.sqrt(d2));
                atas = Math.pow(temp, -1 / (w - 1));
                u[i][k] = (atas / bawah);
            }
        }
    }

    private void hitungPerubahanMatriksEuclidean() {
        for (int i = 0; i < n; i++) {
            double bawah = 0;
            for (int k = 0; k < c; k++) {
                double temp = 0;
                for (int j = 0; j < m; j++) {
                    temp += Math.pow(X[i][j] - V[k][j], 2);
                }
                bawah += Math.pow(temp, -1 / (w - 1));
            }
            for (int k = 0; k < c; k++) {
                double atas;
                double temp = 0;
                for (int j = 0; j < m; j++) {
                    temp += Math.pow(X[i][j] - V[k][j], 2);
                }
                atas = Math.pow(temp, -1 / (w - 1));
                u[i][k] = (atas / bawah);
            }
        }
    }

    public Integer predict(double[] termtfidf) {
        HashMap<Integer, Double> clusterDist = new HashMap<>();
        for (int k = 0; k < c; k++) {
            double dist;
            double qd = 0, q2 = 0, d2 = 0;
            for (int j = 0; j < m; j++) {
                qd += V[k][j] * termtfidf[j];
                q2 += Math.pow(V[k][j], 2);
                d2 += Math.pow(termtfidf[j], 2);
            }
            dist = qd / (Math.sqrt(q2) * Math.sqrt(d2));
            clusterDist.put((k + 1), dist);
            log.info("Jarak ke Cluster " + (k + 1) + " = " + dist);
        }
        double min = (Collections.min(clusterDist.values()));
        for (Map.Entry<Integer, Double> entry : clusterDist.entrySet()) {
            if (entry.getValue() == min) {
                log.info("Hasil Prediksi Cluster : " + entry.getKey());
                return entry.getKey();
            }
        }
        return -1;
    }

//    private void hitungValidasi() {
//        double pciTemp = 0.0;
//        for (int i = 0; i < n; i++) {
//            for (int k = 0; k < c; k++) {
//                double U = u[i][k];
//                pciTemp += Math.pow(U, 2);
//            }
//        }
//        //semakin mendekati 1 maka semakin baik
//        PCI = pciTemp / (double) n;
//
//        //semakin mendekati 1 maka semakin baik
//        MPCI = 1.0 - ((double) c/((double) c - 1.0) * (1.0 - PCI));
//    }

//    public static void main(String[] args) {
//        FCMRequest fcmRequest = new FCMRequest();
//        fcmRequest.setJumlahCluster(5);
//        fcmRequest.setPangkat(2);
//        fcmRequest.setEpsilon(0.000001);
//        fcmRequest.setMaxIter(100);
//        FCMCluster f = new FCMCluster(data, fcmRequest);
//
//        for (int i = 0; i < f.u.length; i++) {
//            System.out.println(Arrays.toString(f.u[i]));
//        }
//        for (int i = 0; i < f.u.length; i++) {
//            double max = 0, tmp = 0;
//            int h = 0;
//            for (int k = 0; k < f.u[0].length; k++) {
//                max = Math.max(f.u[i][k], max);
//                if (tmp != max)
//                    h = k+1;
//                tmp = max;
//            }
//            System.out.println(h);
//        }
//
//        System.out.println("PCI = "+f.PCI);
//        System.out.println("MPCI = "+f.MPCI);
//    }
//
//    static double[][] data = {
//            {15000000, 25000000, 42, 5000000},
//            {20000000, 26420000, 72, 5230000},
//            {17820000, 22052000, 35, 5200000},
//            {16205000, 18500000, 12, 4250000},
//            {8000000, 15200000, 5, 3500000},
//            {14260000, 19640000, 15, 4023000},
//            {7025000, 15230000, 19, 5000000},
//            {25032000, 34000000, 28, 8000000},
//            {24320100, 35100000, 39, 12500000},
//            {25602100, 38200000, 43, 13250000},
//            {19872000, 28000000, 27, 10500000},
//            {19000000, 25000200, 41, 6350000},
//            {16540200, 30000200, 29, 7525000},
//            {28920000, 41000000, 58, 15620000},
//            {15870200, 26750000, 19, 4025000},
//            {26840320, 39000200, 47, 13025000},
//            {24601200, 38450000, 64, 11000250},
//            {21650000, 37525000, 60, 9850000},
//            {18602000, 30500000, 74, 11230000},
//            {35024000, 52000000, 73, 18230000},
//            {39024300, 52050000, 26, 15725000},
//            {27500000, 36500000, 6, 10560000},
//            {32500500, 45600000, 10, 16583000},
//            {27963000, 40250000, 38, 13670000},
//            {37250020, 51000000, 68, 18530000},
//            {16523000, 26750000, 9, 8500000},
//            {25690000, 39565000, 48, 15250000},
//            {34500000, 51065000, 37, 21500000},
//            {9850000, 1350000, 13, 2000000},
//            {16950000, 24580000, 18, 4500000}
//    };
//
    public void matrikRandomStatic() {
        u[0][0] = 0.491956530750276;
        u[1][0] = 0.344718728439505;
        u[2][0] = 0.229980269309523;
        u[3][0] = 0.931951208945668;
        u[4][0] = 0.468799280667409;
        u[5][0] = 0.420775390116286;

        u[0][1] = 0.508043469249724;
        u[1][1] = 0.655281271560495;
        u[2][1] = 0.770019730690477;
        u[3][1] = 0.0680487910543322;
        u[4][1] = 0.531200719332591;
        u[5][1] = 0.579224609883714;
    }
}
