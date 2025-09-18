import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.html',
  standalone: true,    
  styleUrls: ['./login-form.scss'],
  imports: [FormsModule, CommonModule]  
})
export class LoginFormComponent {
  // This method will be called when the form is submitted
  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form submitted!', form.value);
      // 👉 here you can call your auth service
    } else {
      console.log('Form invalid');
    }
  }
}
