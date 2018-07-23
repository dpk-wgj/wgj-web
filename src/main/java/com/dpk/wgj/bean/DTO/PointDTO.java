package com.dpk.wgj.bean.DTO;

import java.io.Serializable;
import java.util.List;

public class PointDTO implements Serializable {

    List<double[]> cores;

    List<List<double[]>> clusters;

    public PointDTO() {
    }

    public PointDTO(List<double[]> cores, List<List<double[]>> clusters) {
        this.cores = cores;
        this.clusters = clusters;
    }

    public List<double[]> getCores() {
        return cores;
    }

    public void setCores(List<double[]> cores) {
        this.cores = cores;
    }

    public List<List<double[]>> getClusters() {
        return clusters;
    }

    public void setClusters(List<List<double[]>> clusters) {
        this.clusters = clusters;
    }
}
