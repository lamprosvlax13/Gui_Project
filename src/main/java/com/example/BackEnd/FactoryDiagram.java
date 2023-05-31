package com.example.BackEnd;

import java.util.HashMap;

public class FactoryDiagram {
    private Diagram diagramType;
    private String typeDiagram;
    final HashMap<String,Diagram> map =new HashMap<>();
    public FactoryDiagram(String typeDiagram) {
        this.typeDiagram=typeDiagram;
        initializeMap();
    }

    public Diagram createDiagram() {

        for (String key : map.keySet()) {
            if (key.equals(this.typeDiagram)) {
                this.diagramType = map.get(key);
                break;
            }
            this.diagramType=map.get("null");
        }
        return this.diagramType;
    }

    private void initializeMap(){

        map.put("BarCharts",new BarCharts());
        map.put("TimeLine",new TimeLine());
        map.put("ScatterPlot",new ScatterPlot());
        map.put("TrendLine",new TrendLine());
        map.put("null", new NullPlot());

    }



}

