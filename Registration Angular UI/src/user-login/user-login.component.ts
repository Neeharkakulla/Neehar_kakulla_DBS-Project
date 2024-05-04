import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../app/user.service';
import { Router } from '@angular/router';
import { UserHomeComponent } from '../user-home/user-home.component';

@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule,UserHomeComponent],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {
  loginForm!:FormGroup;
  errorMsg!:any;

  constructor(private formBuilder: FormBuilder,private router:Router,private userService:UserService){}
    ngOnInit(){
      if(this.userService.isAuthenticatedUser())
        this.router.navigateByUrl('/home')
      this.loginForm = this.formBuilder.group({
        username: ['', [Validators.required, Validators.required]],
        password: ['', [Validators.required, Validators.minLength(8),Validators.required]],
      });
    }

    login(){
      let loginDetails=this.loginForm.value;
      
      this.userService.login(loginDetails.username,loginDetails.password).subscribe({
        next:(data:any)=>{
          this.userService.setisAuthUser(true);
          this.userService.setUserName(data?.username);
          console.log(this.userService.isAuthenticatedUser())
          this.router.navigateByUrl('/home')
        },
        error:(err)=>{this.errorMsg=err?.error}
      })
    }
}
