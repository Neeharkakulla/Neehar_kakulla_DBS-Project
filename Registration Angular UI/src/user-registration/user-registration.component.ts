import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../app/user.service';
import { CommonModule } from '@angular/common';
import { UserLoginComponent } from '../user-login/user-login.component';

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule,UserLoginComponent],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.css'
})
export class UserRegistrationComponent {
  registerForm!: FormGroup;
  errorMsg!:any;
  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email, Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      username: ['', [Validators.required, Validators.required]],
      password: ['', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")]],
    });
  }
  submit(){
    this.userService.registerUser(this.registerForm.value).subscribe({
      next:(data)=>{
        console.log("DATA ::",data);
        this.router.navigateByUrl('/login');
      },
      error:(err)=>{this.errorMsg=err?.error?.error}
    })
  }
}
