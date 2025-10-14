import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../auth/auth';

@Component({
  selector: 'app-tool-bar',
  imports: [MatToolbarModule, MatIconModule, MatButtonModule ],
  templateUrl: './tool-bar.html',
  styleUrl: './tool-bar.scss'
})
export class ToolBar {
  constructor(private http: HttpClient, private authService: AuthService) {}

  onFileSelected(event: Event): void {
  const input = event.target as HTMLInputElement;
  if (!input.files?.length) {
    return;
  }
  // user id from this call
  const user = this.authService.getUser();
  const userId = user?.id;

  const file = input.files[0];

  // Form
  const formData = new FormData();
  formData.append('file', file);
  formData.append('userId', userId);

   this.http.post<{ url: string}>('http://localhost:8080/users/upload', formData).subscribe({
        next: (response) => {
          console.log("File uploaded successfully:", response.url);
        },
        error: (err) => {
          console.error('Upload Faild', err)
        }
      });
  }
}
