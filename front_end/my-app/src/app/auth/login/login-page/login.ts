import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'
import { LoginFormComponent } from '../login-form/login-form';


@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  standalone: true,    
  styleUrls: ['./login.scss'],
  imports: [FormsModule, CommonModule, LoginFormComponent]  
})
export class LoginPage {
  // This method will be called when the form is submitted
  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form submitted!', form.value);
    } else {
      console.log('Form invalid');
    }
  }
}
