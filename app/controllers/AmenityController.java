package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.helper.form;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmenityController extends Controller
{
    private FormFactory formFactory;
    private AmenityRepository amenityRepository;
    private Config config;

    @Inject
    public AmenityController(FormFactory formFactory, Config config, AmenityRepository amenityRepository)
    {
        this.formFactory = formFactory;
        this.config = config;
        this.amenityRepository = amenityRepository;
    }

/*
    public Result getViewHomeAmenities()
    {
        return ok(views.html.ViewAmenities.render());
    }

*/
    /*

    @Transactional
    public Result postViewHomeAmenities(String street, String city, String state) throws Exception
    {

        Amenity amenity = new Amenity();
        DynamicForm form = formFactory.form().bindFromRequest();

        String apiKey = config.getString("apiKeys.attomdata");
        JsonNode test = null;


        URL url = new URL("https://api.gateway.attomdata.com/propertyapi/v1.0.0/saleshistory/detail?" +
                "address1=" + street + "&address2=" + city + "%2C%20" + state);
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
        Logger.debug("foundNode");


        JsonNode propertyNode = propertyNodes.next();


        JsonNode lot = propertyNode.get("lot");
        JsonNode building = propertyNode.get("building");
        JsonNode size = building.get("size");
        JsonNode rooms = building.get("rooms");
        JsonNode buildingSummary = building.get("summary");
        JsonNode summary = propertyNode.get("summary");
        JsonNode saleHistoryNode = propertyNode.get("salehistory");
        JsonNode amountNode = saleHistoryNode.get("amount");


        homeFeature.setHouseSqrFeet(size.get("grosssize").asInt());

        homeFeature.setLotSqrFeet(lot.get("lotsize2").asInt());

        homeFeature.setNumBedrooms(rooms.get("beds").asInt());

        homeFeature.setNumBathrooms(rooms.get("bathstotal").asInt());

        homeFeature.setYearBuilt(summary.get("yearbuilt").asInt());

        homeFeature.setNumFloors(buildingSummary.get("levels").asInt());


        while (propertyNodes.hasNext())
        {
            SaleHistory saleHistory2 = new SaleHistory();

            saleHistory2.setDateOfSale(amountNode.get("salerecdate").asText());
            saleHistory2.setPastSellPrice(amountNode.get("saleamt").decimalValue());

            saleHistoryRepository.addSaleHistory(saleHistory2);
        }


        return ok(views.html.ViewHome.render(homeFeature, saleHistory));
    }


     */
}
