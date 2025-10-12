import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.html',
  standalone: true,
  styleUrls: ['./login-form.scss'],
  imports: [FormsModule, CommonModule]
})
export class LoginFormComponent {
  images: string[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit(form: NgForm) {
    console.log(form.value);

    if (form.valid) {
      const loginPayload = {
        email: form.value.email,
        password: form.value.password
      };

      // login
      this.http.post<any>('http://localhost:8080/auth/login', loginPayload).subscribe({
        next: (response) => {
          console.log('Login success', response);

          // Save token locally
          localStorage.setItem('token', response.token);

          // fetch images after login
          this.loadUserImages();

          // navigate (optional — after fetching)
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

  // separate helper method
  private loadUserImages() {
    const token = localStorage.getItem('token');

    if (!token) {
      console.error('No token found — user must log in first.');
      return;
    }

    this.http.get<string[]>('http://localhost:8080/images', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }).subscribe({
      next: (images) => {
        this.images = images;
        console.log('Fetched images:', images);
      },
      error: (err) => {
        console.error('Error fetching images:', err);
      }
    });
  }
}
