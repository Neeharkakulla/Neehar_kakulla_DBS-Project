import { Routes } from '@angular/router';
import { UserRegistrationComponent } from '../user-registration/user-registration.component';
import { UserHomeComponent } from '../user-home/user-home.component';
import { UserLoginComponent } from '../user-login/user-login.component';
export const routes: Routes = [
    {
        path:'',
        component: UserLoginComponent
    },
    {
        path:'home',
        component:UserHomeComponent
    },
    {
        path:'user-registration',
        component: UserRegistrationComponent,
        pathMatch:'full'
    },
    {
        path:'login',
        component:UserLoginComponent,
        pathMatch:'full'
    }
];
