import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  
  private isAuthUser: boolean=false;
  private userName:string="";
  private BASE_URL="http://localhost:8080"
  constructor(private http:HttpClient) { }

  isAuthenticatedUser(): boolean {
    return this.isAuthUser;
  }
  setisAuthUser(isAuthUser: boolean) {
    this.isAuthUser = isAuthUser;
  }

  setUserName(userName:string){
    this.userName=userName;
  }
  getUserName(){
    return this.userName;
  }

  login(username:any,password:any) {
    return this.http.post(this.BASE_URL+"/login?username="+username+"&password="+password,null)
  }

  registerUser(user: any) {
    return this.http.post(this.BASE_URL+"/api/v1/dbs/registration",user);
  }
}
