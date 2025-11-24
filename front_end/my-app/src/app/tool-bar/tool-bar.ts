import { Component, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../auth/auth';

@Component({
  selector: 'app-tool-bar',
  imports: [MatToolbarModule, MatIconModule, MatButtonModule],
  templateUrl: './tool-bar.html',
  styleUrl: './tool-bar.scss'
})
export class ToolBar {
  @Output() uploaded = new EventEmitter<void>();

  constructor(private http: HttpClient, private authService: AuthService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;

    const user = this.authService.getUser();
    const userId = user?.id;
    const token = localStorage.getItem('token');
    const file = input.files[0];

    const formData = new FormData();
    formData.append('file', file);

    this.http.post<{ url: string }>(
      'https://my-gallery-fe8414be2560.herokuapp.com/images',
      formData,
      {
        headers: { Authorization: `Bearer ${token}` }
      }
    ).subscribe({
      next: (response) => {
        console.log("File uploaded successfully:", response.url);
        this.uploaded.emit();
      },
      error: (err) => {
        console.error('Upload Failed:', err);
      }
    });
  }
}
