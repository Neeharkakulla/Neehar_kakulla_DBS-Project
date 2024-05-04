import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../app/user.service';
import { UserLoginComponent } from '../user-login/user-login.component';

@Component({
  selector: 'app-user-home',
  standalone: true,
  imports: [UserLoginComponent],
  templateUrl: './user-home.component.html',
  styleUrl: './user-home.component.css'
})
export class UserHomeComponent {
    constructor(private router:Router,private userService:UserService){}
    userName:string="";
    ngOnInit(){
     
      // if(!this.userService.isAuthenticatedUser())
      //   this.router.navigateByUrl('/login');
      this.userName=this.userService.getUserName();
      console.log(this.userService.isAuthenticatedUser(),this.userName)
    }
}
