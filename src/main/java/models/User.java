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

package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import controllers.UserApiController.View;



@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    //public Long id;
    //public String username;
    //public String password;
    //public String fullname;
    //public boolean isAdmin=false;
    
    @JsonView(View.Public.class)
	public Long id;
	@JsonView(View.Public.class)
	public String username;
	@JsonView(View.Private.class)
	public String password;
	@JsonView(View.Public.class)
	public String fullname;
    @JsonView(View.Private.class)
    public boolean isAdmin =false;
    public User() {}
    
    public User(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }
    public User(String username, String password, String fullname,boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.isAdmin =isAdmin;
    }
 
}
