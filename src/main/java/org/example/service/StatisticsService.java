package org.example.service;

import org.example.model.Project;
import org.example.model.xml.Statistics;

import java.util.Queue;

/**
 * Provides interface for work with statistic model
 */
public interface StatisticsService {
	Statistics createStatistics(Queue<Project> projectList, String attribute);
}
