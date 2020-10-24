<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LogViewer</title>
<script src="https://cdn.amcharts.com/lib/4/core.js"></script>
<script src="https://cdn.amcharts.com/lib/4/charts.js"></script>
<script src="https://cdn.amcharts.com/lib/4/themes/animated.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
<link rel="stylesheet" href="resources/css/style.css">
<script>
</script>

</head>
<body>

<h1 style="text-align: center; display: inline-block; width: 100%">LogViewer</h1>
<br>

<div id="chartdiv1"></div>
<div id="chartdiv2"></div>
<div id="output">&nbsp;</div>

<div id="my_modal">
<p><a class="modal_close_btn">Close</a></p>
<div id="log_msg">
   l
</div>
</div>
       
  <div class="container">
  <div class="modal">
    <center><a class="btn js-close-modal" onclick="toggleModal()">Close</a></center>
    <br>
    <div class="errorContainer">
	<table>
	<colgroup>
		<col width="10%" />
		<col width="15%" />
		<col width="55%" />
		<col width="20%" />
	</colgroup>
	<tr>
		<th>Type</th>
		<th>Error Code</th>
		<th>Error Message</th>
		<th>Write Date</th>		
	</tr>
	</table>
	<table id="ErrorList">
	<colgroup>
		<col width="10%" />
		<col width="15%" />
		<col width="55%" />
		<col width="20%" />
	</colgroup>
	</table>
    </div>
    </div>
</div>

</body>
<script src='resources/js/script.js'></script>

</html>