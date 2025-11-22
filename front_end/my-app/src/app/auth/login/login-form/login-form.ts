import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../../auth';
import { RouterModule, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.html',
  standalone: true,
  styleUrls: ['./login-form.scss'],
  imports: [FormsModule,
            CommonModule,
            RouterModule,
            RouterLink
          ]
})
export class LoginFormComponent {
  images: string[] = [];

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}

  onSubmit(form: NgForm) {
    console.log(form.value);

    if (form.valid) {
      const loginPayload = {
        email: form.value.email,
        password: form.value.password
      };

      // login
      this.http.post<any>('https://my-gallery-fe8414be2560.herokuapp.com/auth/login', loginPayload).subscribe({
        next: (response) => {
          console.log('Login success', response);

          // Save sensitive data into auth
          this.authService.setAuthData(response.user, response.token)
          this.router.navigate(['/gallery']);
        },
        error: (err) => {
          console.error('Login failed:', err);
          alert('Login failed. Please check your email or password.');
        }
      });
    } else {
      console.log('Form invalid');
    }
  }
}
