import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-tool-bar',
  imports: [MatToolbarModule, MatIconModule, MatButtonModule ],
  templateUrl: './tool-bar.html',
  styleUrl: './tool-bar.scss'
})
export class ToolBar {
  constructor(private http: HttpClient) {}

  onFileSelected(event: Event): void {
  const input = event.target as HTMLInputElement;
  if (!input.files?.length) {
    return;
  }
  const file = input.files[0];
  const formData = new FormData();
  formData.append('file', file);

   this.http.post<{ url: string}>('http://localhost:8080/uploads/upload', formData).subscribe({
        next: (response) => {
          console.log("File uploaded successfully:", response.url);
        },
        error: (err) => {
          console.error('Upload Faild', err)
        }
      });
}


}
