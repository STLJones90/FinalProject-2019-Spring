package controllers;

import models.HomeFeature;
import models.SaleHistory;
import models.SetAddress;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import javax.persistence.Entity;


public class SetAddressController extends Controller
{

    private FormFactory formFactory;


    @Inject
    public SetAddressController(FormFactory formFactory)
    {
        this.formFactory = formFactory;
    }


    @Transactional(readOnly = true)
    public Result getSetAddress()
    {
        return ok(views.html.SetAddress.render());
    }


/*
    @Transactional
    public Result postSetAddress()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        SetAddress setAddress = new SetAddress();

        String street = form.get("street");
        street = street.replace(" ", "%20");

        String city = form.get("city");
        city = city.replace(" ", "%20");

        String state = form.get("state");
        state = state.replace(" ", "%20");

        setAddress.setStreet(street);
        setAddress.setCity(city);
        setAddress.setState(state);

        return ok(views.html.ViewHomeData.render(setAddress));
    }

*/



}
