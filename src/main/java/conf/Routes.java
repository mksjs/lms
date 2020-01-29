/**
 * Copyright (C) 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import org.hibernate.property.IndexPropertyAccessor;

import com.google.inject.Inject;

//import controllers.ApiController;
//import controllers.ApplicationController;
import controllers.BookApiController;
import controllers.BookApplicationController;
import controllers.BookController;
//import controllers.ArticleController;
import controllers.LoginLogoutController;
import controllers.RegisterController;
import controllers.UserApiController;

public class Routes implements ApplicationRoutes {
    
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     * 
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     * 
     * @param router
     *            The default router of this application
     */
    @Override
    public void init(Router router) {  
        
        // puts test data into db:
//        if (!ninjaProperties.isProd()) {
//            router.GET().route("/setup").with(ApplicationController::setup);
//        }
    	if (!ninjaProperties.isProd()) {
            router.GET().route("/setup").with(BookApplicationController::setup);
        }
        
        ///////////////////////////////////////////////////////////////////////
        // Login / Logout
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/register").with(RegisterController::register);
        router.POST().route("/register").with(RegisterController::registerUser);
        router.GET().route("/login").with(LoginLogoutController::login);
        router.POST().route("/login").with(LoginLogoutController::loginPost);
        router.GET().route("/logout").with(LoginLogoutController::logout);
        
        ///////////////////////////////////////////////////////////////////////
        // Add new book
        ///////////////////////////////////////////////////////////////////////
//        router.GET().route("/article/new").with(ArticleController::articleNew);
//        router.POST().route("/article/new").with(ArticleController::articleNewPost);
//        
        router.GET().route("/book/delete").with(BookController::deleteBook);
        router.GET().route("/book/new").with(BookController::addNewBook);
        router.POST().route("/book/new").with(BookController::bookNewPost);
        
        ///////////////////////////////////////////////////////////////////////
        // Add new book
        ///////////////////////////////////////////////////////////////////////
        //router.GET().route("/article/{id}").with(ArticleController::articleShow);
        router.GET().route("/book/{id}").with(BookController::bookShow);
        
		///////////////////////////////////////////////////////////////////////
		// delete  book
		///////////////////////////////////////////////////////////////////////
		
		
		router.POST().route("/book/delete/{id}").with(BookController::deleteBookById);
		
		router.DELETE().route("/api/deleteBook/{username}/{id}").with(BookApiController::deleteBookIdJson);
		///////////////////////////////////////////////////////////////////////
		// delete  book
		///////////////////////////////////////////////////////////////////////
		
		//router.GET().route("/book/update/{id}").with(BookController::updateBook);
		//router.POST().route("/book/update/{id}").with(BookController::updateBookById);
        ///////////////////////////////////////////////////////////////////////
        // Api for management of software
        ///////////////////////////////////////////////////////////////////////
//        router.GET().route("/api/{username}/articles.json").with(ApiController::getArticlesJson);
//        router.GET().route("/api/{username}/article/{id}.json").with(ApiController::getArticleJson);
//        router.GET().route("/api/{username}/articles.xml").with(ApiController::getArticlesXml);
//        router.POST().route("/api/{username}/article.json").with(ApiController::postArticleJson);
//        router.POST().route("/api/{username}/article.xml").with(ApiController::postArticleXml);
		
		//router.GET().route("/api/books.json").with(BookApiController::getBooksNotLogJson);
		router.GET().route("/api/books").with(BookApiController::getBooksJson);
        router.GET().route("/api/book/{id}").with(BookApiController::getBookJson);
        router.GET().route("/api/Allbook/{username}").with(BookApiController:: getAllBooksLoggedInJson);
        router.GET().route("/api/Allbook/{id}").with(BookApiController::getAllBookJson);
        router.GET().route("/api/getFistN/{NBook}").with(BookApiController::getNBookJson);
        router.GET().route("/api/pagination/{page_id}/{nBook}").with(BookApiController::pagination);
//        router.GET().route("/api/{username}/books.xml").with(BookApiController::getBooksXml);
//        router.POST().route("/api/{username}/book.json").with(BookApiController::postBookJson);
//        router.POST().route("/api/{username}/book.xml").with(BookApiController::postBookXml);
        router.POST().route("/api/addBook/{username}/{title}/{authorName}").with(BookApiController::AddBookJson);
        router.PUT().route("/api/updateBookById/{username}/{id}/{title}/{authorName}").with(BookApiController::updataBookJson);
        //router.POST().route("/api/{username}/book.json").with(BookApiController::deleteBookJson);
//        router.POST().route("/api/{username}/book.xml").with(BookApiController::deleteBookXml);
 
        /////////////////
        ////User json
        //////////////// 
      
        router.GET().route("/api/AllUsers").with(UserApiController::getAllUserJson);
        router.GET().route("/api/AllAdminUsers").with(UserApiController::getAllAdminUser);
        router.GET().route("/api/AllNormalUsers").with(UserApiController::getAllNormalUserJson);
        router.GET().route("/api/user/{id}").with(UserApiController::getAnyUserByIdJson);
        router.GET().route("/api/AdminUser/{username}").with(UserApiController::getAdminUserByIdJson);
        router.GET().route("/api/normalUser/{username}").with(UserApiController::getNormalUserByIdJson);
        
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(BookApplicationController::index);
       // router.GET().route("/index/{page_id}").with(BookController::index1);
    }

}
