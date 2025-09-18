import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  standalone: true,    
  styleUrls: ['./login.scss'],
  imports: [FormsModule],      
})
export class LoginComponent {
  
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
