package com.ex.springboot.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MapDistanceDTO implements Comparable<MapDistanceDTO> {
	private EventDTO event;
	private String distance;
	private int offestt;
	private int limit;
	private int currentPage;
	
	 @Override
	    public int compareTo(MapDistanceDTO o) {
	        // 거리를 기준으로 오름차순으로 정렬합니다.
	        double distance1 = Double.parseDouble(this.distance);
	        double distance2 = Double.parseDouble(o.getDistance());
	        return Double.compare(distance1, distance2);
	    }
	 

}
