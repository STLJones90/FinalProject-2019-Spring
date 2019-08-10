package controllers;

import models.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.List;

public class TestController extends Controller
{
    private FormFactory formFactory;
    private JPAApi db;

    @Inject
    public TestController(FormFactory formFactory, JPAApi db)
    {
        this.formFactory = formFactory;
        this.db = db;
    }

    public Result getTest()
    {
        return ok(views.html.test.render("Test Text"));
    }

    public Result postTest()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String test = form.get("test");
        return ok(views.html.test.render(test));
    }

    @Transactional(readOnly = true)
    public Result getTestDb()
    {
        String sql = "SELECT t FROM Test t";
        TypedQuery query = db.em().createQuery(sql, Test.class);
        List<Test> test = query.getResultList();

        return ok(views.html.test.render("Rows: " + test.size()));
    }

    @Transactional
    public Result postTestDb()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String testName = form.get("test");
        Test test = new Test();
        test.setTestName(testName);
        db.em().persist(test);

        return redirect("/testdb");
    }


    public Result getZillow() throws Exception
    {
        String answer = "";


        URL url = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz17rshl5psej_4srxq" +
                "&address=30+Chicot+drive&citystatezip=Maumelle%2C+Ar");


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(url.openStream());
        Node searchResults = doc.getElementsByTagName("street").item(0);

        answer = searchResults.getTextContent();

        return ok("Got it: " + answer);

    }


    /*

    public Result getZillow() throws Exception
    {
        String answer = "";


        URL url = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz17rshl5psej_4srxq" +
                "&address=30+Chicot+drive&citystatezip=Maumelle%2C+Ar");


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(url.openStream());
        Node searchResults = doc.getElementsByTagName("SearchResults:searchresults").item(0);
        Node response = searchResults.getLastChild();
        NodeList results = response.getChildNodes();



        for (int i = 0; i < results.getLength(); i++)
        {
            Node result = results.item(i);
            answer = result.getFirstChild() .getTextContent();
        }


        return ok("Got it: " + answer);

    }


*/

}



