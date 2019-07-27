package controllers;

import models.SalesTrend;
import play.mvc.Controller;
import play.mvc.Result;

public class ChartController extends Controller
{
    public Result getDemo2()
    {

        return ok(views.html.SalesTrendChart.render("merp", "32|12|45|49|12|9"));
    }

}
