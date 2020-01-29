package controllers;

import com.google.inject.Inject;


import dao.UserDao;
import etc.LoggedInUser;
import models.User;
import models.UsersDto;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

public class UserApiController {
	@Inject
	UserDao userDao;
	
	public Result getAllUserJson() {
		UsersDto usersDto =  userDao.getAllUsers();
		
		return Results.json().jsonView(View.Public.class).render(usersDto);
	}
	
	
	
	public Result getAllAdminUser() {
		UsersDto usersDto =userDao.getAllAdminUsers();
		return Results.json().jsonView(View.Public.class).render(usersDto);
	}
	
	
	
	public Result getAllNormalUserJson() {
		UsersDto usersDto =userDao.getAllNormalUsers();
		return Results.json().jsonView(View.Public.class).render(usersDto);
	}
	
	public Result getAnyUserByIdJson(@PathParam("id") Long id) {
		User user =  userDao.getAnyUserById(id);
		
		return Results.json().jsonView(View.Public.class).render(user);
	}
	
	public Result getAdminUserByIdJson(@PathParam("username") String username) {
		
		User user =  userDao.getAdminUserById(username);
		System.out.println(user);
		if(user==null) {			
	            return Results.notFound();
		}
		return Results.json().jsonView(View.Public.class).render(user);
	}

	public Result getNormalUserByIdJson(@PathParam("username") String username) {
		
		User user =  userDao.getNormalUserById(username);
		System.out.println(user);
		if(user==null) {			
	            return Results.notFound();
		}
		return Results.json().jsonView(View.Public.class).render(user);
	}
	
	
	public static class View {
		public static class Public {};
		public static class Private {};
	}

	
}
