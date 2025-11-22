import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../../auth';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './register-form.html',
  styleUrls: ['./register-form.scss'],
})
export class RegisterFormComponent {
  isLoading = false;
  message = '';
  private apiUrl = 'https://my-gallery-fe8414be2560.herokuapp.com/users';

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  onRegister(form: NgForm) {
    if (form.invalid) return;

    const { email, password } = form.value;
    this.isLoading = true;
    this.message = '';

    this.http.post<any>(this.apiUrl, { email, password }).subscribe({
      next: (res) => {
        console.log('Server response:', res);

        if (res.token && res.user) {
          this.authService.setAuthData(res.user, res.token);
        }

        this.isLoading = false;
        this.message = res.message || 'User created successfully!';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000)
      },
      error: (err) => {
        console.error('❌ Error:', err);
        this.isLoading = false;
        
         if (err.status === 400) {
          this.message = 'Invalid data. Please check your input.';
        } else if (err.status === 409) {
          this.message = 'User already exists.';
        } else {
          this.message = 'Failed to register. Please try again.';
        }
      },
    });
  }
}
