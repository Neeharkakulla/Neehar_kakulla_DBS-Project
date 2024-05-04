import { Component } from '@angular/core';
import { NavigationStart, Router, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';
import { UserService } from './user.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule,FormsModule,ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'RegistrationApp';

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe((event: any) => {
        if (event.navigationTrigger === 'imperative' || event.navigationTrigger === 'popstate') {
          this.onPageRefresh();
        }
      });
  }

  onPageRefresh() {
    this.userService.setisAuthUser(false);
  }
}
