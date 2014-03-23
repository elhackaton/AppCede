<!DOCTYPE html>
<html>
  <head>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/map.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300' rel='stylesheet' type='text/css'>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script src="/js/jquery-2.1.0.min.js"></script>
    <script src="/js/fontawesome-markers.js"></script>
    <script>
        var map;
        var ajaxmapload = false;
        var json;
        var marks = [];


        function initialize() {
          var mapOptions = {
            zoom: 15,
            center: new google.maps.LatLng(36.8369, -2.45169)
          };
          map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);

          if(ajaxmapload)
            loadMarks();
          else
            ajaxmapload = true;
        }
        google.maps.event.addDomListener(window, 'load', initialize);

        $.ajax({
          url: "http://192.168.1.40/api/reviews",
          cache: false
        })
        .done(function( result ) {
          json = result;
          if(ajaxmapload)
            loadMarks();
          else
            ajaxmapload = true;

        });

        function clearMarks() {
          for (var i = marks.length - 1; i >= 0; i--) {
            marks[i].setMap(null);
          };

          marks = [];
        }
        function loadMarks() {
          for (var i = json.length - 1; i >= 0; i--) {

            var re = json[i];

            var myLatlng = new google.maps.LatLng(re.lat,re.lng);

            if(((type[2] && re.solutions.length>0) || type[re.review]) && category[re.category] && place[re.place])
            {
              // To add the marker to the map, use the 'map' property
              /*var marker = new google.maps.Marker({
                  position: myLatlng,
                  map: map,
                  title:json[i].title
              });*/

            var path;
            if(re.review)
            {
              if(re.category == 0)
                var image = 'img/gota-verde-movilidad.png';
              else if(re.category == 1)
                var image = 'img/gota-verde-visual.png';
              else if(re.category == 2)
                var image = 'img/gota-verde-auditivo.png';
              else
                var image = 'img/gota-verde-intelectual.png';  
            }
            else
            {
              if(re.category == 0)
                var image = 'img/gota-roja-movilidad.png';
              else if(re.category == 1)
                var image = 'img/gota-roja-visual.png';
              else if(re.category == 2)
                var image = 'img/gota-roja-auditivo.png';
              else
                var image = 'img/gota-roja-intelectual.png';   
            } 

            if(re.review)
              var color = '#37ab2e';
            else
              var color = '#A72D2D';

              marker = new google.maps.Marker({
                  position: myLatlng,
                  map: map,
                  icon: image,
                  title: re.title,
                  id:re.id,
              }); 
              var id = re.id;

              google.maps.event.addListener(marker, 'click', function(event) {
                var ll = event.latLng;
                window.location = 'reviews/'+this.id;
              }.bind(marker));
              marks[marks.length] = marker;
            }

          };
        }

        var type=[true,true,true];
        var category = [true,true,true,true];
        var place = [true,true,true,true,true];

        function typefunction(n)
        {
          for (var i = type.length - 1; i >= 0; i--) {
            if(i == n)
              type[n] = true;
            else
              type[i] = false;    

            if(!type[i])
              $('#type'+i).css({backgroundColor:'#37ab2e'});
            else
              $('#type'+i).css({backgroundColor:'#278b1e'});
        }
          clearMarks();
          loadMarks();
        }
        function categoryfunction(n)
        {
          for (var i = category.length - 1; i >= 0; i--) {
            if(i == n)
              category[n] = true;
            else
              category[i] = false;    

            if(!category[i])
              $('#category'+i).css({backgroundColor:'#37ab2e'});
            else
              $('#category'+i).css({backgroundColor:'#278b1e'});
        }

          clearMarks();
          loadMarks();
        }
        function placefunction(n)
        {
          for (var i = place.length - 1; i >= 0; i--) {
            if(i == n)
              place[n] = true;
            else
              place[i] = false;    

            if(!place[i])
              $('#place'+i).css({backgroundColor:'#37ab2e'});
            else
              $('#place'+i).css({backgroundColor:'#278b1e'});
        }
          clearMarks();
          loadMarks();
        }        


    </script>
  </head>
  <body>
    <div id='upper'><img src="/img/logo-appcede-horizontal.png" alt="">
        <div class="btn btn-success pull-right">FAQ</div>
        <div class="btn btn-success pull-right">Contacta</div>
        <div class="btn btn-success pull-right">App</div>
    </div>
    <div id='padding'>
      <div id='bar'>
        <div>
          <h4>Valoración</h4>
          <ul class='list-unstyled'>
            <li id='type1' onclick='typefunction(1)'><i class="fa fa-thumbs-o-up"></i> Positiva</li>
            <li id='type0' onclick='typefunction(0)'><i class="fa fa-thumbs-o-down"></i> Problemas</li>
            <li id='type2' onclick='typefunction(2)'><i class="fa fa-lightbulb-o"></i> Soluciones</li>
          </ul>
        </div>
        <div>
          <h4>Discapacidad</h4>
          <ul class=' list-unstyled'>
            <li id='category0' onclick='categoryfunction(0)'><div class='category' id='movilidad'></div> Movilidad</li>
            <li id='category1' onclick='categoryfunction(1)'><div class='category' id='visual'></div> Visual</li>
            <li id='category2' onclick='categoryfunction(2)'><div class='category' id='auditiva'></div> Auditiva</li>
            <li id='category3' onclick='categoryfunction(3)'><div class='category' id='intelectual'></div> Intelectual</li>
          </ul>
        </div>      
        <div>
          <h4>Lugar</h4>
          <ul class=' list-unstyled'>
            <li id='place0' onclick='placefunction(0)'><i class="fa fa-cutlery"></i> Hosteleria</li>
            <li id='place1' onclick='placefunction(1)'><i class="fa fa-h-square"></i> Hoteles</li>
            <li id='place2' onclick='placefunction(2)'><i class="fa fa-shopping-cart"></i> Comercio</li>
            <li id='place3' onclick='placefunction(3)'><i class="fa fa-road"></i> Via pública</li>
            <li id='place4' onclick='placefunction(4)'><i class="fa fa-building-o "></i> Instituciones</li>
            <li id='place5' onclick='placefunction(5)'><i class="fa fa-bug"></i> Ocio</li>
          </ul>
        </div>
      </div>
      <div id="map-canvas"></div>
    </div>
  </body>
</html>