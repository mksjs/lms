

package controllers;

import java.util.List;
import java.util.Map;

import models.Book;
import ninja.Result;
import ninja.Results;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import dao.BookTo;
import dao.SetupDao;

public class BookApplicationController {

    @Inject
    BookTo bookTo;

    @Inject
    SetupDao setupDao;

    public BookApplicationController() {

    }

    /**
     * Method to put initial data in the db...
     * 
     * @return
     */
    public Result setup() {

        setupDao.setup();

        return Results.ok();

    }

    public Result index() {

        Book frontBook = bookTo.getFirstBookForFrontPage();

        List<Book> olderBooks = bookTo.getOlderBooksForFrontPage();

        Map<String, Object> map = Maps.newHashMap();
        map.put("frontBooke", frontBook);
        map.put("olderBooks", olderBooks);

        return Results.html().render("frontBook", frontBook)
                .render("olderBooks", olderBooks);

    }
}
