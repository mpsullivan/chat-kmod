/*
 * Copyright 2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ws.salient.chat;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LineChart implements Renderable, Attachable, Serializable {

    private static final long serialVersionUID = 1L;

    private String chartType;
    private List<List> rows;
    private String containerId;
    private List<Column> columns;
    private List<String> imageUrls;
    private String title;
    private String subtitle;
    private Integer width;
    private Integer height;

    public LineChart() {
    }

    public LineChart(String title, String subtitle, List<List> rows, String xAxisType, String xAxisLabel) {
        this.title = title;
        this.subtitle = subtitle;
        this.chartType = "LineChart";
        this.rows = rows;
        this.columns = new LinkedList();
        columns.add(new Column(xAxisType, xAxisLabel, null));
        containerId = "visualization";
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    
    

    public LineChart withTitle(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        return this;
    }

    public LineChart withColumn(String type, String name, String color) {
        Column column = new Column(type, name, color);
        this.columns.add(column);
        return this;
    }

    public LineChart withWidth(Integer width) {
        this.width = width;
        return this;
    }

    public LineChart withHeight(Integer height) {
        this.height = height;
        return this;
    }

    @Override
    public String toString() {
        return "Chart{" + "chartType=" + chartType + ", rows=" + rows + ", containerId=" + containerId + '}';
    }

    @Override
    public List<String> getContents(Context context) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>\n\n<head>\n  <!--Load the AJAX API-->\n  <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n  <script type=\"text/javascript\">\n    google.charts.load('current', {\n      packages: ['line']\n    });\n    google.charts.setOnLoadCallback(drawLineColors);\n\n    function drawLineColors() {\n      var data = new google.visualization.DataTable();\n");
     
        
        columns.forEach((column) -> {
            builder.append("      data.addColumn('");
            builder.append(column.getType());
            builder.append("', '");
            builder.append(column.getName());
            builder.append("');\n");
        });
        
        
        builder.append("      data.addRows([\n        ");

        for (List row : rows) {
            if (row != rows.get(0)) {
                builder.append(",");
            }
            builder.append("[");
            for (int index = 0; index < row.size(); index++) {
                if (index > 0) {
                    builder.append(",");
                }
                Object value = row.get(index);
                if (value instanceof Date) {
                    Instant instant = ((Date) value).toInstant();
                    ZonedDateTime datetime = ZonedDateTime.ofInstant(instant, context.getZoneId());
                    builder.append("new Date(");
                    builder.append(datetime.getYear());
                    builder.append(",");
                    builder.append(datetime.getMonthValue());
                    builder.append(",");
                    builder.append(datetime.getDayOfMonth());
                    builder.append(",");
                    builder.append(datetime.getHour());
                    builder.append(",");
                    builder.append(datetime.getMinute());
                    builder.append(",0)");
                } else {
                    builder.append(value);
                }
            }
            builder.append("]");
        }

        Map options = new LinkedHashMap();
        Map chart = (Map) options.get("chart");
        if (chart == null) {
            chart = new LinkedHashMap();
            options.put("chart", chart);
        }
        if (title != null) {
            chart.put("title", title);
        }
        if (subtitle != null) {
            chart.put("subtitle", subtitle);
        }
        if (width != null) {
            options.put("width", width);
        }
        if (height != null) {
            options.put("height", height);
        }
        
        builder.append("      ]);\n\n      ");

        builder.append("var options = {\n        chart: {\n          title: '").append(((Map) options.get("chart")).get("title")).append("',\n          subtitle: '").append(((Map) options.get("chart")).get("subtitle")).append("'\n");
        builder.append("        },\n");
        builder.append("        series: {\n");

        for (int index = 1; index < columns.size(); index++) {
            Column column = columns.get(index);
            if (index > 1) {
                builder.append(",");
            }
            builder.append(index - 1);
            builder.append(": {\n            color: '");
            builder.append(column.getColor());
            builder.append("'\n          }\n");
        }
        builder.append("        },\n        hAxis: {\n          format: 'h:mm a'\n        },\n        width: 600,\n        height: 300\n      };\n\n      var chart = new google.charts.Line(document.getElementById('chart_div'));\n      chart.draw(data, options);\n\n\n    }\n  </script>\n</head>\n\n<body>\n  <!--Div that will hold the pie chart-->\n  <div id=\"chart_div\"></div>\n</body>\n\n</html>\n");
        return Arrays.asList(builder.toString());
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public Map<String, Object> getAttachment(Context context) {
        Map attachment = new LinkedHashMap();
        attachment.put("image_url", imageUrls.get(0));
        return attachment;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

}
