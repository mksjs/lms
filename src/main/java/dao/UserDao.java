
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

package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.Book;
import models.User;
import models.UsersDto;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import javax.persistence.FlushModeType;


public class UserDao {
    
    @Inject
    Provider<EntityManager> entityManagerProvider;
    
    @UnitOfWork
    public boolean isUserAndPasswordValid(String username, String password) {
        
        if (username != null && password != null) {
            
            EntityManager entityManager = entityManagerProvider.get();
            
            TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
            User user = getSingleResult(q.setParameter("usernameParam", username));

            if (user != null) {
                
                if (user.password.equals(password)) {

                    return true;
                }
                
            }

        }
        
        return false;
 
    }
    @Transactional
    public UsersDto getAllUsers() {
    	EntityManager entityManager = entityManagerProvider.get();
    	
    	TypedQuery<User> U = entityManager.createQuery("SELECT x FROM User x  ",User.class);
    	List<User> user = U.getResultList();
    	UsersDto usersDto = new UsersDto();
    	usersDto.users = user;
    	return usersDto;
     }
    
    @Transactional
    public UsersDto getAllAdminUsers() {
    	EntityManager entityManager = entityManagerProvider.get();
    	
    	TypedQuery<User> U = entityManager.createQuery("SELECT x FROM User x where x.isAdmin=true ",User.class);
    	List<User> user = U.getResultList();
    	UsersDto usersDto = new UsersDto();
    	usersDto.users = user;
    	return usersDto;
     }
    
    @Transactional
    public UsersDto getAllNormalUsers() {
    	EntityManager entityManager = entityManagerProvider.get();
    	
    	TypedQuery<User> U = entityManager.createQuery("SELECT x FROM User x where x.isAdmin=false ",User.class);
    	List<User> user = U.getResultList();
    	UsersDto usersDto = new UsersDto();
    	usersDto.users = user;
    	return usersDto;
     }
    @Transactional
    public User getAnyUserById(Long id) {
    	EntityManager entityManager = entityManagerProvider.get();
    	
    	TypedQuery<User> U = entityManager.createQuery("SELECT x FROM User x where x.id= :idParam ",User.class);
    	User user = U.setParameter("idParam", id).getSingleResult();
    	
    	return user;
     }
    
    
    @Transactional
    public User getAdminUserById( String username) {
		// TODO Auto-generated method stub
    	EntityManager entityManager = entityManagerProvider.get();
    	User user =null;
    	try {
    		TypedQuery<User> U = entityManager.createQuery("SELECT x FROM User x WHERE x.username = :usernameParam  and x.isAdmin = true ",User.class);
    		 user = U.setParameter("usernameParam", username).getSingleResult();
    	}catch(NoResultException nre) {
    		
    	}
    	return user;
		
	}
    
    
    @Transactional
    public User getNormalUserById(String username) {
		// TODO Auto-generated method stub
    	
    	EntityManager entityManager = entityManagerProvider.get();
    	User user =null;
    	try {
    		TypedQuery<User> U = entityManager.createQuery("SELECT x FROM User x WHERE x.username = :usernameParam and x.isAdmin = false ",User.class);
    		 user = U.setParameter("usernameParam", username).getSingleResult();
    	}catch(NoResultException nre) {
    		
    	}
    	return user;
	}
//    @UnitOfWork
//    public User getUser(String username) {
//        
//       
//    	EntityManager entityManager = entityManagerProvider.get();
//        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE x.username = :usernameParam", User.class);
//        User user = q.setParameter("usernameParam", username).getSingleResult();        
//        System.out.println("userdao"+user.username);
//        return user;
//        
//        
//    }
    @Transactional
    public boolean userCheck(String username) {
    	EntityManager entityManager = entityManagerProvider.get();
    	TypedQuery<User> q = entityManager.createQuery("SELECT x from User x where username = :usernameParam  ", User.class);
    	User user=null;
    	try {
    		user = q.setParameter("usernameParam", username).getSingleResult();
    	}
    	catch(NoResultException nre) {
    		
    	}
    	if(user==null) {
    		return false;
    	}
    	return true;
    			
    }
    @Transactional
	public boolean addStudent(String username, String fullname, String password) {
		EntityManager entityManager = entityManagerProvider.get();
		boolean isPresent = userCheck(username);
		if(isPresent ==false ) {
			User user  = new User(username,password, fullname);
			entityManager.persist(user);
			
			entityManager.setFlushMode(FlushModeType.COMMIT);
           
			return true;
		}
		return false;
		
	}
    

    /**
     * Get single result without throwing NoResultException, you can of course just catch the
     * exception and return null, it's up to you.
     */
    private static <T> T getSingleResult(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
	
	

}
