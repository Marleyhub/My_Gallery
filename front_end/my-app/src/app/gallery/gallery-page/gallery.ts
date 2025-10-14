import { Component, OnInit } from '@angular/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ToolBar } from '../../tool-bar/tool-bar';

@Component({
  selector: 'gallery-page',
  styleUrl: 'gallery.scss',
  templateUrl: 'gallery.html',
  standalone: true,
  imports: [CommonModule, MatGridListModule, ToolBar],
})
export class GalleryPage implements OnInit {
  images: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadUserImages();
  }
  private loadUserImages(): void {
    // saved at outh into local storage rigth after login
    const token = localStorage.getItem('token');

    if (!token) {
      console.error('No token found — user must log in first.');
      return;
    }
    // fetch images from server
    this.http.get<string[]>('http://localhost:8080/users/images', {
      headers: { Authorization: `Bearer ${token}` }
    }).subscribe({
      next: (images) => {
        this.images = images;
        console.log('Loaded images:', images);
      },
      error: (err) => {
        console.error('Error loading images:', err);
      }
    });
  }
}
