<html>
  <head>
    <title>BigGIS Raster Pipeline Prototype</title>
  </head>
  <body bgcolor=white>
	<h2>BigGIS Raster Pipeline</h2>
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
            console.log(event.data);
        };
    </script>
  </body>
</html>