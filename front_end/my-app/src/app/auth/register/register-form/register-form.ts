import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './register-form.html',
  styleUrls: ['./register-form.scss'],
})
export class RegisterFormComponent {
  isLoading = false;
  private apiUrl = 'http://localhost:8080/users/create';

  constructor(private http: HttpClient, private router: Router) {}

  onRegister(form: NgForm) {
    if (form.invalid) return;

    const { email, password } = form.value;
    this.isLoading = true;

    this.http.post(this.apiUrl, { email, password }).subscribe({
      next: (res) => {
        console.log('✅ User created:', res);
        this.isLoading = false;
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('❌ Error:', err);
        this.isLoading = false;
        alert('Failed to register. Please try again.');
      },
    });
  }
}
