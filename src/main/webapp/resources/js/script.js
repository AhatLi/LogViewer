
 var chart = null;
 am4core.ready(function() {

 // Themes begin
 am4core.useTheme(am4themes_animated);
 // Themes end

 chart1 = am4core.create("chartdiv1", am4charts.XYChart);
 chart2 = am4core.create("chartdiv2", am4charts.XYChart);

 chart1.data = [{
  "keyword": "INFO",
  "val": 0
 }, {
  "keyword": "ERROR",
  "val": 0
 }];

 chart2.data = [{
  "keyword": "INFO",
  "val": 0
 }, {
  "keyword": "ERROR",
  "val": 0
 }];

 chart1.padding(40, 40, 40, 40);
 chart2.padding(40, 40, 40, 40);
 
let title1 = chart1.titles.create();
title1.text = "S3 Log Count";
title1.fontSize = 25;
title1.marginBottom = 30;

let title2 = chart2.titles.create();
title2.text = "Apache Access Log Count";
title2.fontSize = 25;
title2.marginBottom = 30;

 var categoryAxis1 = chart1.xAxes.push(new am4charts.CategoryAxis());
 categoryAxis1.renderer.grid.template.location = 0;
 categoryAxis1.dataFields.category = "keyword";
 categoryAxis1.renderer.minGridDistance = 60;
 categoryAxis1.renderer.inversed = true;
 categoryAxis1.renderer.grid.template.disabled = true;

 var categoryAxis2 = chart2.xAxes.push(new am4charts.CategoryAxis());
 categoryAxis2.renderer.grid.template.location = 0;
 categoryAxis2.dataFields.category = "keyword";
 categoryAxis2.renderer.minGridDistance = 60;
 categoryAxis2.renderer.inversed = true;
 categoryAxis2.renderer.grid.template.disabled = true;

 var valueAxis1 = chart1.yAxes.push(new am4charts.ValueAxis());
 valueAxis1.min = 0;
 valueAxis1.extraMax = 0.1;

 var valueAxis2 = chart2.yAxes.push(new am4charts.ValueAxis());
 valueAxis2.min = 0;
 valueAxis2.extraMax = 0.1;

 var series1 = chart1.series.push(new am4charts.ColumnSeries());
 series1.dataFields.categoryX = "keyword";
 series1.dataFields.valueY = "val";
 series1.tooltipText = "{valueY.value}"
 series1.columns.template.strokeOpacity = 0;
 series1.columns.template.column.cornerRadiusTopRight = 10;
 series1.columns.template.column.cornerRadiusTopLeft = 10;
 //series.interpolationDuration = 1500;
 //series.interpolationEasing = am4core.ease.linear;
 var labelBullet1 = series1.bullets.push(new am4charts.LabelBullet());
 labelBullet1.label.verticalCenter = "bottom";
 labelBullet1.label.dy = -10;
 labelBullet1.label.text = "{values.valueY.workingValue.formatNumber('#.')}";

 var series2 = chart2.series.push(new am4charts.ColumnSeries());
 series2.dataFields.categoryX = "keyword";
 series2.dataFields.valueY = "val";
 series2.tooltipText = "{valueY.value}"
 series2.columns.template.strokeOpacity = 0;
 series2.columns.template.column.cornerRadiusTopRight = 10;
 series2.columns.template.column.cornerRadiusTopLeft = 10;
 //series.interpolationDuration = 1500;
 //series.interpolationEasing = am4core.ease.linear;
 var labelBullet2 = series2.bullets.push(new am4charts.LabelBullet());
 labelBullet2.label.verticalCenter = "bottom";
 labelBullet2.label.dy = -10;
 labelBullet2.label.text = "{values.valueY.workingValue.formatNumber('#.')}";

chart1.background.stroke = am4core.color("red");
chart2.background.stroke = am4core.color("blue"); 

 // as by default columns of the same series are of the same color, we add adapter which takes colors from chart.colors color set
 series1.columns.template.adapter.add("fill", function (fill, target) {
  return chart1.colors.getIndex(target.dataItem.index);
 });
 
 series2.columns.template.adapter.add("fill", function (fill, target) {
     return chart2.colors.getIndex(target.dataItem.index);
    });
 
 //categoryAxis.sortBySeries = series;
 }); // end am4core.ready()
 
  var wsUri = "ws://localhost:8080/LogViewer/websocket/echo.do";
 
  function init() {
      var output = document.getElementById("output");
  }
  function send_message() {
      websocket = new WebSocket(wsUri);
      websocket.onopen = function(evt) {
          onOpen(evt)
      };
      websocket.onmessage = function(evt) {
          chart1.invalidateRawData();
          chart2.invalidateRawData();
          onMessage(evt)
      };
      websocket.onerror = function(evt) {
          onError(evt)
      };
  }
 
  function onOpen(evt) {
      writeToScreen("Connected to Endpoint!");
      doSend(textID.value);
  }
  function onMessage(evt) {       
  	
  	console.log(evt.data);
  	
  	const data = JSON.parse(evt.data);  	
  	if(data.type == "text")
	{
		const text = data.value;
	    writeToScreen("Message Received: " + text);
	}
  	else if(data.type == "chartdata")
	{
		const arr = data.value;
	  	var lastIndex = 0;
		var j = 0;
		
		var tmpChart = null;
		if(data.tname == "S3")
		{
			tmpChart = chart1;
		}
		else if(data.tname = "Apache")
		{
			tmpChart = chart2;
		}
		else
		{
			return;
		}
		
		am4core.array.each(tmpChart.data, function (item) 
		{
			 if(item.keyword != arr[j].keyword)
			 {
				tmpChart.data = data.value;
				return;
			 }
			 item.val = arr[j].val;
			 j++;
		});
		
		tmpChart.invalidateRawData();
	}
  	else if(data.type == "error")
  	{
		const arr = data.value;
		for(var i = 0; i < arr.length; i++)
		{
			add_error_row(arr[i].code, arr[i].type, arr[i].text, arr[i].date);
		}
		openModal();
  	}
  }
  function onError(evt) {
      writeToScreen('ERROR: ' + evt.data);
  }
  function doSend(message) {
      writeToScreen("Message Sent: " + message);
      websocket.send(message);
      //websocket.close();
  }
  function writeToScreen(message) {
      var pre = document.createElement("p");
      pre.style.wordWrap = "break-word";
      pre.innerHTML = message;
      
      output.appendChild(pre);
  }
  window.addEventListener("load", init, false);

	function toggleModal()
	{
		if($('.container').hasClass('modal-open'))
		{
	  		$('.container').removeClass('modal-open');
		}
		else
		{
	  		$('.container').addClass('modal-open');
		}
	}

	function openModal()
	{
		if(!$('.container').hasClass('modal-open'))
		{
	  		$('.container').addClass('modal-open');
		}
	}
	
	
 function add_error_row(type, code, msg, date) 
 {
    var my_tbody = document.getElementById('ErrorList');
    var row = my_tbody.insertRow(0);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    cell1.innerHTML = type;
    cell2.innerHTML = code;
    cell3.innerHTML = msg;
    cell4.innerHTML = date;
  }
  
  
  
  send_message()