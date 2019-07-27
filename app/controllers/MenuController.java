package controllers;

import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class MenuController extends Controller
{

    @Transactional(readOnly = true)
    public Result getMenu()
    {
        return ok(views.html.Menu.render());
    }




}
