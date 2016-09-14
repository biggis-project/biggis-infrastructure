<html>
  <head>
    <title>BigGIS Pipeline Prototype</title>
  </head>
  <body bgcolor=white>
	<h2>BigGIS Pipeline Prototype</h2>
	
	<table id="kafka-event-table">
	
	</table>
	
      <script type="text/javascript">
        var url = "/biggis/api/events";
        var source = new EventSource(url);
        
        source.onerror = function (event) {
            console.log(event);
            console.log(source);
            console.log("error [" + source.readyState + "]");
        };

        source.onopen = function (event) {
            console.log("eventsource opened!");
            // Send request
            console.log(event);
        };
        

        source.onmessage = function (event) {
            //console.log(event.data);
            
            var table = document.getElementById("kafka-event-table");
            var row = table.insertRow(0);
            var cell1 = row.insertCell(0);
            cell1.innerHTML = event.data;
        };
    </script>
  </body>
</html>