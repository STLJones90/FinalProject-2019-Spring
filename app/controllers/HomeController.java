package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


        int zipCode = Integer.parseInt(form.get("zipCode"));

        int radius = Integer.parseInt(form.get("radius"));

        String type = form.get("type");


        JsonNode amenityNode = getAreaAmenities(zipCode, radius);
        amenityNode = amenityNode.get("response");
        amenityNode = amenityNode.get("result");
        Logger.debug("RESULT: " + amenityNode);


        Logger.debug("foundNode");


        amenityNode = amenityNode.get("package");

        Iterator<JsonNode> itemNodes = amenityNode.get("item").iterator();

        List<Amenity> amenities = new ArrayList<>();

        while (itemNodes.hasNext())
        {
            Amenity amenity = new Amenity();

            JsonNode itemNode = itemNodes.next();


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

                amenities.add(amenity);

            }


        }


        return ok(views.html.ViewAmenities.render(amenities));
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

        int zipCode = Integer.parseInt(form.get("zipCode"));
        String interval = form.get("interval");
        int startYear = Integer.parseInt(form.get("startYear"));
        int endYear = Integer.parseInt(form.get("endYear"));

        JsonNode node = getAreaSalesTrend(zipCode, interval, startYear, endYear);

        node = node.get("salestrends");

        Logger.debug("SALESTRENDS: " + node);

        Iterator<JsonNode> salesTrendNodes = node.elements();

        Logger.debug("foundNode");

        List<SalesTrend> salesTrends = new ArrayList<>();

        int monthCount = 1;
        int quarterCount = 1;

        while (salesTrendNodes.hasNext())
        {

            JsonNode salesTrendNode = salesTrendNodes.next();

            SalesTrend salesTrend = new SalesTrend();

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



            //////////QUARTER////////////////////

            if(interval.equals("Quarterly"))
            {

                if (quarterCount > 4)
                {
                    quarterCount = 1;
                }

                switch (quarterCount)
                {
                    case 1:
                    {
                        salesTrend.setQuarter(q1);
                        break;
                    }

                    case 2:
                    {
                        salesTrend.setQuarter(q2);
                        break;
                    }

                    case 3:
                    {
                        salesTrend.setQuarter(q3);
                        break;
                    }

                    case 4:
                    {
                        salesTrend.setQuarter(q4);
                        break;
                    }
                }

            }


            ///////////MONTH//////////////

            if(interval.equals("Monthly"))
            {

                if (monthCount > 12)
                {
                    monthCount = 1;
                }

                switch (monthCount)
                {
                    case 1:
                    {
                        salesTrend.setMonth(january);
                        break;
                    }

                    case 2:
                    {
                        salesTrend.setMonth(february);
                        break;
                    }

                    case 3:
                    {
                        salesTrend.setMonth(march);
                        break;
                    }

                    case 4:
                    {
                        salesTrend.setMonth(april);
                        break;
                    }

                    case 5:
                    {
                        salesTrend.setMonth(may);
                        break;
                    }

                    case 6:
                    {
                        salesTrend.setMonth(june);
                        break;
                    }

                    case 7:
                    {
                        salesTrend.setMonth(july);
                        break;
                    }

                    case 8:
                    {
                        salesTrend.setMonth(august);
                        break;
                    }

                    case 9:
                    {
                        salesTrend.setMonth(september);
                        break;
                    }

                    case 10:
                    {
                        salesTrend.setMonth(october);
                        break;
                    }

                    case 11:
                    {
                        salesTrend.setMonth(november);
                        break;
                    }

                    case 12:
                    {
                        salesTrend.setMonth(december);
                        break;
                    }

                }

            }


            JsonNode dateRangeNode = salesTrendNode.get("daterange");
            JsonNode SalesTrendNode = salesTrendNode.get("SalesTrend");

            salesTrend.setStartDateRange(dateRangeNode.get("start").asText());
            salesTrend.setEndDateRange(dateRangeNode.get("end").asText());
            salesTrend.setHomeCount(SalesTrendNode.get("homesalecount").asInt());
            salesTrend.setAverageSalePrice(SalesTrendNode.get("avgsaleprice").asInt());


            monthCount++;
            quarterCount++;

            salesTrends.add(salesTrend);

        }

        return ok(views.html.ViewSalesTrends.render(salesTrends));
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

        DynamicForm form = formFactory.form().bindFromRequest();

        String street = form.get("street");
        street = street.replace(" ", "%20");

        String city = form.get("city");
        city = city.replace(" ", "%20");

        String state = form.get("state");
        state = state.replace(" ", "%20");


        //////////URL's///////////
        JsonNode node = getHomeFeatures(street, city, state);


        node = node.get("property");

        Logger.debug("PROPERTY: " + node);


        Iterator<JsonNode> propertyNodes = node.elements();

        Logger.debug("foundNode");

        JsonNode propertyNode = propertyNodes.next();

        JsonNode lot = propertyNode.get("lot");
        JsonNode building = propertyNode.get("building");
        JsonNode size = building.get("size");
        JsonNode rooms = building.get("rooms");
        JsonNode buildingSummary = building.get("summary");
        JsonNode summary = propertyNode.get("summary");


        homeFeature.setHouseSqrFeet(size.get("grosssize").asInt());

        homeFeature.setLotSqrFeet(lot.get("lotsize2").asInt());

        homeFeature.setNumBedrooms(rooms.get("beds").asInt());

        homeFeature.setNumBathrooms(rooms.get("bathstotal").asInt());

        homeFeature.setYearBuilt(summary.get("yearbuilt").asInt());

        homeFeature.setNumFloors(buildingSummary.get("levels").asInt());

        List<SaleHistory> saleHistories = new ArrayList<>();

        Iterator<JsonNode> saleHistoryNodes = propertyNode.get("salehistory").iterator();


        while (saleHistoryNodes.hasNext())
        {
            SaleHistory saleHistory = new SaleHistory();
            JsonNode saleHistoryNode = saleHistoryNodes.next();
            JsonNode amountNode = saleHistoryNode.get("amount");
            JsonNode calculationNode = saleHistoryNode.get("calculation");

            saleHistory.setDateOfSale(amountNode.get("salerecdate").asText());
            saleHistory.setPastSellPrice(amountNode.get("saleamt").decimalValue());
            saleHistory.setPricePerSqrFoot(calculationNode.get("pricepersizeunit").decimalValue());

            saleHistories.add(saleHistory);
        }

        return ok(views.html.ViewHome.render(homeFeature, saleHistories));

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