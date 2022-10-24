package id.ac.usd.ir.service;

import id.ac.usd.ir.payload.FCMRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smile.math.Math;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FCMCluster {
    private Logger log = LoggerFactory.getLogger(FCMCluster.class);

    private double[][] X; //data yang akan dicluster
    private double[][] u; //darajat keanggotaan
    private double[][] V; //pusat cluster
    private int n; //jumlah data / dokumen
    private int m; //jumlah variabel / term
    private int c; //jumlah cluster
    private double w; //fuzziness/pangkat
//    private double PCI; //partition coefficient index
//    private double MPCI; //modification partition coefficient index

    public FCMCluster(double[][] data, FCMRequest fcmRequest) {
        this.X = data;
        this.n = data.length;
        this.m = data[0].length;
        this.c = fcmRequest.getJumlahCluster();
        this.w = fcmRequest.getPangkat();

        log.info("Memulai Clustering ...");

        V = new double[c][m];
        u = new double[n][c];
        matrikRandom();
        double P0 = 0;
        double P1 = 0;
        for (int i = 1; i <= fcmRequest.getMaxIter(); i++) {
            log.info("Iterasi ke " + i + "; P1 - P0 = " +  new BigDecimal(Math.abs(P1 - P0)));
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
        for (int i = 0; i < n; i++) {
            double Q = 0;
            for (int k = 0; k < c; k++) {
                u[i][k] = 10 + Math.random();
                Q += u[i][k];
            }
            for (int k = 0; k < c; k++) {
                u[i][k] /= Q;
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
                }
                temp = qd / (Math.sqrt(q2) * Math.sqrt(d2));
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


    public double[][] getX() {
        return X;
    }

    public double[][] getU() {
        return u;
    }

    public double[][] getV() {
        return V;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getC() {
        return c;
    }
}
