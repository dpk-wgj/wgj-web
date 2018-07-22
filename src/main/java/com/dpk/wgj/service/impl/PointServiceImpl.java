package com.dpk.wgj.service.impl;

import com.dpk.wgj.bean.DTO.PointDTO;
import com.dpk.wgj.service.PointService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointServiceImpl implements PointService {

    public PointDTO findPoint(double[][] points, double radius, int minpts){
        List<double[]> cores;
        List<double[]> targetCores;
        List<List<double[]>> clusters;
        Map<String, Object> map = new HashMap<>();

        PointDTO pointDTO = new PointDTO();

        //findCores
        cores = new ArrayList<double[]>();
        targetCores = new ArrayList<double[]>();
        for (int i = 0; i < points.length; i++) {
            int pts = 0;
            for (int j = 0; j < points.length; j++) {
                for (int k = 0; k < points[i].length; k++) {
                    if (countEurDistance(points[i], points[j]) < radius) {
                        pts++;
                    }
                }
            }
            if (pts >= minpts) {
                cores.add(points[i]);
                targetCores.add(points[i]);
            }
        }
        pointDTO.setCores(targetCores);

        // cluster
        clusters = new ArrayList<List<double[]>>();
        int clusterNum = 0;
        for (int i = 0; i < cores.size(); i++) {
            clusters.add(new ArrayList<double[]>());
            clusters.get(clusterNum).add(cores.get(i));
            densityConnected(points, cores.get(i), clusterNum, cores, clusters, radius);
            clusterNum++;
        }
        pointDTO.setClusters(clusters);
        return pointDTO;
    }


    private double countEurDistance(double[] point1, double[] point2) {
        double eurDistance = 0.0;
        for (int i = 0; i < point1.length; i++) {
            eurDistance += (point1[i] - point2[i]) * (point1[i] - point2[i]);
        }
        return Math.sqrt(eurDistance);
    }

    private void densityConnected(double[][] points, double[] core, int clusterNum, List<double[]> cores, List<List<double[]>> clusters, double radius) {
        boolean isputToCluster;//是否已经归为某个类
        boolean isneighbour = false;//是不是core的“邻居”
        cores.remove(core);//对某个core点处理后就从core集中去掉
        for (int i = 0; i < points.length; i++) {
            isneighbour = false;
            isputToCluster = false;
            for (List<double[]> cluster : clusters) {
                if (cluster.contains(points[i])) {//如果已经归为某个类
                    isputToCluster = true;
                    break;
                }
            }
            if (isputToCluster) continue;//已在聚类中，跳过，不处理
            if (countEurDistance(points[i], core) < radius) {//是目前加入的core点的“邻居”吗？，ture的话，就和这个core加入一个类
                clusters.get(clusterNum).add(points[i]);
                isneighbour = true;
            }
            if (isneighbour) {//如果是邻居，才会接下来对邻居进行densityConnected处理，否则，结束这个core点的处理
                if (cores.contains(points[i])) {
                    cores.remove(points[i]);
                    densityConnected(points, points[i], clusterNum, cores, clusters, radius);
                }
            }
        }

    }
}
