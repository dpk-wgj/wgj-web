package com.dpk.wgj.service;

import com.dpk.wgj.bean.DTO.PointDTO;

import java.util.Map;

public interface PointService {

    public PointDTO findPoint(double[][] points, double radius, int minpts);

}
