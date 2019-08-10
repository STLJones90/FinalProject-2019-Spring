package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class HomeController extends Controller
{

    private FormFactory formFactory;
    private HomeRepository homeRepository;
    private SaleHistoryRepository saleHistoryRepository;
    private SaleHistory saleHistory;
    private Config config;

    @Inject
    public HomeController(FormFactory formFactory, HomeRepository homeRepository, Config config,
                          SaleHistoryRepository saleHistoryRepository, SaleHistory saleHistory)
    {
        this.formFactory = formFactory;
        this.homeRepository = homeRepository;
        this.config = config;
        this.saleHistoryRepository = saleHistoryRepository;
        this.saleHistory = saleHistory;
    }


    private JsonNode getZillowHomeFeatures(String zillowStreet, String zillowCity, String zillowState) throws Exception
    {
        URL saleHistoryUrl = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?" +
                "zws-id=X1-ZWz17rshl5psej_4srxq&" +
                "address=" + zillowStreet + "&citystatezip=" + zillowCity + "%2C+" + zillowState);

        HttpURLConnection connection = (HttpURLConnection) saleHistoryUrl.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(content.toString());

    }


    private JsonNode getAreaSalesTrend(int zipCode, String interval, int startYear, int endYear) throws Exception
    {

        String apiKey = config.getString("apiKeys.attomdata");

        URL saleHistoryUrl = new URL("https://api.gateway.attomdata.com/propertyapi/v1.0.0/salestrend/snapshot?" +
                "geoid=ZI" + zipCode + "&interval=" + interval + "&startyear=" + startYear + "&endyear=" + endYear +
                "&startmonth=january&endmonth=december");


        HttpURLConnection connection = (HttpURLConnection) saleHistoryUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("apikey", apiKey);
        connection.setRequestProperty("accept", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(content.toString());

    }

    private JsonNode getBusinessPhotos(String businessName) throws Exception
    {
        String apiKey = config.getString("apiKeys.google");

        URL saleHistoryUrl = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" +
                "input=" + businessName + "&inputtype=textquery&fields=photo&" +
                "key=" + apiKey);

        HttpURLConnection connection = (HttpURLConnection) saleHistoryUrl.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(content.toString());

    }


    private JsonNode getAreaAmenities(int zipCode, int radius) throws Exception
    {
        String apiKey = config.getString("apiKeys.attomdata");

        URL saleHistoryUrl = new URL("https://api.gateway.attomdata.com/poisearch/v2.0.0/poi/geography?" +
                "PostalCodeKey=" + zipCode + "&SearchDistance=" + radius + "&RecordLimit=50&Sort=DISTANCE");

        HttpURLConnection connection = (HttpURLConnection) saleHistoryUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("apikey", apiKey);
        connection.setRequestProperty("accept", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(content.toString());

    }

    private JsonNode getHomeFeatures(String street, String city, String state) throws Exception
    {
        String apiKey = config.getString("apiKeys.attomdata");
        URL saleHistoryUrl = new URL("https://api.gateway.attomdata.com/propertyapi/v1.0.0/saleshistory/detail?" +
                "address1=" + street + "&address2=" + city + "%2C%20" + state);
        HttpURLConnection connection = (HttpURLConnection) saleHistoryUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("apikey", apiKey);
        connection.setRequestProperty("accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(content.toString());
    }


    @Transactional
    public Result postAddHomes() throws Exception
    {

        String apiKey = config.getString("apiKeys.attomdata");

        List<Home> homes = new ArrayList<>();

        JsonNode test = null;

        URL url = new URL("https://api.gateway.attomdata.com/propertyapi/" +
                "v1.0.0/property/address?postalcode=72113&page=1&pagesize=10");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("apikey", apiKey);
        connection.setRequestProperty("accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(content.toString());

        node = node.get("property");
        Logger.debug("PROPERTY: " + node);
        test = node;

        Iterator<JsonNode> propertyNodes = node.elements();

        while (propertyNodes.hasNext())
        {
            Home home = new Home();
            Logger.debug("foundNode");
            JsonNode propertyNode = propertyNodes.next();

            JsonNode address = propertyNode.get("address");
            JsonNode location = propertyNode.get("location");
            home.setCity(address.get("locality").asText());
            home.setAddress(address.get("line1").asText());
            home.setState(address.get("countrySubd").asText());
            home.setZipCode(address.get("postal1").asInt());
            home.setLatitude(location.get("latitude").asDouble());
            home.setLongitude(location.get("longitude").asDouble());
            homeRepository.add(home);
        }
        return ok("did it");
    }


    @Transactional(readOnly = true)
    public Result getViewAreaAmenities()
    {
        return ok(views.html.Menu.render());
    }


    @Transactional
    public Result postViewAreaAmenities() throws Exception
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        //most of our method is in this try/catch block in order to prevent an error if the user enters in blank or
        //illogical inputs. It will redirect the user to an error page.
        try
        {
            //getting the input from the form in the Nav Bar and converting it to ints
            int zipCode = Integer.parseInt(form.get("zipCode"));
            int radius = Integer.parseInt(form.get("radius"));

            //"type" is business type that the user chooses to search for
            String type = form.get("type");

            //plugging in our ints into parameters for our API call method
            JsonNode amenityNode = getAreaAmenities(zipCode, radius);

            //going down through the keys until we get to where we want to be
            amenityNode = amenityNode.get("response");
            amenityNode = amenityNode.get("result");
            Logger.debug("RESULT: " + amenityNode);
            Logger.debug("foundNode");
            amenityNode = amenityNode.get("package");

            //now that we are where we want to be, we iterate through the "amenityNode" keys
            Iterator<JsonNode> itemNodes = amenityNode.get("item").iterator();

            //creating lists for the data we are about to store
            List<Amenity> amenities = new ArrayList<>();
            List<Location> locations = new ArrayList<>();
            List<Double> startingLatitudes = new ArrayList<>();
            List<Double> startingLongitudes = new ArrayList<>();
            List<String> businessPhotos = new ArrayList<>();

            //looping through the data and adding to our lists as ling as there is another key in the array of
            //"package" keys
            while (itemNodes.hasNext())
            {
                Amenity amenity = new Amenity();
                Location location = new Location();
                JsonNode itemNode = itemNodes.next();

                //Attom data will return every business in every category. I felt as if that would be too much data on
                //my map and wanted to give the user an option to pick a particular category. "business_category"
                //contains keys with a generic names and "type" is what the user selects. This loop returns data that
                //will only be from the category that matches the inputted type.
                if (itemNode.get("business_category").asText().equals(type))
                {

                    amenity.setAmenityZipCode(itemNode.get("zip_code").asInt());
                    amenity.setAmenityAddress(itemNode.get("street").asText());
                    amenity.setAmenityCity(itemNode.get("city").asText());
                    amenity.setAmenityType(itemNode.get("business_category").asText());
                    amenity.setAmenityName(itemNode.get("name").asText());
                    amenity.setAmenityLongitude(itemNode.get("geo_longitude").asDouble());
                    amenity.setAmenityLatitude(itemNode.get("geo_latitude").asDouble());
                    amenity.setIndustry(itemNode.get("industry").asText());

                    location.setLatitude(itemNode.get("geo_latitude").asDouble());
                    location.setLongitude(itemNode.get("geo_longitude").asDouble());

                    amenities.add(amenity);
                    locations.add(location);

                    startingLatitudes.add(itemNode.get("geo_latitude").asDouble());
                    startingLongitudes.add(itemNode.get("geo_longitude").asDouble());

                    //here we are storing "businessNameURL" in a String to be used later on for a search feature in
                    //javascript. We replace the spaces with %20 as we would get an error otherwise.
                    String businessNameURL = amenity.getAmenityName();
                    businessNameURL = businessNameURL.replace(" ", "%20");

                    //this try/catch block catches errors that will be caused if a business does not have a stock
                    //google photo
                    try
                    {
                        JsonNode node = getBusinessPhotos(businessNameURL);
                        node = node.get("candidates");
                        Iterator<JsonNode> candidateNodes = node.elements();
                        JsonNode candidateNode = candidateNodes.next();
                        candidateNode = candidateNode.get("photos");
                        Iterator<JsonNode> photoNodes = candidateNode.elements();
                        JsonNode photoNode = photoNodes.next();
                        String businessPhoto = photoNode.get("photo_reference").asText();
                        businessPhotos.add(businessPhoto);

                    }

                    //both of these will add String noPhoto to our businessPhotos list instead of businessPhoto if we
                    //get and error. The link is to a stock "no photo available" link
                    catch (NullPointerException e)
                    {
                        String noPhoto = "https://www.hopkinsmedicine.org/-/media/feature/noimageavailable.ashx";
                        businessPhotos.add(noPhoto);
                    }
                    catch (NoSuchElementException e)
                    {
                        String noPhoto = "https://www.hopkinsmedicine.org/-/media/feature/noimageavailable.ashx";
                        businessPhotos.add(noPhoto);
                    }
                }
            }


            //our Javascript file takes a "locations" (lat/long) parameter. We take our list of location objects and
            //convert them to Json where they will end up being placed into a parameter of a locations function in
            //javascript. Essentially, this is what puts all of the blips for businesses on our map
            JsonNode json = Json.toJson(locations);

            //Our javascript map needs a lat/long to mark as the center of the map. The first lat/long we store in our
            //is what we mark as the center of the map. I'm sure there is a better way to do this but it works.
            Double startingLatitude = startingLatitudes.get(0);
            Double startingLongitude = startingLongitudes.get(0);

            //converting all of our amenities variables into a long String so they can be parsed in javascript
            String businessZipCode = amenities.stream()
                    .map(amenity -> Integer.toString(amenity.getAmenityZipCode())).
                            collect(Collectors.joining("|"));

            String businessAddress = amenities.stream()
                    .map(amenity -> amenity.getAmenityAddress()).
                            collect(Collectors.joining("|"));

            String businessName = amenities.stream()
                    .map(amenity -> amenity.getAmenityName()).
                            collect(Collectors.joining("|"));

            String businessType = amenities.stream()
                    .map(amenity -> amenity.getIndustry()).
                            collect(Collectors.joining("|"));

            String businessPhoto = businessPhotos.stream()
                    .collect(Collectors.joining("|"));


            //Pass everything into the HTML file
            return ok(views.html.ViewAmenities.render(amenities, startingLatitude, startingLongitude,
                    new Html(Json.stringify(json)), businessName, businessAddress, businessType,
                    businessPhoto, businessZipCode));

        } catch (NumberFormatException e)
        {
            return ok(views.html.ErrorPage.render());
        }

    }

    @Transactional(readOnly = true)
    public Result getSaleTrends()
    {
        return ok(views.html.Menu.render());
    }

    @Transactional
    public Result postSaleTrends() throws Exception
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        //this try/catch block re-directs the user to an error page if they leave any fields blank or enter a zip code
        //that does not exist
        try
        {
            //getting the user input from the HTML and assigning the values to strings
            int zipCode = Integer.parseInt(form.get("zipCode"));
            String interval = form.get("interval");
            int startYear = Integer.parseInt(form.get("startYear"));
            int endYear = Integer.parseInt(form.get("endYear"));

            //now that we have values for the these variables, we put them into the constructor which calls the API
            JsonNode node = getAreaSalesTrend(zipCode, interval, startYear, endYear);

            //we search for the "salestrends" key which contains several keys that we need to grab the values from
            node = node.get("salestrends");
            Logger.debug("SALESTRENDS: " + node);
            Iterator<JsonNode> salesTrendNodes = node.elements();
            Logger.debug("foundNode");

            List<SalesTrend> salesTrends = new ArrayList<>();

            int monthCount = 1;
            int quarterCount = 1;

            //looping through all of the sales data
            while (salesTrendNodes.hasNext())
            {
                JsonNode salesTrendNode = salesTrendNodes.next();

                SalesTrend salesTrend = new SalesTrend();

                //I had to do some funky code here to get my chart to display the month the data is associated
                //with. Originally there was no date associated with the data. I made Strings for all 12 months and all
                //four quarters
                String january = "January";
                String february = "February";
                String march = "March";
                String april = "April";
                String may = "May";
                String june = "June";
                String july = "July";
                String august = "August";
                String september = "September";
                String october = "October";
                String november = "Novemeber";
                String december = "December";

                String q1 = "Q1";
                String q2 = "Q2";
                String q3 = "Q3";
                String q4 = "Q4";

                //if the user selects "quarterly" as the interval, we go into the quarterCount switch statement and
                //assign "setInterval" value "Q1" the first go around, "Q2" the second, and so on until we hit 5 where
                //quarter count is reset to one. We go through this loop until our salesTrendNodes while loop is done
                if (interval.equals("Quarterly"))
                {
                    if (quarterCount > 4)
                    {
                        quarterCount = 1;
                    }
                    switch (quarterCount)
                    {
                        case 1:
                        {
                            salesTrend.setInterval(q1);
                            break;
                        }
                        case 2:
                        {
                            salesTrend.setInterval(q2);
                            break;
                        }
                        case 3:
                        {
                            salesTrend.setInterval(q3);
                            break;
                        }
                        case 4:
                        {
                            salesTrend.setInterval(q4);
                            break;
                        }
                    }
                }
                //same with the quarter loop. We assign setInterval a value using a switch statement
                if (interval.equals("Monthly"))
                {
                    if (monthCount > 12)
                    {
                        monthCount = 1;
                    }
                    switch (monthCount)
                    {
                        case 1:
                        { salesTrend.setInterval(january);
                            break; }
                        case 2:
                        {
                            salesTrend.setInterval(february);
                            break;
                        }
                        case 3:
                        {
                            salesTrend.setInterval(march);
                            break;
                        }
                        case 4:
                        {
                            salesTrend.setInterval(april);
                            break;
                        }
                        case 5:
                        {
                            salesTrend.setInterval(may);
                            break;
                        }
                        case 6:
                        {
                            salesTrend.setInterval(june);
                            break;
                        }
                        case 7:
                        {
                            salesTrend.setInterval(july);
                            break;
                        }
                        case 8:
                        {
                            salesTrend.setInterval(august);
                            break;
                        }
                        case 9:
                        {
                            salesTrend.setInterval(september);
                            break;
                        }
                        case 10:
                        {
                            salesTrend.setInterval(october);
                            break;
                        }
                        case 11:
                        {
                            salesTrend.setInterval(november);
                            break;
                        }
                        case 12:
                        {
                            salesTrend.setInterval(december);
                            break;
                        }
                    }
                }
                //storing the data from the API
                JsonNode dateRangeNode = salesTrendNode.get("daterange");
                JsonNode SalesTrendNode = salesTrendNode.get("SalesTrend");
                salesTrend.setStartDateRange(dateRangeNode.get("start").asText());
                salesTrend.setEndDateRange(dateRangeNode.get("end").asText());
                salesTrend.setHomeCount(SalesTrendNode.get("homesalecount").asInt());
                salesTrend.setAverageSalePrice(SalesTrendNode.get("avgsaleprice").asInt());
                salesTrend.setMedSalePrice(SalesTrendNode.get("medsaleprice").asInt());

                //increasing the count for our setInterval loop
                monthCount++;
                quarterCount++;
                salesTrends.add(salesTrend);
            }
            //converting all of our salesTrends variables into a long String so they can be parsed in javascript and
            //be inserted as data points in my chart
            String averageSaleDataPoint = salesTrends.stream()
                    .map(salesTrend -> Long.toString(salesTrend.getAverageSalePrice()))
                    .collect(Collectors.joining("|"));

            String medSaleDataPoint = salesTrends.stream()
                    .map(salesTrend -> Long.toString(salesTrend.getMedSalePrice()))
                    .collect(Collectors.joining("|"));

            String homesSoldDataPoint = salesTrends.stream()
                    .map(salesTrend -> Long.toString(salesTrend.getHomeCount()))
                    .collect(Collectors.joining("|"));

            String yearLabels = salesTrends.stream()
                    .map(salesTrend -> salesTrend.getInterval() + " " + salesTrend.getStartDateRange())
                    .collect(Collectors.joining("|"));

            String intervalLabels = salesTrends.stream()
                    .map(salesTrend -> salesTrend.getInterval())
                    .collect(Collectors.joining("|"));

            //pushing all of the data into the HTML file
            return ok(views.html.SalesTrendChart.render(averageSaleDataPoint, medSaleDataPoint, homesSoldDataPoint,
                    yearLabels, intervalLabels, salesTrends, startYear, endYear, zipCode));

        }
        catch (NumberFormatException e)
        {
            return ok(views.html.ErrorPage.render());
        }
    }


    @Transactional(readOnly = true)
    public Result getViewHomeDetails()
    {
        return ok(views.html.Menu.render());
    }


    @Transactional
    public Result postViewHomeDetails() throws Exception
    {
        HomeFeature homeFeature = new HomeFeature();

        //This is where we assign our address variables a value from the form on our 'Menu' HTML file
        //They will be used throughout this method to connect to various API URLs
        DynamicForm form = formFactory.form().bindFromRequest();

        String street = form.get("street");
        street = street.replace(" ", "%20");

        String city = form.get("city");
        city = city.replace(" ", "%20");

        String state = form.get("state");
        state = state.replace(" ", "%20");



        try
        {
            //calling the getHomeFeatures method which connects to Attom Data's API
            JsonNode node = getHomeFeatures(street, city, state);

            //Going through the JSON to get where we want to be
            node = node.get("property");
            Logger.debug("PROPERTY: " + node);
            //Iterating through the JSON "property" array
            Iterator<JsonNode> propertyNodes = node.elements();
            Logger.debug("foundNode");
            JsonNode propertyNode = propertyNodes.next();

            //grabbing all of our keys and assigning them to unique nodes
            //some keys are nested within other keys i.e. JsonNode size, rooms, buildingSummary
            JsonNode lot = propertyNode.get("lot");
            JsonNode address = propertyNode.get("address");
            JsonNode location = propertyNode.get("location");
            JsonNode building = propertyNode.get("building");
            JsonNode size = building.get("size");
            JsonNode rooms = building.get("rooms");
            JsonNode buildingSummary = building.get("summary");
            JsonNode summary = propertyNode.get("summary");

            //setting our POJOs
            homeFeature.setFullAddress(address.get("oneLine").asText());

            homeFeature.setLatitude(location.get("latitude").asDouble());

            homeFeature.setLongitude(location.get("longitude").asDouble());

            homeFeature.setHouseSqrFeet(size.get("grosssize").asInt());

            homeFeature.setLotSqrFeet(lot.get("lotsize2").asInt());

            homeFeature.setNumBedrooms(rooms.get("beds").asText());

            homeFeature.setNumBathrooms(rooms.get("bathstotal").asInt());

            homeFeature.setYearBuilt(summary.get("yearbuilt").asInt());

            homeFeature.setNumFloors(buildingSummary.get("levels").asInt());

            List<SaleHistory> saleHistories = new ArrayList<>();

            Iterator<JsonNode> saleHistoryNodes = propertyNode.get("salehistory").iterator();

            //making sure we do not show confusing data
            if(homeFeature.getNumBedrooms().equals("0"))
            {
                homeFeature.setNumBedrooms("N/A");
            }

            //This is where we get the sale history of a home. Some homes have been bought and sold multiple times
            //so we need to make sure we get a list of all of those transactions and store them in an ArrayList
            while (saleHistoryNodes.hasNext())
            {
                SaleHistory saleHistory = new SaleHistory();
                JsonNode saleHistoryNode = saleHistoryNodes.next();
                JsonNode amountNode = saleHistoryNode.get("amount");
                JsonNode calculationNode = saleHistoryNode.get("calculation");

                //some homes have never been sold and, therefore, have NULL values which will cause an error
                //the following try/catch statement will store data into the POJO and we will deal with it
                //later in the code
                try
                {
                    saleHistory.setDateOfSale(amountNode.get("salerecdate").asText());
                }

                catch (NullPointerException e)
                {
                    saleHistory.setDateOfSale("No Data");
                }

                //as with the following try/catch statement, we are simply catching errors, temporarily giving
                //the POJO a value, and will deal with it later in the code
                try
                {
                    saleHistory.setPastSellPrice(amountNode.get("saleamt").decimalValue());

                }
                //again, storing data into the POJO to prevent a NULL error.
                catch (NullPointerException e)
                {
                    BigDecimal zero = new BigDecimal(0);
                    saleHistory.setPastSellPrice(zero);
                }

                saleHistory.setPricePerSqrFoot(calculationNode.get("pricepersizeunit").decimalValue());

                saleHistories.add(saleHistory);

                BigDecimal zero = new BigDecimal(0);

                //Here is where we finally deal with all of those NullPointerExceptions.
                //This loop simply looks for any saleHistory object that has Big Decimal "0" stored as a value
                //we remove the saleHistory from our list and are program continues to function
                //There is probably a better way to do this but it works
                for (int i = 0; i < saleHistories.size() ; i++)
                {
                    if(saleHistory.getPastSellPrice().equals(zero))
                    {
                        saleHistories.remove(saleHistory);
                    }
                }

            }

            //we convert these ints to Strings so that JavaScript can use them to make charts in our chart.js file
            String houseSqrFeet = Integer.toString(homeFeature.getHouseSqrFeet());
            String lotSqrFeet = Integer.toString(homeFeature.getLotSqrFeet());

            //same here, we are going to use these coordinates in our Map and Street View javascript files
            String latitude = Double.toString(homeFeature.getLatitude());
            String longitude = Double.toString(homeFeature.getLongitude());

            //This try block contains the code which connects to Zillow's API. We are going to grab a picture of the
            //home from Zillow's website. Unfortunately not all homes have Zillow pictures and we do not want to
            //get an NullPointerException error
            try
            {
                //this is a security feature. My API key is stored in a separate file that is NOT added to github
                String apiKey = config.getString("apiKeys.zillow");

                //using the street, city, and state Strings we already have from the form, we plug them into the URL
                //and get XML data unique to that address. All we need from this URL is the "zpid".
                //In order to get a picture of the home, we have to open a connection to a separate URL and plug
                //the zpid into that URL
                URL zillowUrl = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id="
                        +apiKey + "&address=" + street + "&citystatezip=" + city + "%2C+" + state);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(zillowUrl.openStream());

                //getting the zpid and storing it in a String
                Node searchResults = doc.getElementsByTagName("zpid").item(0);
                String zpId = searchResults.getTextContent();

                //plugging the zpid into the new URL
                URL zillowIdUrl = new URL("http://www.zillow.com/webservice/GetUpdatedPropertyDetails.htm?" +
                        "zws-id=" + apiKey+ "&zpid=" + zpId);

                DocumentBuilderFactory dbf2 = DocumentBuilderFactory.newInstance();
                DocumentBuilder db2 = dbf2.newDocumentBuilder();
                Document doc2 = db2.parse(zillowIdUrl.openStream());

                //Zillow does not allow their pictures to be embedded.
                //We store the URL for the picture of the home in a string
                //and will place that string into an HREF in our HTML
                Node searchResults2 = doc2.getElementsByTagName("url").item(0);
                String homePicture = searchResults2.getTextContent();

                return ok(views.html.ViewHome.render(homeFeature, saleHistories, houseSqrFeet, lotSqrFeet, latitude,
                        longitude, homePicture));

            }

            //if the home does not have a picture, we use a stock "no image" URL
            catch (NullPointerException e)
            {
                String homePicture = "https://www.readlightnovel.org/assets/images/noimage.jpg";
                return ok(views.html.ViewHome.render(homeFeature, saleHistories, houseSqrFeet, lotSqrFeet, latitude,
                        longitude, homePicture));
            }
        }

        //if any address fields are not entered, we route the user to an error page
        catch (IOException e)

        {
            return ok(views.html.ErrorPage.render());
        }
    }


    @Transactional(readOnly = true)
    public Result getHomeList()
    {
        List<Home> homes = homeRepository.getList();
        return ok(views.html.HomeList.render(homes));
    }

    @Transactional(readOnly = true)
    public Result getAddHomes()
    {
        List<Home> homes = homeRepository.getList();
        return ok(views.html.AddHomes.render());
    }


}