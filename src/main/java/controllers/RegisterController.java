package controllers;

//import javax.persistence.EntityManager;

//import models.User;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
//import ninja.session.Session;
import ninja.validation.Validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
//import com.google.inject.Provider;
//import com.google.inject.persist.Transactional;


import dao.UserDao;


@Singleton
public class RegisterController{
	
	 @Inject
	    UserDao userDao;
	    
	    
	    ///////////////////////////////////////////////////////////////////////////
	    // Register
	    ///////////////////////////////////////////////////////////////////////////
	    public Result register(Context context) {

	        return Results.html();

	    }

	    public Result registerUser(@Param("username") String username,
	                            @Param("password") String password,
	                            @Param("fullname") String fullname,
	                            Context context,
	                            Validation validation) {

	        boolean isUserExist = userDao.userCheck(username);

	        if (isUserExist || validation.hasViolations()) {
	           
	            context.getFlashScope().error("UserExist");

	            return Results.redirect("/register");

	        } else {

	            // something is wrong with the input or password not found.;
//	        	
	        	if(username.length() >5 && password.length() >= 5 && fullname.length() >=3) {
		        	userDao.addStudent(username, fullname, password);
	//	            context.getFlashScope().put("username", username);
	//	            context.getFlashScope().put("rememberMe", String.valueOf(rememberMe));
		            context.getFlashScope().success("Congratulations! Registration Successful");

		            return Results.redirect("/login");
	        	}
	        	else {
	        		if(username.length()<=5)
	        			context.getFlashScope().error("username length should be greater than 5 ");
	        		else if(password.length()<5 ) {
	        			context.getFlashScope().error("password length should be greater than or equal to 5 ");
	        		}else if(fullname.length()<3) {
	        			context.getFlashScope().error("fullname length should be greater than or equal to 3 ");
	        		}

		            return Results.redirect("/register");
	        	}

	        }

	    }

	
}

