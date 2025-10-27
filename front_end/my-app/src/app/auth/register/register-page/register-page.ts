import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'
import { RegisterFormComponent } from '../register-form/register-form';


@Component({
  selector: 'app-login',
  templateUrl: './register-page.html',
  standalone: true,    
  styleUrls: ['./register-page.scss'],
  imports: [FormsModule, CommonModule, RegisterFormComponent]  
})
export class RegisterPageComponent {
  // This method will be called when the form is submitted
  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form submitted!', form.value);
    } else {
      console.log('Form invalid');
    }
  }
}
