var lat = parseFloat(document.getElementById("map").getAttribute("data-lat"));
var long = parseFloat(document.getElementById("map").getAttribute("data-long"));
var locations = JSON.parse(document.getElementById("map").getAttribute("data-locations"));
var businessName = document.getElementById('map').getAttribute('data-name').split("|");
var businessAddress = document.getElementById('map').getAttribute('data-address').split("|");
var businessType = document.getElementById('map').getAttribute('data-industry').split("|");
var businessPhoto = document.getElementById('map').getAttribute('data-photo').split("|");
var businessZipCode = document.getElementById('map').getAttribute('data-zipCode').split("|");

var mymap = L.map('mapid').setView([lat, long], 15);


L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?'
+'access_token=pk.eyJ1IjoibHJmbHJmIiwiYSI6ImNqdG94bWQwdTQ4Mng0Ym11YW52MWZ5bnEifQ.lZEH9-1FD4H2KYhVZDIe4Q', {
maxZoom: 18,

attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
  '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
  'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
id: 'mapbox.streets'
}).addTo(mymap);


for (var i = 0; i < locations.length; i++)
{
   var mapLocation = locations[i];
   var marker =  L.marker([mapLocation.latitude, mapLocation.longitude]);

   marker.addTo(mymap);

/*
   var handler = function(j)
   {
       return function()
       {
          window.location.href = "/testdb/" + j;
       }
   }
    (i);
*/

    marker.bindPopup("<h4>"+"Business Type: "+businessType[i]+"</h4>"+
    "<h4>"+"Business Name: "+businessName[i]+"</h4>"+
    "<h4>"+"Business Address: "+businessAddress[i]+"</h4>"+
    "<h4>"+"<a target='_blank' href=https://www.google.com/search?q="+businessName[i].replace(/ /g, '+') +">"+"Search Google"+"</a>"+"</h4>"+
    "<img src ='https://maps.googleapis.com/maps/api/place/photo?maxwidth=215&maxheight=215&photoreference="+businessPhoto[i]+"&key=AIzaSyD2ePWXCVK00oXrZwA9yqPOQqF6o6eXlss'/>"

    );




}

/*L.circle([51.508, -0.11], {

color: 'red',

fillColor: '#f03',

fillOpacity: 0.5,

radius: 4000

}).addTo(mymap);

​

L.polygon([

[51.508, -0.08],

[51.500, -0.06],

[51.511, -0.048]

]).addTo(mymap);*/