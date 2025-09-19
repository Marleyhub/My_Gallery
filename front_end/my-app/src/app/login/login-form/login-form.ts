import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.html',
  standalone: true,    
  styleUrls: ['./login-form.scss'],
  imports: [FormsModule, CommonModule]  
})
export class LoginFormComponent {
  constructor(protected http: HttpClient) {}

  onSubmit(form: NgForm) {
    console.log(form.value)
    if (form.valid) {
      const loginPayload = {
        userId: form.value.email,
        password: form.value.password
      };

      this.http.post('http://localhost:8080/api/login', loginPayload).subscribe({
        next: (response) => {
          console.log('Login success', response)
        },
        error: (err) => {
          console.log('Login error', err)
        }
      });
    } else {
      console.log('Form invalid');
    }
  }
}
